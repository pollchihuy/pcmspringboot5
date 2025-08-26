package com.juaracoding.pcmspringboot5.coretan;

import com.juaracoding.pcmspringboot5.dto.resp.RespGroupMenuDTO;

public class Banyak {

    public static void main(String[] args) {
        RespGroupMenuDTO groupMenuDTO = new RespGroupMenuDTO();
        groupMenuDTO.setNama("Hue");
        groupMenuDTO.setId(1L);

        System.out.println(groupMenuDTO.getNama());
        System.out.println(groupMenuDTO.getId());
    }
}
