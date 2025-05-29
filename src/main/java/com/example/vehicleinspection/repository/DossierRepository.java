package com.example.vehicleinspection.repository;

import com.example.vehicleinspection.model.Dossier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DossierRepository extends JpaRepository<Dossier, Integer> {
 Optional<List<Dossier>> findAllByCPiste(Integer pisteId);

 @Query("SELECT d FROM Dossier d LEFT JOIN DossierDefaut dd ON d.numDossier = dd.id.nDossier WHERE d.cPiste = :pisteId AND dd.id.nDossier IS NULL")
 List<Dossier> findAllByCPisteAndDossierDefautExists(Integer pisteId);
}
