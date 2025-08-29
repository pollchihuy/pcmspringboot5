package com.juaracoding.pcmspringboot5.controller;

import com.juaracoding.pcmspringboot5.dto.val.ValRegisDTO;
import com.juaracoding.pcmspringboot5.dto.val.ValVerifyRegisDTO;
import com.juaracoding.pcmspringboot5.service.AppDetailServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AppDetailServices appDetailServices;

    @PostMapping("/regis")
    public ResponseEntity<Object> regis(@Valid @RequestBody ValRegisDTO valRegisDTO,
                                        HttpServletRequest request){
        return appDetailServices.regis(appDetailServices.mapToEntity(valRegisDTO),request);
    }

    @PostMapping("/verify-regis")
    public ResponseEntity<Object> verifyRegis(@Valid @RequestBody ValVerifyRegisDTO valVerifyRegisDTO,
                                        HttpServletRequest request){
        return appDetailServices.verifyRegis(appDetailServices.mapToEntity(valVerifyRegisDTO),request);
    }
}
