package com.juaracoding.pcmspringboot5.service;

import com.juaracoding.pcmspringboot5.core.IService;
import com.juaracoding.pcmspringboot5.dto.resp.RespGroupMenuDTO;
import com.juaracoding.pcmspringboot5.model.LogGroupMenu;
import com.juaracoding.pcmspringboot5.model.Menu;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 *  Platform Code : TRN
 *  Modul Code : 02
 *  FV : Failed Validation
 *  FE : Failed Error
 */
public class MenuService implements IService<Menu> {

    @Override
    public ResponseEntity<Object> save(Menu menu) {
        return null;//TRN02FV001
    }

    @Override
    public ResponseEntity<Object> update(Long id, Menu menu) {
        return null;
    }

    @Override
    public ResponseEntity<Object> delete(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findAll() {
        return null;
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String column, String value) {
        return null;
    }

    @Override
    public List<RespGroupMenuDTO> entityToDTO(List<Menu> list) {
        return List.of();
    }

    @Override
    public LogGroupMenu mapToLog(Menu menu, Character flag, Long userId) {
        return null;
    }
}
