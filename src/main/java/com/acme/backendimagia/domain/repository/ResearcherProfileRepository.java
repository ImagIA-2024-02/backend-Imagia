package com.acme.backendimagia.domain.repository;

import com.acme.backendimagia.domain.model.ResearcherProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResearcherProfileRepository extends JpaRepository<ResearcherProfile, Long> {
    ResearcherProfile findByUserId(Long userId);
}
