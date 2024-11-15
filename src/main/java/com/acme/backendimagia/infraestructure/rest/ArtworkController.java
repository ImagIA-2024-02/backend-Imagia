package com.acme.backendimagia.infraestructure.rest;

import com.acme.backendimagia.application.artwork_recognition.ArtworkService;
import com.acme.backendimagia.application.dto.ArtworkDTO;
import com.acme.backendimagia.application.dto.UserDTO;
import com.acme.backendimagia.application.user_management.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/obras_arte")
public class ArtworkController {
    @Autowired
    private UserService userService;

    @Autowired
    private ArtworkService artworkService;

    @PostMapping()
    public ResponseEntity<Map<String, Object>> registerArtwork(@Valid @RequestBody ArtworkDTO artworkDTO) {
        Map<String, Object> response = new HashMap<>();

        // Verificar si el usuario existe
        UserDTO user = userService.getUserById(artworkDTO.getUserId());
        if (user == null) {
            response.put("message", "Usuario no encontrado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Crear y guardar la obra de arte
        try {
            artworkService.createArtwork(artworkDTO);
            response.put("message", "Obra de arte registrada correctamente");
            response.put("obraDeArte", artworkDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("message", "Error al registrar la obra de arte");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{obraId}")
    public ResponseEntity<Map<String, Object>> deleteArtwork(@PathVariable Long obraId) {
        artworkService.deleteArtwork(obraId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Obra de arte eliminada");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{obraId}")
    public ResponseEntity<ArtworkDTO> getArtworkById(@PathVariable Long obraId) {
        ArtworkDTO artworkDTO = artworkService.getArtworkById(obraId);
        return ResponseEntity.ok(artworkDTO);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ArtworkDTO>> getArtworksByUserId(@PathVariable Long usuarioId) {
        List<ArtworkDTO> artwork = artworkService.getSavedArtworksByUser(usuarioId);
        return ResponseEntity.ok(artwork);
    }

    @GetMapping("/autor/{autor}")
    public ResponseEntity<List<ArtworkDTO>> getArtworksByAuthor(@PathVariable String autor) {
        List<ArtworkDTO> artwork = artworkService.getArtworksByAuthor(autor);
        return ResponseEntity.ok(artwork);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<ArtworkDTO> getArtworkByName(@PathVariable String nombre) {
        ArtworkDTO artworkDTO = artworkService.getArtworkByName(nombre);
        return ResponseEntity.ok(artworkDTO);
    }


}
