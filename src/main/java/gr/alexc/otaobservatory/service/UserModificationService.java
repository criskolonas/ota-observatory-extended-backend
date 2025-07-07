package gr.alexc.otaobservatory.service;

import gr.alexc.otaobservatory.dto.UserModificationDetailsResponseDTO;
import gr.alexc.otaobservatory.dto.UserModificationPermissionsRequestDTO;
import gr.alexc.otaobservatory.dto.UserModificationPermissionsResponseDTO;
import gr.alexc.otaobservatory.dto.mapper.UserModificationMapper;
import gr.alexc.otaobservatory.entity.User;
import gr.alexc.otaobservatory.repository.ota.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserModificationService {

    private final UserRepository userRepository;
    private final UserModificationMapper userModificationMapper;
    private final UserRoleService userRoleService ;


    @Transactional
    public List<UserModificationPermissionsResponseDTO> changeUserPermissions(List<UserModificationPermissionsRequestDTO> requests) {
        List<User> updatedUsers = new ArrayList<>();

        for (UserModificationPermissionsRequestDTO request : requests) {
            User userFound = userRepository.getUserByEmail(request.getEmail());
            if (userFound != null) {
                userRoleService.changeAdminRole(request.getEmail(),request.getIsAdmin());
            } else {
                // Optional: Handle case when user is not found
                // e.g. throw an exception or skip
            }
        }

        userRepository.saveAll(updatedUsers);

        return userModificationMapper.userToPermissionsReq(updatedUsers);
    }

    public List<UserModificationDetailsResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        return userModificationMapper.userToDetailsReq(users);
    }
}
