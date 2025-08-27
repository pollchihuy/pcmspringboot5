package com.juaracoding.pcmspringboot5.repo;

import com.juaracoding.pcmspringboot5.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User,Long> {
    Page<User> findByNamaLengkapContains(Pageable pageable, String nama);
    Page<User> findByEmailContains(Pageable pageable, String nama);
    Page<User> findByNoHpContains(Pageable pageable, String nama);
    Page<User> findByUsernameContains(Pageable pageable, String nama);
//    Page<User> findByTangglLahirContains(Pageable pageable, String nama);

    List<User> findByNamaLengkapContains(String nama);
    List<User> findByEmailContains(String nama);
    List<User> findByNoHpContains(String nama);
    List<User> findByUsernameContains(String nama);
//    List<User> findByTangglLahirContains(String nama);
}
