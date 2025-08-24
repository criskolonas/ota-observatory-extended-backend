package gr.alexc.otaobservatory.service;

import gr.alexc.otaobservatory.dto.UserModificationDetailsResponseDTO;
import gr.alexc.otaobservatory.dto.UserModificationPermissionsRequestDTO;
import gr.alexc.otaobservatory.dto.UserModificationPermissionsResponseDTO;
import gr.alexc.otaobservatory.dto.mapper.UserModificationMapper;
import gr.alexc.otaobservatory.entity.Role;
import gr.alexc.otaobservatory.entity.User;
import gr.alexc.otaobservatory.exception.UserNotFoundException;
import gr.alexc.otaobservatory.repository.ota.RoleRepository;
import gr.alexc.otaobservatory.repository.ota.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserModificationService {

    private final UserRepository userRepository;
    private final UserModificationMapper userModificationMapper;
    private final RoleRepository roleRepo;

    public List<User> changeUserPermissions(List<UserModificationPermissionsRequestDTO> requests) {
        List<User> updatedUsers = new ArrayList<>();
        Role role = roleRepo.findById(1L).orElse(null);

        for (UserModificationPermissionsRequestDTO request : requests) {

            User user = userRepository.getUserByEmail(request.getEmail()).orElseThrow(()-> new UserNotFoundException("Ο χρήστης δεν βρέθηκε."));

                if (user == null || role == null) {
                    return null;
                }

                Collection<Role> roles = user.getRole();

                if(user.getRole().contains(role)){
                    if(!request.getIsAdmin()){
                        roles.remove(role);
                    }
                }else{
                    if(request.getIsAdmin()){
                        roles.add(role);
                    }
                }
                updatedUsers.add(user);
        }
        userRepository.saveAll(updatedUsers);
        return updatedUsers;
    }

    public List<UserModificationDetailsResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        return userModificationMapper.userToDetailsReq(users);
    }
}
