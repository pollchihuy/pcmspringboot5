//package com.juaracoding.pcmspringboot5.controller;
//
//import com.juaracoding.pcmspringboot5.model.Customer;
//import com.juaracoding.pcmspringboot5.model.Email;
//import jakarta.validation.Valid;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("customer")
//public class CutomerController {
//
//    // /customer
//    @PostMapping
//    public Object save(@Valid @RequestBody Customer customer){
//        System.out.println("Customer Name "+customer.getNama());
//        System.out.println("Customer Umur "+customer.getUmur());
//        System.out.println("Customer tanggalLahir "+customer.getTanggalLahir().toString());
//        List<Email> list = customer.getEmailCustomer();
//        System.out.println("========= EMAIL CUSTOMER =========");
//        for(Email email : list){
//            System.out.println(email.getEmail());
//            System.out.println(email.getHost());
//            System.out.println("=====================================");
//        }
//        System.out.println("Customer Level ID : "+customer.getLevel().getId());
//        System.out.println("Customer Level Name : "+customer.getLevel().getLevelName());
//
//        return customer;
//    }
//
//    // /customer
//    @GetMapping
//    public Object findAll(){
//        return "Get - FindAll";
//    }
//
//    /** Contoh Path Variable
//     *  /customer/asc/id
//     * */
//    @GetMapping("/{sort}/{sort_by}/{page}")
//    public Object findByParam(
//            @PathVariable(value = "sort_by") String sortBy,
//            @PathVariable String sort,
//            @PathVariable Integer page
//    ){
//        System.out.println("Sort : "+sort);
//        System.out.println("SortBy : "+sortBy);
//        System.out.println("page : "+page);
//
//        return "Get - findByParam";
//    }
//
//    /** Contoh Path Variable
//     *  /customer/asc/id
//     * */
//
//    // /customer
//    @GetMapping("/find")
//    public Object findByReqParam(
//            @RequestParam String value,
//            @RequestParam(value = "column_name") String columnName,
//            @RequestParam Integer size
//    ){
//        System.out.println("columnName : "+columnName);
//        System.out.println("size : "+size);
//        System.out.println("value : "+value);
//
//        return "Get - findByReqParam";
//    }
//
//    @GetMapping("/multipart")
//    public Object reqMultipart(
//            @RequestParam String data1,
//            @RequestParam String data2,
//            @RequestParam MultipartFile file1
//    ){
//        System.out.println("data1 : "+data1);
//        System.out.println("data2 : "+data2);
//        System.out.println("file1 : "+file1.getOriginalFilename());
//
//        return "Get - reqMultipart";
//    }
//
//    @GetMapping("/multipart-array")
//    public Object reqMultipartArray(
//            @RequestParam String[] data1,
//            @RequestParam String[] data2,
//            @RequestParam MultipartFile[] file1
//    ){
//        for (String data : data1) {
//            System.out.println("data1 : "+data);
//        }
//        for (String data : data2) {
//            System.out.println("data2 : "+data);
//        }
//        for (MultipartFile file : file1) {
//            System.out.println("file1 : "+file.getOriginalFilename());
//        }
//        return "Get - reqMultipartArray";
//    }
//}