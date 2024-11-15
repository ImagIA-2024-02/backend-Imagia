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
public class PerfilInvestigadorService {

        @Autowired
        private ResearcherProfileRepository researcherProfileRepository;

        @Autowired
        private UserRepository userRepository;

        public ResearcherProfileDTO obtenerPerfilPorUsuarioId(Long usuarioId) {
            ResearcherProfile perfil = researcherProfileRepository.findByUserId(usuarioId);
            if (perfil == null) {
                throw new RuntimeException("Perfil de investigador no encontrado para el usuario con ID: " + usuarioId);
            }
            return mapToDTO(perfil);
        }

        // Método para actualizar un perfil de investigador
        @Transactional
        public void actualizarPerfil(ResearcherProfileDTO perfilDTO) {
            ResearcherProfile perfil = researcherProfileRepository.findByUserId(perfilDTO.getUserId());
            if (perfil == null) {
                throw new RuntimeException("Perfil de investigador no encontrado para el usuario con ID: " + perfilDTO.getUserId());
            }

            perfil.setInterests(perfilDTO.getIntereses());
            perfil.setInstitution(perfilDTO.getInstitution());
            perfil.setAcademicDegree(perfilDTO.getAcademicDegree());
            perfil.setResearchArea(perfilDTO.getResearchArea());
            perfil.setInterests(perfilDTO.getIntereses());

            researcherProfileRepository.save(perfil);
        }

        @Transactional
        public void crearPerfilInvestigador(ResearcherProfileDTO perfilDTO) {
            User user = userRepository.findById(perfilDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + perfilDTO.getUserId()));

            if (user.getUserType() != UserType.INVESTIGADOR) {
                throw new IllegalArgumentException("El usuario con ID " + perfilDTO.getUserId() + " no es un investigador.");
            }

            ResearcherProfile perfil = new ResearcherProfile();
            perfil.setUser(user);
            perfil.setInterests(perfilDTO.getIntereses());
            perfil.setInstitution(perfilDTO.getInstitution());
            perfil.setAcademicDegree(perfilDTO.getAcademicDegree());
            perfil.setResearchArea(perfilDTO.getResearchArea());
            perfil.setInterests(perfilDTO.getIntereses());

            researcherProfileRepository.save(perfil);
        }

        private ResearcherProfileDTO mapToDTO(ResearcherProfile perfil) {
            ResearcherProfileDTO perfilDTO = new ResearcherProfileDTO();
            perfilDTO.setUserId(perfil.getUser().getId());
            perfilDTO.setIntereses(perfil.getInterests());
            perfilDTO.setInstitution(perfil.getInstitution());
            perfilDTO.setAcademicDegree(perfil.getAcademicDegree());
            perfilDTO.setResearchArea(perfil.getResearchArea());
            return perfilDTO;
        }


}
