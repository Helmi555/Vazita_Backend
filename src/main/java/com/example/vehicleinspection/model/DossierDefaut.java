package com.example.vehicleinspection.model;


import com.example.vehicleinspection.model.keys.DossierDefautId;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "DOSSIER_DEFAUTS")
public class DossierDefaut {

    @EmbeddedId
    private DossierDefautId id;

    @Column(name = "NUM_CENTRE",nullable = false, precision = 5, scale = 0)
    private Integer numCentre;
    @Column(name = "DAT_CTRL",nullable = false)
    private LocalDate dateControl;
    @Column(name = "DATE_HEURE_ENREGISTREMENT")
    private LocalDateTime dateHeureEnregistrement;
    @Column(name = "NUM_CHASSIS",length = 25)
    private String numChassis;
    private String matAgent;
    @Column(name = "CODE_MARQUE")
    private String codeMarque;


    public DossierDefautId getId() {
        return id;
    }

    public void setId(DossierDefautId id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "DossierDefaut{" +
                "id=" + id +
                ", numCentre=" + numCentre +
                ", dateControl=" + dateControl +
                ", dateHeureEnregistrement=" + dateHeureEnregistrement +
                ", numChassis='" + numChassis + '\'' +
                ", matAgent='" + matAgent + '\'' +
                ", codeMarque='" + codeMarque + '\'' +
                '}';
    }

    public DossierDefaut(DossierDefautId id, Integer numCentre, LocalDate dateControl, LocalDateTime dateHeureEnregistrement, String numChassis, String matAgent,String codeMarque) {
        this.id = id;
        this.numCentre = numCentre;
        this.dateControl = dateControl;
        this.dateHeureEnregistrement = dateHeureEnregistrement;
        this.numChassis = numChassis;
        this.matAgent = matAgent;
        this.codeMarque=codeMarque;
    }

    public String getCodeMarque() {
        return codeMarque;
    }

    public void setCodeMarque(String codeMarque) {
        this.codeMarque = codeMarque;
    }

    public DossierDefaut() {
    }
}
