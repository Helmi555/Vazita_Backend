package com.example.vehicleinspection.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "POINTS_DEFAUTS")
public class PointDefaut {

    @Id
    @Column(name="CODE_POINT")
    private Integer codePoint;

    @Column(name="CODE_CHAPITRE")
    private Integer codeChapitre;

    @Column(name="LIBELLE_POINT")
    private String libellePoint;

    public PointDefaut() {}

    public PointDefaut(Integer codePoint, Integer codeChapitre, String libellePoint) {
        this.codePoint = codePoint;
        this.codeChapitre = codeChapitre;
        this.libellePoint = libellePoint;
    }

    public Integer getCodePoint() {
        return codePoint;
    }

    public void setCodePoint(Integer codePoint) {
        this.codePoint = codePoint;
    }

    public Integer getCodeChapitre() {
        return codeChapitre;
    }

    public void setCodeChapitre(Integer codeChapitre) {
        this.codeChapitre = codeChapitre;
    }

    public String getLibellePoint() {
        return libellePoint;
    }

    public void setLibellePoint(String libellePoint) {
        this.libellePoint = libellePoint;
    }
}
