package com.juaracoding.pcmspringboot5.service;

import com.juaracoding.pcmspringboot5.core.IReport;
import com.juaracoding.pcmspringboot5.core.IService;
import com.juaracoding.pcmspringboot5.dto.resp.RespUserDTO;
import com.juaracoding.pcmspringboot5.dto.val.ValUserDTO;
import com.juaracoding.pcmspringboot5.model.User;
import com.juaracoding.pcmspringboot5.repo.UserRepo;
import com.juaracoding.pcmspringboot5.utils.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDate;
import java.util.*;

/**
 *  Platform Code : TRN
 *  Modul Code : 04
 *  FV : Failed Validation - 001-010
 *  FE : Failed Error - 001-010
 */
@Service
@Transactional
public class UserService implements IService<User>, IReport<User> {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private PdfGenerator pdfGenerator;

    private StringBuilder sBuild = new StringBuilder();

    public ResponseEntity<Object> save(User user, HttpServletRequest request){
        try {
            if(user==null){
                return new GlobalResponse().tidakDapatDiproses("TRN04FV001");
            }
            if(user.getId()!=null){
                return new GlobalResponse().tidakDapatDiproses("TRN04FV002");
            }
            userRepo.save(user);
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN04FE001");
        }

        return new GlobalResponse().dataBerhasilDisimpan();
    }

    public ResponseEntity<Object> update(Long id,User user, HttpServletRequest request){
        try {
            Optional<User> op = userRepo.findById(id);
            if(!op.isPresent()){
                return new GlobalResponse().dataTidakDitemukan("TRN04FV011");
            }
            User userDB = op.get();
            userDB.setNamaLengkap(user.getNamaLengkap());
            userDB.setUsername(user.getUsername());
            userDB.setPassword(user.getPassword());
            userDB.setEmail(user.getEmail());
            userDB.setNoHp(user.getNoHp());
            userDB.setModifiedBy(1L);
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN04FE011");
        }
        return new GlobalResponse().dataBerhasilDiubah();
    }

    public ResponseEntity<Object> delete(Long id, HttpServletRequest request){
        try{
            Optional<User> op = userRepo.findById(id);
            if(!op.isPresent()){
                return new GlobalResponse().dataTidakDitemukan("TRN04FV021");
            }
            userRepo.deleteById(id);
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN04FE021");
        }
        return new GlobalResponse().dataBerhasilDihapus();
    }

    public ResponseEntity<Object> findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        Page<User> page = null;
        Map<String,Object> mapResponse = null;

