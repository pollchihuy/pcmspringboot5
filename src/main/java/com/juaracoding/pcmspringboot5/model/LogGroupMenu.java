package com.juaracoding.pcmspringboot5.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "LogGroupMenu")
public class LogGroupMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "IDGroupMenu",nullable = false)
    private Long idGroupMenu;

    @Column(name = "NamaGroupMenu", nullable = false,length = 30)
    private String nama;

    @Column(name = "CreatedBy",updatable = false,nullable = false)
    private Long createdBy=1L;

    @Column(name = "CreatedDate",updatable = false,nullable = false)
    @CreationTimestamp
    private Date createdDate;

    @Column(name = "Flag")// i e d atau i u d
    private Character flag;

    public Character getFlag() {
        return flag;
    }

    public void setFlag(Character flag) {
        this.flag = flag;
    }

    public Long getIdGroupMenu() {
        return idGroupMenu;
    }

    public void setIdGroupMenu(Long idGroupMenu) {
        this.idGroupMenu = idGroupMenu;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
