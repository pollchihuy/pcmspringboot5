package com.juaracoding.pcmspringboot5.repo;

import com.juaracoding.pcmspringboot5.model.GroupMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupMenuRepo extends JpaRepository<GroupMenu, Long> {

    /** Derived Query
     *  Native Query / JPQL
     */
    //Nama -> SELECT * FROM MstGroupMenu WHERE toLower(NamaGroupMenu) LIKE '%toLower(?)%'
    Page<GroupMenu> findByNamaContains(Pageable pageable, String nama);
    List<GroupMenu> findByNamaContains(String nama);

    /** SELECT TOP 1 *  FROM MstGroupMenu ORDER BY ID DESC; */
    Optional<GroupMenu> findTop1ByOrderByIdDesc();
//    Page<GroupMenu> findByDeskripsiContains(Pageable pageable, String nama);
//    Page<GroupMenu> findByPathContainsAndDeskripsiContains(Pageable pageable, String value,String value2);
}
