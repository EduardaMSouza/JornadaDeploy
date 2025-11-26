package com.example.ProjetoJornadaMobile.repository;

import com.example.ProjetoJornadaMobile.domain.Motor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotorRepository extends JpaRepository<Motor, Long> {

}