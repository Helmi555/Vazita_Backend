package com.example.vehicleinspection.dto.response;


import com.example.vehicleinspection.model.Chapitre;

import java.util.List;

public class ChapitreResponse {

    private Integer codeChapitre;
    private  String libelleChapitre;
    private List<PointDefautResponse> pointDefautResponses;

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

    public List<PointDefautResponse> getPointDefautResponses() {
        return pointDefautResponses;
    }

    public void setPointDefautResponses(List<PointDefautResponse> pointDefautResponses) {
        this.pointDefautResponses = pointDefautResponses;
    }

    public ChapitreResponse(Integer codeChapitre, String libelleChapitre, List<PointDefautResponse> pointDefautResponses) {
        this.codeChapitre = codeChapitre;
        this.libelleChapitre = libelleChapitre;
        this.pointDefautResponses = pointDefautResponses;
    }

    public ChapitreResponse() {
    }

    public static ChapitreResponse mapToChapitreResponse(Chapitre chapitre, List<PointDefautResponse> pointDefautResponses) {
        ChapitreResponse response = new ChapitreResponse();
        response.setCodeChapitre(chapitre.getCodeChapitre());
        response.setLibelleChapitre(chapitre.getLibelleChapitre());
        response.setPointDefautResponses(pointDefautResponses);
        return response;
    }
}
