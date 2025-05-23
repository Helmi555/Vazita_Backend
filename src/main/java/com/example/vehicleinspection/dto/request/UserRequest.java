package com.example.vehicleinspection.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public class UserRequest {

    @NotBlank(message = "Id must not be empty")
    private String idUser;
    @NotBlank(message = "Password must not be empty")
    private String password;
    private String firstName;
    private String lastName;
    private String firstNameA;
    private String lastNameA;
     private LocalDate startDate;
     private LocalDate endDate;
    @Pattern(regexp = "E|A",message = "Status should be A ou E")
     private String status="E";
    @NotBlank(message = "Code group must not be empty")
     private String codGrp;
    @NotNull(message = "Id centre must not be null")
     private Integer idCentre;

    public UserRequest(String idUser, String password, String firstName, String lastName, String firstNameA, String lastNameA, LocalDate startDate, LocalDate endDate, String status, String codGrp, Integer idCentre) {
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

    public UserRequest() {
    }

    public @NotBlank(message = "Id must not be empty") String getIdUser() {
        return idUser;
    }

    public void setIdUser(@NotBlank(message = "Id must not be empty") String idUser) {
        this.idUser = idUser;
    }

    public @NotBlank(message = "Password must not be empty") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password must not be empty") String password) {
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

    public @Pattern(regexp = "E|A", message = "Status should be A ou E") String getStatus() {
        return status;
    }

    public void setStatus(@Pattern(regexp = "E|A", message = "Status should be A ou E") String status) {
        this.status = status;
    }

    public @NotBlank(message = "Code group must not be empty") String getCodGrp() {
        return codGrp;
    }

    public void setCodGrp(@NotBlank(message = "Code group must not be empty") String codGrp) {
        this.codGrp = codGrp;
    }

    public @NotNull(message = "Id centre must not be null") Integer getIdCentre() {
        return idCentre;
    }

    public void setIdCentre(@NotNull(message = "Id centre must not be null") Integer idCentre) {
        this.idCentre = idCentre;
    }
}
