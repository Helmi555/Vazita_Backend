package com.example.vehicleinspection.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = " MES_DOSSIERS")
public class Dossier {
    @Id
    @Column(name="N_DOSSIER",precision = 10,scale = 0)
    private Integer numDossier;

    @Column(name="NUM_CHASSIS")
    private String numChassis;

    @Column(name = "IMMATRICULATION")
    private String immatriculation;

    @Column(name = "C_PISTE")
    private Integer cPiste;

     @Column(name="DATE_HEURE_ENREGISTREMENT")
    private LocalDateTime dateHeureEnregistrement;


    public Dossier(Integer numDossier, String numChassis, String immatriculation, Integer cPiste, LocalDateTime dateHeureEnregistrement) {
        this.numDossier = numDossier;
        this.numChassis = numChassis;
        this.immatriculation = immatriculation;
        this.cPiste = cPiste;
        this.dateHeureEnregistrement = dateHeureEnregistrement;
    }

    public Integer getNumDossier() {
        return numDossier;
    }

    public void setNumDossier(Integer numDossier) {
        this.numDossier = numDossier;
    }

    public String getNumChassis() {
        return numChassis;
    }

    public void setNumChassis(String numChassis) {
        this.numChassis = numChassis;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public Integer getcPiste() {
        return cPiste;
    }

    public void setcPiste(Integer cPiste) {
        this.cPiste = cPiste;
    }

    public LocalDateTime getDateHeureEnregistrement() {
        return dateHeureEnregistrement;
    }

    public void setDateHeureEnregistrement(LocalDateTime dateHeureEnregistrement) {
        this.dateHeureEnregistrement = dateHeureEnregistrement;
    }

    public Dossier() {
    }
}
