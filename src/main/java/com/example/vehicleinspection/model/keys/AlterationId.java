package com.example.vehicleinspection.model.keys;

import jakarta.persistence.Embeddable;

@Embeddable
public class AlterationId {
    private Integer codeChapitre;
    private Integer codePoint;
    private Integer codeAlteration;

    public AlterationId() {}

    public AlterationId(Integer codeChapitre, Integer codePoint, Integer codeAlteration) {
        this.codeChapitre = codeChapitre;
        this.codePoint = codePoint;
        this.codeAlteration = codeAlteration;
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

    public Integer getCodeAlteration() {
        return codeAlteration;
    }

    public void setCodeAlteration(Integer codeAlteration) {
        this.codeAlteration = codeAlteration;
    }

    @Override
    public String toString() {
        return "AlterationId{" +
                "codeChapitre=" + codeChapitre +
                ", codePoint=" + codePoint +
                ", codeAlteration=" + codeAlteration +
                '}';
    }
}
