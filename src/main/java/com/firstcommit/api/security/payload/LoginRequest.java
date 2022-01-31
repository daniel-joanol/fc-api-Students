package com.firstcommit.api.security.payload;

/**
 * Clase DTO para las peticiones de login de usuarios.
 */
public class LoginRequest {
    private String username;
    private String password;
    private Boolean remember;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getRemember() {
        return remember;
    }
}
