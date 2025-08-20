package com.juaracoding.pcmspringboot5.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "MstMenu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NamaMenu", nullable = false,unique = true,length = 30)
    private String nama;

    @Column(name = "Path", nullable = false,unique = true,length = 30)
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDGroupMenu", nullable = false,foreignKey = @ForeignKey(name = "fk-to-groupmenu"))
    private GroupMenu groupMenu;

    @Column(name = "CreatedBy",updatable = false,nullable = false)
    private Long createdBy;

    @Column(name = "CreatedDate",updatable = false,nullable = false)
    @CreationTimestamp
    private Date createdDate;

    @Column(name = "ModifiedBy",insertable = false)
    private Long modifiedBy;//updatedBy

    @Column(name = "ModifiedDate",insertable = false)
    @UpdateTimestamp
    private Date modifiedDate;//updateAt

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
