package com.juaracoding.pcmspringboot5.controller;


import com.juaracoding.pcmspringboot5.dto.val.ValGroupMenuDTO;
import com.juaracoding.pcmspringboot5.handler.ResponseHandler;
import com.juaracoding.pcmspringboot5.service.GroupMenuService;
import com.juaracoding.pcmspringboot5.utils.GlobalFunction;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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

    /**
     * Api Default untuk membuka menu
     * @return
     */
    @GetMapping
    public ResponseEntity<Object> findAll(){
        return groupMenuService.findAll();
    }

    @GetMapping("/{sort}/{sort_by}/{page}")
    public ResponseEntity<Object> findByParam(
            @PathVariable String sort,
            @PathVariable(value = "sort_by") String sortBy,
            @PathVariable Integer page,
            @RequestParam Integer size,
            @RequestParam String  column,
            @RequestParam String  value
            ) {
        Boolean isSort = true;
        Pageable pageable = null;

        if(!GlobalFunction.checkValue(value)){
            return new ResponseHandler().handleResponse("Anda Berniat Jahat !!", HttpStatus.BAD_REQUEST,null,"X-001");
        }
        switch (sort) {
            case "asc":pageable = PageRequest.of(page, size, Sort.by(sortBy));break;
            case "desc":pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());break;
            default:isSort = false;break;
        }
        if(!isSort){
            return new ResponseHandler().handleResponse("Data Tidak Ditemukan", HttpStatus.BAD_REQUEST,null,"X-001");
        }
        if(!sortByValidation(sortBy)){
            return new ResponseHandler().handleResponse("Data Tidak Ditemukan", HttpStatus.BAD_REQUEST,null,"X-001");
        }

        return groupMenuService.findByParam(pageable,column,value);

    }

    public Boolean sortByValidation(String sortBy){
        Boolean isValid = true;
        switch (sortBy){
            case "nama":break;
            case "id":break;
            default:isValid = false;break;
        }
        return isValid;
    }
}
