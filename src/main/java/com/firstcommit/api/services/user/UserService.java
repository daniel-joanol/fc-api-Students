package com.firstcommit.api.services.user;

import com.firstcommit.api.dto.NewPassDto;
import com.firstcommit.api.dto.UserDto;
import org.springframework.http.ResponseEntity;

/**
 * Interfaz del servicio del usuario
 */
public interface UserService {

    ResponseEntity<?> getName(String owner);
    ResponseEntity<?> newPass(NewPassDto newPassDto);
    ResponseEntity<?> createUser(UserDto userDto);
}
