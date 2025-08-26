package com.juaracoding.pcmspringboot5.repo;

import com.juaracoding.pcmspringboot5.model.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MenuRepo extends JpaRepository<Menu,Long> {
    Page<Menu> findByNamaContains(Pageable pageable, String nama);
    Page<Menu> findByPathContains(Pageable pageable, String nama);

    List<Menu> findByNamaContains(String nama);
    List<Menu> findByPathContains(String nama);
}
