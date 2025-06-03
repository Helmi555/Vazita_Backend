package com.example.vehicleinspection.model;

import com.example.vehicleinspection.model.keys.AlterationId;
import jakarta.persistence.*;

@Entity
@Table(name = "ALTERATIONS")
public class Alteration {
    @Id
    @AttributeOverrides({
            @AttributeOverride(name = "codeChapitre", column = @Column(name = "CODE_CHAPITRE")),
            @AttributeOverride(name = "codePoint", column = @Column(name = "CODE_POINT")),
            @AttributeOverride(name = "codeAlteration", column = @Column(name = "CODE_ALTERATION"))
    })
    private AlterationId alterationId;

    @Column(name="LIBELLE_ALTERATION")
    private String libelleAlteration;

    public AlterationId getAlterationId() {
        return alterationId;
    }

    public void setAlterationId(AlterationId alterationId) {
        this.alterationId = alterationId;
    }

    public String getLibelleAlteration() {
        return libelleAlteration;
    }

    public void setLibelleAlteration(String libelleAlteration) {
        this.libelleAlteration = libelleAlteration;
    }

    public Alteration(AlterationId alterationId, String libelleAlteration) {
        this.alterationId = alterationId;
        this.libelleAlteration = libelleAlteration;
    }

    public Alteration() {
    }

    @Override
    public String toString() {
        return "Alteration{" +
                "alterationId=" + alterationId +
                ", libelleAlteration='" + libelleAlteration + '\'' +
                '}';
    }
}
