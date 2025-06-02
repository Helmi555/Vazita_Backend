package com.example.vehicleinspection.service;

import com.example.vehicleinspection.dto.response.MarqueResponse;

import java.util.List;
import java.util.Map;

public interface MarqueVEHService {
    List<String> searchMarques(String query);

    List<String> searchMarquesByYear(String year);

    Map<String,Object> getMarquesByDossierDefaut(String year, String marque);
}
