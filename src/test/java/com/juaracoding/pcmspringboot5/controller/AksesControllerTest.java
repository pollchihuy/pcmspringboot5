package com.juaracoding.pcmspringboot5.controller;


import com.juaracoding.pcmspringboot5.config.OtherConfig;
import com.juaracoding.pcmspringboot5.dto.relation.RelMenuDTO;
import com.juaracoding.pcmspringboot5.model.Akses;
import com.juaracoding.pcmspringboot5.model.Menu;
import com.juaracoding.pcmspringboot5.repo.AksesRepo;
import com.juaracoding.pcmspringboot5.repo.MenuRepo;
import com.juaracoding.pcmspringboot5.utils.DataGenerator;
import com.juaracoding.pcmspringboot5.utils.TokenGenerator;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.util.*;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AksesControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private AksesRepo aksesRepo;

    @Autowired
    private MenuRepo menuRepo;

    private JSONObject req;
    private Akses akses;
    private Random rand ;
    private String token;
    private DataGenerator dataGenerator;
    private List<Menu> listMenu;
    @BeforeClass
    private void init(){
        token = new TokenGenerator(AuthControllerTest.authorization).getToken();
        rand  = new Random();
        req = new JSONObject();
        akses = new Akses();
        dataGenerator = new DataGenerator();
        Optional<Akses> op = aksesRepo.findTop1ByOrderByIdDesc();
        listMenu = menuRepo.findAll();/** mengambil data menu untuk diinput ke data akses nantinya*/
        akses = op.get();
    }

    @BeforeTest
    private void setup(){
        /** sifatnya optional */
    }

    @Test(priority = 0)
    void save(){
        Response response ;
        String nama = dataGenerator.dataNamaTim();
        try{
            req.put("nama", nama);
            List<RelMenuDTO> menuList = new ArrayList<>();
            for (Menu m:
                listMenu) {
                RelMenuDTO relMenuDTO = new RelMenuDTO();
                relMenuDTO.setId(m.getId());
                menuList.add(relMenuDTO);
            }
            req.put("menu_list",menuList);

            response = given().
                    header("Content-Type","application/json").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    body(req).
                    request(Method.POST,"akses");

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
            System.out.println(response.getBody().prettyPrint());
            Assert.assertEquals(intResponse,201);
            Assert.assertEquals(jsonPath.getString("message"),"Data Berhasil Disimpan");
            Assert.assertNotNull(jsonPath.getString("data"));
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
        }catch (Exception e){
            System.out.println("Error : "+e.getMessage());
            Assert.assertNotNull(null);//untuk menyatakan bahwa ini error atau diskip
        }
    }

    @Test(priority = 10)
    void update(){
        Response response ;
        req.clear();
        try{
            String reqNama = dataGenerator.dataNamaTim();
            List<RelMenuDTO> menuList = new ArrayList<>();
            /** jika ingin melakukan perbedaan data di relasi many to many nya ,
             * gunakan break misalkan pada loop ke 3, jadi saat save dia dapat semua menu
             * tetapi di update menjadi 3 menu saja
             */
            for (Menu m:
                    listMenu) {
                RelMenuDTO relMenuDTO = new RelMenuDTO();
                relMenuDTO.setId(m.getId());
                menuList.add(relMenuDTO);
            }

            System.out.println("ReqNama : "+reqNama);
            akses.setNama(reqNama);
            akses.setMenuList(akses.getMenuList());

            req.put("nama", reqNama);
            req.put("menu_list", menuList);

            response = given().
                    header("Content-Type","application/json").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    body(req).
                    request(Method.PUT,"akses/"+ akses.getId());

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
//            System.out.println(response.getBody().prettyPrint());
            Assert.assertEquals(intResponse,200);
            Assert.assertEquals(jsonPath.getString("message"),"Data Berhasil Diubah");
            Assert.assertNotNull(jsonPath.getString("data"));
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
        }catch (Exception e){
            System.out.println(e.getMessage());
            Assert.assertNotNull(null);//untuk menyatakan bahwa ini error atau diskip
        }
    }

    @Test(priority = 20)
    void findById(){
        Response response ;
        try{
            response = given().
                    header("Content-Type","application/json").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    request(Method.GET,"/akses/"+ akses.getId());

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
//            System.out.println(response.getBody().prettyPrint());
            Assert.assertEquals(intResponse,200);
            Assert.assertEquals(jsonPath.getString("message"),"Data Ditemukan");
            Assert.assertEquals(Long.parseLong(jsonPath.getString("data.id")), akses.getId());
            Assert.assertEquals(jsonPath.getString("data.nama"), akses.getNama());
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
        }catch (Exception e){
            System.out.println(e.getMessage());
            Assert.assertNotNull(null);//untuk menyatakan bahwa ini error atau diskip
        }
    }

    @Test(priority = 30)
    void findAll(){
        Response response ;
        try{
            response = given().
                    header("Content-Type","application/json").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    request(Method.GET,"/akses");

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
            List ltData = jsonPath.getList("data.content");
            long intData =  aksesRepo.count();
//            System.out.println(response.getBody().prettyPrint());
            Assert.assertEquals(intResponse,200);
            Assert.assertEquals(jsonPath.getString("message"),"Data Ditemukan");
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
// ======================================================================================================================================================
            Assert.assertEquals(jsonPath.getString("data.sort_by"),"id");
            Assert.assertEquals(Integer.parseInt(jsonPath.getString("data.current_page")),0);
            Assert.assertEquals(jsonPath.getString("data.column_name"),"id");
            Assert.assertNotNull(jsonPath.getString("data.total_pages"));
            Assert.assertEquals(jsonPath.getString("data.sort"),"asc");
            Assert.assertEquals(Integer.parseInt(jsonPath.getString("data.size_per_page")), OtherConfig.getDefaultPaginationSize());
            Assert.assertEquals(jsonPath.getString("data.value"),"");
            Assert.assertEquals(Integer.parseInt(jsonPath.getString("data.total_data")),intData);

        }catch (Exception e){
            System.out.println(e.getMessage());
            Assert.assertNotNull(null);//untuk menyatakan bahwa ini error atau diskip
        }
    }

    @Test(priority = 40)
    void findByParam(){
        Response response ;
        String pathVariable = "/akses/asc/id/0";
        String strValue = akses.getNama();

        try{
            response = given().
                    header("Content-Type","application/json").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    params("column","nama").
                    params("value",strValue).
                    params("size",10).
                    request(Method.GET,pathVariable);

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
            List<Map<String,Object>> ltData = jsonPath.getList("data.content");
            int intData = ltData.size();
            Map<String,Object> map = ltData.get(0);

            System.out.println(response.getBody().prettyPrint());
            Assert.assertEquals(intResponse,200);
            Assert.assertEquals(jsonPath.getString("message"),"Data Ditemukan");
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
// ======================================================================================================================================================
            Assert.assertEquals(jsonPath.getString("data.sort_by"),"id");
            Assert.assertEquals(Integer.parseInt(jsonPath.getString("data.current_page")),0);
            Assert.assertEquals(jsonPath.getString("data.column_name"),"nama");
            Assert.assertNotNull(jsonPath.getString("data.total_pages"));
            Assert.assertEquals(Integer.parseInt(jsonPath.getString("data.total_data")),intData);
            Assert.assertEquals(jsonPath.getString("data.sort"),"asc");
            Assert.assertEquals(Integer.parseInt(jsonPath.getString("data.size_per_page")), 10);
            Assert.assertEquals(jsonPath.getString("data.value"),strValue);
// ======================================================================================================================================================

            Assert.assertEquals(map.get("nama"),strValue);
            Assert.assertEquals(Long.parseLong(map.get("id").toString()), akses.getId());

        }catch (Exception e){
            System.out.println(e.getMessage());
            Assert.assertNotNull(null);//untuk menyatakan bahwa ini error atau diskip
        }
    }

    @Test(priority = 50)
    void uploadExcel(){
        Response response ;
        try{
            response = given().
                    header("Content-Type","multipart/form-data").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    multiPart("file",new File(System.getProperty("user.dir")+"/src/test/resources/data-test/akses.xlsx"),
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet").
                    request(Method.POST,"akses/upload-excel");

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
            Assert.assertEquals(intResponse,201);
            Assert.assertEquals(jsonPath.getString("message"),"Data Berhasil Disimpan");
            Assert.assertNotNull(jsonPath.getString("data"));
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
        }catch (Exception e){
            System.out.println(e.getMessage());
            Assert.assertNotNull(null);//untuk menyatakan bahwa ini error atau diskip
        }
    }
    @Test(priority = 50)
    void downloadExcel(){
        Response response ;
        try{
            response = given().
                    header("Content-Type","application/json").
                    header("accept","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    params("column","nama").
                    params("value", akses.getNama()).
                    request(Method.GET,"akses/excel");

            int intResponse = response.getStatusCode();
            Assert.assertEquals(intResponse,200);
            /** khusus untuk download file harus di cek header nya */
            Assert.assertTrue(response.getHeader("Content-Disposition").contains(".xlsx"));// file nya memiliki extension .xlsx
            Assert.assertEquals(response.getHeader("Content-Type"),"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");// content type wajib ini untuk excel
        }catch (Exception e){
            System.out.println(e.getMessage());
            Assert.assertNotNull(null);//untuk menyatakan bahwa ini error atau diskip
        }
    }

    @Test(priority = 60)
    void downloadPdf(){
        Response response ;
        try{
            response = given().
                    header("Content-Type","application/json").
                    header("accept","application/pdf").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    params("column","nama").
                    params("value", akses.getNama()).
                    request(Method.GET,"akses/pdf");

            int intResponse = response.getStatusCode();
            Assert.assertEquals(intResponse,200);
            /** khusus untuk download file harus di cek header nya */
            Assert.assertTrue(response.getHeader("Content-Disposition").contains(".pdf"));// file nya memiliki extension .xlsx
            Assert.assertEquals(response.getHeader("Content-Type"),"application/pdf");// content type wajib ini untuk excel
        }catch (Exception e){
            System.out.println(e.getMessage());
            Assert.assertNotNull(null);//untuk menyatakan bahwa ini error atau diskip
        }
    }

    @Test(priority = 999)
    void delete(){
        Response response ;
        try{
            response = given().
                    header("Content-Type","application/json").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    request(Method.DELETE,"/akses/"+ akses.getId());

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
//            System.out.println(response.getBody().prettyPrint());
            Assert.assertEquals(intResponse,200);
            Assert.assertEquals(jsonPath.getString("message"),"Data Berhasil Dihapus");
            Assert.assertNotNull(jsonPath.getString("data"));
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
        }catch (Exception e){
            System.out.println(e.getMessage());
            Assert.assertNotNull(null);//untuk menyatakan bahwa ini error atau diskip
        }
    }
}