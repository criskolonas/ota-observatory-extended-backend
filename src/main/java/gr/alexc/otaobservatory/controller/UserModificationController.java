package gr.alexc.otaobservatory.controller;

import gr.alexc.otaobservatory.dto.UserModificationPermissionsRequestDTO;
import gr.alexc.otaobservatory.dto.UserModificationPermissionsResponseDTO;
import gr.alexc.otaobservatory.service.UserModificationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserModificationController {

    private final UserModificationService userModificationService;

    @PostMapping("change-permissions")
    public ResponseEntity<List<UserModificationPermissionsResponseDTO>> changeUserPermissions(@RequestBody List<UserModificationPermissionsRequestDTO> request, HttpServletResponse response) {

      List<UserModificationPermissionsResponseDTO> changedUsers = this.userModificationService.changeUserPermissions(request);

        return ResponseEntity.ok(changedUsers);
    }
}
