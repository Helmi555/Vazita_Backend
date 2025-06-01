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
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DossierServiceImpl implements DossierService {

    private final DossierRepository dossierRepository;
    private final UserRepository userRepository;
    private final CentreCVTRepository centreCVTRepository;
    private final static Logger logger= LoggerFactory.getLogger(DossierServiceImpl.class);
    private final AlterationRepository alterationRepository;
    private final DossierDefautRepository dossierDefautRepository;
    private final EntityManager entityManager;

    public DossierServiceImpl(DossierRepository dossierRepository, UserRepository userRepository, CentreCVTRepository centreCVTRepository, AlterationRepository alterationRepository, DossierDefautRepository dossierDefautRepository, EntityManager entityManager) {
        this.dossierRepository = dossierRepository;
        this.userRepository = userRepository;
        this.centreCVTRepository = centreCVTRepository;
        this.alterationRepository = alterationRepository;
        this.dossierDefautRepository = dossierDefautRepository;
        this.entityManager = entityManager;
    }

    @Override
    public List<DossierResponse> getDossierByPisteId(Integer pisteId) {

        List<Dossier> dossiers = dossierRepository.findAllByCPisteAndDossierDefautExists(pisteId);
        if (dossiers.isEmpty()) {
            logger.info("No dossiers found for pisteId {}", pisteId);
        }

        return dossiers.stream()
                .map(DossierResponse::dossierToDossierResponse)
                .toList();
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
                    matAgent,
                    dossier.getCodeMarque()
            );
            logger.info("DossierDefaut to save is\n {}",dossierDefaut);
            dossierDefautRepository.save(dossierDefaut);
        }
        logger.info("Successfully submitted dossier with {} alterations", alterations.size());
    }


    @Override
    @Transactional
    public void updateDossierById(Integer numDossier, DossierDefautsRequest dossierDefautsRequest, Integer numCentre) {
        Dossier dossier = dossierRepository.findById(numDossier)
                .orElseThrow(() -> new UsernameNotFoundException("Dossier not found with id : "+numDossier));
        LocalDate dateCtrl = LocalDate.now();
        List<Integer> codeAlterations=dossierDefautsRequest.getCodeDefauts().stream().map(s->s.substring(2)).map(Integer::valueOf).toList();
        logger.info("Les code alterations are : {}", codeAlterations);
        List<Alteration> alterations=alterationRepository.findAllById(codeAlterations);
        logger.info("Alterations to update are\n {} and the old are \n{}", alterations,dossierDefautsRequest.getCodeDefauts());


        DossierDefaut dd=dossierDefautRepository.findFirstById_NDossier(numDossier).orElse(null);
        logger.info("*********** Dossier defaut  is\n {}", dd);
        String matAgent= dd!=null?dd.getMatAgent():null;
        logger.info("*********** Mat Agent is\n {}", matAgent);

        dossierDefautRepository.deleteAllByNDossier(numDossier);
        dossierDefautRepository.flush();
        entityManager.clear();
        // Create and save new DossierDefaut records
        for (Alteration alteration : alterations) {
            String codeDef = alteration.getCodeChapitre().toString() +
                    alteration.getCodePoint().toString() +
                    alteration.getCodeAlteration().toString();
            DossierDefaut dossierDefaut = new DossierDefaut(
                    new DossierDefautId(numDossier,codeDef),
                    numCentre,
                    dateCtrl,
                    dossier.getDateHeureEnregistrement(),
                    dossier.getNumChassis(),
                    matAgent,
                    dossier.getCodeMarque()
            );
            logger.info("DossierDefaut to save is\n {}", dossierDefaut);
            dossierDefautRepository.save(dossierDefaut);
        }
        logger.info("Successfully updated dossier {} with {} alterations", numDossier, alterations.size());
    }

}
