package com.juaracoding.pcmspringboot5.controller;


import com.juaracoding.pcmspringboot5.dto.val.ValUserDTO;
import com.juaracoding.pcmspringboot5.service.UserService;
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
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody ValUserDTO valUserDTO, HttpServletRequest request) {
        return userService.save(userService.mapToEntity(valUserDTO),request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@Valid @RequestBody ValUserDTO valUserDTO,
                                         @PathVariable Long id, HttpServletRequest request) {
        return userService.update(id, userService.mapToEntity(valUserDTO),request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {
        return userService.delete(id,request);
    }

    /**
     * Api Default untuk membuka user
     * @return
     */
    @GetMapping
    public ResponseEntity<Object> findAll(HttpServletRequest request){
        return userService.findAll(request);
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
            return new GlobalResponse().andaBerniatJahat("TRN04FV041");
        }
        switch (sort) {
            case "asc":pageable = PageRequest.of(page, size, Sort.by(sortBy));break;
            case "desc":pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());break;
            default:isSort = false;break;
        }
        if(!isSort){
            return new GlobalResponse().dataTidakDitemukan("TRN04FV042");
        }
        if(!sortByValidation(sortBy)){
            return new GlobalResponse().dataTidakDitemukan("TRN04FV043");
        }
        return userService.findByParam(pageable,column,value,request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id, HttpServletRequest request) {
        return userService.findById(id,request);
    }

    @PostMapping("/upload-excel")
    public ResponseEntity<Object> uploadExcel(@RequestParam MultipartFile file, HttpServletRequest request) {
        return userService.uploadExcel(file,request);
    }

    @GetMapping("/excel")
    public Object downloadExcel(
            @RequestParam String column,
            @RequestParam String value,
            HttpServletRequest request,HttpServletResponse response) {
        return userService.downloadReportExcel(column,value,request,response);
    }

    @GetMapping("/pdf")
    public Object downloadPDF(
            @RequestParam String column,
            @RequestParam String value,
            HttpServletRequest request,HttpServletResponse response) {
        return userService.downloadReportPDF(column,value,request,response);
    }

    public Boolean sortByValidation(String sortBy){
        Boolean isValid = true;
        switch (sortBy){
            case "nama":break;
            case "nohp":break;
            case "email":break;
            case "username":break;
            case "id":break;
            default:isValid = false;break;
        }
        return isValid;
    }
}
