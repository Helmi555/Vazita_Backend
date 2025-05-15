package com.example.vehicleinspection.dto.response;

import com.example.vehicleinspection.model.User;
import com.example.vehicleinspection.model.enums.Role;

import java.time.LocalDate;

public class UserResponse {

    private String idUser;
    private String firstName;
    private String lastName;
    private String firstNameA;
    private String lastNameA;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status; // Updated: derived from status
    private String designation;
    private Integer idCentre;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Integer getIdCentre() {
        return idCentre;
    }

    public void setIdCentre(Integer idCentre) {
        this.idCentre = idCentre;
    }

    public UserResponse(String idUser, String firstName, String lastName, String firstNameA, String lastNameA, LocalDate startDate, LocalDate endDate, String status, String designation, Integer idCentre) {
        this.idUser = idUser;
        this.firstName = firstName;
        this.lastName = lastName;
        this.firstNameA = firstNameA;
        this.lastNameA = lastNameA;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.designation = designation;
        this.idCentre = idCentre;
    }

    public UserResponse() {
    }

    public static UserResponse userToResponseDto(User user) {
        return new UserResponse(
                user.getIdUser(),
                user.getFirstName(),
                user.getLastName(),
                user.getFirstNameA(),
                user.getLastNameA(),
                user.getStartDate(),
                user.getEndDate(),
                user.getStatus(),
                user.getCodGrp() != null ? Role.fromCode(user.getCodGrp()).toString() : null,
                user.getIdCentre()
        );
    }

}
