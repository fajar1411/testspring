package com.example.testSpring.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_m_user")
public class User {
    @Id
    private Integer id;

    @OneToOne
    @JoinColumn(name = "id", nullable = false, referencedColumnName = "id")
    private Amartek amartek;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;
    @Column(name = "verification_code")
    private String verificationCode;
    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<TrAmartek> trAmarteks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<TrAmartek> getTrAmarteks() {
        return trAmarteks;
    }

    public void setTrAmarteks(Set<TrAmartek> trAmarteks) {
        this.trAmarteks = trAmarteks;
    }

    public Amartek getAmartek() {
        return amartek;
    }

    public void setAmartek(Amartek amartek) {
        this.amartek = amartek;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
