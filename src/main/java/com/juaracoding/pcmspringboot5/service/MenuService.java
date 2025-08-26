package com.juaracoding.pcmspringboot5.service;

import com.juaracoding.pcmspringboot5.core.IReport;
import com.juaracoding.pcmspringboot5.core.IService;
import com.juaracoding.pcmspringboot5.dto.resp.RespMenuDTO;
import com.juaracoding.pcmspringboot5.dto.val.ValMenuDTO;
import com.juaracoding.pcmspringboot5.model.GroupMenu;
import com.juaracoding.pcmspringboot5.model.Menu;
import com.juaracoding.pcmspringboot5.repo.MenuRepo;
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
 *  Modul Code : 02
 *  FV : Failed Validation - 001-010
 *  FE : Failed Error - 001-010
 */
@Service
@Transactional
public class MenuService implements IService<Menu>, IReport<Menu> {

    @Autowired
    private MenuRepo menuRepo;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private PdfGenerator pdfGenerator;

    private StringBuilder sBuild = new StringBuilder();

    public ResponseEntity<Object> save(Menu menu, HttpServletRequest request){
        try {
            if(menu==null){
                return new GlobalResponse().tidakDapatDiproses("TRN02FV001");
            }
            if(menu.getId()!=null){
                return new GlobalResponse().tidakDapatDiproses("TRN02FV002");
            }
            menuRepo.save(menu);
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN02FE001");
        }

        return new GlobalResponse().dataBerhasilDisimpan();
    }

    public ResponseEntity<Object> update(Long id,Menu menu, HttpServletRequest request){
        try {
            Optional<Menu> op = menuRepo.findById(id);
            if(!op.isPresent()){
                return new GlobalResponse().dataTidakDitemukan("TRN02FV011");
            }
            Menu menuDB = op.get();
            menuDB.setNama(menu.getNama());
            menuDB.setPath(menu.getPath());
            menuDB.setGroupMenu(menu.getGroupMenu());
            menuDB.setModifiedBy(1L);
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN02FE011");
        }
        return new GlobalResponse().dataBerhasilDiubah();
    }

    public ResponseEntity<Object> delete(Long id, HttpServletRequest request){
        try{
            Optional<Menu> op = menuRepo.findById(id);
            if(!op.isPresent()){
                return new GlobalResponse().dataTidakDitemukan("TRN02FV021");
            }
            menuRepo.deleteById(id);
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN02FE021");
        }
        return new GlobalResponse().dataBerhasilDihapus();
    }

    public ResponseEntity<Object> findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        Page<Menu> page = null;
        Map<String,Object> mapResponse = null;

