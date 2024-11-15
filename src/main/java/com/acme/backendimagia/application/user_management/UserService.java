package com.acme.backendimagia.application.user_management;

import com.acme.backendimagia.application.dto.UserDTO;
import com.acme.backendimagia.domain.model.UserType;
import com.acme.backendimagia.domain.model.User;
import com.acme.backendimagia.domain.repository.UserRepository;
import com.acme.backendimagia.interfaces.persistence.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO registerUser(UserDTO userDTO) {
        User nuevoUser = new User();
        nuevoUser.setEmail(userDTO.getEmail());
        nuevoUser.setFirstName(userDTO.getFirstName());
        nuevoUser.setLastName(userDTO.getLastName());
        nuevoUser.setPhone(userDTO.getPhone());
        nuevoUser.setPassword(userDTO.getPassword());

        try {
            nuevoUser.setUserType(UserType.valueOf(userDTO.getUserType().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de usuario inválido.");
        }

        User savedUser = userRepository.save(nuevoUser);
        return mapToDTO(savedUser);
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        return mapToDTO(user);
    }
    // Actualizar un usuario
    public void updateUser(UserDTO userDTO) {
        User existingUser = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

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
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }
        return "Sesión iniciada";
    }
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
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
