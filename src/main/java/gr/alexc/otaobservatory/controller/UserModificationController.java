package gr.alexc.otaobservatory.controller;

import gr.alexc.otaobservatory.dto.UserModificationDetailsResponseDTO;
import gr.alexc.otaobservatory.dto.UserModificationPermissionsRequestDTO;
import gr.alexc.otaobservatory.dto.UserModificationPermissionsResponseDTO;
import gr.alexc.otaobservatory.entity.User;
import gr.alexc.otaobservatory.service.UserModificationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserModificationController {

    private final UserModificationService userModificationService;

    @PostMapping("api/admin/change-permissions")
    public ResponseEntity<Boolean> changeUserPermissions(@RequestBody List<UserModificationPermissionsRequestDTO> request, HttpServletResponse response) {

     Boolean changedUsers = this.userModificationService.changeUserPermissions(request);

        return ResponseEntity.ok(changedUsers);
    }

    @GetMapping("api/admin/all-users")
    public ResponseEntity<List<UserModificationDetailsResponseDTO>> changeUserPermissions(@CookieValue(name = "jwtToken", required = false) String jwtToken) {
        //TODO Check token is of admin user

        List<UserModificationDetailsResponseDTO> allUsers = this.userModificationService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }
}
