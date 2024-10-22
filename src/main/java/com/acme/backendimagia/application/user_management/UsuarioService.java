package com.acme.backendimagia.application.user_management;

import com.acme.backendimagia.application.dto.UsuarioDTO;
import com.acme.backendimagia.domain.model.TipoUsuario;
import com.acme.backendimagia.domain.model.Usuario;
import com.acme.backendimagia.domain.repository.UsuarioRepository;
import com.acme.backendimagia.interfaces.persistence.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioDTO registrarUsuario(UsuarioDTO usuarioDTO) {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setEmail(usuarioDTO.getEmail());
        nuevoUsuario.setNombre(usuarioDTO.getNombre());
        nuevoUsuario.setApellido(usuarioDTO.getApellido());
        nuevoUsuario.setTelefono(usuarioDTO.getTelefono());
        nuevoUsuario.setPassword(usuarioDTO.getPassword());

        try {
            nuevoUsuario.setTipoUsuario(TipoUsuario.valueOf(usuarioDTO.getTipoUsuario().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de usuario inválido.");
        }

        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);
        return mapToDTO(usuarioGuardado);
    }

    public UsuarioDTO obtenerUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        return mapToDTO(usuario);
    }
    // Actualizar un usuario
    public void actualizarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuarioExistente = usuarioRepository.findById(usuarioDTO.getId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        usuarioExistente.setEmail(usuarioDTO.getEmail());
        usuarioExistente.setNombre(usuarioDTO.getNombre());
        usuarioExistente.setApellido(usuarioDTO.getApellido());
        usuarioExistente.setTelefono(usuarioDTO.getTelefono());
        usuarioExistente.setTipoUsuario(TipoUsuario.valueOf(usuarioDTO.getTipoUsuario().toUpperCase()));

        usuarioRepository.save(usuarioExistente);
    }

    public void eliminarUsuario(Long id) {usuarioRepository.deleteById(id);
    }
    // Iniciar sesión con correo y contraseña

    public String iniciarSesion(String email, String password) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        if (!usuario.getPassword().equals(password)) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }
        return "Sesión iniciada";
    }
    public UsuarioDTO obtenerUsuarioPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        return mapToDTO(usuario);
    }

    // Conversión de entidad a DTO
    private UsuarioDTO mapToDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        dto.setTelefono(usuario.getTelefono());
        dto.setPassword(usuario.getPassword());
        dto.setTipoUsuario(usuario.getTipoUsuario().name());
        return dto;
    }




}
