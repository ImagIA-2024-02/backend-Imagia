package com.acme.backendimagia.domain.repository;

import com.acme.backendimagia.domain.model.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {
    StudentProfile findByUserId(Long userId);
}
