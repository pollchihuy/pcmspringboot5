package com.juaracoding.pcmspringboot5.controller;


import com.juaracoding.pcmspringboot5.dto.val.ValGroupMenuDTO;
import com.juaracoding.pcmspringboot5.handler.ResponseHandler;
import com.juaracoding.pcmspringboot5.service.GroupMenuService;
import com.juaracoding.pcmspringboot5.utils.GlobalFunction;
import com.juaracoding.pcmspringboot5.utils.GlobalResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("group-menu")
public class GroupMenuController {

    @Autowired
    private GroupMenuService groupMenuService;

    @PostMapping
    @PreAuthorize("hasAuthority('Group-Menu')")
    public ResponseEntity<Object> save(@Valid @RequestBody ValGroupMenuDTO valGroupMenuDTO, HttpServletRequest request) {
        return groupMenuService.save(groupMenuService.mapToEntity(valGroupMenuDTO),request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Group-Menu')")
    public ResponseEntity<Object> update(@Valid @RequestBody ValGroupMenuDTO valGroupMenuDTO,
                                         @PathVariable Long id, HttpServletRequest request) {
        return groupMenuService.update(id,groupMenuService.mapToEntity(valGroupMenuDTO),request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Group-Menu')")
    public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {
        return groupMenuService.delete(id,request);
    }

    /**
     * Api Default untuk membuka menu
     * @return
     */
    @GetMapping
    @PreAuthorize("hasAuthority('Group-Menu')")
    public ResponseEntity<Object> findAll(HttpServletRequest request){
        return groupMenuService.findAll(request);
    }

    @GetMapping("/{sort}/{sort_by}/{page}")
    @PreAuthorize("hasAuthority('Group-Menu')")
    public ResponseEntity<Object> findByParam(
            @PathVariable String sort,
            @PathVariable(value = "sort_by") String sortBy,
            @PathVariable Integer page,
            @RequestParam Integer size,
            @RequestParam String  column,
            @RequestParam String  value,
            HttpServletRequest request
            ) {
        Boolean isSort = true;
        Pageable pageable = null;

        if(!GlobalFunction.checkValue(value)){
            return new GlobalResponse().andaBerniatJahat("TRN01FV041");
        }
        switch (sort) {
            case "asc":pageable = PageRequest.of(page, size, Sort.by(sortBy));break;
            case "desc":pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());break;
            default:isSort = false;break;
        }
        if(!isSort){
            return new GlobalResponse().dataTidakDitemukan("TRN01FV042");
        }
        if(!sortByValidation(sortBy)){
            return new GlobalResponse().dataTidakDitemukan("TRN01FV043");
        }
        return groupMenuService.findByParam(pageable,column,value,request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Group-Menu')")
    public ResponseEntity<Object> findById(@PathVariable Long id, HttpServletRequest request) {
        return groupMenuService.findById(id,request);
    }

    @PostMapping("/upload-excel")
    @PreAuthorize("hasAuthority('Group-Menu')")
    public ResponseEntity<Object> uploadExcel(@RequestParam MultipartFile file, HttpServletRequest request) {
        return groupMenuService.uploadExcel(file,request);
    }

    @GetMapping("/excel")
    @PreAuthorize("hasAuthority('Group-Menu')")
    public Object downloadExcel(
            @RequestParam String column,
            @RequestParam String value,
            HttpServletRequest request,HttpServletResponse response) {
        return groupMenuService.downloadReportExcel(column,value,request,response);
    }

    @GetMapping("/pdf")
    @PreAuthorize("hasAuthority('Group-Menu')")
    public Object downloadPDF(
            @RequestParam String column,
            @RequestParam String value,
            HttpServletRequest request,HttpServletResponse response) {
        return groupMenuService.downloadReportPDF(column,value,request,response);
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
