package com.example.vehicleinspection.repository;

import com.example.vehicleinspection.model.DossierDefaut;
import com.example.vehicleinspection.model.keys.DossierDefautId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DossierDefautRepository extends JpaRepository<DossierDefaut, DossierDefautId> {

}
