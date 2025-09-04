package com.juaracoding.pcmspringboot5.service;

import com.juaracoding.pcmspringboot5.core.IReport;
import com.juaracoding.pcmspringboot5.core.IService;
import com.juaracoding.pcmspringboot5.dto.relation.RelMenuDTO;
import com.juaracoding.pcmspringboot5.dto.resp.RespAksesDTO;
import com.juaracoding.pcmspringboot5.dto.val.ValAksesDTO;
import com.juaracoding.pcmspringboot5.model.Akses;
import com.juaracoding.pcmspringboot5.model.Menu;
import com.juaracoding.pcmspringboot5.repo.AksesRepo;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *  Platform Code : TRN
 *  Modul Code : 03
 *  FV : Failed Validation - 001-010
 *  FE : Failed Error - 001-010
 */
@Service
@Transactional
public class AksesService implements IService<Akses>, IReport<Akses> {

    @Autowired
    private AksesRepo aksesRepo;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private PdfGenerator pdfGenerator;

    private StringBuilder sBuild = new StringBuilder();

    public ResponseEntity<Object> save(Akses akses, HttpServletRequest request){
        try {
            if(akses==null){
                return new GlobalResponse().tidakDapatDiproses("TRN03FV001");
            }
            if(akses.getId()!=null){
                return new GlobalResponse().tidakDapatDiproses("TRN03FV002");
            }
            aksesRepo.save(akses);
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN03FE001");
        }

        return new GlobalResponse().dataBerhasilDisimpan();
    }

    public ResponseEntity<Object> update(Long id,Akses akses, HttpServletRequest request){
        try {
            Optional<Akses> op = aksesRepo.findById(id);
            if(!op.isPresent()){
                return new GlobalResponse().dataTidakDitemukan("TRN03FV011");
            }
            Akses aksesDB = op.get();
            aksesDB.setNama(akses.getNama());
            aksesDB.setMenuList(akses.getMenuList());
            aksesDB.setModifiedBy(1L);
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN03FE011");
        }
        return new GlobalResponse().dataBerhasilDiubah();
    }

    public ResponseEntity<Object> delete(Long id, HttpServletRequest request){
        try{
            Optional<Akses> op = aksesRepo.findById(id);
            if(!op.isPresent()){
                return new GlobalResponse().dataTidakDitemukan("TRN03FV021");
            }
            aksesRepo.deleteById(id);
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN03FE021");
        }
        return new GlobalResponse().dataBerhasilDihapus();
    }

    public ResponseEntity<Object> findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        Page<Akses> page = null;
        Map<String,Object> mapResponse = null;

