package com.example.vehicleinspection.service;

import com.example.vehicleinspection.dto.response.DossierDefautResponse;

import java.util.List;

public interface DossierDefautService {

    List<DossierDefautResponse> getDossierDefauts();

    List<DossierDefautResponse> getDossierDefautById(Integer nDossier);
}
