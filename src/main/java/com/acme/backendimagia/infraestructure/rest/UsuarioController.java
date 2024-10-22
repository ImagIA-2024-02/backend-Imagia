package com.acme.backendimagia.infraestructure.rest;

import com.acme.backendimagia.application.dto.PerfilCiudadanoDTO;
import com.acme.backendimagia.application.dto.PerfilEstudianteDTO;
import com.acme.backendimagia.application.dto.PerfilInvestigadorDTO;
import com.acme.backendimagia.application.dto.UsuarioDTO;
import com.acme.backendimagia.application.user_management.PerfilCiudadanoService;
import com.acme.backendimagia.application.user_management.PerfilEstudianteService;
import com.acme.backendimagia.application.user_management.PerfilInvestigadorService;
import com.acme.backendimagia.application.user_management.UsuarioService;
import com.acme.backendimagia.domain.model.Usuario;
import com.acme.backendimagia.infraestructure.rest.dto.AuthResponse;
import com.acme.backendimagia.infraestructure.rest.dto.LoginRequest;
import com.acme.backendimagia.infraestructure.rest.dto.RegisterRequest;
import com.acme.backendimagia.interfaces.persistence.exception.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

        @Autowired
        private UsuarioService usuarioService;

        @Autowired
        private PerfilEstudianteService perfilEstudianteService;

       @Autowired
        private PerfilCiudadanoService perfilCiudadanoService;

       @Autowired
        PerfilInvestigadorService perfilInvestigadorService;

    // Registro de un nuevo usuario (ciudadano, estudiante, investigador)
     @PostMapping("/registro")
    public ResponseEntity<Map<String, Object>> registrarUsuario(@Valid @RequestBody RegisterRequest request) {
         UsuarioDTO usuarioDTO = new UsuarioDTO();
         usuarioDTO.setEmail(request.getEmail());
         usuarioDTO.setPassword(request.getPassword());
         usuarioDTO.setNombre(request.getNombre());
         usuarioDTO.setApellido(request.getApellido());
         usuarioDTO.setTelefono(request.getTelefono());
         usuarioDTO.setTipoUsuario(request.getTipoUsuario());

         UsuarioDTO usuarioRegistrado = usuarioService.registrarUsuario(usuarioDTO);

         // verificar si el usuario fue guardado correctamente

            if (usuarioRegistrado == null || usuarioRegistrado.getId() == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("message", "Error al registrar el usuario"));
            }

         // Si el tipo de usuario es Ciudadano, Estudiante o Investigador, se crea el perfil correspondiente

            if (usuarioRegistrado.getTipoUsuario().equals("CIUDADANO")) {
                PerfilCiudadanoDTO perfilCiudadano = new PerfilCiudadanoDTO();
                perfilCiudadano.setUsuarioId(usuarioRegistrado.getId());
                perfilCiudadanoService.crearPerfilCiudadano(perfilCiudadano);
            }
            else if (usuarioRegistrado.getTipoUsuario().equals("ESTUDIANTE")) {
                PerfilEstudianteDTO perfilEstudiante = new PerfilEstudianteDTO();
                perfilEstudiante.setUsuarioId(usuarioRegistrado.getId());
                perfilEstudianteService.crearPerfilEstudiante(perfilEstudiante);


            } else if (usuarioRegistrado.getTipoUsuario().equals("INVESTIGADOR")) {
                PerfilInvestigadorDTO perfilInvestigador = new PerfilInvestigadorDTO();
                perfilInvestigador.setUsuarioId(usuarioRegistrado.getId());
                perfilInvestigadorService.crearPerfilInvestigador(perfilInvestigador);
            }

         // Crear un mapa con los datos que se devolverán como respuesta JSON

            HashMap<String, Object> response = new HashMap<>();
            response.put("message", "Usuario registrado correctamente");
            response.put("usuario", usuarioRegistrado);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

            }

        // Eliminar un usuario
        @DeleteMapping("/{id}")
         public ResponseEntity<HashMap<String, Object>> eliminarUsuario(@PathVariable Long id) {
            usuarioService.eliminarUsuario(id);
            HashMap<String, Object> response = new HashMap<>();
            response.put("message", "Usuario eliminado correctamente");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

    // -------------------
    // Gestión de Perfiles
    // -------------------

    // obtener el perfil de ciudadano por su ID de usuario
    @GetMapping("/perfil-ciudadano/{usuarioId}")
    public ResponseEntity<PerfilCiudadanoDTO> obtenerPerfilCiudadano(@PathVariable Long usuarioId) {
        PerfilCiudadanoDTO perfilCiudadano = perfilCiudadanoService.obtenerPerfilPorUsuarioId(usuarioId);
        return ResponseEntity.ok(perfilCiudadano);
    }

    // actualizar el perfil de ciudadano
    @PutMapping("/perfil-ciudadano/{usuarioId}")
    public ResponseEntity<HashMap<String, Object>> actualizarPerfilCiudadano(@PathVariable Long usuarioId, @RequestBody PerfilCiudadanoDTO perfilCiudadanoDTO) {
        perfilCiudadanoDTO.setUsuarioId(usuarioId);
        perfilCiudadanoService.actualizarPerfil(perfilCiudadanoDTO);
        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "Perfil de ciudadano actualizado correctamente");
        return ResponseEntity.ok(response);
    }

    // obtener el perfil de estudiante por su ID de usuario
    @GetMapping("/perfil-estudiante/{usuarioId}")
    public ResponseEntity<PerfilEstudianteDTO> obtenerPerfilEstudiante(@PathVariable Long usuarioId) {
        PerfilEstudianteDTO perfilEstudiante = perfilEstudianteService.obtenerPerfilPorUsuarioId(usuarioId);
        return ResponseEntity.ok(perfilEstudiante);
    }

    // actualizar el perfil de estudiante
    @PutMapping("/perfil-estudiante/{usuarioId}")
    public ResponseEntity<HashMap<String, Object>> actualizarPerfilEstudiante(@PathVariable Long usuarioId, @RequestBody PerfilEstudianteDTO perfilEstudianteDTO) {
        perfilEstudianteDTO.setUsuarioId(usuarioId);
        perfilEstudianteService.actualizarPerfil(perfilEstudianteDTO);
        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "Perfil de estudiante actualizado correctamente");
        return ResponseEntity.ok(response);
    }

    // obtener el perfil de investigador por su ID de usuario
    @GetMapping("/perfil-investigador/{usuarioId}")
    public ResponseEntity<PerfilInvestigadorDTO> obtenerPerfilInvestigador(@PathVariable Long usuarioId) {
        PerfilInvestigadorDTO perfilInvestigador = perfilInvestigadorService.obtenerPerfilPorUsuarioId(usuarioId);
        return ResponseEntity.ok(perfilInvestigador);
    }

    // actualizar el perfil de investigador

    @PutMapping("/perfil-investigador/{usuarioId}")
    public ResponseEntity<HashMap<String, Object>> actualizarPerfilInvestigador(@PathVariable Long usuarioId, @RequestBody PerfilInvestigadorDTO perfilInvestigadorDTO) {
        perfilInvestigadorDTO.setUsuarioId(usuarioId);
        perfilInvestigadorService.actualizarPerfil(perfilInvestigadorDTO);
        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "Perfil de investigador actualizado correctamente");
        return ResponseEntity.ok(response);
    }

    // obtener perfil completo de un usuario (ciudadano, estudiante, investigador)
    @GetMapping("/perfil-completo-ciudadano/{usuarioId}")
    public ResponseEntity<HashMap<String, Object>> obtenerPerfilCompletoCiudadano(@PathVariable Long usuarioId) {
        try {
            UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioPorId(usuarioId);
            PerfilCiudadanoDTO perfilCiudadano = perfilCiudadanoService.obtenerPerfilPorUsuarioId(usuarioId);

            HashMap<String, Object> response = new HashMap<>();
            response.put("email", usuarioDTO.getEmail());
            response.put("nombre", usuarioDTO.getNombre());
            response.put("apellido", usuarioDTO.getApellido());
            response.put("telefono", usuarioDTO.getTelefono());
            response.put("descripcion", perfilCiudadano.getDescripcion());
            response.put("intereses", perfilCiudadano.getIntereses());
            response.put("obras_favoritas", perfilCiudadano.getObrasFavoritas());
            response.put("tipo_de_arte", perfilCiudadano.getTipoDeArte());
            return ResponseEntity.ok(response);

        } catch (NotFoundException e) {
            HashMap<String, Object> response = new HashMap<>();
            response.put("mensaje", "Usuario o perfil no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            HashMap<String, Object> response = new HashMap<>();
            response.put("mensaje", "Error al obtener el perfil del usuario");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }


    }

    // obtener perfil completo de un usuario (ciudadano, estudiante, investigador)
    @GetMapping("/perfil-completo-estudiante/{usuarioId}")
    public ResponseEntity<HashMap<String, Object>> obtenerPerfilCompletoEstudiante(@PathVariable Long usuarioId) {
        try {
            UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioPorId(usuarioId);
            PerfilEstudianteDTO perfilEstudiante = perfilEstudianteService.obtenerPerfilPorUsuarioId(usuarioId);

            HashMap<String, Object> response = new HashMap<>();
            response.put("email", usuarioDTO.getEmail());
            response.put("nombre", usuarioDTO.getNombre());
            response.put("apellido", usuarioDTO.getApellido());
            response.put("telefono", usuarioDTO.getTelefono());
            response.put("institucion", perfilEstudiante.getInstitucion());
            response.put("carrera", perfilEstudiante.getCarrera());
            response.put("semestre", perfilEstudiante.getSemestre());
            response.put("descripcion", perfilEstudiante.getDescripcion());
            response.put("intereses", perfilEstudiante.getIntereses());
            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            HashMap<String, Object> response = new HashMap<>();
            response.put("message", "Perfil de estudiante no encontrado para el usuario con ID: " + usuarioId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            HashMap<String, Object> response = new HashMap<>();
            response.put("message", "Error al obtener el perfil de estudiante");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }


    }

    // obtener perfil completo de un usuario (ciudadano, estudiante, investigador)
    @GetMapping("/perfil-completo-investigador/{usuarioId}")

    public ResponseEntity<HashMap<String, Object>> obtenerPerfilCompletoInvestigador(@PathVariable Long usuarioId) {
        try {
            UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioPorId(usuarioId);
            PerfilInvestigadorDTO perfilInvestigador = perfilInvestigadorService.obtenerPerfilPorUsuarioId(usuarioId);

            HashMap<String, Object> response = new HashMap<>();
            response.put("email", usuarioDTO.getEmail());
            response.put("nombre", usuarioDTO.getNombre());
            response.put("apellido", usuarioDTO.getApellido());
            response.put("telefono", usuarioDTO.getTelefono());
            response.put("institucion", perfilInvestigador.getInstitucion());
            response.put("area_investigacion", perfilInvestigador.getAreaInvestigacion());
            response.put("descripcion", perfilInvestigador.getDescripcion());
            response.put("intereses", perfilInvestigador.getIntereses());
            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            HashMap<String, Object> response = new HashMap<>();
            response.put("message", "Perfil de investigador no encontrado para el usuario con ID: " + usuarioId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            HashMap<String, Object> response = new HashMap<>();
            response.put("message", "Error al obtener el perfil de investigador");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    // -------------------
    // Inicio de Sesión
    // -------------------

    // Iniciar sesión con correo y contraseña

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> iniciarSesion(@Valid @RequestBody LoginRequest request) {
        try {
            String token = usuarioService.iniciarSesion(request.getEmail(), request.getPassword());
            UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioPorEmail(request.getEmail());
            AuthResponse response = new AuthResponse();
            response.setToken(token);
            response.setTipoUsuario(usuarioDTO.getTipoUsuario());
            response.setUsuarioId(usuarioDTO.getId());
            response.setNombre(usuarioDTO.getNombre());
            return ResponseEntity.ok(response);

     } catch (RuntimeException e) {
      if (e.getMessage().equals("Usuario no encontrado")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new AuthResponse());
        } else if (e.getMessage().equals("Contraseña incorrecta")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponse());
      }
        }






}
}
