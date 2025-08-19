package com.juaracoding.pcmspringboot5.model;


import jakarta.persistence.*;

@Entity
@Table(name = "MstEmail",
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"email","host"},name = "idx-email-host"))
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_email")
    @SequenceGenerator(name = "seq_email",sequenceName = "seq_email",allocationSize = 1)
    private Long id;

    private String email;
    private String host;


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
