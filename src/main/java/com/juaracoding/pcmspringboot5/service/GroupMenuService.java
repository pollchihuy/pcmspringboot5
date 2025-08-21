package com.juaracoding.pcmspringboot5.service;

import com.juaracoding.pcmspringboot5.dto.resp.RespGroupMenuDTO;
import com.juaracoding.pcmspringboot5.dto.val.ValGroupMenuDTO;
import com.juaracoding.pcmspringboot5.model.GroupMenu;
import com.juaracoding.pcmspringboot5.model.LogGroupMenu;
import com.juaracoding.pcmspringboot5.repo.GroupMenuRepo;
import com.juaracoding.pcmspringboot5.repo.LogGroupMenuRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GroupMenuService {

    @Autowired
    private GroupMenuRepo groupMenuRepo;

    @Autowired
    private LogGroupMenuRepo logGroupMenuRepo;

    public ResponseEntity<Object> save(GroupMenu groupMenu){
        groupMenuRepo.save(groupMenu);
        logGroupMenuRepo.save(mapToLog(groupMenu,'i',1L));
        return ResponseEntity.ok().body("Berhasil Disimpan");
    }

    public ResponseEntity<Object> update(Long id,GroupMenu groupMenu){
        Optional<GroupMenu> op = groupMenuRepo.findById(id);
        if(!op.isPresent()){
           return ResponseEntity.badRequest().body("Data Tidak Ditemukan");
        }
        GroupMenu groupMenuDB = op.get();
        groupMenuDB.setNama(groupMenu.getNama());
        groupMenuDB.setModifiedBy(1L);

        logGroupMenuRepo.save(mapToLog(groupMenuDB,'u',1L));

        return ResponseEntity.ok().body("Berhasil Diubah");
    }
    public ResponseEntity<Object> delete(Long id){
        Optional<GroupMenu> op = groupMenuRepo.findById(id);
        if(!op.isPresent()){
            return ResponseEntity.badRequest().body("Data Tidak Ditemukan");
        }
        groupMenuRepo.deleteById(id);
        logGroupMenuRepo.save(mapToLog(op.get(),'d',1L));
        return ResponseEntity.ok().body("Berhasil Dihapus");
    }

    public ResponseEntity<Object> findAll(){
        List<GroupMenu> list = groupMenuRepo.findAll();
        return ResponseEntity.ok().body(entityToDTO(list));
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
