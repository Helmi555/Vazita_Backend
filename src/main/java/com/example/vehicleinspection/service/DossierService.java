package com.example.vehicleinspection.service;

import com.example.vehicleinspection.dto.request.DossierDefautsRequest;
import com.example.vehicleinspection.dto.response.DossierResponse;

import java.util.List;

public interface DossierService {
    List<DossierResponse> getDossierByPisteId(Integer pisteId);

    void submitDossierById(Integer numDossier, DossierDefautsRequest dossierDefautsRequest, Integer numCentre, String matAgent);
}
