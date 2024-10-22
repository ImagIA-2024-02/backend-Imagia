package com.acme.backendimagia.application.user_management;
import com.acme.backendimagia.application.dto.PerfilEstudianteDTO;
import com.acme.backendimagia.domain.model.PerfilEstudiante;
import com.acme.backendimagia.domain.model.TipoUsuario;
import com.acme.backendimagia.domain.model.Usuario;
import com.acme.backendimagia.domain.repository.PerfilEstudianteRepository;
import com.acme.backendimagia.domain.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PerfilEstudianteService {

    @Autowired
    private PerfilEstudianteRepository perfilEstudianteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    public PerfilEstudianteDTO obtenerPerfilPorUsuarioId(Long usuarioId) {
        PerfilEstudiante perfil = perfilEstudianteRepository.findByUsuarioId(usuarioId);
        if (perfil == null) {
            throw new RuntimeException("Perfil de estudiante no encontrado para el usuario con ID: " + usuarioId);
        }
        return mapToDTO(perfil);
    }

    // Método para actualizar un perfil de estudiante
    @Transactional
    public void actualizarPerfil(PerfilEstudianteDTO perfilDTO) {
        PerfilEstudiante perfil = perfilEstudianteRepository.findByUsuarioId(perfilDTO.getUsuarioId());
        if (perfil == null) {
            throw new RuntimeException("Perfil de estudiante no encontrado para el usuario con ID: " + perfilDTO.getUsuarioId());
        }

        perfil.setInstitucion(perfilDTO.getInstitucion());
        perfil.setIntereses(perfilDTO.getIntereses());
        perfil.setInstitucion(perfilDTO.getInstitucion());
        perfil.setCarrera(perfilDTO.getCarrera());
        perfil.setSemestre(perfilDTO.getSemestre());
        perfil.setDescripcion(perfilDTO.getDescripcion());


        perfilEstudianteRepository.save(perfil);
    }

    @Transactional
    public void crearPerfilEstudiante(PerfilEstudianteDTO perfilDTO) {
        Usuario usuario = usuarioRepository.findById(perfilDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + perfilDTO.getUsuarioId()));

        if (usuario.getTipoUsuario() != TipoUsuario.ESTUDIANTE) {
            throw new IllegalArgumentException("El usuario con ID " + perfilDTO.getUsuarioId() + " no es un estudiante.");
        }

        PerfilEstudiante perfil = new PerfilEstudiante();
        perfil.setUsuario(usuario);
        perfil.setIntereses(perfilDTO.getIntereses());
        perfil.setInstitucion(perfilDTO.getInstitucion());
        perfil.setCarrera(perfilDTO.getCarrera());
        perfil.setSemestre(perfilDTO.getSemestre());
        perfil.setDescripcion(perfilDTO.getDescripcion());

        perfilEstudianteRepository.save(perfil);
    }

    private PerfilEstudianteDTO mapToDTO(PerfilEstudiante perfil) {
        PerfilEstudianteDTO dto = new PerfilEstudianteDTO();
        dto.setUsuarioId(perfil.getUsuario().getId());
        dto.setIntereses(perfil.getIntereses());
        dto.setInstitucion(perfil.getInstitucion());
        dto.setCarrera(perfil.getCarrera());
        dto.setSemestre(perfil.getSemestre());
        dto.setDescripcion(perfil.getDescripcion());
        return dto;
    }
}
