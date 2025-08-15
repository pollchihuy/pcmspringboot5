package com.juaracoding.pcmspringboot5.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

/**
 {
     "nama":"Paul",
     "umur": 30,
     "tanggal_lahir":"1995-12-12",
     "notes" : null,
     "email_customer":
     [
         {
             "email":"paul@gmail.com",
             "host":"gmail"
         },
         {
             "email":"paul@yahoo.com",
             "host":"yahoo"
         }
     ],
     "level" : {
                 "id":1,
                 "level_name": "silver"
             }
 }
 */
public class Customer {

    private String nama;
    private Integer umur;

    @JsonProperty("tanggal_lahir")
    private LocalDate tanggalLahir;

    @JsonProperty("email_customer")
    private List<Email> emailCustomer;

    private LevelCustomer level;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Integer getUmur() {
        return umur;
    }

    public void setUmur(Integer umur) {
        this.umur = umur;
    }

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public List<Email> getEmailCustomer() {
        return emailCustomer;
    }

    public void setEmailCustomer(List<Email> emailCustomer) {
        this.emailCustomer = emailCustomer;
    }

    public LevelCustomer getLevel() {
        return level;
    }

    public void setLevel(LevelCustomer level) {
        this.level = level;
    }
}
