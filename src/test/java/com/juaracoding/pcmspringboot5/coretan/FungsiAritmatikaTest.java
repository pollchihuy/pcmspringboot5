package com.juaracoding.pcmspringboot5.coretan;

import org.junit.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class FungsiAritmatikaTest {

    FungsiAritmatika fungsiAritmatika;

    @BeforeClass
    public void init() {
        fungsiAritmatika = new FungsiAritmatika();
    }


    @Test(priority = 0)
    public void tambah(){
        int intOutput = fungsiAritmatika.tambah(2,2);
        Assert.assertEquals(4, intOutput);
    }


    @Test(priority = 10)
    public void kurang(){
        int intOutput = fungsiAritmatika.kurang(2,2);
        Assert.assertEquals(0, intOutput);
    }

    @AfterSuite
    public void endSuite(){
        System.out.println("END SUITE !!");
    }
}
