package com.acme.backendimagia.application.user_management;
import com.acme.backendimagia.application.dto.StudentProfileDTO;
import com.acme.backendimagia.domain.model.StudentProfile;
import com.acme.backendimagia.domain.model.UserType;
import com.acme.backendimagia.domain.model.User;
import com.acme.backendimagia.domain.repository.StudentProfileRepository;
import com.acme.backendimagia.domain.repository.UserRepository;
import com.acme.backendimagia.shared.exception.BusinessExceptions;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentProfileService {

    @Autowired
    private StudentProfileRepository studentProfileRepository;

    @Autowired
    private UserRepository userRepository;


    public StudentProfileDTO getProfileByUserId(Long userId) {
        StudentProfile profile = studentProfileRepository.findByUserId(userId);
        if (profile == null) {
            throw new BusinessExceptions.ResourceNotFoundException("Perfil de estudiante no encontrado para el usuario con ID: " + userId);
        }
        return mapToDTO(profile);
    }

    // Método para actualizar un perfil de estudiante
    @Transactional
    public void updateProfile(StudentProfileDTO profileDTO) {
        StudentProfile profile = studentProfileRepository.findByUserId(profileDTO.getUserId());
        if (profile == null) {
            throw new BusinessExceptions.ResourceNotFoundException("Perfil de estudiante no encontrado para el usuario con ID: " + profileDTO.getUserId());
        }

        profile.setInstitution(profileDTO.getInstitution());
        profile.setInterests(profileDTO.getInterests());
        profile.setInstitution(profileDTO.getInstitution());
        profile.setCareer(profileDTO.getCareer());
        profile.setSemester(profileDTO.getSemester());
        profile.setDescription(profileDTO.getDescription());


        studentProfileRepository.save(profile);
    }

    @Transactional
    public void createStudentProfile(StudentProfileDTO profileDTO) {
        User user = userRepository.findById(profileDTO.getUserId())
                .orElseThrow(() -> new BusinessExceptions.ResourceNotFoundException("Usuario no encontrado con ID: " + profileDTO.getUserId()));

        if (user.getUserType() != UserType.ESTUDIANTE) {
            throw new BusinessExceptions.BadRequestException("El usuario con ID " + profileDTO.getUserId() + " no es un estudiante.");
        }

        StudentProfile profile = new StudentProfile();
        profile.setUser(user);
        profile.setInterests(profileDTO.getInterests());
        profile.setInstitution(profileDTO.getInstitution());
        profile.setCareer(profileDTO.getCareer());
        profile.setSemester(profileDTO.getSemester());
        profile.setDescription(profileDTO.getDescription());

        studentProfileRepository.save(profile);
    }

    private StudentProfileDTO mapToDTO(StudentProfile profile) {
        StudentProfileDTO dto = new StudentProfileDTO();
        dto.setUserId(profile.getUser().getId());
        dto.setInterests(profile.getInterests());
        dto.setInstitution(profile.getInstitution());
        dto.setCareer(profile.getCareer());
        dto.setSemester(profile.getSemester());
        dto.setDescription(profile.getDescription());
        return dto;
    }
}
