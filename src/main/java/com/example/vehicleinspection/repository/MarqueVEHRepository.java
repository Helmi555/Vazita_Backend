package com.example.vehicleinspection.repository;

import com.example.vehicleinspection.model.MarqueVEH;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarqueVEHRepository extends JpaRepository<MarqueVEH, String> {

    @Query("SELECT DISTINCT m.desiGL FROM MarqueVEH m WHERE LOWER(m.desiGL) LIKE LOWER(CONCAT(:query, '%'))")
    List<String> searchStartingWith(@Param("query") String desiGL);

    List<MarqueVEH> findAllByCdMarqueIn(List<String>codes);

    Optional<MarqueVEH> findByDesiGL(String marque);
}
