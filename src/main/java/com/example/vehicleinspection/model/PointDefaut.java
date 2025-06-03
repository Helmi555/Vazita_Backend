package com.example.vehicleinspection.model;


import com.example.vehicleinspection.model.keys.PointDefautId;
import jakarta.persistence.*;

@Entity
@Table(name = "POINTS_DEFAUTS")
public class PointDefaut {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "codeChapitre", column = @Column(name = "CODE_CHAPITRE")),
            @AttributeOverride(name = "codePoint", column = @Column(name = "CODE_POINT"))
    })
    private PointDefautId id;

    @Column(name="LIBELLE_POINT")
    private String libellePoint;

    public PointDefaut() {}

    public PointDefautId getId() {
        return id;
    }

    public void setId(PointDefautId id) {
        this.id = id;
    }

    public String getLibellePoint() {
        return libellePoint;
    }

    public void setLibellePoint(String libellePoint) {
        this.libellePoint = libellePoint;
    }

    public PointDefaut(PointDefautId id, String libellePoint) {
        this.id = id;
        this.libellePoint = libellePoint;
    }
}
