package com.example.vehicleinspection.service.Impl;

import com.example.vehicleinspection.dto.response.DossierDefautResponse;
import com.example.vehicleinspection.exception.ElementNotFoundException;
import com.example.vehicleinspection.model.DossierDefaut;
import com.example.vehicleinspection.repository.DossierDefautRepository;
import com.example.vehicleinspection.service.DossierDefautService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DossierDefautServiceImpl implements DossierDefautService {

private final DossierDefautRepository dossierDefautRepository;

    public DossierDefautServiceImpl(DossierDefautRepository dossierDefautRepository) {
        this.dossierDefautRepository = dossierDefautRepository;
    }

    @Override
    public List<DossierDefautResponse> getDossierDefauts() {
        return dossierDefautRepository.findAll().stream().map(DossierDefautResponse::toDossierDefautResponse).toList();
    }

    @Override
    public List<DossierDefautResponse> getDossierDefautById(Integer nDossier) {
        List<DossierDefaut> dossierDefauts = dossierDefautRepository.findAllByNDossier(nDossier).orElseThrow(
                ()->new ElementNotFoundException("DossierDefaut not found for "+ nDossier)
        );
        return dossierDefauts.stream().map(DossierDefautResponse::toDossierDefautResponse).toList();
    }
}
