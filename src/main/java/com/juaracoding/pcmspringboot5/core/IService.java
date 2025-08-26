package com.juaracoding.pcmspringboot5.core;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * jatah sequence di kontrak ini adalah 001 - 100
 * @param <T>
 */
public interface IService<T> {
    public ResponseEntity<Object> save(T t, HttpServletRequest request);//001-010
    public ResponseEntity<Object> update(Long id,T t, HttpServletRequest request);//011-020
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request);//021-030
    public ResponseEntity<Object> findAll(HttpServletRequest request);//031-040
    public ResponseEntity<Object> findByParam(Pageable pageable, String column, String value, HttpServletRequest request);//041-050
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request);//051-060
}