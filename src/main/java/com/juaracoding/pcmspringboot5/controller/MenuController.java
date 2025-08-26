package com.juaracoding.pcmspringboot5.controller;


import com.juaracoding.pcmspringboot5.dto.val.ValMenuDTO;
import com.juaracoding.pcmspringboot5.service.MenuService;
import com.juaracoding.pcmspringboot5.utils.GlobalFunction;
import com.juaracoding.pcmspringboot5.utils.GlobalResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody ValMenuDTO valMenuDTO, HttpServletRequest request) {
        return menuService.save(menuService.mapToEntity(valMenuDTO),request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@Valid @RequestBody ValMenuDTO valMenuDTO,
                                         @PathVariable Long id, HttpServletRequest request) {
        return menuService.update(id, menuService.mapToEntity(valMenuDTO),request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {
        return menuService.delete(id,request);
    }

    /**
     * Api Default untuk membuka menu
     * @return
     */
    @GetMapping
    public ResponseEntity<Object> findAll(HttpServletRequest request){
        return menuService.findAll(request);
    }

    @GetMapping("/{sort}/{sort_by}/{page}")
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
            return new GlobalResponse().andaBerniatJahat("TRN02FV041");
        }
        switch (sort) {
            case "asc":pageable = PageRequest.of(page, size, Sort.by(sortBy));break;
            case "desc":pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());break;
            default:isSort = false;break;
        }
        if(!isSort){
            return new GlobalResponse().dataTidakDitemukan("TRN02FV042");
        }
        if(!sortByValidation(sortBy)){
            return new GlobalResponse().dataTidakDitemukan("TRN02FV043");
        }
        return menuService.findByParam(pageable,column,value,request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id, HttpServletRequest request) {
        return menuService.findById(id,request);
    }

    @PostMapping("/upload-excel")
    public ResponseEntity<Object> uploadExcel(@RequestParam MultipartFile file, HttpServletRequest request) {
        return menuService.uploadExcel(file,request);
    }

    @GetMapping("/excel")
    public Object downloadExcel(
            @RequestParam String column,
            @RequestParam String value,
            HttpServletRequest request,HttpServletResponse response) {
        return menuService.downloadReportExcel(column,value,request,response);
    }

    @GetMapping("/pdf")
    public Object downloadPDF(
            @RequestParam String column,
            @RequestParam String value,
            HttpServletRequest request,HttpServletResponse response) {
        return menuService.downloadReportPDF(column,value,request,response);
    }

    public Boolean sortByValidation(String sortBy){
        Boolean isValid = true;
        switch (sortBy){
            case "nama":break;
            case "path":break;
            case "id":break;
            default:isValid = false;break;
        }
        return isValid;
    }
}
