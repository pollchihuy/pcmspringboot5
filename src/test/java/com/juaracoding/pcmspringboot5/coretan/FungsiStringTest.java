package com.juaracoding.pcmspringboot5.coretan;

import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class FungsiStringTest {

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Start Suite Jalan");
    }

    FungsiString fs;
    String valStr = "";
    @BeforeClass
    public void init(){
        fs = new FungsiString();
        valStr = "Paul";
    }

    @Test(priority = 0)
    public void hitungPanjangString(){
        int output = fs.hitungPanjangString("Paul");
        Assert.assertEquals(4,output);
    }

    @Test(priority = 5)
    public void cobaSubString2(){
        String subStr = fs.cobaSubString(valStr,1);
        Assert.assertEquals("aul",subStr);
    }

    @Test(priority = 10)
    public void cobaSubString(){
        String subStr = fs.cobaSubString(valStr,1,4);
        Assert.assertEquals("aul",subStr);
    }

    @AfterClass
    public void end(){
        fs= null;
    }
}
