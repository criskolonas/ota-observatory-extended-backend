package gr.alexc.otaobservatory.service;

import gr.alexc.otaobservatory.dto.UserModificationDetailsResponseDTO;
import gr.alexc.otaobservatory.dto.UserModificationPermissionsRequestDTO;
import gr.alexc.otaobservatory.dto.UserModificationPermissionsResponseDTO;
import gr.alexc.otaobservatory.dto.mapper.UserModificationMapper;
import gr.alexc.otaobservatory.entity.Role;
import gr.alexc.otaobservatory.entity.User;
import gr.alexc.otaobservatory.repository.ota.RoleRepository;
import gr.alexc.otaobservatory.repository.ota.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserModificationService {

    private final UserRepository userRepository;
    private final UserModificationMapper userModificationMapper;
    private final RoleRepository roleRepo;

    @Transactional
    public Boolean changeUserPermissions(List<UserModificationPermissionsRequestDTO> requests) {
        List<User> updatedUsers = new ArrayList<>();

        for (UserModificationPermissionsRequestDTO request : requests) {
            User userFound = userRepository.getUserByEmail(request.getEmail());
            if (userFound != null) {
                User user = userRepository.getUserByEmail(request.getEmail());
                Role role = roleRepo.findById(1L).orElse(null);

                if (user == null || role == null) {
                    return false;
                }

                Collection<Role> roles = user.getRole();

                if(user.getRole().contains(role)){
                    if(!request.getIsAdmin()){
                        roles.remove(role);
                    }
                }else{
                    if(!request.getIsAdmin()){
                        roles.add(role);
                    }
                }

                userRepository.save(user);            }
            else {
                // Optional: Handle case when user is not found
                // e.g. throw an exception or skip
            }
        }

        userRepository.saveAll(updatedUsers);

        return true;
    }

    public List<UserModificationDetailsResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        return userModificationMapper.userToDetailsReq(users);
    }
}