        try{
            page = aksesRepo.findAll(pageable);
            if(page.isEmpty()){
                return new GlobalResponse().dataTidakDitemukan("TRN03FV031");
            }
            mapResponse = new TransformPagination().
                    transformPagination(
                            entityToDTO(page.getContent()),
                            page,
                            "id",
                            ""
                    );
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN03FE031");
        }
        return new GlobalResponse().dataDitemukan( mapResponse );
    }

    public ResponseEntity<Object> findByParam(Pageable pageable,String column, String value, HttpServletRequest request){
        Page<Akses> page = null;
        Boolean isFound = true;
        Map<String,Object> mapResponse = null;
        try{
            switch (column){
                case "nama" : page = aksesRepo.findByNamaContains(pageable,value);break;
                case "id" : page = aksesRepo.findAll(pageable);break;
                default : isFound = false;
            }
            /** tidak ditemukan karena pengiriman flag nama kolom tidak sesuai */
            if(!isFound){
                return new GlobalResponse().dataTidakDitemukan("TRN03FV044");
            }
            /** tidak ditemukan sesudah dilakukan query */
            if(page.isEmpty()){
                return new GlobalResponse().dataTidakDitemukan("TRN03FV045");
            }
            mapResponse = new TransformPagination().
                    transformPagination(
                            entityToDTO(page.getContent()),
                            page,
                            column,
                            value
                    );
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN03FE041");
        }
        return new GlobalResponse().dataDitemukan( mapResponse );
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        Akses aksesDB = null;
        try {
            Optional<Akses> op = aksesRepo.findById(id);
            if(!op.isPresent()){
                return new GlobalResponse().dataTidakDitemukan("TRN03FV051");
            }
            aksesDB = op.get();
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN03FE051");
        }
        return new GlobalResponse().dataDitemukan(entityToDTO(aksesDB));
    }

    @Override
    public ResponseEntity<Object> uploadExcel(MultipartFile file, HttpServletRequest request) {
        String message = "";
        try {
            if(!ExcelReader.hasWorkBookFormat(file)){
                return new GlobalResponse().formatFileHarusExcel("TRN03FV101");
            }
            List lt = new ExcelReader(file.getInputStream(),"akses").getDataMap();
            if(lt.isEmpty()){
                return new GlobalResponse().fileExcelKosong("TRN03FV102");
            }
            aksesRepo.saveAll(convertListWorkBookToListEntity(lt,1L));
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN03FE101");
        }
        return new GlobalResponse().dataBerhasilDisimpan();
    }

    @Override
    public List<Akses> convertListWorkBookToListEntity(List<Map<String, String>> workBookData, Long userId) {
        List<Akses> list = new ArrayList<>();
        for (Map<String, String> map : workBookData) {
            Akses akses = new Akses();
            akses.setNama(map.get("NAMA AKSES"));
            akses.setCreatedBy(userId);
            list.add(akses);
        }
        return list;
    }

    @Override
    public Object downloadReportExcel(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<Akses> list = null;
        Boolean isFound = true;
        Map<String,Object> mapResponse = null;
        try {
            switch (column){
                case "nama" : list = aksesRepo.findByNamaContains(value);break;
                case "id" : list = aksesRepo.findAll();break;
                default : isFound = false;
            }
            /** tidak ditemukan karena pengiriman flag nama kolom tidak sesuai */
            if(!isFound){
                return new GlobalResponse().dataTidakDitemukan("TRN03FV111");
            }
            /** tidak ditemukan sesudah dilakukan query */
            if(list.isEmpty()){
                return new GlobalResponse().dataTidakDitemukan("TRN03FV112");
            }
            List<RespAksesDTO> listDTO = entityToDTO(list);
            new MappingReport().mappingReportExcel(listDTO,"akses",new RespAksesDTO(),response);
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN03FE111");
        }
        return "";
    }

    @Override
    public Object downloadReportPDF(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<Akses> list = null;
        Boolean isFound = true;
        Map<String,Object> mapResponse = null;
        try {
            switch (column){
                case "nama" : list = aksesRepo.findByNamaContains(value);break;
                case "id" : list = aksesRepo.findAll();break;
                default : isFound = false;
            }
            /** tidak ditemukan karena pengiriman flag nama kolom tidak sesuai */
            if(!isFound){
                return new GlobalResponse().dataTidakDitemukan("TRN03FV111");
            }
            /** tidak ditemukan sesudah dilakukan query */
            if(list.isEmpty()){
                return new GlobalResponse().dataTidakDitemukan("TRN03FV112");
            }
            List<RespAksesDTO> listDTO = entityToDTO(list);
            new MappingReport().mappingReportPDF(
                    listDTO,
                    "akses",
                    "MENU",
                    new RespAksesDTO(),
                    springTemplateEngine,
                    pdfGenerator,
                    "Paul",
                    response);
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN03FE111");
        }
        return "";
    }

    public Akses mapToEntity(ValAksesDTO valAksesDTO){
        Akses akses = new Akses();
        akses.setNama(valAksesDTO.getNama());
        List<RelMenuDTO> listDTO = valAksesDTO.getMenuList();
        List<Menu> listMenu = new ArrayList<>();
        for(RelMenuDTO relMenuDTO : listDTO){
            Menu menu = new Menu();
            menu.setId(relMenuDTO.getId());
            listMenu.add(menu);
        }
        akses.setMenuList(listMenu);
        return akses;
    }

    public List<RespAksesDTO> entityToDTO(List<Akses> list){
        List<RespAksesDTO> listDTO = new ArrayList<>();
        for (Akses akses : list) {
            RespAksesDTO respAksesDTO = new RespAksesDTO();
            respAksesDTO.setNama(akses.getNama());
            respAksesDTO.setId(akses.getId());
            listDTO.add(respAksesDTO);
        }
        return listDTO;
    }

    public RespAksesDTO entityToDTO(Akses akses){
        RespAksesDTO respAksesDTO = new RespAksesDTO();
        respAksesDTO.setNama(akses.getNama());
        respAksesDTO.setId(akses.getId());

        return respAksesDTO;
    }
}