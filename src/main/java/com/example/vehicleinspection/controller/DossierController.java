package com.example.vehicleinspection.controller;


import com.example.vehicleinspection.dto.request.DossierDefautsRequest;
import com.example.vehicleinspection.dto.response.DossierResponse;
import com.example.vehicleinspection.model.Dossier;
import com.example.vehicleinspection.service.DossierService;
import com.example.vehicleinspection.util.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/dossiers")
public class DossierController {

    private final DossierService dossierService;
    private final JwtUtils jwtUtils;

    public DossierController(DossierService dossierService, JwtUtils jwtUtils) {
        this.dossierService = dossierService;
        this.jwtUtils = jwtUtils;
    }


    @PreAuthorize("hasAnyRole('ADMIN','INSPECTOR')")
    @GetMapping("/{pisteId}")
    public ResponseEntity<?> getDossierByPisteId(@PathVariable Integer pisteId) {

        if(pisteId == null || pisteId < 0) {
            return ResponseEntity.badRequest().build();
        }
        List<DossierResponse> dossierResponses=dossierService.getDossierByPisteId(pisteId);
        return ResponseEntity.ok(dossierResponses);

    }

    @PreAuthorize("hasAnyRole('ADMIN','INSPECTOR')")
    @PostMapping("/{numDossier}")
    public ResponseEntity<?> submitDossierByPisteId(@RequestHeader("Authorization") String auth,@PathVariable Integer numDossier, @RequestBody DossierDefautsRequest dossierDefautsRequest) {

        String token= auth.replace("Bearer ", "");;
        if(numDossier == null || numDossier < 0) {
            return ResponseEntity.badRequest().build();
        }
        Integer numCentre=jwtUtils.extractIdCentreFromJwtToken(token);
        if(numCentre == null || numCentre < 0) {
            return ResponseEntity.badRequest().build();
        }
        String matAgent= jwtUtils.extractIdUser(token);
        if(matAgent == null || matAgent.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        dossierService.submitDossierById(numDossier,dossierDefautsRequest,numCentre,matAgent);
        return ResponseEntity.ok().body(Map.of("message","Dossier submitted successfully"));

    }
}
