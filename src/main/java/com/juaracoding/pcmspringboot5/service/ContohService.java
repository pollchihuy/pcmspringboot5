package com.juaracoding.pcmspringboot5.service;


import com.juaracoding.pcmspringboot5.utils.WriteExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContohService {
    @Autowired
    Contoh2Service  contoh2Service;
    @Autowired
    private WriteExcel writeExcel;

    public String save(){
        return "Data Berhasil Disimpan";
    }

    public String contoh2ServiceReturn(){
        return contoh2Service.save();
    }

    public void callAsync(){
        try {
            writeExcel.printData2();
        } catch (InterruptedException e) {
            System.out.println("Error "+e.getMessage());
        }
    }
}
