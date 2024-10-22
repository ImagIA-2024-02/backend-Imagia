package com.acme.backendimagia.domain.repository;

import com.acme.backendimagia.domain.model.PerfilCiudadano;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilCiudadanoRepository extends JpaRepository<PerfilCiudadano, Long> {
    PerfilCiudadano findByUsuarioId(Long usuarioId);
}
