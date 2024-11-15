package com.acme.backendimagia.infraestructure.rest;

import com.acme.backendimagia.application.dto.CitizenProfileDTO;
import com.acme.backendimagia.application.dto.StudentProfileDTO;
import com.acme.backendimagia.application.dto.ResearcherProfileDTO;
import com.acme.backendimagia.application.dto.UserDTO;
import com.acme.backendimagia.application.user_management.CitizenProfileService;
import com.acme.backendimagia.application.user_management.StudentProfileService;
import com.acme.backendimagia.application.user_management.PerfilInvestigadorService;
import com.acme.backendimagia.application.user_management.UserService;
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
public class UserController {

        @Autowired
        private UserService userService;

        @Autowired
        private StudentProfileService studentProfileService;

       @Autowired
        private CitizenProfileService citizenProfileService;

       @Autowired
        PerfilInvestigadorService perfilInvestigadorService;

    // Registro de un nuevo usuario (ciudadano, estudiante, investigador)
     @PostMapping("/registro")
    public ResponseEntity<Map<String, Object>> registerUser(@Valid @RequestBody RegisterRequest request) {
         UserDTO userDTO = new UserDTO();
         userDTO.setEmail(request.getEmail());
         userDTO.setPassword(request.getPassword());
         userDTO.setFirstName(request.getNombre());
         userDTO.setLastName(request.getApellido());
         userDTO.setPhone(request.getTelefono());
         userDTO.setUserType(request.getTipoUsuario());

         UserDTO registeredUser = userService.registerUser(userDTO);

         // verificar si el usuario fue guardado correctamente

            if (registeredUser == null || registeredUser.getId() == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("message", "Error al registrar el usuario"));
            }

         // Si el tipo de usuario es Ciudadano, Estudiante o Investigador, se crea el perfil correspondiente

            if (registeredUser.getUserType().equals("CIUDADANO")) {
                CitizenProfileDTO citizenProfile = new CitizenProfileDTO();
                citizenProfile.setUserId(registeredUser.getId());
                citizenProfileService.createCitizenProfile(citizenProfile);
            }
            else if (registeredUser.getUserType().equals("ESTUDIANTE")) {
                StudentProfileDTO studentProfile = new StudentProfileDTO();
                studentProfile.setUserId(registeredUser.getId());
                studentProfileService.createStudentProfile(studentProfile);


            } else if (registeredUser.getUserType().equals("INVESTIGADOR")) {
                ResearcherProfileDTO researcherProfile = new ResearcherProfileDTO();
                researcherProfile.setUserId(registeredUser.getId());
                perfilInvestigadorService.crearPerfilInvestigador(researcherProfile);
            }

         // Crear un mapa con los datos que se devolverán como respuesta JSON

            HashMap<String, Object> response = new HashMap<>();
            response.put("message", "Usuario registrado correctamente");
            response.put("users", registeredUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

            }

        // Eliminar un usuario
        @DeleteMapping("/{id}")
         public ResponseEntity<HashMap<String, Object>> deleteUser(@PathVariable Long id) {
            userService.deleteUser(id);
            HashMap<String, Object> response = new HashMap<>();
            response.put("message", "Usuario eliminado correctamente");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

    // -------------------
    // Gestión de Perfiles
    // -------------------

    // obtener el perfil de ciudadano por su ID de usuario
    @GetMapping("/perfil-ciudadano/{usuarioId}")
    public ResponseEntity<CitizenProfileDTO> getCitizenProfile(@PathVariable Long usuarioId) {
        CitizenProfileDTO citizenProfile = citizenProfileService.getProfileByUserId(usuarioId);
        return ResponseEntity.ok(citizenProfile);
    }

    // actualizar el perfil de ciudadano
    @PutMapping("/perfil-ciudadano/{usuarioId}")
    public ResponseEntity<HashMap<String, Object>> updateCitizenProfile(@PathVariable Long usuarioId, @RequestBody CitizenProfileDTO citizenProfileDTO) {
        citizenProfileDTO.setUserId(usuarioId);
        citizenProfileService.updateProfile(citizenProfileDTO);
        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "Perfil de ciudadano actualizado correctamente");
        return ResponseEntity.ok(response);
    }

    // obtener el perfil de estudiante por su ID de usuario
    @GetMapping("/perfil-estudiante/{usuarioId}")
    public ResponseEntity<StudentProfileDTO> getStudentProfile(@PathVariable Long usuarioId) {
        StudentProfileDTO studentProfile = studentProfileService.getProfileByUserId(usuarioId);
        return ResponseEntity.ok(studentProfile);
    }

