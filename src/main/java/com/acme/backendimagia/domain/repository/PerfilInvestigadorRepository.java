package com.acme.backendimagia.domain.repository;

import com.acme.backendimagia.domain.model.PerfilInvestigador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilInvestigadorRepository extends JpaRepository<PerfilInvestigador, Long> {
    PerfilInvestigador findByUsuarioId(Long usuarioId);
}
