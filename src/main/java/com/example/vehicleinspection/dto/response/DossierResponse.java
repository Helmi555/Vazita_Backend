package com.example.vehicleinspection.dto.response;

import com.example.vehicleinspection.model.Dossier;
import jakarta.persistence.Column;

import java.time.LocalDateTime;

public class DossierResponse {

    private Integer numDossier;

    private String numChassis;

    private String immatriculation;

    private Integer cPiste;

    private LocalDateTime dateHeureEnregistrement;


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

    public DossierResponse() {
    }
    public DossierResponse(Integer numDossier, String numChassis, String immatriculation, Integer cPiste, LocalDateTime dateHeureEnregistrement) {
        this.numDossier = numDossier;
        this.numChassis = numChassis;
        this.immatriculation = immatriculation;
        this.cPiste = cPiste;
        this.dateHeureEnregistrement = dateHeureEnregistrement;
    }
    public static DossierResponse dossierToDossierResponse(Dossier dossier) {
        return new DossierResponse(
                dossier.getNumDossier(),
                dossier.getNumChassis(),
                dossier.getImmatriculation(),
                dossier.getcPiste(),
                dossier.getDateHeureEnregistrement()

        );
    }
}
