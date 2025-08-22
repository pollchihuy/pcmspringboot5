package com.juaracoding.pcmspringboot5.core;

import com.juaracoding.pcmspringboot5.dto.resp.RespGroupMenuDTO;
import com.juaracoding.pcmspringboot5.model.GroupMenu;
import com.juaracoding.pcmspringboot5.model.LogGroupMenu;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IService<T> {
    public ResponseEntity<Object> save(T t);//001-010
    public ResponseEntity<Object> update(Long id,T t);//011-020
    public ResponseEntity<Object> delete(Long id);//021-030
    public ResponseEntity<Object> findAll();//031-040
    public ResponseEntity<Object> findByParam(Pageable pageable, String column, String value);//041-050
    public List<RespGroupMenuDTO> entityToDTO(List<T> list);
    public LogGroupMenu mapToLog(T t, Character flag, Long userId);
}
