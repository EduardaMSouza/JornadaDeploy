package com.example.ProjetoJornadaMobile.repository;

import com.example.ProjetoJornadaMobile.domain.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    @Override
    Optional<Projeto> findById(Long aLong);
}
