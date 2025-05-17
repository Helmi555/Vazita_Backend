package com.example.vehicleinspection.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "UTILISATEURS")
public class User {
    @Id
    @Column(name = "ID_USER", length = 100)
    private String idUser;


    @Column(name = "PASSE",nullable = false,length = 100)
    private String password;

    @Column(name = "PRENOM",length = 100)
    private String firstName;

    @Column(name = "NOM",length = 100)
    private String lastName;

    @Column(name = "PRENOMA",length = 100)
    private String firstNameA;

    @Column(name = "NOMA",length = 100)
    private String lastNameA;

    @Column(name = "DATE_DEB")
    private LocalDate startDate;

    @Column(name = "DATE_FIN")
    private LocalDate endDate;

    @Column(name = "ETAT", nullable = false,length = 1)
    private String status;

    @Column(name = "COD_GRP",nullable = false,length = 3)
    private String codGrp;

    @Column(name = "ID_CENTRE" ,nullable = false)
    private Integer idCentre;

    public User() {
    }

    public User(String idUser, String password, String firstName, String lastName, String firstNameA, String lastNameA, LocalDate startDate, LocalDate endDate, String status, String codGrp, Integer idCentre) {
        this.idUser = idUser;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.firstNameA = firstNameA;
        this.lastNameA = lastNameA;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.codGrp = codGrp;
        this.idCentre = idCentre;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstNameA() {
        return firstNameA;
    }

    public void setFirstNameA(String firstNameA) {
        this.firstNameA = firstNameA;
    }

    public String getLastNameA() {
        return lastNameA;
    }

    public void setLastNameA(String lastNameA) {
        this.lastNameA = lastNameA;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCodGrp() {
        return codGrp;
    }

    public void setCodGrp(String codGrp) {
        this.codGrp = codGrp;
    }

    public Integer getIdCentre() {
        return idCentre;
    }

    public void setIdCentre(Integer idCentre) {
        this.idCentre = idCentre;
    }

    public boolean isValid() {
        return "E".equals(this.status);
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser='" + idUser + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstNameA='" + firstNameA + '\'' +
                ", lastNameA='" + lastNameA + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                ", codGrp='" + codGrp + '\'' +
                ", idCentre=" + idCentre +
                '}';
    }
}
