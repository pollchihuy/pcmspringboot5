package com.juaracoding.pcmspringboot5.dto.resp;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.juaracoding.pcmspringboot5.dto.relation.RelGroupMenuDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class RespMenuDTO {

    private Long id;
    private String nama;

    private String path;

    private Long idGroupMenu;

    private String namaGroupMenu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdGroupMenu() {
        return idGroupMenu;
    }

    public void setIdGroupMenu(Long idGroupMenu) {
        this.idGroupMenu = idGroupMenu;
    }

    public String getNamaGroupMenu() {
        return namaGroupMenu;
    }

    public void setNamaGroupMenu(String namaGroupMenu) {
        this.namaGroupMenu = namaGroupMenu;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
