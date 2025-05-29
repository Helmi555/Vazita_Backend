package com.example.vehicleinspection.controller;

import com.example.vehicleinspection.service.DossierDefautService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/dossierdefauts")
public class DossierDefautController {

    private final DossierDefautService dossierDefautService;

    public DossierDefautController(DossierDefautService dossierDefautService) {
        this.dossierDefautService = dossierDefautService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','ADJOINT')")
    @GetMapping("")
    public ResponseEntity<?> getDossierDefauts(){
        return ResponseEntity.ok().body(Map.of("data",dossierDefautService.getDossierDefauts()));
    }


    @PreAuthorize("hasAnyRole('ADMIN','ADJOINT')")
    @GetMapping("/{nDossier}")
    public ResponseEntity<?> getDossierDefautById(@PathVariable Integer nDossier){
        return ResponseEntity.ok().body(Map.of("data",dossierDefautService.getDossierDefautById(nDossier)));
    }
}
