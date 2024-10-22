package com.acme.backendimagia.application.user_management;

import com.acme.backendimagia.application.dto.PerfilInvestigadorDTO;
import com.acme.backendimagia.domain.model.PerfilInvestigador;
import com.acme.backendimagia.domain.model.TipoUsuario;
import com.acme.backendimagia.domain.model.Usuario;
import com.acme.backendimagia.domain.repository.PerfilInvestigadorRepository;
import com.acme.backendimagia.domain.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class PerfilInvestigadorService {

        @Autowired
        private PerfilInvestigadorRepository perfilInvestigadorRepository;

        @Autowired
        private UsuarioRepository usuarioRepository;

        public PerfilInvestigadorDTO obtenerPerfilPorUsuarioId(Long usuarioId) {
            PerfilInvestigador perfil = perfilInvestigadorRepository.findByUsuarioId(usuarioId);
            if (perfil == null) {
                throw new RuntimeException("Perfil de investigador no encontrado para el usuario con ID: " + usuarioId);
            }
            return mapToDTO(perfil);
        }

        // Método para actualizar un perfil de investigador
        @Transactional
        public void actualizarPerfil(PerfilInvestigadorDTO perfilDTO) {
            PerfilInvestigador perfil = perfilInvestigadorRepository.findByUsuarioId(perfilDTO.getUsuarioId());
            if (perfil == null) {
                throw new RuntimeException("Perfil de investigador no encontrado para el usuario con ID: " + perfilDTO.getUsuarioId());
            }

            perfil.setIntereses(perfilDTO.getIntereses());
            perfil.setInstitucion(perfilDTO.getInstitucion());
            perfil.setGradoAcademico(perfilDTO.getGradoAcademico());
            perfil.setAreaInvestigacion(perfilDTO.getAreaInvestigacion());
            perfil.setIntereses(perfilDTO.getIntereses());

            perfilInvestigadorRepository.save(perfil);
        }

        @Transactional
        public void crearPerfilInvestigador(PerfilInvestigadorDTO perfilDTO) {
            Usuario usuario = usuarioRepository.findById(perfilDTO.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + perfilDTO.getUsuarioId()));

            if (usuario.getTipoUsuario() != TipoUsuario.INVESTIGADOR) {
                throw new IllegalArgumentException("El usuario con ID " + perfilDTO.getUsuarioId() + " no es un investigador.");
            }

            PerfilInvestigador perfil = new PerfilInvestigador();
            perfil.setUsuario(usuario);
            perfil.setIntereses(perfilDTO.getIntereses());
            perfil.setInstitucion(perfilDTO.getInstitucion());
            perfil.setGradoAcademico(perfilDTO.getGradoAcademico());
            perfil.setAreaInvestigacion(perfilDTO.getAreaInvestigacion());
            perfil.setIntereses(perfilDTO.getIntereses());

            perfilInvestigadorRepository.save(perfil);
        }

        private PerfilInvestigadorDTO mapToDTO(PerfilInvestigador perfil) {
            PerfilInvestigadorDTO perfilDTO = new PerfilInvestigadorDTO();
            perfilDTO.setUsuarioId(perfil.getUsuario().getId());
            perfilDTO.setIntereses(perfil.getIntereses());
            perfilDTO.setInstitucion(perfil.getInstitucion());
            perfilDTO.setGradoAcademico(perfil.getGradoAcademico());
            perfilDTO.setAreaInvestigacion(perfil.getAreaInvestigacion());
            return perfilDTO;
        }


}
