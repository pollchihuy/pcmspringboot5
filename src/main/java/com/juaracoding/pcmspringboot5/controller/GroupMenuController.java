package com.juaracoding.pcmspringboot5.controller;


import com.juaracoding.pcmspringboot5.dto.val.ValGroupMenuDTO;
import com.juaracoding.pcmspringboot5.service.GroupMenuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("group-menu")
public class GroupMenuController {

    @Autowired
    private GroupMenuService groupMenuService;

    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody ValGroupMenuDTO valGroupMenuDTO) {
        return groupMenuService.save(groupMenuService.mapToEntity(valGroupMenuDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@Valid @RequestBody ValGroupMenuDTO valGroupMenuDTO,
                                         @PathVariable Long id) {
        return groupMenuService.update(id,groupMenuService.mapToEntity(valGroupMenuDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return groupMenuService.delete(id);
    }

    @GetMapping
    public ResponseEntity<Object> findAll(){
        return groupMenuService.findAll();
    }
}
