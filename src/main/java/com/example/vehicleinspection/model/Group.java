package com.example.vehicleinspection.model;


import com.example.vehicleinspection.model.enums.Role;
import jakarta.persistence.*;

@Entity
@Table(name="GROUPE")
public class Group {
    @Id
    @Column(name = "COD_GRP",nullable = false,unique = true)
    private Integer codGrp;

    @Column(name = "DESIGNATION",nullable = false,unique = true)
    @Enumerated(EnumType.STRING)
    private Role designation;

    public Integer getCodGrp() {
        return codGrp;
    }

    public void setCodGrp(Integer codGrp) {
        this.codGrp = codGrp;
    }

    public Role getDesignation() {
        return designation;
    }

    public void setDesignation(Role designation) {
        this.designation = designation;
    }

    public Group(Integer codGrp, Role designation) {
        this.codGrp = codGrp;
        this.designation = designation;
    }

    public Group() {
    }
}
