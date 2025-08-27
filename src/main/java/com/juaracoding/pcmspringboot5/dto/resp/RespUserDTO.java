package com.juaracoding.pcmspringboot5.dto.resp;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class RespUserDTO {
    private Long id;
    private String username;

    private String email;

    @JsonProperty("no_hp")
    private String noHp;

    private String password;

    @JsonProperty("nama_lengkap")
    private String namaLengkap;

    @JsonProperty("tanggal_lahir")
    private LocalDate tanggalLahir;
    private Integer umur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUmur() {
        return umur;
    }

    public void setUmur(Integer umur) {
        this.umur = umur;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }
}
