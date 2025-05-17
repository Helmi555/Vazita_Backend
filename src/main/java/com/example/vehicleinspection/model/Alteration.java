package com.example.vehicleinspection.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ALTERATIONS")
public class Alteration {
    @Id
    @Column(name="CODE_ALTERATION")
    private Integer codeAlteration;


    @Column(name="CODE_CHAPITRE")
    private Integer codeChapitre;


    @Column(name="CODE_POINT")
    private Integer codePoint;


    @Column(name="LIBELLE_ALTERATION")
    private String libelleAlteration;

    public Integer getCodeAlteration() {
        return codeAlteration;
    }

    public void setCodeAlteration(Integer codeAlteration) {
        this.codeAlteration = codeAlteration;
    }

    public Integer getCodeChapitre() {
        return codeChapitre;
    }

    public void setCodeChapitre(Integer codeChapitre) {
        this.codeChapitre = codeChapitre;
    }

    public Integer getCodePoint() {
        return codePoint;
    }

    public void setCodePoint(Integer codePoint) {
        this.codePoint = codePoint;
    }

    public String getLibelleAlteration() {
        return libelleAlteration;
    }

    public void setLibelleAlteration(String libelleAlteration) {
        this.libelleAlteration = libelleAlteration;
    }

    public Alteration(Integer codeAlteration, Integer codeChapitre, Integer codePoint, String libelleAlteration) {
        this.codeAlteration = codeAlteration;
        this.codeChapitre = codeChapitre;
        this.codePoint = codePoint;
        this.libelleAlteration = libelleAlteration;
    }

    public Alteration() {
    }

    @Override
    public String toString() {
        return "Alteration{" +
                "codeAlteration=" + codeAlteration +
                ", codeChapitre=" + codeChapitre +
                ", codePoint=" + codePoint +
                ", libelleAlteration='" + libelleAlteration + '\'' +
                '}';
    }
}
