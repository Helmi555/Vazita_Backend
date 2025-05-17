package com.example.vehicleinspection.service.Impl;

import com.example.vehicleinspection.repository.PointDefautRepository;
import com.example.vehicleinspection.service.PointDefautService;
import org.springframework.stereotype.Service;


@Service
public class PointDefautServiceImpl implements PointDefautService {

private final PointDefautRepository pointDefautRepository;

    public PointDefautServiceImpl(PointDefautRepository pointDefautRepository) {
        this.pointDefautRepository = pointDefautRepository;
    }
}
