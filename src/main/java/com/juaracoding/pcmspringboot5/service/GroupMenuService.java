package com.juaracoding.pcmspringboot5.service;

import com.juaracoding.pcmspringboot5.core.IReport;
import com.juaracoding.pcmspringboot5.core.IService;
import com.juaracoding.pcmspringboot5.dto.resp.RespGroupMenuDTO;
import com.juaracoding.pcmspringboot5.dto.val.ValGroupMenuDTO;
import com.juaracoding.pcmspringboot5.model.Akses;
import com.juaracoding.pcmspringboot5.model.GroupMenu;
import com.juaracoding.pcmspringboot5.model.LogGroupMenu;
import com.juaracoding.pcmspringboot5.repo.GroupMenuRepo;
import com.juaracoding.pcmspringboot5.repo.LogGroupMenuRepo;
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
 *  Modul Code : 01
 *  FV : Failed Validation - 001-010
 *  FE : Failed Error - 001-010
 */
@Service
@Transactional
public class GroupMenuService implements IService<GroupMenu>, IReport<GroupMenu> {

    @Autowired
    private GroupMenuRepo groupMenuRepo;

    @Autowired
    private LogGroupMenuRepo logGroupMenuRepo;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private PdfGenerator pdfGenerator;

    private StringBuilder sBuild = new StringBuilder();

    public ResponseEntity<Object> save(GroupMenu groupMenu, HttpServletRequest request){
        try {
            if(groupMenu==null){
                return new GlobalResponse().tidakDapatDiproses("TRN01FV001");
            }
            if(groupMenu.getId()!=null){
                return new GlobalResponse().tidakDapatDiproses("TRN01FV002");
            }
            groupMenuRepo.save(groupMenu);
            logGroupMenuRepo.save(mapToLog(groupMenu,'i',1L));
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN01FE001");
        }

        return new GlobalResponse().dataBerhasilDisimpan();
    }

    public ResponseEntity<Object> update(Long id,GroupMenu groupMenu, HttpServletRequest request){
        try {
            Optional<GroupMenu> op = groupMenuRepo.findById(id);
            if(!op.isPresent()){
                return new GlobalResponse().dataTidakDitemukan("TRN01FV011");
            }
            GroupMenu groupMenuDB = op.get();
            groupMenuDB.setNama(groupMenu.getNama());
            groupMenuDB.setModifiedBy(1L);
            logGroupMenuRepo.save(mapToLog(groupMenuDB,'u',1L));
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN01FE011");
        }
        return new GlobalResponse().dataBerhasilDiubah();
    }

    public ResponseEntity<Object> delete(Long id, HttpServletRequest request){
        try{
            Optional<GroupMenu> op = groupMenuRepo.findById(id);
            if(!op.isPresent()){
                return new GlobalResponse().dataTidakDitemukan("TRN01FV021");
            }
            groupMenuRepo.deleteById(id);
            logGroupMenuRepo.save(mapToLog(op.get(),'d',1L));
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN01FE021");
        }
        return new GlobalResponse().dataBerhasilDihapus();
    }

    public ResponseEntity<Object> findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        Page<GroupMenu> page = null;
        Map<String,Object> mapResponse = null;

