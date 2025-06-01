package com.example.vehicleinspection.repository;

import com.example.vehicleinspection.model.DossierDefaut;
import com.example.vehicleinspection.model.keys.DossierDefautId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface DossierDefautRepository extends JpaRepository<DossierDefaut, DossierDefautId> {

    @Query("DELETE FROM DossierDefaut dd WHERE dd.id.nDossier = :nDossier")
    @Modifying
    @Transactional
    void deleteByIdNDossier(@Param("nDossier") Integer nDossier);

    @Query("SELECT dd from DossierDefaut dd where dd.id.nDossier=:nDossier")
    Optional<List<DossierDefaut>> findAllByNDossier(@Param("nDossier") Integer nDossier);

    Optional<DossierDefaut> findFirstById_NDossier(Integer nDossier);

    @Modifying
    @Transactional
    @Query("DELETE FROM DossierDefaut d WHERE d.id.nDossier = :nDossier")
    void deleteAllByNDossier(@Param("nDossier") Integer nDossier);

    @Query("SELECT m FROM DossierDefaut m WHERE FUNCTION('TO_CHAR', m.dateControl, 'YYYY') = :year")
    List<DossierDefaut> findByYear(@Param("year") String year);

}
