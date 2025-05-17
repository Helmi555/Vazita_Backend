package com.example.vehicleinspection.service.Impl;


import com.example.vehicleinspection.dto.response.AlterationResponse;
import com.example.vehicleinspection.dto.response.ChapitreResponse;
import com.example.vehicleinspection.dto.response.PointDefautResponse;
import com.example.vehicleinspection.model.Alteration;
import com.example.vehicleinspection.model.Chapitre;
import com.example.vehicleinspection.model.PointDefaut;
import com.example.vehicleinspection.repository.AlterationRepository;
import com.example.vehicleinspection.repository.ChapitreRepository;
import com.example.vehicleinspection.repository.PointDefautRepository;
import com.example.vehicleinspection.service.AlterationService;
import com.example.vehicleinspection.service.ChapitreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChapitreServiceImpl implements ChapitreService {

    private final ChapitreRepository chapitreRepository;
    private final PointDefautRepository pointDefautRepository;
    private final AlterationRepository alterationRepository;
    private final static Logger logger= LoggerFactory.getLogger(ChapitreService.class);

    public ChapitreServiceImpl(ChapitreRepository chapitreRepository, PointDefautRepository pointDefautRepository, AlterationRepository alterationRepository) {
            this.chapitreRepository = chapitreRepository;
            this.pointDefautRepository = pointDefautRepository;
            this.alterationRepository = alterationRepository;
        }

    @Override
    public List<ChapitreResponse> getChapitres() {
        List<Chapitre> chapitres = chapitreRepository.findAll();
        List<PointDefaut> pointDefauts = pointDefautRepository.findAll();
        List<Alteration> alterations = alterationRepository.findAll();
        logger.info("\nIl y a {} chapitres \n{} points \n{} alterations",chapitres.size(),pointDefauts.size(),alterations.size());

        Map<Integer, List<Alteration>> alterationsByPointId = alterations.stream()
                .collect(Collectors.groupingBy(Alteration::getCodePoint));

        Map<Integer, List<PointDefaut>> pointDefautsByChapitreId = pointDefauts.stream()
                .collect(Collectors.groupingBy(PointDefaut::getCodeChapitre));

        // Build chapitre response
        List<ChapitreResponse> chapitreResponses = new ArrayList<>();

        for (Chapitre chapitre : chapitres) {
            List<PointDefaut> chapterPoints = pointDefautsByChapitreId
                    .getOrDefault(chapitre.getCodeChapitre(), new ArrayList<>());

            List<PointDefautResponse> pointResponses = chapterPoints.stream().map(point -> {
                List<Alteration> pointAlterations = alterationsByPointId
                        .getOrDefault(point.getCodePoint(), new ArrayList<>());

                List<AlterationResponse> alterationResponses = pointAlterations.stream()
                        .map(AlterationResponse::mapToAlterationResponse)
                        .collect(Collectors.toList());

                return PointDefautResponse.mapToPointDefautResponse(point, alterationResponses);
            }).collect(Collectors.toList());

            ChapitreResponse chapitreResponse = ChapitreResponse.mapToChapitreResponse(chapitre, pointResponses);
            chapitreResponses.add(chapitreResponse);
        }

        return chapitreResponses;
    }


}
