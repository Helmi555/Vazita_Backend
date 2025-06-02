package com.example.vehicleinspection.controller;

import com.example.vehicleinspection.service.MarqueVEHService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/marques")
public class MarqueController {

    private final MarqueVEHService marqueService;

    public MarqueController(MarqueVEHService marqueService) {
        this.marqueService = marqueService;
    }


    @PreAuthorize("hasAnyRole('ADMIN','ADJOINT')")
    @GetMapping("/")
    public ResponseEntity<?> getMarquesByYear(@RequestParam("year") String year){
        System.out.println("Year is : "+year);
        if(year == null || year.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(Map.of("data", marqueService.searchMarquesByYear(year)));

    }

    @PreAuthorize("hasAnyRole('ADMIN','ADJOINT')")
    @GetMapping("/bydossierdefaut")
    public ResponseEntity<?> getMarquesByDossierDefaut(@RequestParam("year") String year,@RequestParam("marque") String marque){

        if(marque == null || marque.isEmpty()) {
           return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(Map.of("data",marqueService.getMarquesByDossierDefaut(year,marque)));
    }

    @PreAuthorize("hasAnyRole('ADMIN','ADJOINT')")
    @GetMapping("/search")
    public ResponseEntity<?> searchMarques(@RequestParam("query") String query){
        System.out.println("Query is : "+query);
        if(query == null || query.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(Map.of("data", marqueService.searchMarques(query)));
    }

}