        try{
            page = groupMenuRepo.findAll(pageable);
            if(page.isEmpty()){
                return new GlobalResponse().dataTidakDitemukan("TRN01FV031");
            }
            mapResponse = new TransformPagination().
                    transformPagination(
                            entityToDTO(page.getContent()),
                            page,
                            "id",
                            ""
                    );
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN01FE031");
        }
        return new GlobalResponse().dataDitemukan( mapResponse );
    }

    public ResponseEntity<Object> findByParam(Pageable pageable,String column, String value, HttpServletRequest request){
        Page<GroupMenu> page = null;
        Boolean isFound = true;
        Map<String,Object> mapResponse = null;
        try{
            switch (column){
                case "nama" : page = groupMenuRepo.findByNamaContains(pageable,value);break;
                case "id" : page = groupMenuRepo.findAll(pageable);break;
                default : isFound = false;
            }
            /** tidak ditemukan karena pengiriman flag nama kolom tidak sesuai */
            if(!isFound){
                return new GlobalResponse().dataTidakDitemukan("TRN01FV044");
            }
            /** tidak ditemukan sesudah dilakukan query */
            if(page.isEmpty()){
                return new GlobalResponse().dataTidakDitemukan("TRN01FV045");
            }
            mapResponse = new TransformPagination().
                    transformPagination(
                            entityToDTO(page.getContent()),
                            page,
                            "id",
                            ""
                    );
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN01FE041");
        }
        return new GlobalResponse().dataDitemukan( mapResponse );
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        GroupMenu groupMenuDB = null;
        try {
            Optional<GroupMenu> op = groupMenuRepo.findById(id);
            if(!op.isPresent()){
                return new GlobalResponse().dataTidakDitemukan("TRN01FV051");
            }
            groupMenuDB = op.get();
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN01FE051");
        }
        return new GlobalResponse().dataDitemukan(groupMenuDB);
    }

    @Override
    public ResponseEntity<Object> uploadExcel(MultipartFile file, HttpServletRequest request) {
        String message = "";
        try {
            if(!ExcelReader.hasWorkBookFormat(file)){
                return new GlobalResponse().formatFileHarusExcel("TRN01FV101");
            }
            List lt = new ExcelReader(file.getInputStream(),"group-menu").getDataMap();
            if(lt.isEmpty()){
                return new GlobalResponse().fileExcelKosong("TRN01FV102");
            }
            groupMenuRepo.saveAll(convertListWorkBookToListEntity(lt,1L));
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN01FE101");
        }
        return new GlobalResponse().dataBerhasilDisimpan();
    }

    @Override
    public List<GroupMenu> convertListWorkBookToListEntity(List<Map<String, String>> workBookData, Long userId) {
        List<GroupMenu> list = new ArrayList<>();
        for (Map<String, String> map : workBookData) {
            GroupMenu groupMenu = new GroupMenu();
            groupMenu.setNama(map.get("NAMA GROUP MENU"));
            groupMenu.setCreatedBy(userId);
            list.add(groupMenu);
        }
        return list;
    }

    @Override
    public Object downloadReportExcel(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<GroupMenu> list = null;
        Boolean isFound = true;
        Map<String,Object> mapResponse = null;
        try {
            switch (column){
                case "nama" : list = groupMenuRepo.findByNamaContains(value);break;
                case "id" : list = groupMenuRepo.findAll();break;
                default : isFound = false;
            }
            /** tidak ditemukan karena pengiriman flag nama kolom tidak sesuai */
            if(!isFound){
                return new GlobalResponse().dataTidakDitemukan("TRN01FV111");
            }
            /** tidak ditemukan sesudah dilakukan query */
            if(list.isEmpty()){
                return new GlobalResponse().dataTidakDitemukan("TRN01FV112");
            }
            List<RespGroupMenuDTO> listDTO = entityToDTO(list);
            new MappingReport().mappingReportExcel(listDTO,"group-menu",new RespGroupMenuDTO(),response);
//            new MappingReport().mappingReportExcel(list,"group-menu",new GroupMenu()
//                    ,response);
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN01FE111");
        }
        return "";
    }

    @Override
    public Object downloadReportPDF(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<GroupMenu> list = null;
        Boolean isFound = true;
        Map<String,Object> mapResponse = null;
        try {
            switch (column){
                case "nama" : list = groupMenuRepo.findByNamaContains(value);break;
                case "id" : list = groupMenuRepo.findAll();break;
                default : isFound = false;
            }
            /** tidak ditemukan karena pengiriman flag nama kolom tidak sesuai */
            if(!isFound){
                return new GlobalResponse().dataTidakDitemukan("TRN01FV111");
            }
            /** tidak ditemukan sesudah dilakukan query */
            if(list.isEmpty()){
                return new GlobalResponse().dataTidakDitemukan("TRN01FV112");
            }
            List<RespGroupMenuDTO> listDTO = entityToDTO(list);
            new MappingReport().mappingReportPDF(
                    listDTO,
                    "group-menu",
                    "GROUP MENU",
                    new RespGroupMenuDTO(),
                    springTemplateEngine,
                    pdfGenerator,
                    "Paul",
                    response);
//            new MappingReport().mappingReportPDF(
//                    list,
//
//                    "group-menu",
//                    "GROUP MENU",
//                    new GroupMenu(),
//                    springTemplateEngine,
//                    pdfGenerator,
//                    "Paul",
//                    response);
//            new MappingReport().mappingReportExcel(list,"group-menu",new GroupMenu()
//                    ,response);
        }catch (Exception e){
            return new GlobalResponse().internalServerError("TRN01FE111");
        }
        return "";
    }

    public GroupMenu mapToEntity(ValGroupMenuDTO valGroupMenuDTO){
        GroupMenu groupMenu = new GroupMenu();
        groupMenu.setNama(valGroupMenuDTO.getNama());
        return groupMenu;
    }

    public List<RespGroupMenuDTO> entityToDTO(List<GroupMenu> list){
        List<RespGroupMenuDTO> listDTO = new ArrayList<>();
        for (GroupMenu groupMenu : list) {
            RespGroupMenuDTO respGroupMenuDTO = new RespGroupMenuDTO();
            respGroupMenuDTO.setNama(groupMenu.getNama());
            respGroupMenuDTO.setId(groupMenu.getId());
            listDTO.add(respGroupMenuDTO);
        }
        return listDTO;
    }

    public LogGroupMenu mapToLog(GroupMenu groupMenu, Character flag,Long userId){
        LogGroupMenu logGroupMenu = new LogGroupMenu();
        logGroupMenu.setIdGroupMenu(groupMenu.getId());
        logGroupMenu.setNama(groupMenu.getNama());
        logGroupMenu.setFlag(flag);
        logGroupMenu.setCreatedBy(userId);
        return logGroupMenu;
    }
}
