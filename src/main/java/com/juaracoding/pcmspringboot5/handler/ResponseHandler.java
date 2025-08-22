package com.juaracoding.pcmspringboot5.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/** Untuk Format Response Seluruh API */
public class ResponseHandler {

    public ResponseEntity<Object> handleResponse(
            String message,
            HttpStatus status,
            Object data,
            Object errorCode
    ){

        Map<String,Object> map = new HashMap<>();
        map.put("message",message);
        map.put("status",status.value());
        map.put("data",data==null?"":data);
        map.put("timestamp", Instant.now().toString());
        map.put("success",!status.isError());
        if(errorCode!=null) {
            map.put("errorCode",errorCode);
        }
        return new ResponseEntity<>(map,status);

    }
}
