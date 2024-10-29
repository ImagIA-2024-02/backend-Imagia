package com.acme.backendimagia.application.artwork_recognition;

import com.acme.backendimagia.application.dto.ObraDeArteDTO;
import com.acme.backendimagia.domain.model.ObraDeArte;
import com.acme.backendimagia.domain.model.Usuario;
import com.acme.backendimagia.domain.repository.ObraDeArteRepository;
import com.acme.backendimagia.domain.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ObraDeArteService {
    @Autowired
    private ObraDeArteRepository obraDeArteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<ObraDeArteDTO> obtenerObrasGuardadasPorUsuario(Long usuarioId) {
        List<ObraDeArte> obras = obraDeArteRepository.findByUsuarioId(usuarioId);
        return (obras != null ? obras : List.<ObraDeArte>of()).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ObraDeArteDTO> obtenerObrasPorAutor(String autor) {
        List<ObraDeArte> obras = obraDeArteRepository.findByAutor(autor);
        return (obras != null ? obras : List.<ObraDeArte>of()).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ObraDeArteDTO obtenerObraPorNombre(String nombre) {
        Optional<ObraDeArte> obraOptional = obraDeArteRepository.findFirstByNombre(nombre);
        return obraOptional.map(this::mapToDTO).orElse(null);
    }

    @Transactional
    public void crearObraDeArte(ObraDeArteDTO obraDeArteDTO) {
        Usuario usuario = usuarioRepository.findById(obraDeArteDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + obraDeArteDTO.getUsuarioId()));

        ObraDeArte obra = new ObraDeArte();
        obra.setUsuario(usuario);
        obra.setAutor(obraDeArteDTO.getAutor());
        obra.setNombre("No reconocido");
        obra.setEpoca("No reconocido");
        obra.setDescripcion("No reconocido");
        obra.setImg(obraDeArteDTO.getImg());

        obraDeArteRepository.save(obra);
    }

    public void eliminarObraDeArte(Long obraId) {
        obraDeArteRepository.deleteById(obraId);
    }

    public ObraDeArteDTO obtenerObraDeArtePorId(Long obraId){
        Optional<ObraDeArte> obraOptional = obraDeArteRepository.findById(obraId);
        return obraOptional.map(this::mapToDTO).orElse(null);
    }

    // Método privado para mapear ObraDeArte a ObraDeArteDTO
    private ObraDeArteDTO mapToDTO(ObraDeArte obra) {
        ObraDeArteDTO dto = new ObraDeArteDTO();
        dto.setId(obra.getId());
        dto.setAutor(obra.getAutor());
        dto.setNombre(obra.getNombre());
        dto.setEpoca(obra.getEpoca());
        dto.setDescripcion(obra.getDescripcion());
        dto.setImg(obra.getImg());
        dto.setUsuarioId(obra.getUsuario().getId()); // Asegúrate de que getUsuario() no sea null
        return dto;
    }
}
