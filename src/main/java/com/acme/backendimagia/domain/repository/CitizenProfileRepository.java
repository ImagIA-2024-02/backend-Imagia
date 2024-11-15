package com.acme.backendimagia.domain.repository;

import com.acme.backendimagia.domain.model.CitizenProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitizenProfileRepository extends JpaRepository<CitizenProfile, Long> {
    CitizenProfile findByUserId(Long userId);
}
