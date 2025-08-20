package com.juaracoding.pcmspringboot5.model;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "MstAkses")
public class Akses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name="NamaAkses", nullable=false,unique=true, length=30)
    private String nama;

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

    @ManyToMany
    @JoinTable(name = "MapAksesMenu",
    uniqueConstraints = @UniqueConstraint(name = "unq-akses-to-menu",columnNames = {"IDAkses","IDMenu"}),
    joinColumns = @JoinColumn(name = "IDAkses",foreignKey = @ForeignKey(name = "fk-toAkses")),
    inverseJoinColumns = @JoinColumn(name = "IDMenu",foreignKey = @ForeignKey(name = "fk-toMenu"))
    )
    private List<Menu> menuList;
}