        try{
            page = userRepo.findAll(pageable);
            if(page.isEmpty()){
                return new GlobalResponse().dataTidakDitemukan("TRN04FV031");
            }
            mapResponse = new TransformPagination().
                    transformPagination(
                            entityToDTO(page.getContent()),
                            page,
                            "id",
                            ""
                    );
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN04FE031");
        }
        return new GlobalResponse().dataDitemukan( mapResponse );
    }

    public ResponseEntity<Object> findByParam(Pageable pageable,String column, String value, HttpServletRequest request){
        Page<User> page = null;
        Boolean isFound = true;
        Map<String,Object> mapResponse = null;
        try{
            switch (column){
                case "email" : page = userRepo.findByEmailContains(pageable,value);break;
                case "username" : page = userRepo.findByUsernameContains(pageable,value);break;
                case "nohp" : page = userRepo.findByNoHpContains(pageable,value);break;
                case "nama" : page = userRepo.findByNamaLengkapContains(pageable,value);break;
                case "id" : page = userRepo.findAll(pageable);break;
                default : isFound = false;
            }
            /** tidak ditemukan karena pengiriman flag nama kolom tidak sesuai */
            if(!isFound){
                return new GlobalResponse().dataTidakDitemukan("TRN04FV044");
            }
            /** tidak ditemukan sesudah dilakukan query */
            if(page.isEmpty()){
                return new GlobalResponse().dataTidakDitemukan("TRN04FV045");
            }
            mapResponse = new TransformPagination().
                    transformPagination(
                            entityToDTO(page.getContent()),
                            page,
                            "id",
                            ""
                    );
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN04FE041");
        }
        return new GlobalResponse().dataDitemukan( mapResponse );
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        User userDB = null;
        try {
            Optional<User> op = userRepo.findById(id);
            if(!op.isPresent()){
                return new GlobalResponse().dataTidakDitemukan("TRN04FV051");
            }
            userDB = op.get();
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN04FE051");
        }
        return new GlobalResponse().dataDitemukan(userDB);
    }

    @Override
    public ResponseEntity<Object> uploadExcel(MultipartFile file, HttpServletRequest request) {
        String message = "";
        try {
            if(!ExcelReader.hasWorkBookFormat(file)){
                return new GlobalResponse().formatFileHarusExcel("TRN04FV101");
            }
            List lt = new ExcelReader(file.getInputStream(),"user").getDataMap();
            if(lt.isEmpty()){
                return new GlobalResponse().fileExcelKosong("TRN04FV102");
            }
            userRepo.saveAll(convertListWorkBookToListEntity(lt,1L));
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN04FE101");
        }
        return new GlobalResponse().dataBerhasilDisimpan();
    }

    @Override
    public List<User> convertListWorkBookToListEntity(List<Map<String, String>> workBookData, Long userId) {
        List<User> list = new ArrayList<>();
        for (Map<String, String> map : workBookData) {
            User user = new User();
            user.setUsername(map.get("USERNAME"));
            user.setPassword(map.get("PASSWORD"));
            user.setNamaLengkap(map.get("NAMA"));
            user.setEmail(map.get("EMAIL"));
            user.setTanggalLahir(new Date(map.get("TANGGAL LAHIR")));
            user.setNoHp(map.get("NO HP"));
            user.setCreatedBy(userId);
            list.add(user);
        }
        return list;
    }

    @Override
    public Object downloadReportExcel(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<User> list = null;
        Boolean isFound = true;
        Map<String,Object> mapResponse = null;
        try {
            switch (column){
                case "email" : list = userRepo.findByEmailContains(value);break;
                case "username" : list = userRepo.findByUsernameContains(value);break;
                case "nohp" : list = userRepo.findByNoHpContains(value);break;
                case "nama" : list = userRepo.findByNamaLengkapContains(value);break;
                case "id" : list = userRepo.findAll();break;
                default : isFound = false;
            }
            /** tidak ditemukan karena pengiriman flag nama kolom tidak sesuai */
            if(!isFound){
                return new GlobalResponse().dataTidakDitemukan("TRN04FV111");
            }
            /** tidak ditemukan sesudah dilakukan query */
            if(list.isEmpty()){
                return new GlobalResponse().dataTidakDitemukan("TRN04FV112");
            }
            List<RespUserDTO> listDTO = entityToDTO(list);
            new MappingReport().mappingReportExcel(listDTO,"user",new RespUserDTO(),response);
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN04FE111");
        }
        return "";
    }

    @Override
    public Object downloadReportPDF(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<User> list = null;
        Boolean isFound = true;
        Map<String,Object> mapResponse = null;
        try {
            switch (column){
                case "email" : list = userRepo.findByEmailContains(value);break;
                case "username" : list = userRepo.findByUsernameContains(value);break;
                case "nohp" : list = userRepo.findByNoHpContains(value);break;
                case "nama" : list = userRepo.findByNamaLengkapContains(value);break;
                case "id" : list = userRepo.findAll();break;
                default : isFound = false;
            }
            /** tidak ditemukan karena pengiriman flag nama kolom tidak sesuai */
            if(!isFound){
                return new GlobalResponse().dataTidakDitemukan("TRN04FV111");
            }
            /** tidak ditemukan sesudah dilakukan query */
            if(list.isEmpty()){
                return new GlobalResponse().dataTidakDitemukan("TRN04FV112");
            }
            List<RespUserDTO> listDTO = entityToDTO(list);
            new MappingReport().mappingReportPDF(
                    listDTO,
                    "user",
                    "USER",
                    new RespUserDTO(),
                    springTemplateEngine,
                    pdfGenerator,
                    "Paul",
                    response);
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN04FE111");
        }
        return "";
    }

    public User mapToEntity(ValUserDTO valUserDTO){
        User user = new User();
        user.setNamaLengkap(valUserDTO.getNamaLengkap());
        user.setPassword(valUserDTO.getPassword());
        user.setNoHp(valUserDTO.getNoHp());
        user.setEmail(valUserDTO.getEmail());
        return user;
    }

    public List<RespUserDTO> entityToDTO(List<User> list){
        List<RespUserDTO> listDTO = new ArrayList<>();
        for (User user : list) {
            RespUserDTO respUserDTO = new RespUserDTO();
            respUserDTO.setNamaLengkap(user.getNamaLengkap());
            respUserDTO.setPassword(user.getPassword());
            respUserDTO.setId(user.getId());
            respUserDTO.setNoHp(user.getNoHp());
            respUserDTO.setEmail(user.getEmail());
            listDTO.add(respUserDTO);
        }
        return listDTO;
    }
}