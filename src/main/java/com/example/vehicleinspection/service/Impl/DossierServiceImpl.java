package com.example.vehicleinspection.service.Impl;

import com.example.vehicleinspection.dto.request.DossierDefautsRequest;
import com.example.vehicleinspection.dto.response.DossierResponse;
import com.example.vehicleinspection.model.Alteration;
import com.example.vehicleinspection.model.Dossier;
import com.example.vehicleinspection.model.DossierDefaut;
import com.example.vehicleinspection.model.keys.DossierDefautId;
import com.example.vehicleinspection.repository.*;
import com.example.vehicleinspection.service.DossierService;
import com.example.vehicleinspection.service.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DossierServiceImpl implements DossierService {

    private final DossierRepository dossierRepository;
    private final UserRepository userRepository;
    private final CentreCVTRepository centreCVTRepository;
    private final static Logger logger= LoggerFactory.getLogger(DossierServiceImpl.class);
    private final AlterationRepository alterationRepository;
    private final DossierDefautRepository dossierDefautRepository;

    public DossierServiceImpl(DossierRepository dossierRepository, UserRepository userRepository, CentreCVTRepository centreCVTRepository, AlterationRepository alterationRepository, DossierDefautRepository dossierDefautRepository) {
        this.dossierRepository = dossierRepository;
        this.userRepository = userRepository;
        this.centreCVTRepository = centreCVTRepository;
        this.alterationRepository = alterationRepository;
        this.dossierDefautRepository = dossierDefautRepository;
    }

    @Override
    public List<DossierResponse> getDossierByPisteId(Integer pisteId) {
        List<Dossier> dossiers=dossierRepository.findAllByCPiste(pisteId).orElse(Collections.emptyList());

        return  dossiers.stream().map(DossierResponse::dossierToDossierResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void submitDossierById(Integer numDossier, DossierDefautsRequest dossierDefautsRequest, Integer numCentre, String matAgent) {

        Dossier dossier=dossierRepository.findById(numDossier).orElse(null);
        if(dossier==null){
            throw new UsernameNotFoundException("Dossier not found");
        }
        LocalDate dateCtrl=LocalDate.now();
        List<Alteration> alterations=alterationRepository.findAllById(dossierDefautsRequest.getCodeAlterations());
        logger.info("Alterations to save are\n {}",alterations);

        for(Alteration alteration:alterations){
            String codeDef=alteration.getCodeChapitre().toString()+alteration.getCodePoint().toString()+ alteration.getCodeAlteration().toString();
            DossierDefaut dossierDefaut=new DossierDefaut(
                   new DossierDefautId(numDossier,codeDef),
                    numCentre,
                    dateCtrl,
                    dossier.getDateHeureEnregistrement(),
                    dossier.getNumChassis(),
                    matAgent
            );
            logger.info("DossierDefaut to save is\n {}",dossierDefaut);
            dossierDefautRepository.save(dossierDefaut);
        }
        logger.info("Successfully submitted dossier with {} alterations", alterations.size());
    }

}
