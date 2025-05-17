package com.example.vehicleinspection.model.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;


@Embeddable
public class DossierDefautId {
    @Column(name = "N_DOSSIER", precision = 10, scale = 0)
    private Integer nDossier;

    @Column(name = "CODE_DEFAUT", length = 10)
    private String codeDefaut;

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

    public DossierDefautId(Integer nDossier, String codeDefaut) {
        this.nDossier = nDossier;
        this.codeDefaut = codeDefaut;
    }

    public DossierDefautId() {
    }

    @Override
    public String toString() {
        return "DossierDefautId{" +
                "nDossier=" + nDossier +
                ", codeDefaut='" + codeDefaut + '\'' +
                '}';
    }
}
