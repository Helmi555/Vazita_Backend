package com.example.vehicleinspection.controller;


import com.example.vehicleinspection.dto.request.DossierDefautsRequest;
import com.example.vehicleinspection.dto.response.DossierResponse;
import com.example.vehicleinspection.model.Dossier;
import com.example.vehicleinspection.service.DossierService;
import com.example.vehicleinspection.util.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
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


    @Operation(summary = "Get dossier by pisteId", description = "Authenticates a user and returns a JWT token")
    @PreAuthorize("hasAnyRole('ADMIN','INSPECTEUR')")
    @GetMapping("/{pisteId}")
    public ResponseEntity<?> getDossierByPisteId(@PathVariable Integer pisteId) {

        if (pisteId == null || pisteId < 0) {
            return ResponseEntity.badRequest().build();
        }
        List<DossierResponse> dossierResponses = dossierService.getDossierByPisteId(pisteId);
        return ResponseEntity.ok(dossierResponses);

    }

    @PreAuthorize("hasAnyRole('INSPECTEUR')")
    @PostMapping("/{numDossier}")
    public ResponseEntity<?> submitDossierByPisteId(@RequestHeader("Authorization") String auth, @PathVariable Integer numDossier, @RequestBody DossierDefautsRequest dossierDefautsRequest) {

        String token = auth.replace("Bearer ", "");
        ;
        if (numDossier == null || numDossier < 0) {
            return ResponseEntity.badRequest().build();
        }
        Integer numCentre = jwtUtils.extractIdCentreFromJwtToken(token);
        if (numCentre == null || numCentre < 0) {
            return ResponseEntity.badRequest().build();
        }
        String matAgent = jwtUtils.extractIdUser(token);
        if (matAgent == null || matAgent.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        dossierService.submitDossierById(numDossier, dossierDefautsRequest, numCentre, matAgent);
        return ResponseEntity.ok().body(Map.of("message", "Dossier submitted successfully"));

    }

    @PreAuthorize("hasAnyRole('ADMIN','ADJOINT')")
    @PutMapping("/{numDossier}")
    public ResponseEntity<?> updateDossierByPisteId(@RequestHeader("Authorization") String auth, @PathVariable Integer numDossier, @RequestBody DossierDefautsRequest dossierDefautsRequest) {

        String token = auth.replace("Bearer ", "");
        ;
        if (numDossier == null || numDossier < 0) {
            return ResponseEntity.badRequest().build();
        }
        Integer numCentre = jwtUtils.extractIdCentreFromJwtToken(token);
        if (numCentre == null || numCentre < 0) {
            return ResponseEntity.badRequest().build();
        }
        String matAgent = jwtUtils.extractIdUser(token);
        if (matAgent == null || matAgent.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        if(dossierDefautsRequest.getCodeDefauts().isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        dossierService.updateDossierById(numDossier, dossierDefautsRequest, numCentre);
        return ResponseEntity.ok().body(Map.of("message", "Dossier updated successfully"));

    }
}
