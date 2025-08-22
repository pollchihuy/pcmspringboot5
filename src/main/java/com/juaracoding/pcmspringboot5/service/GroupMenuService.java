package com.juaracoding.pcmspringboot5.service;

import com.juaracoding.pcmspringboot5.core.IService;
import com.juaracoding.pcmspringboot5.dto.resp.RespGroupMenuDTO;
import com.juaracoding.pcmspringboot5.dto.val.ValGroupMenuDTO;
import com.juaracoding.pcmspringboot5.handler.ResponseHandler;
import com.juaracoding.pcmspringboot5.model.GroupMenu;
import com.juaracoding.pcmspringboot5.model.LogGroupMenu;
import com.juaracoding.pcmspringboot5.model.Menu;
import com.juaracoding.pcmspringboot5.repo.GroupMenuRepo;
import com.juaracoding.pcmspringboot5.repo.LogGroupMenuRepo;
import com.juaracoding.pcmspringboot5.utils.TransformPagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class GroupMenuService implements IService<GroupMenu> {

    @Autowired
    private GroupMenuRepo groupMenuRepo;

    @Autowired
    private LogGroupMenuRepo logGroupMenuRepo;

    public ResponseEntity<Object> save(GroupMenu groupMenu){
        try {
            if(groupMenu==null){
                return new ResponseHandler().handleResponse("Tidak Dapat DiProses", HttpStatus.BAD_REQUEST,null,"TRN01FV001");
            }
            if(groupMenu.getId()!=null){
                return new ResponseHandler().handleResponse("Tidak Dapat DiProses", HttpStatus.BAD_REQUEST,null,"TRN01FV002");
            }
            groupMenuRepo.save(groupMenu);
            logGroupMenuRepo.save(mapToLog(groupMenu,'i',1L));
        }catch (Exception e){
            return new ResponseHandler().handleResponse("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR,null,"TRN01FE001");
        }

        return new ResponseHandler().handleResponse("Berhasil Disimpan", HttpStatus.CREATED,null,null);
    }

    public ResponseEntity<Object> update(Long id,GroupMenu groupMenu){
        Optional<GroupMenu> op = groupMenuRepo.findById(id);
        if(!op.isPresent()){
            return new ResponseHandler().handleResponse("Data Tidak Ditemukan", HttpStatus.BAD_REQUEST,null,"TRN01FV011");
        }
        GroupMenu groupMenuDB = op.get();
        groupMenuDB.setNama(groupMenu.getNama());
        groupMenuDB.setModifiedBy(1L);

        logGroupMenuRepo.save(mapToLog(groupMenuDB,'u',1L));

        return new ResponseHandler().handleResponse("Berhasil Diubah", HttpStatus.OK,null,null);
    }
    public ResponseEntity<Object> delete(Long id){
        Optional<GroupMenu> op = groupMenuRepo.findById(id);
        if(!op.isPresent()){
            return ResponseEntity.badRequest().body("Data Tidak Ditemukan");
        }
        groupMenuRepo.deleteById(id);
        logGroupMenuRepo.save(mapToLog(op.get(),'d',1L));
        return new ResponseHandler().handleResponse("Berhasil Dihapus", HttpStatus.OK,null,null);
    }

    public ResponseEntity<Object> findAll(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        Page<GroupMenu> page = null;
        page = groupMenuRepo.findAll(pageable);
        if(page.isEmpty()){
            return new ResponseHandler().handleResponse("Data Tidak Ditemukan", HttpStatus.BAD_REQUEST,null,"FV");
        }
        Map<String,Object> mapResponse = new TransformPagination().
                transformPagination(
                entityToDTO(page.getContent()),
                page,
                "id",
                ""
        );
        return new ResponseHandler().handleResponse("Data Ditemukan", HttpStatus.OK,mapResponse,null);
    }

    public ResponseEntity<Object> findByParam(Pageable pageable,String column, String value){
        Page<GroupMenu> page = null;
        Boolean isFound = true;

        switch (column){
            case "nama" : page = groupMenuRepo.findByNamaContains(pageable,value);break;
            case "id" : page = groupMenuRepo.findAll(pageable);break;
            default : isFound = false;
        }
        if(!isFound){
            return new ResponseHandler().handleResponse("Data Tidak Ditemukan", HttpStatus.BAD_REQUEST,null,"FV");
        }
        if(page.isEmpty()){
            return new ResponseHandler().handleResponse("Data Tidak Ditemukan", HttpStatus.BAD_REQUEST,null,"FV");
        }
        Map<String,Object> mapResponse = new TransformPagination().
                transformPagination(
                        entityToDTO(page.getContent()),
                        page,
                        "id",
                        ""
                );
        return new ResponseHandler().handleResponse("Data Ditemukan", HttpStatus.OK,mapResponse,null);
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
