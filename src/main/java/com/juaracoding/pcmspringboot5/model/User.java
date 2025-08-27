package com.juaracoding.pcmspringboot5.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

@Entity
@Table(name = "MstUser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Username",unique = true,nullable = false,length = 20)
    private String username;
    @Column(name = "Password",nullable = false,length = 64)
    private String password;

    @Column(name = "NamaLengkap",nullable = false,length = 50)
    private String namaLengkap;
    @Column(name = "Email",nullable = false,length = 64)
    private String email;
    @Column(name = "NoHp",nullable = false,length = 17)
    private String noHp;
    @Column(name = "TanggalLahir",nullable = false)
    private LocalDate tanggalLahir;
    @Column(name = "LinkImage")
    private String linkImage;
    @Column(name = "PathImage")
    private String pathImage;
    @Column(name = "IsRegistered")
    private Boolean isRegistered;
    @Column(name = "OTP",length = 64)
    private String otp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDAkses",foreignKey = @ForeignKey(name = "fk-user-to-akses"))
    private Akses akses;

    @Transient
    private Integer umur;

    @Column(name = "TokenEstafet",length = 64)
    private String tokenEstafet;

    @Column(name = "CreatedBy",updatable = false,nullable = false)
    private Long createdBy=1L;

    @Column(name = "CreatedDate",updatable = false,nullable = false)
    @CreationTimestamp
    private Date createdDate;

    @Column(name = "ModifiedBy",insertable = false)
    private Long modifiedBy;

    @Column(name = "ModifiedDate",insertable = false)
    @UpdateTimestamp
    private Date modifiedDate;

    public Akses getAkses() {
        return akses;
    }

    public void setAkses(Akses akses) {
        this.akses = akses;
    }

    public String getTokenEstafet() {
        return tokenEstafet;
    }

    public void setTokenEstafet(String tokenEstafet) {
        this.tokenEstafet = tokenEstafet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public Boolean getRegistered() {
        return isRegistered;
    }

    public void setRegistered(Boolean registered) {
        isRegistered = registered;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Integer getUmur() {
        return Period.between(tanggalLahir, LocalDate.now()).getYears();
    }

    public void setUmur(Integer umur) {
        this.umur = umur;
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