    // actualizar el perfil de estudiante
    @PutMapping("/perfil-estudiante/{usuarioId}")
    public ResponseEntity<HashMap<String, Object>> updateStudentProfile(@PathVariable Long usuarioId, @RequestBody StudentProfileDTO studentProfileDTO) {
        studentProfileDTO.setUserId(usuarioId);
        studentProfileService.updateProfile(studentProfileDTO);
        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "Perfil de estudiante actualizado correctamente");
        return ResponseEntity.ok(response);
    }

    // obtener el perfil de investigador por su ID de usuario
    @GetMapping("/perfil-investigador/{usuarioId}")
    public ResponseEntity<ResearcherProfileDTO> getResearcherProfile(@PathVariable Long usuarioId) {
        ResearcherProfileDTO researcherProfile = perfilInvestigadorService.obtenerPerfilPorUsuarioId(usuarioId);
        return ResponseEntity.ok(researcherProfile);
    }

    // actualizar el perfil de investigador

    @PutMapping("/perfil-investigador/{usuarioId}")
    public ResponseEntity<HashMap<String, Object>> updateResearcherProfile(@PathVariable Long usuarioId, @RequestBody ResearcherProfileDTO researcherProfileDTO) {
        researcherProfileDTO.setUserId(usuarioId);
        perfilInvestigadorService.actualizarPerfil(researcherProfileDTO);
        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "Perfil de investigador actualizado correctamente");
        return ResponseEntity.ok(response);
    }

    // obtener perfil completo de un usuario (ciudadano, estudiante, investigador)
    @GetMapping("/perfil-completo-ciudadano/{usuarioId}")
    public ResponseEntity<HashMap<String, Object>> getCompleteCitizenProfile(@PathVariable Long usuarioId) {
        try {
            UserDTO userDTO = userService.getUserById(usuarioId);
            CitizenProfileDTO citizenProfile = citizenProfileService.getProfileByUserId(usuarioId);

            HashMap<String, Object> response = new HashMap<>();
            response.put("email", userDTO.getEmail());
            response.put("nombre", userDTO.getFirstName());
            response.put("apellido", userDTO.getLastName());
            response.put("telefono", userDTO.getPhone());
            response.put("descripcion", citizenProfile.getDescription());
            response.put("intereses", citizenProfile.getInterests());
            response.put("obras_favoritas", citizenProfile.getSavedArtworks());
            response.put("tipo_de_arte", citizenProfile.getArtType());
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
    public ResponseEntity<HashMap<String, Object>> getCompleteStudentProfile(@PathVariable Long usuarioId) {
        try {
            UserDTO userDTO = userService.getUserById(usuarioId);
            StudentProfileDTO studentProfile = studentProfileService.getProfileByUserId(usuarioId);

            HashMap<String, Object> response = new HashMap<>();
            response.put("email", userDTO.getEmail());
            response.put("nombre", userDTO.getFirstName());
            response.put("apellido", userDTO.getLastName());
            response.put("telefono", userDTO.getPhone());
            response.put("institucion", studentProfile.getInstitution());
            response.put("carrera", studentProfile.getCareer());
            response.put("semestre", studentProfile.getSemester());
            response.put("descripcion", studentProfile.getDescription());
            response.put("intereses", studentProfile.getInterests());
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

    public ResponseEntity<HashMap<String, Object>> getCompleteResearcherProfile(@PathVariable Long usuarioId) {
        try {
            UserDTO userDTO = userService.getUserById(usuarioId);
            ResearcherProfileDTO researcherProfile = perfilInvestigadorService.obtenerPerfilPorUsuarioId(usuarioId);

            HashMap<String, Object> response = new HashMap<>();
            response.put("email", userDTO.getEmail());
            response.put("nombre", userDTO.getFirstName());
            response.put("apellido", userDTO.getLastName());
            response.put("telefono", userDTO.getPhone());
            response.put("institucion", researcherProfile.getInstitution());
            response.put("area_investigacion", researcherProfile.getResearchArea());
            response.put("descripcion", researcherProfile.getDescription());
            response.put("intereses", researcherProfile.getIntereses());
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
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            String token = userService.login(request.getEmail(), request.getPassword());
            UserDTO userDTO = userService.getUserByEmail(request.getEmail());
            AuthResponse response = new AuthResponse();
            response.setToken(token);
            response.setTipoUsuario(userDTO.getUserType());
            response.setUsuarioId(userDTO.getId());
            response.setNombre(userDTO.getFirstName());
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
