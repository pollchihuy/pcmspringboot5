package com.juaracoding.pcmspringboot5.repo;

import com.juaracoding.pcmspringboot5.model.Akses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AksesRepo extends JpaRepository<Akses,Long> {
    Page<Akses> findByNamaContains(Pageable pageable, String nama);
    List<Akses> findByNamaContains(String nama);

    Optional<Akses> findTop1ByOrderByIdDesc();
}
