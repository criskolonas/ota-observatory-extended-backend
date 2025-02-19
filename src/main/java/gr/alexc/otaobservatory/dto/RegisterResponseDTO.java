package gr.alexc.otaobservatory.dto;

import gr.alexc.otaobservatory.entity.User;

public class RegisterResponseDTO {

    private String email;
    private String userName;

    public RegisterResponseDTO(User user) {
        this.userName = user.getUsername();
        this.email = user.getPassword();
    }
}