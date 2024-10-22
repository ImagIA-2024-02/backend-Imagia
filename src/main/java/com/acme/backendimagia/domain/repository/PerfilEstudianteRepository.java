package com.acme.backendimagia.domain.repository;

import com.acme.backendimagia.domain.model.PerfilEstudiante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilEstudianteRepository extends JpaRepository<PerfilEstudiante, Long> {
    PerfilEstudiante findByUsuarioId(Long usuarioId);
}
