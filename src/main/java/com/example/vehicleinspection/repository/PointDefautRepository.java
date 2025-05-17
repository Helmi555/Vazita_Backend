package com.example.vehicleinspection.repository;

import com.example.vehicleinspection.model.PointDefaut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PointDefautRepository extends JpaRepository<PointDefaut,Integer> {
}
