package com.acme.backendimagia.application.user_management;

import com.acme.backendimagia.application.dto.ResearcherProfileDTO;
import com.acme.backendimagia.domain.model.ResearcherProfile;
import com.acme.backendimagia.domain.model.UserType;
import com.acme.backendimagia.domain.model.User;
import com.acme.backendimagia.domain.repository.ResearcherProfileRepository;
import com.acme.backendimagia.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ResearcherProfileService {

        @Autowired
        private ResearcherProfileRepository researcherProfileRepository;

        @Autowired
        private UserRepository userRepository;

        public ResearcherProfileDTO getProfileByID(Long userId) {
            ResearcherProfile profile = researcherProfileRepository.findByUserId(userId);
            if (profile == null) {
                throw new RuntimeException("Perfil de investigador no encontrado para el usuario con ID: " + userId);
            }
            return mapToDTO(profile);
        }

        // Método para actualizar un perfil de investigador
        @Transactional
        public void updateProfile(ResearcherProfileDTO profileDTO) {
            ResearcherProfile profile = researcherProfileRepository.findByUserId(profileDTO.getUserId());
            if (profile == null) {
                throw new RuntimeException("Perfil de investigador no encontrado para el usuario con ID: " + profileDTO.getUserId());
            }

            profile.setInterests(profileDTO.getInterests());
            profile.setInstitution(profileDTO.getInstitution());
            profile.setAcademicDegree(profileDTO.getAcademicDegree());
            profile.setResearchArea(profileDTO.getResearchArea());
            profile.setInterests(profileDTO.getInterests());

            researcherProfileRepository.save(profile);
        }

        @Transactional
        public void createResearcherProfile(ResearcherProfileDTO profileDTO) {
            User user = userRepository.findById(profileDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + profileDTO.getUserId()));

            if (user.getUserType() != UserType.INVESTIGADOR) {
                throw new IllegalArgumentException("El usuario con ID " + profileDTO.getUserId() + " no es un investigador.");
            }

            ResearcherProfile perfil = new ResearcherProfile();
            perfil.setUser(user);
            perfil.setInterests(profileDTO.getInterests());
            perfil.setInstitution(profileDTO.getInstitution());
            perfil.setAcademicDegree(profileDTO.getAcademicDegree());
            perfil.setResearchArea(profileDTO.getResearchArea());
            perfil.setInterests(profileDTO.getInterests());

            researcherProfileRepository.save(perfil);
        }

        private ResearcherProfileDTO mapToDTO(ResearcherProfile profile) {
            ResearcherProfileDTO profileDTO = new ResearcherProfileDTO();
            profileDTO.setUserId(profile.getUser().getId());
            profileDTO.setInterests(profile.getInterests());
            profileDTO.setInstitution(profile.getInstitution());
            profileDTO.setAcademicDegree(profile.getAcademicDegree());
            profileDTO.setResearchArea(profile.getResearchArea());
            return profileDTO;
        }


}
