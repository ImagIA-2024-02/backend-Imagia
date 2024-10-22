package com.acme.backendimagia.application.user_management;

import com.acme.backendimagia.application.dto.PerfilCiudadanoDTO;
import com.acme.backendimagia.domain.model.PerfilCiudadano;
import com.acme.backendimagia.domain.model.TipoUsuario;
import com.acme.backendimagia.domain.model.Usuario;
import com.acme.backendimagia.domain.repository.PerfilCiudadanoRepository;
import com.acme.backendimagia.domain.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class PerfilCiudadanoService {

    @Autowired
    private PerfilCiudadanoRepository perfilCiudadanoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public PerfilCiudadanoDTO obtenerPerfilPorUsuarioId(Long usuarioId) {
        PerfilCiudadano perfil = perfilCiudadanoRepository.findByUsuarioId(usuarioId);
        if (perfil == null) {
            throw new RuntimeException("Perfil de ciudadano no encontrado para el usuario con ID: " + usuarioId);
        }
        return mapToDTO(perfil);
    }

    // Método para actualizar un perfil de ciudadano
    @Transactional
    public void actualizarPerfil(PerfilCiudadanoDTO perfilDTO) {
        PerfilCiudadano perfil = perfilCiudadanoRepository.findByUsuarioId(perfilDTO.getUsuarioId());
        if (perfil == null) {
            throw new RuntimeException("Perfil de ciudadano no encontrado para el usuario con ID: " + perfilDTO.getUsuarioId());
        }

        perfil.setIntereses(perfilDTO.getIntereses());
        perfil.setTipoDeArte(perfilDTO.getTipoDeArte());
        perfil.setDescripcion(perfilDTO.getDescripcion());
        perfil.setObrasFavoritas(perfilDTO.getObrasFavoritas());


        perfilCiudadanoRepository.save(perfil);
    }

    @Transactional
    public void crearPerfilCiudadano(PerfilCiudadanoDTO perfilDTO) {
        Usuario usuario = usuarioRepository.findById(perfilDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + perfilDTO.getUsuarioId()));
        if (usuario.getTipoUsuario() != TipoUsuario.CIUDADANO) {
            throw new IllegalArgumentException("El usuario con ID " + perfilDTO.getUsuarioId() + " no es un ciudadano.");
        }

        PerfilCiudadano perfil = new PerfilCiudadano();
        perfil.setUsuario(usuario);
        perfil.setIntereses(perfilDTO.getIntereses());
        perfil.setTipoDeArte(perfilDTO.getTipoDeArte());
        perfil.setDescripcion(perfilDTO.getDescripcion());
        perfil.setObrasFavoritas(perfilDTO.getObrasFavoritas());


        perfilCiudadanoRepository.save(perfil);
    }

    private PerfilCiudadanoDTO mapToDTO(PerfilCiudadano perfil) {
        PerfilCiudadanoDTO dto = new PerfilCiudadanoDTO();
        dto.setUsuarioId(perfil.getUsuario().getId());
        dto.setIntereses(perfil.getIntereses());
        dto.setTipoDeArte(perfil.getTipoDeArte());
        dto.setDescripcion(perfil.getDescripcion());
        dto.setObrasFavoritas(perfil.getObrasFavoritas());
        return dto;
    }
}
