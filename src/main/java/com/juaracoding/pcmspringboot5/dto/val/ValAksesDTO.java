package com.juaracoding.pcmspringboot5.dto.val;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.juaracoding.pcmspringboot5.dto.relation.RelMenuDTO;
import com.juaracoding.pcmspringboot5.model.Menu;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

public class ValAksesDTO {

    private String nama;

    @JsonProperty("menu_list")
    private List<RelMenuDTO> menuList;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public List<RelMenuDTO> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<RelMenuDTO> menuList) {
        this.menuList = menuList;
    }
}
