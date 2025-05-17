package com.example.vehicleinspection.dto.response;


import com.example.vehicleinspection.model.Alteration;

public class AlterationResponse {

    private Integer codeAlteration;
    private String libelleAlteration;
    private Integer codePoint;
    private Integer codeChapitre;

    public Integer getCodePoint() {
        return codePoint;
    }

    public void setCodePoint(Integer codePoint) {
        this.codePoint = codePoint;
    }

    public Integer getCodeAlteration() {
        return codeAlteration;
    }

    public void setCodeAlteration(Integer codeAlteration) {
        this.codeAlteration = codeAlteration;
    }

    public String getLibelleAlteration() {
        return libelleAlteration;
    }

    public void setLibelleAlteration(String libelleAlteration) {
        this.libelleAlteration = libelleAlteration;
    }

    public Integer getCodeChapitre() {
        return codeChapitre;
    }

    public void setCodeChapitre(Integer codeChapitre) {
        this.codeChapitre = codeChapitre;
    }

    public AlterationResponse() {
    }
    public AlterationResponse(Integer codeAlteration, String libelleAlteration, Integer codePoint, Integer codeChapitre) {
        this.codeAlteration = codeAlteration;
        this.libelleAlteration = libelleAlteration;
        this.codePoint = codePoint;
        this.codeChapitre = codeChapitre;
    }

    public static AlterationResponse mapToAlterationResponse(Alteration alteration) {
        AlterationResponse response = new AlterationResponse();
        response.setCodeAlteration(alteration.getCodeAlteration());
        response.setLibelleAlteration(alteration.getLibelleAlteration());
        response.setCodePoint(alteration.getCodePoint());
        response.setCodeChapitre(alteration.getCodeChapitre());
        return response;
    }

}
