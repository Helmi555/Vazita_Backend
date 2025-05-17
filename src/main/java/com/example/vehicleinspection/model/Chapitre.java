package com.example.vehicleinspection.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="CHAPITRES")
public class Chapitre {
    @Id
    @Column(name = "CODE_CHAPITRE")
    private Integer codeChapitre;

    @Column(name = "LIBELLE_CHAPITRE")
    private  String libelleChapitre;

    public Chapitre(Integer codeChapitre, String libelleChapitre) {
        this.codeChapitre = codeChapitre;
        this.libelleChapitre = libelleChapitre;
    }

    public Integer getCodeChapitre() {
        return codeChapitre;
    }

    public void setCodeChapitre(Integer codeChapitre) {
        this.codeChapitre = codeChapitre;
    }

    public String getLibelleChapitre() {
        return libelleChapitre;
    }

    public void setLibelleChapitre(String libelleChapitre) {
        this.libelleChapitre = libelleChapitre;
    }

    public Chapitre() {
    }
}
