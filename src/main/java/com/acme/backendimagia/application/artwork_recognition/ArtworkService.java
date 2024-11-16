package com.acme.backendimagia.application.artwork_recognition;

import com.acme.backendimagia.application.dto.ArtworkDTO;
import com.acme.backendimagia.domain.model.Artwork;
import com.acme.backendimagia.domain.model.User;
import com.acme.backendimagia.domain.repository.ArtworkRepository;
import com.acme.backendimagia.domain.repository.UserRepository;
import com.acme.backendimagia.shared.exception.BusinessExceptions;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArtworkService {
    @Autowired
    private ArtworkRepository artworkRepository;

    @Autowired
    private UserRepository userRepository;

    public List<ArtworkDTO> getSavedArtworksByUser(Long userId) {
        // Verificar si el usuario existe
        if (!userRepository.existsById(userId)) {
            throw new BusinessExceptions.ResourceNotFoundException("Usuario no encontrado con ID: " + userId);
        }

        List<Artwork> artworks = artworkRepository.findByUserId(userId);
        return (artworks != null ? artworks : List.<Artwork>of()).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ArtworkDTO> getArtworksByAuthor(String author) {
        List<Artwork> artworks = artworkRepository.findByAuthor(author);
        return (artworks != null ? artworks : List.<Artwork>of()).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ArtworkDTO getArtworkByName(String name) {
        return artworkRepository.findFirstByName(name)
                .map(this::mapToDTO)
                .orElseThrow(() -> new BusinessExceptions.ResourceNotFoundException("Obra de arte no encontrada con nombre: " + name));
    }

    @Transactional
    public void createArtwork(ArtworkDTO artworkDTO) {
        User user = userRepository.findById(artworkDTO.getUserId())
                .orElseThrow(() -> new BusinessExceptions.ResourceNotFoundException("Usuario no encontrado con ID: " + artworkDTO.getUserId()));

        Artwork obra = new Artwork();
        obra.setUser(user);
        obra.setAuthor(artworkDTO.getAuthor());
        obra.setName("No reconocido");
        obra.setEpoch("No reconocido");
        obra.setDescription("No reconocido");
        obra.setImg(artworkDTO.getImg());

        artworkRepository.save(obra);
    }

    public void deleteArtwork(Long obraId) {
        if (!artworkRepository.existsById(obraId)) {
            throw new BusinessExceptions.ResourceNotFoundException("Obra de arte no encontrada con ID: " + obraId);
        }
        artworkRepository.deleteById(obraId);
    }


    public ArtworkDTO getArtworkById(Long obraId){
        Optional<Artwork> obraOptional = artworkRepository.findById(obraId);
        return obraOptional.map(this::mapToDTO)
                .orElseThrow(() -> new BusinessExceptions.ResourceNotFoundException("Obra de arte no encontrada con ID: " + obraId));
    }

    // Método privado para mapear ObraDeArte a ObraDeArteDTO
    private ArtworkDTO mapToDTO(Artwork obra) {
        ArtworkDTO dto = new ArtworkDTO();
        dto.setId(obra.getId());
        dto.setAuthor(obra.getAuthor());
        dto.setName(obra.getName());
        dto.setEpoch(obra.getEpoch());
        dto.setDescription(obra.getDescription());
        dto.setImg(obra.getImg());
        dto.setUserId(obra.getUser().getId()); // Asegúrate de que getUsuario() no sea null
        return dto;
    }
}
