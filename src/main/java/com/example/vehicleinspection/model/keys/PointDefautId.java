package com.example.vehicleinspection.model.keys;

import jakarta.persistence.Embeddable;

@Embeddable
public class PointDefautId  {
    private Integer codeChapitre;
    private Integer codePoint;

    // Default constructor
    public PointDefautId() {}

    public PointDefautId(Integer codeChapitre, Integer codePoint) {
        this.codeChapitre = codeChapitre;
        this.codePoint = codePoint;
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
}
