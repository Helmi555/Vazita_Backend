package com.example.vehicleinspection.repository;

import com.example.vehicleinspection.model.PointDefaut;
import com.example.vehicleinspection.model.keys.PointDefautId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PointDefautRepository extends JpaRepository<PointDefaut, PointDefautId> {
}
