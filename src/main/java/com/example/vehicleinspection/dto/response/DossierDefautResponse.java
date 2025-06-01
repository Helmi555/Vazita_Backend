package com.example.vehicleinspection.dto.response;

import com.example.vehicleinspection.model.DossierDefaut;
import com.example.vehicleinspection.model.keys.DossierDefautId;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DossierDefautResponse {

    private Integer nDossier;
    private String codeDefaut;
    private Integer numCentre;
    private LocalDate dateControl;
    private LocalDateTime dateHeureEnregistrement;
    private String numChassis;
    private String matAgent;
    private String codemarque;

    public Integer getnDossier() {
        return nDossier;
    }

    public void setnDossier(Integer nDossier) {
        this.nDossier = nDossier;
    }

    public String getCodeDefaut() {
        return codeDefaut;
    }

    public void setCodeDefaut(String codeDefaut) {
        this.codeDefaut = codeDefaut;
    }

    public Integer getNumCentre() {
        return numCentre;
    }

    public void setNumCentre(Integer numCentre) {
        this.numCentre = numCentre;
    }

    public LocalDate getDateControl() {
        return dateControl;
    }

    public void setDateControl(LocalDate dateControl) {
        this.dateControl = dateControl;
    }

    public LocalDateTime getDateHeureEnregistrement() {
        return dateHeureEnregistrement;
    }

    public void setDateHeureEnregistrement(LocalDateTime dateHeureEnregistrement) {
        this.dateHeureEnregistrement = dateHeureEnregistrement;
    }

    public String getNumChassis() {
        return numChassis;
    }

    public void setNumChassis(String numChassis) {
        this.numChassis = numChassis;
    }

    public String getMatAgent() {
        return matAgent;
    }

    public void setMatAgent(String matAgent) {
        this.matAgent = matAgent;
    }

    public DossierDefautResponse(Integer nDossier, String codeDefaut, Integer numCentre, LocalDate dateControl, LocalDateTime dateHeureEnregistrement, String numChassis, String matAgent, String codemarque) {
        this.nDossier = nDossier;
        this.codeDefaut = codeDefaut;
        this.numCentre = numCentre;
        this.dateControl = dateControl;
        this.dateHeureEnregistrement = dateHeureEnregistrement;
        this.numChassis = numChassis;
        this.matAgent = matAgent;
        this.codemarque = codemarque;
    }

    public String getCodemarque() {
        return codemarque;
    }

    public void setCodemarque(String codemarque) {
        this.codemarque = codemarque;
    }

    public DossierDefautResponse() {
    }

    public static DossierDefautResponse toDossierDefautResponse(DossierDefaut dossierDefaut) {
        return new DossierDefautResponse(
                dossierDefaut.getId().getnDossier(),
                dossierDefaut.getId().getCodeDefaut(),
                dossierDefaut.getNumCentre(),
                dossierDefaut.getDateControl(),
                dossierDefaut.getDateHeureEnregistrement(),
                dossierDefaut.getNumChassis(),
                dossierDefaut.getMatAgent(),
                dossierDefaut.getCodeMarque()
        );
    }
}
