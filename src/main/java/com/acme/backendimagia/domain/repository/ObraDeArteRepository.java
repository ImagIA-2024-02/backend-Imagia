package com.acme.backendimagia.domain.repository;

import com.acme.backendimagia.domain.model.ObraDeArte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ObraDeArteRepository extends JpaRepository<ObraDeArte, Long> {
    List<ObraDeArte> findByUsuarioId(Long usuarioId);
    List<ObraDeArte> findByAutor(String autor);
    Optional<ObraDeArte> findFirstByNombre(String nombre);
    Optional<ObraDeArte> findById(Long id);

}
