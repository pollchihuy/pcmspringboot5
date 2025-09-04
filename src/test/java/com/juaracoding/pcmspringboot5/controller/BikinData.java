package com.juaracoding.pcmspringboot5.controller;

import com.juaracoding.pcmspringboot5.model.GroupMenu;
import com.juaracoding.pcmspringboot5.repo.GroupMenuRepo;
import com.juaracoding.pcmspringboot5.utils.DataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BikinData extends AbstractTestNGSpringContextTests {

    @Autowired
    GroupMenuRepo groupMenuRepo;

    DataGenerator dataGenerator = new DataGenerator();

    @Test
    public void inputData(){
        List<GroupMenu> groupMenus = groupMenuRepo.findAll();
        for (int i = 0; i < 1000000; i++) {
            GroupMenu groupMenu = new GroupMenu();
            groupMenu.setNama(dataGenerator.genDataAlfabet(5,30));
            groupMenu.setCreatedBy(1L);
            groupMenuRepo.save(groupMenu);
        }
//        groupMenuRepo.saveAll(groupMenus);
    }
}
