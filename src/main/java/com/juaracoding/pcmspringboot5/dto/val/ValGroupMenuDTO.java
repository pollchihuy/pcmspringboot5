package com.juaracoding.pcmspringboot5.dto.val;

import com.juaracoding.pcmspringboot5.utils.ConstantMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ValGroupMenuDTO {

    @NotBlank
    @NotNull(message = ConstantMessage.NAMA_GROUP_MENU_NULL)
    @Pattern(regexp = "^[\\w \\s]{5,30}$",message = "Format Tidak Sesuai Alfanumerik dan spasi Min 5 Maks 30 , ex : 'User Management'")
    private String nama;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
