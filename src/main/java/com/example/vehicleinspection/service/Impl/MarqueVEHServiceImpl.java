package com.example.vehicleinspection.service.Impl;

import com.example.vehicleinspection.dto.response.MarqueResponse;
import com.example.vehicleinspection.exception.ElementNotFoundException;
import com.example.vehicleinspection.model.Alteration;
import com.example.vehicleinspection.model.DossierDefaut;
import com.example.vehicleinspection.model.MarqueVEH;
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
    public Map<String,Object> getMarquesByDossierDefaut(String year,String marque) {
        List<DossierDefaut> list;
        MarqueVEH marqueVEH=marqueVEHRepository.findByDesiGL(marque).orElseThrow(
                ()->new ElementNotFoundException("Marque n' existe pas")
        );
        if(year==null || year.isBlank()|| year.equals("0")){
           list = dossierDefautRepository.findByCodeMarque(marqueVEH.getCdMarque());
        }else{
            list= dossierDefautRepository.findByYearAndCodeMarque(year,marqueVEH.getCdMarque());
        }

        System.out.println("******* List of dossier defauts is : "+list+"\n marque est "+ marque);


        Map<String, Long> codeDefautCounts = list.stream()
                .collect(Collectors.groupingBy(d -> d.getId().getCodeDefaut(), Collectors.counting()));

        List<Map.Entry<String, Long>> top5CodeDefauts = codeDefautCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .toList();

        System.out.println("Top 5 code defauts:");
        List<MarqueResponse> marqueResponseList=new ArrayList<>();
        for (Map.Entry<String, Long> entry : top5CodeDefauts) {
            Alteration alt=alterationRepository.findById(Integer.parseInt(entry.getKey().substring(2))).orElse(null);
            if(alt!=null) marqueResponseList.add(new MarqueResponse(alt.getLibelleAlteration(), entry.getValue()) );
            System.out.println("CodeDefaut: " + entry.getKey() + ", Count: " + entry.getValue());
        }
        marqueResponseList.add(new MarqueResponse("Autres", (long) (codeDefautCounts.size()-top5CodeDefauts.size())));
        Map<String,Object> response=new HashMap<>();
        response.put("alterations",marqueResponseList);
        return response;
    }
}
