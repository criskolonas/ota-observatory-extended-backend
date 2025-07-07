package gr.alexc.otaobservatory.service;

import gr.alexc.otaobservatory.entity.Role;
import gr.alexc.otaobservatory.entity.User;
import gr.alexc.otaobservatory.repository.ota.RoleRepository;
import gr.alexc.otaobservatory.repository.ota.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional          // one transaction for read‑modify‑write
public class UserRoleService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;

    public UserRoleService(UserRepository userRepo, RoleRepository roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @Transactional
    public void changeAdminRole(String email,boolean isAdmin) {
        User user = userRepo.getUserByEmail(email);
        Role role = roleRepo.findById(1L).orElse(null);

        if (user == null || role == null) {
            return; // or throw an exception
        }

        Collection<Role> roles = user.getRole();

        if(user.getRole().contains(role)){
            if(!isAdmin){
                roles.remove(role);
            }
        }else{
            if(!isAdmin){
                roles.add(role);
            }
        }

        userRepo.save(user);
    }

}
