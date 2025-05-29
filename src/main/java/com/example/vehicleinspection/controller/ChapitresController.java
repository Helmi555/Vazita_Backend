package com.example.vehicleinspection.controller;


import com.example.vehicleinspection.dto.response.ChapitreResponse;
import com.example.vehicleinspection.service.ChapitreService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chapitres")
public class ChapitresController {

    private final ChapitreService chapitreService;

    public ChapitresController(ChapitreService chapitreService) {
        this.chapitreService = chapitreService;
    }

    @PreAuthorize("hasAnyRole('INSPECTEUR','ADMIN')")
    @GetMapping("")
    public ResponseEntity<?> getChapitres() {
        List<ChapitreResponse> chapitreResponseList=chapitreService.getChapitres();
        return ResponseEntity.ok(chapitreResponseList);

    }
}
