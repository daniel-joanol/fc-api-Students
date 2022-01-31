package com.firstcommit.api.dto;

/**
 * Dto para creación de una nueva contraseña
 */
public class NewPassDto {

    private String username;
    private String password;

    public NewPassDto() {
    }

    public NewPassDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
