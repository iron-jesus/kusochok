package ua.pp.kusochok.rest.dto;

import ua.pp.kusochok.models.enums.Role;

public class SignInResponseDto {
    public String username;
    public String token;
    public Role role;

    public SignInResponseDto(String username, String token, Role role) {
        this.username = username;
        this.token = token;
        this.role = role;
    }

    public SignInResponseDto() {
    }
}
