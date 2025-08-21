//package com.juaracoding.pcmspringboot5.model;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.validation.constraints.Pattern;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import java.time.LocalDate;
//import java.util.List;
//
///**
// {
//     "nama":"Paul",
//     "umur": 30,
//     "tanggal_lahir":"1995-12-12",
//     "notes" : null,
//     "email_customer":
//     [
//         {
//             "email":"paul@gmail.com",
//             "host":"gmail"
//         },
//         {
//             "email":"paul@yahoo.com",
//             "host":"yahoo"
//         }
//     ],
//     "level" : {
//                 "id":1,
//                 "level_name": "silver"
//             }
// }
// */
//public class Customer {
//
//    private Long id;
//
////    int intX , intY , intZ ;
//    @Pattern(regexp = "^[\\w]{10,20}$",message = "Error brOh format alfanumerik min 10 maks 20")
//    private String nama;
//    private Integer umur;
//    @Pattern(regexp = "^[\\d]{6}$",message = "Format OTP Tidak Valid")
//    @DateTimeFormat(pattern = "dd-MM-yyyy")
//    //MM - bulan dalam angka
//    //MMM - bulan dalam text tapi singkatan contoh aug, jun,jul, sept
//    //MMMM - full text
//    private String otp;
//
//    @JsonProperty("tanggal_lahir")
//    private LocalDate tanggalLahir;
//
//    @JsonProperty("email_customer")
//    private List<Email> emailCustomer;
//
//    private LevelCustomer level;
//
//    public String getOtp() {
//        return otp;
//    }
//
//    public void setOtp(String otp) {
//        this.otp = otp;
//    }
//
//    public String getNama() {
//        return nama;
//    }
//
//    public void setNama(String nama) {
//        this.nama = nama;
//    }
//
//    public Integer getUmur() {
//        return umur;
//    }
//
//    public void setUmur(Integer umur) {
//        this.umur = umur;
//    }
//
//    public LocalDate getTanggalLahir() {
//        return tanggalLahir;
//    }
//
//    public void setTanggalLahir(LocalDate tanggalLahir) {
//        this.tanggalLahir = tanggalLahir;
//    }
//
//    public List<Email> getEmailCustomer() {
//        return emailCustomer;
//    }
//
//    public void setEmailCustomer(List<Email> emailCustomer) {
//        this.emailCustomer = emailCustomer;
//    }
//
//    public LevelCustomer getLevel() {
//        return level;
//    }
//
//    public void setLevel(LevelCustomer level) {
//        this.level = level;
//    }
//}
