package com.acme.backendimagia.application.user_management;

import com.acme.backendimagia.application.dto.CitizenProfileDTO;
import com.acme.backendimagia.domain.model.CitizenProfile;
import com.acme.backendimagia.domain.model.UserType;
import com.acme.backendimagia.domain.model.User;
import com.acme.backendimagia.domain.repository.CitizenProfileRepository;
import com.acme.backendimagia.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CitizenProfileService {

    @Autowired
    private CitizenProfileRepository citizenProfileRepository;

    @Autowired
    private UserRepository userRepository;

    public CitizenProfileDTO getProfileByUserId(Long userId) {
        CitizenProfile profile = citizenProfileRepository.findByUserId(userId);
        if (profile == null) {
            throw new RuntimeException("Perfil de ciudadano no encontrado para el usuario con ID: " + userId);
        }
        return mapToDTO(profile);
    }

    // Método para actualizar un perfil de ciudadano
    @Transactional
    public void updateProfile(CitizenProfileDTO profileDTO) {
        CitizenProfile profile = citizenProfileRepository.findByUserId(profileDTO.getUserId());
        if (profile == null) {
            throw new RuntimeException("Perfil de ciudadano no encontrado para el usuario con ID: " + profileDTO.getUserId());
        }

        profile.setInterests(profileDTO.getInterests());
        profile.setArtType(profileDTO.getArtType());
        profile.setDescription(profileDTO.getDescription());
        profile.setFavoriteArtworks(profileDTO.getSavedArtworks());


        citizenProfileRepository.save(profile);
    }

    @Transactional
    public void createCitizenProfile(CitizenProfileDTO profileDTO) {
        User user = userRepository.findById(profileDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + profileDTO.getUserId()));
        if (user.getUserType() != UserType.CIUDADANO) {
            throw new IllegalArgumentException("El usuario con ID " + profileDTO.getUserId() + " no es un ciudadano.");
        }

        CitizenProfile perfil = new CitizenProfile();
        perfil.setUser(user);
        perfil.setInterests(profileDTO.getInterests());
        perfil.setArtType(profileDTO.getArtType());
        perfil.setDescription(profileDTO.getDescription());
        perfil.setFavoriteArtworks(profileDTO.getSavedArtworks());


        citizenProfileRepository.save(perfil);
    }

    private CitizenProfileDTO mapToDTO(CitizenProfile profile) {
        CitizenProfileDTO dto = new CitizenProfileDTO();
        dto.setUserId(profile.getUser().getId());
        dto.setInterests(profile.getInterests());
        dto.setArtType(profile.getArtType());
        dto.setDescription(profile.getDescription());
        dto.setSavedArtworks(profile.getFavoriteArtworks());
        return dto;
    }
}
