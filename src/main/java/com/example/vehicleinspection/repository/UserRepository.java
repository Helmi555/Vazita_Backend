package com.example.vehicleinspection.repository;


import com.example.vehicleinspection.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    List<User> findAllByIdCentre(Integer idCentre);
}
