package gr.alexc.otaobservatory.service;

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
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserModificationService {

    private final UserRepository userRepository;
    private final UserModificationMapper userModificationMapper;


    @Transactional
    public List<UserModificationPermissionsResponseDTO> changeUserPermissions(List<UserModificationPermissionsRequestDTO> requests) {
        List<User> updatedUsers = new ArrayList<>();

        for (UserModificationPermissionsRequestDTO request : requests) {
            User userFound = userRepository.getUserByEmail(request.getEmail());

            if (userFound != null) {
                userFound.setIs_admin(request.getIs_admin());
                updatedUsers.add(userFound);
            } else {
                // Optional: Handle case when user is not found
                // e.g. throw an exception or skip
            }
        }

        userRepository.saveAll(updatedUsers);

        // You can customize the response DTO as needed
        return userModificationMapper.userToPermissionsReq(updatedUsers);
    }
}
