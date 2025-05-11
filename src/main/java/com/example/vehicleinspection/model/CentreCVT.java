package com.example.vehicleinspection.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="CENTRE_CVT")
public class CentreCVT {

    @Id
    @Column(name = "ID_CENTRE")
    private Integer idCentre;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "MACHINE")
    private String machine;

    @Column(name = "SID")
    private String sid;

    public CentreCVT(Integer idCentre, String username, String password, String machine, String sid) {
        this.idCentre = idCentre;
        this.username = username;
        this.password = password;
        this.machine = machine;
        this.sid = sid;
    }

    public Integer getIdCentre() {
        return idCentre;
    }

    public void setIdCentre(Integer idCentre) {
        this.idCentre = idCentre;
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

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public CentreCVT() {
    }
}
