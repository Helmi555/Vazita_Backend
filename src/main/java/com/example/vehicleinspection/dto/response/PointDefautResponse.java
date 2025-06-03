package com.example.vehicleinspection.dto.response;


import com.example.vehicleinspection.model.Alteration;
import com.example.vehicleinspection.model.PointDefaut;

import java.util.List;

public class PointDefautResponse {

    private Integer codePoint;
    private Integer codeChapitre;
    private String libellePoint;
    private List<AlterationResponse> alterationResponses;



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

    public List<AlterationResponse> getAlterationResponses() {
        return alterationResponses;
    }

    public void setAlterationResponses(List<AlterationResponse> alterationResponses) {
        this.alterationResponses = alterationResponses;
    }

    public PointDefautResponse() {
    }
    public PointDefautResponse(Integer codePoint, Integer codeChapitre, String libellePoint, List<AlterationResponse> alterationResponses) {
        this.codePoint = codePoint;
        this.codeChapitre = codeChapitre;
        this.libellePoint = libellePoint;
        this.alterationResponses = alterationResponses;
    }

    public static PointDefautResponse mapToPointDefautResponse(PointDefaut pointDefaut, List<AlterationResponse> alterations) {
        PointDefautResponse response = new PointDefautResponse();
        response.setCodePoint(pointDefaut.getId().getCodePoint());
        response.setCodeChapitre(pointDefaut.getId().getCodeChapitre());
        response.setLibellePoint(pointDefaut.getLibellePoint());
        response.setAlterationResponses(alterations);
        return response;
    }

}
