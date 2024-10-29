package com.acme.backendimagia.infraestructure.rest;

import com.acme.backendimagia.application.artwork_recognition.ObraDeArteService;
import com.acme.backendimagia.application.dto.ObraDeArteDTO;
import com.acme.backendimagia.application.dto.UsuarioDTO;
import com.acme.backendimagia.application.user_management.UsuarioService;
import com.acme.backendimagia.domain.model.Usuario;
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
public class ObraDeArteController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ObraDeArteService obraDeArteService;

    @PostMapping()
    public ResponseEntity<Map<String, Object>> registrarObraDeArte(@Valid @RequestBody ObraDeArteDTO obraDeArteDTO) {
        Map<String, Object> response = new HashMap<>();

        // Verificar si el usuario existe
        UsuarioDTO usuario = usuarioService.obtenerUsuarioPorId(obraDeArteDTO.getUsuarioId());
        if (usuario == null) {
            response.put("message", "Usuario no encontrado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Crear y guardar la obra de arte
        try {
            obraDeArteService.crearObraDeArte(obraDeArteDTO);
            response.put("message", "Obra de arte registrada correctamente");
            response.put("obraDeArte", obraDeArteDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("message", "Error al registrar la obra de arte");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{obraId}")
    public ResponseEntity<Map<String, Object>> eliminarObraDeArte(@PathVariable Long obraId) {
        obraDeArteService.eliminarObraDeArte(obraId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Obra de arte eliminada");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{obraId}")
    public ResponseEntity<ObraDeArteDTO> obtenerObraDeArtePorId(@PathVariable Long obraId) {
        ObraDeArteDTO obraDeArteDTO = obraDeArteService.obtenerObraDeArtePorId(obraId);
        return ResponseEntity.ok(obraDeArteDTO);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ObraDeArteDTO>> obtenerObrasDeArtePorUsuarioId(@PathVariable Long usuarioId) {
        List<ObraDeArteDTO> obras = obraDeArteService.obtenerObrasGuardadasPorUsuario(usuarioId);
        return ResponseEntity.ok(obras);
    }

    @GetMapping("/autor/{autor}")
    public ResponseEntity<List<ObraDeArteDTO>> obtenerObrasDeArtePorAutor(@PathVariable String autor) {
        List<ObraDeArteDTO> obras = obraDeArteService.obtenerObrasPorAutor(autor);
        return ResponseEntity.ok(obras);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<ObraDeArteDTO> obtenerObraDeArtePorNombre(@PathVariable String nombre) {
        ObraDeArteDTO obraDeArteDTO = obraDeArteService.obtenerObraPorNombre(nombre);
        return ResponseEntity.ok(obraDeArteDTO);
    }


}
