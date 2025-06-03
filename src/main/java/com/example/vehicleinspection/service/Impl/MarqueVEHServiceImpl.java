package com.example.vehicleinspection.service.Impl;

import com.example.vehicleinspection.dto.response.MarqueResponse;
import com.example.vehicleinspection.exception.ElementNotFoundException;
import com.example.vehicleinspection.model.Alteration;
import com.example.vehicleinspection.model.DossierDefaut;
import com.example.vehicleinspection.model.MarqueVEH;
import com.example.vehicleinspection.model.keys.AlterationId;
import com.example.vehicleinspection.repository.AlterationRepository;
import com.example.vehicleinspection.repository.DossierDefautRepository;
import com.example.vehicleinspection.repository.MarqueVEHRepository;
import com.example.vehicleinspection.service.MarqueVEHService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MarqueVEHServiceImpl implements MarqueVEHService {

    private final MarqueVEHRepository marqueVEHRepository;
    private final DossierDefautRepository dossierDefautRepository;
    private final AlterationRepository alterationRepository;

    public MarqueVEHServiceImpl(MarqueVEHRepository marqueVEHRepository, DossierDefautRepository dossierDefautRepository, AlterationRepository alterationRepository) {
        this.marqueVEHRepository = marqueVEHRepository;
        this.dossierDefautRepository = dossierDefautRepository;
        this.alterationRepository = alterationRepository;
    }

    @Override
    public List<String> searchMarques(String query) {
        return marqueVEHRepository.searchStartingWith(query);
    }

    @Override
    public List<String> searchMarquesByYear(String year) {
        List<DossierDefaut> dossierDefautList=dossierDefautRepository.findByYear(year);
        List<MarqueVEH> marqueVEHList=marqueVEHRepository.findAllByCdMarqueIn(dossierDefautList.stream().map(DossierDefaut::getCodeMarque).toList());
        return marqueVEHList.stream().map(MarqueVEH::getDesiGL).toList();
    }

    @Override
    public Map<String, Object> getMarquesByDossierDefaut(String year, String marque) {
        List<DossierDefaut> list;

        MarqueVEH marqueVEH = marqueVEHRepository.findByDesiGL(marque)
                .orElseThrow(() -> new ElementNotFoundException("Marque " + marque + " n'existe pas"));

        if (year == null || year.isBlank() || year.equals("0")) {
            list = dossierDefautRepository.findByCodeMarque(marqueVEH.getCdMarque());
        } else {
            list = dossierDefautRepository.findByYearAndCodeMarque(year, marqueVEH.getCdMarque());
        }

        System.out.println("******* List of dossier defauts is : " + list + "\n marque est " + marque);

        // 1) Count how many times each 3‑digit codeDefaut appears:
        Map<String, Long> codeDefautCounts = list.stream()
                .collect(Collectors.groupingBy(d -> d.getId().getCodeDefaut(), Collectors.counting()));

        // 2) Sort by count descending, take up to 5 entries:
        List<Map.Entry<String, Long>> top5CodeDefauts = codeDefautCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .toList();

        System.out.println("Top 5 code defauts:");

        // 3) For each of those top codes, parse the three digits, build an AlterationId, then load Alteration:
        List<MarqueResponse> marqueResponseList = new ArrayList<>();
        for (Map.Entry<String, Long> entry : top5CodeDefauts) {
            String code = entry.getKey(); // e.g. "012" or "043" or "456"

            try {
                if (code.length() != 3) {
                    throw new IllegalArgumentException("CodeDefaut invalide: " + code);
                }

                int codeChapitre    = Integer.parseInt(code.substring(0, 1)); // first digit
                int codePoint       = Integer.parseInt(code.substring(1, 2)); // second digit
                int codeAlteration  = Integer.parseInt(code.substring(2, 3)); // third digit

                AlterationId alterationId = new AlterationId(codeChapitre, codePoint, codeAlteration);
                Alteration alt = alterationRepository.findById(alterationId).orElse(null);

                if (alt != null) {
                    marqueResponseList.add(new MarqueResponse(
                            alt.getLibelleAlteration(),
                            entry.getValue()
                    ));
                }
            } catch (Exception e) {
                System.err.println("Erreur lors du parsing ou récupération pour codeDefaut: " + code);
            }

            System.out.println("CodeDefaut: " + code + ", Count: " + entry.getValue());
        }

        // 4) If we exactly took 5 distinct codes, add an "Autres" entry for the remaining ones:
        if (top5CodeDefauts.size() == 5) {
            long autresCount = codeDefautCounts.size() - top5CodeDefauts.size();
            if (autresCount > 0) {
                marqueResponseList.add(new MarqueResponse("Autres", autresCount));
            }
        }

        // 5) Wrap into a Map and return
        Map<String, Object> response = new HashMap<>();
        response.put("alterations", marqueResponseList);
        return response;
    }


}
