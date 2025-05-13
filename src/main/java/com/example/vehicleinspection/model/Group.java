package com.example.vehicleinspection.model;


import com.example.vehicleinspection.model.enums.Role;
import jakarta.persistence.*;

@Entity
@Table(name="GROUPE")
public class Group {
    @Id
    @Column(name = "COD_GRP",length = 3)
    private String codGrp;

    @Column(name = "DESIGNATION",length = 100)
    @Enumerated(EnumType.STRING)
    private Role designation;

    public String getCodGrp() {
        return codGrp;
    }

    public void setCodGrp(String codGrp) {
        this.codGrp = codGrp;
    }

    public Role getDesignation() {
        return designation;
    }

    public void setDesignation(Role designation) {
        this.designation = designation;
    }

    public Group(String codGrp, Role designation) {
        this.codGrp = codGrp;
        this.designation = designation;
    }

    public Group() {
    }
}
