package com.juaracoding.pcmspringboot5.repo;

import com.juaracoding.pcmspringboot5.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepo extends JpaRepository<Menu,Long> {
}
