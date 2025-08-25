package com.juaracoding.pcmspringboot5.utils;

import com.juaracoding.pcmspringboot5.handler.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GlobalResponse {

    public ResponseEntity<Object> tidakDapatDiproses(String errorCode){
        return new ResponseHandler().handleResponse("Tidak Dapat DiProses", HttpStatus.BAD_REQUEST,null,errorCode);
    }
    public ResponseEntity<Object> dataTidakDitemukan(String errorCode){
        return new ResponseHandler().handleResponse("Data Tidak Ditemukan", HttpStatus.BAD_REQUEST,null,errorCode);
    }
    public ResponseEntity<Object> andaBerniatJahat(String errorCode){
        return new ResponseHandler().handleResponse("Anda Berniat Jahat", HttpStatus.BAD_REQUEST,null,errorCode);
    }

    public ResponseEntity<Object> internalServerError(String errorCode){
        return new ResponseHandler().handleResponse("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR,null,errorCode);
    }

    public ResponseEntity<Object> dataBerhasilDisimpan(){
        return new ResponseHandler().handleResponse("Data Berhasil Disimpan", HttpStatus.CREATED,null,null);
    }

    public ResponseEntity<Object> dataBerhasilDiubah(){
        return new ResponseHandler().handleResponse("Data Berhasil Diubah", HttpStatus.OK,null,null);
    }
    public ResponseEntity<Object> dataBerhasilDihapus(){
        return new ResponseHandler().handleResponse("Data Berhasil Dihapus", HttpStatus.OK,null,null);
    }
    public ResponseEntity<Object> dataDitemukan(Object data){
        return new ResponseHandler().handleResponse("Data Ditemukan", HttpStatus.OK,data,null);
    }

    public ResponseEntity<Object> formatFileHarusExcel(String errorCode){
        return new ResponseHandler().handleResponse("Format File Harus Excel", HttpStatus.BAD_REQUEST,null,errorCode);
    }
    public ResponseEntity<Object> fileExcelKosong(String errorCode){
        return new ResponseHandler().handleResponse("File Excel Kosong", HttpStatus.BAD_REQUEST,null,errorCode);
    }
}
