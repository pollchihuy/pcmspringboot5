package com.juaracoding.pcmspringboot5.dto.val;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.juaracoding.pcmspringboot5.dto.relation.RelGroupMenuDTO;
import com.juaracoding.pcmspringboot5.model.GroupMenu;
import com.juaracoding.pcmspringboot5.utils.ConstantMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ValMenuDTO {

    @NotBlank
    @NotNull(message = "Nama Menu Tidak Boleh Kosong")
    @Pattern(regexp = "^[\\w \\s]{5,30}$",message = "Format Tidak Sesuai Alfanumerik dan spasi Min 5 Maks 30 , ex : 'User Management'")
    private String nama;

    @NotBlank
    @NotNull(message = "Path Tidak Boleh Kosong")//group-menu
    @Pattern(regexp = "^[a-z0-9\\-\\/]{5,30}$",message = "Format Tidak Sesuai Alfanumerik dan spasi Min 5 Maks 30 , ex : 'User Management'")
    private String path;

    @JsonProperty("group_menu")
    private RelGroupMenuDTO groupMenu;

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

    public RelGroupMenuDTO getGroupMenu() {
        return groupMenu;
    }

    public void setGroupMenu(RelGroupMenuDTO groupMenu) {
        this.groupMenu = groupMenu;
    }
}
