package com.acme.backendimagia.application.user_management;

import com.acme.backendimagia.application.dto.UserDTO;
import com.acme.backendimagia.domain.model.UserType;
import com.acme.backendimagia.domain.model.User;
import com.acme.backendimagia.domain.repository.UserRepository;
import com.acme.backendimagia.interfaces.persistence.exception.NotFoundException;
import com.acme.backendimagia.shared.exception.BusinessExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO registerUser(UserDTO userDTO) {
        User newUser = new User();
        newUser.setEmail(userDTO.getEmail());
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setPhone(userDTO.getPhone());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        try {
            newUser.setUserType(UserType.valueOf(userDTO.getUserType().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new BusinessExceptions.BadRequestException("Tipo de usuario inválido.");
        }

        User savedUser = userRepository.save(newUser);
        return mapToDTO(savedUser);
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessExceptions.ResourceNotFoundException("Usuario no encontrado"));

        return mapToDTO(user);
    }
    // Actualizar un usuario
    public void updateUser(UserDTO userDTO) {
        User existingUser = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new BusinessExceptions.ResourceNotFoundException("Usuario no encontrado"));

        existingUser.setEmail(userDTO.getEmail());
        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setPhone(userDTO.getPhone());
        existingUser.setUserType(UserType.valueOf(userDTO.getUserType().toUpperCase()));

        userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    // Iniciar sesión con correo y contraseña

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessExceptions.ResourceNotFoundException("Usuario no encontrado"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessExceptions.UnauthorizedException("Contraseña incorrecta");
        }
        return "Sesión iniciada";
    }
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessExceptions.ResourceNotFoundException("Usuario no encontrado"));
        return mapToDTO(user);
    }

    // Conversión de entidad a DTO
    private UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setPassword(user.getPassword());
        dto.setUserType(user.getUserType().name());
        return dto;
    }




}
