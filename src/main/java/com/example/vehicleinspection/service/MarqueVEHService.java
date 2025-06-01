package com.example.vehicleinspection.service;

import com.example.vehicleinspection.dto.response.MarqueResponse;

import java.util.List;

public interface MarqueVEHService {
    List<String> searchMarques(String query);

    List<String> searchMarquesByYear(String year);

    List<MarqueResponse> getMarquesByDossierDefaut(String year);
}
