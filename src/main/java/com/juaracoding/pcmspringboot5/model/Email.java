package com.juaracoding.pcmspringboot5.model;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "MstEmail",
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"email","host"},name = "idx-email-host"))
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Email",length = 64,unique=true,nullable = false)
    private String email;

    @Column(name = "Host",length = 20,unique=true,
            nullable = false)
    private String host;

    /** Change / Adapt When Migration */
    @Column(name = "Payroll",columnDefinition = "DECIMAL (10,2) default 0")
    private Double payroll;

    @Column(name = "CreatedBy",updatable = false,nullable = false)
    private Long createdBy;
    @Column(name = "CreatedDate",updatable = false,nullable = false)
    private Date createdDate;

    @Column(name = "ModifiedBy",insertable = false)
    private Long modifiedBy;//updatedBy
    @Column(name = "ModifiedDate",insertable = false)
    private Date modifiedDate;//updateAt

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
