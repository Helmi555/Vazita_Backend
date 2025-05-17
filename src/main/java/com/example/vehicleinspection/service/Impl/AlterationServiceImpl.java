package com.example.vehicleinspection.service.Impl;

import com.example.vehicleinspection.repository.AlterationRepository;
import com.example.vehicleinspection.service.AlterationService;
import org.springframework.stereotype.Service;


@Service
public class AlterationServiceImpl implements AlterationService {

    private final AlterationRepository alterationRepository;

    public AlterationServiceImpl(AlterationRepository alterationRepository) {
        this.alterationRepository = alterationRepository;
    }
}
