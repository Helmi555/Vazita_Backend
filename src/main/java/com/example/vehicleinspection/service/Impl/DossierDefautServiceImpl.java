package com.example.vehicleinspection.service.Impl;

import com.example.vehicleinspection.repository.DossierDefautRepository;
import com.example.vehicleinspection.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class DossierDefautServiceImpl implements AuthService.DossierDefautService {

private final DossierDefautRepository dossierDefautRepository;

    public DossierDefautServiceImpl(DossierDefautRepository dossierDefautRepository) {
        this.dossierDefautRepository = dossierDefautRepository;
    }
}
