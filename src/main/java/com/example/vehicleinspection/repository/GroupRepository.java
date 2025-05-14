package com.example.vehicleinspection.repository;

import com.example.vehicleinspection.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GroupRepository extends JpaRepository<Group, String> {
}
