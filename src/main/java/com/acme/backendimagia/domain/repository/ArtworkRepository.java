package com.acme.backendimagia.domain.repository;

import com.acme.backendimagia.domain.model.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArtworkRepository extends JpaRepository<Artwork, Long> {
    List<Artwork> findByUserId(Long userId);
    List<Artwork> findByAuthor(String author);
    Optional<Artwork> findFirstByName(String name);
    Optional<Artwork> findById(Long id);

}
