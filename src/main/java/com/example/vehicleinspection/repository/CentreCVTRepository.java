package com.example.vehicleinspection.repository;

import com.example.vehicleinspection.model.CentreCVT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CentreCVTRepository extends JpaRepository<CentreCVT, Integer> {
}
