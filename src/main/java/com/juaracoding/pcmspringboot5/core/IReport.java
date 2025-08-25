package com.juaracoding.pcmspringboot5.core;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/** jatah sequence di kontrak ini adalah 101 s.d 200 */
public interface IReport<T> {
    public ResponseEntity<Object> uploadExcel(MultipartFile file, HttpServletRequest request);//101-110
    public List<T> convertListWorkBookToListEntity(List<Map<String,String>> workBookData, Long userId);//111-120
    public Object downloadReportExcel(String column, String value, HttpServletRequest request, HttpServletResponse response);//121-130
    public Object downloadReportPDF(String column, String value, HttpServletRequest request, HttpServletResponse response);//131-140
}