        try{
            page = menuRepo.findAll(pageable);
            if(page.isEmpty()){
                return new GlobalResponse().dataTidakDitemukan("TRN02FV031");
            }
            mapResponse = new TransformPagination().
                    transformPagination(
                            entityToDTO(page.getContent()),
                            page,
                            "id",
                            ""
                    );
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN02FE031");
        }
        return new GlobalResponse().dataDitemukan( mapResponse );
    }

    public ResponseEntity<Object> findByParam(Pageable pageable,String column, String value, HttpServletRequest request){
        Page<Menu> page = null;
        Boolean isFound = true;
        Map<String,Object> mapResponse = null;
        try{
            switch (column){
                case "path" : page = menuRepo.findByPathContains(pageable,value);break;
                case "nama" : page = menuRepo.findByNamaContains(pageable,value);break;
                case "id" : page = menuRepo.findAll(pageable);break;
                default : isFound = false;
            }
            /** tidak ditemukan karena pengiriman flag nama kolom tidak sesuai */
            if(!isFound){
                return new GlobalResponse().dataTidakDitemukan("TRN02FV044");
            }
            /** tidak ditemukan sesudah dilakukan query */
            if(page.isEmpty()){
                return new GlobalResponse().dataTidakDitemukan("TRN02FV045");
            }
            mapResponse = new TransformPagination().
                    transformPagination(
                            entityToDTO(page.getContent()),
                            page,
                            "id",
                            ""
                    );
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN02FE041");
        }
        return new GlobalResponse().dataDitemukan( mapResponse );
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        Menu menuDB = null;
        try {
            Optional<Menu> op = menuRepo.findById(id);
            if(!op.isPresent()){
                return new GlobalResponse().dataTidakDitemukan("TRN02FV051");
            }
            menuDB = op.get();
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN02FE051");
        }
        return new GlobalResponse().dataDitemukan(menuDB);
    }

    @Override
    public ResponseEntity<Object> uploadExcel(MultipartFile file, HttpServletRequest request) {
        String message = "";
        try {
            if(!ExcelReader.hasWorkBookFormat(file)){
                return new GlobalResponse().formatFileHarusExcel("TRN02FV101");
            }
            List lt = new ExcelReader(file.getInputStream(),"menu").getDataMap();
            if(lt.isEmpty()){
                return new GlobalResponse().fileExcelKosong("TRN02FV102");
            }
            menuRepo.saveAll(convertListWorkBookToListEntity(lt,1L));
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN02FE101");
        }
        return new GlobalResponse().dataBerhasilDisimpan();
    }

    @Override
    public List<Menu> convertListWorkBookToListEntity(List<Map<String, String>> workBookData, Long userId) {
        List<Menu> list = new ArrayList<>();
        for (Map<String, String> map : workBookData) {
            Menu menu = new Menu();
            menu.setNama(map.get("NAMA MENU"));
            menu.setPath(map.get("PATH"));
            GroupMenu groupMenu = new GroupMenu();
            groupMenu.setId(Long.parseLong(map.get("ID GROUP").toString()));
            menu.setGroupMenu(groupMenu);
            menu.setCreatedBy(userId);
            list.add(menu);
        }
        return list;
    }

    @Override
    public Object downloadReportExcel(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<Menu> list = null;
        Boolean isFound = true;
        Map<String,Object> mapResponse = null;
        try {
            switch (column){
                case "path" : list = menuRepo.findByPathContains(value);break;
                case "nama" : list = menuRepo.findByNamaContains(value);break;
                case "id" : list = menuRepo.findAll();break;
                default : isFound = false;
            }
            /** tidak ditemukan karena pengiriman flag nama kolom tidak sesuai */
            if(!isFound){
                return new GlobalResponse().dataTidakDitemukan("TRN02FV111");
            }
            /** tidak ditemukan sesudah dilakukan query */
            if(list.isEmpty()){
                return new GlobalResponse().dataTidakDitemukan("TRN02FV112");
            }
            List<RespMenuDTO> listDTO = entityToDTO(list);
            new MappingReport().mappingReportExcel(listDTO,"menu",new RespMenuDTO(),response);
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN02FE111");
        }
        return "";
    }

    @Override
    public Object downloadReportPDF(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<Menu> list = null;
        Boolean isFound = true;
        Map<String,Object> mapResponse = null;
        try {
            switch (column){
                case "path" : list = menuRepo.findByPathContains(value);break;
                case "nama" : list = menuRepo.findByNamaContains(value);break;
                case "id" : list = menuRepo.findAll();break;
                default : isFound = false;
            }
            /** tidak ditemukan karena pengiriman flag nama kolom tidak sesuai */
            if(!isFound){
                return new GlobalResponse().dataTidakDitemukan("TRN02FV111");
            }
            /** tidak ditemukan sesudah dilakukan query */
            if(list.isEmpty()){
                return new GlobalResponse().dataTidakDitemukan("TRN02FV112");
            }
            List<RespMenuDTO> listDTO = entityToDTO(list);
            new MappingReport().mappingReportPDF(
                    listDTO,
                    "menu",
                    "MENU",
                    new RespMenuDTO(),
                    springTemplateEngine,
                    pdfGenerator,
                    "Paul",
                    response);
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN02FE111");
        }
        return "";
    }

    public Menu mapToEntity(ValMenuDTO valMenuDTO){
        Menu menu = new Menu();
        menu.setNama(valMenuDTO.getNama());
        menu.setPath(valMenuDTO.getPath());
        GroupMenu groupMenu = new GroupMenu();
        groupMenu.setId(valMenuDTO.getGroupMenu().getId());
        menu.setGroupMenu(groupMenu);
        return menu;
    }

    public List<RespMenuDTO> entityToDTO(List<Menu> list){
        List<RespMenuDTO> listDTO = new ArrayList<>();
        for (Menu menu : list) {
            RespMenuDTO respMenuDTO = new RespMenuDTO();
            respMenuDTO.setNama(menu.getNama());
            respMenuDTO.setPath(menu.getPath());
            respMenuDTO.setId(menu.getId());
            respMenuDTO.setIdGroupMenu(menu.getGroupMenu().getId());
            respMenuDTO.setNamaGroupMenu(menu.getGroupMenu().getNama());
            listDTO.add(respMenuDTO);
        }
        return listDTO;
    }
}