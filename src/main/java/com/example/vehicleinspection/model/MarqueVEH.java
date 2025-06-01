package com.example.vehicleinspection.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MARQUE_VEH")
public class MarqueVEH {
    @Id
    @Column(name = "CD_MARQ")
    private String cdMarque;
    @Column(name = "DESIGL")
    private String desiGL;
    @Column(name = "DESIGA")
    private String desiGA;

    public String getCdMarque() {
        return cdMarque;
    }

    public void setCdMarque(String cdMarque) {
        this.cdMarque = cdMarque;
    }

    public String getDesiGL() {
        return desiGL;
    }

    public void setDesiGL(String desiGL) {
        this.desiGL = desiGL;
    }

    public String getDesiGA() {
        return desiGA;
    }

    public void setDesiGA(String desiGA) {
        this.desiGA = desiGA;
    }

    public MarqueVEH(String cdMarque, String desiGL, String desiGA) {
        this.cdMarque = cdMarque;
        this.desiGL = desiGL;
        this.desiGA = desiGA;
    }

    public MarqueVEH() {
    }
}
