package com.example.vehicleinspection.service.Impl;

import com.example.vehicleinspection.dto.response.MarqueResponse;
import com.example.vehicleinspection.model.DossierDefaut;
import com.example.vehicleinspection.model.MarqueVEH;
import com.example.vehicleinspection.repository.DossierDefautRepository;
import com.example.vehicleinspection.repository.MarqueVEHRepository;
import com.example.vehicleinspection.service.MarqueVEHService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MarqueVEHServiceImpl implements MarqueVEHService {

    private final MarqueVEHRepository marqueVEHRepository;
    private final DossierDefautRepository dossierDefautRepository;

    public MarqueVEHServiceImpl(MarqueVEHRepository marqueVEHRepository, DossierDefautRepository dossierDefautRepository) {
        this.marqueVEHRepository = marqueVEHRepository;
        this.dossierDefautRepository = dossierDefautRepository;
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
    public List<MarqueResponse> getMarquesByDossierDefaut(String year) {
        List<DossierDefaut> list;
        if(year==null || year.isBlank()){
           list = dossierDefautRepository.findAll();
        }else{
            list= dossierDefautRepository.findByYear(year);
        }

        Map<String, Long> counts = list.stream()
                .collect(Collectors.groupingBy(
                        DossierDefaut::getCodeMarque,
                        Collectors.counting()
                ));
        List<MarqueResponse> marqueResponseList=new ArrayList<>();

        counts.forEach((codeMarque,count)-> {
            marqueVEHRepository.findById(codeMarque).ifPresent(marque -> marqueResponseList.add(new MarqueResponse(marque.getDesiGL(), count)));
        });

        return marqueResponseList;
    }
}
