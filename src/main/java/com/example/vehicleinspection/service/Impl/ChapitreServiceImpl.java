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

        logger.info("\nIl y a {} chapitres \n{} points \n{} alterations",
                chapitres.size(), pointDefauts.size(), alterations.size());

        // Group alterations by (codeChapitre, codePoint)
        Map<String, List<Alteration>> alterationsGrouped = alterations.stream()
                .collect(Collectors.groupingBy(a ->
                        a.getAlterationId().getCodeChapitre() + "-" + a.getAlterationId().getCodePoint()
                ));

        // Group pointDefauts by codeChapitre
        Map<Integer, List<PointDefaut>> pointsByChapitre = pointDefauts.stream()
                .collect(Collectors.groupingBy(p -> p.getId().getCodeChapitre()));

        List<ChapitreResponse> chapitreResponses = new ArrayList<>();

        for (Chapitre chapitre : chapitres) {
            List<PointDefaut> chapterPoints = pointsByChapitre.getOrDefault(
                    chapitre.getCodeChapitre(), new ArrayList<>());

            List<PointDefautResponse> pointResponses = chapterPoints.stream().map(point -> {
                String key = point.getId().getCodeChapitre() + "-" + point.getId().getCodePoint();
                List<Alteration> pointAlterations = alterationsGrouped.getOrDefault(key, new ArrayList<>());

                List<AlterationResponse> alterationResponses = pointAlterations.stream()
                        .map(AlterationResponse::mapToAlterationResponse)
                        .collect(Collectors.toList());

                return PointDefautResponse.mapToPointDefautResponse(point, alterationResponses);
            }).collect(Collectors.toList());

            chapitreResponses.add(ChapitreResponse.mapToChapitreResponse(chapitre, pointResponses));
        }

        return chapitreResponses;
    }


}
