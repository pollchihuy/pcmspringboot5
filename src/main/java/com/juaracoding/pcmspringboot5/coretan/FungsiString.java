package com.juaracoding.pcmspringboot5.coretan;


public class FungsiString {

    public Integer hitungPanjangString(String str){
        return str.length();
    }

    public String cobaSubString(String str,Integer indexAwal,Integer indexAkhir){
        return str.substring(indexAwal,indexAkhir);
    }
    public String cobaSubString(String str,Integer indexAwal){
        return str.substring(indexAwal);
    }
}
