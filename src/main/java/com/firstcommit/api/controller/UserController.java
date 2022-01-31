package com.firstcommit.api.controller;

import com.firstcommit.api.dto.UserDto;
import com.firstcommit.api.security.payload.MessageResponse;
import com.firstcommit.api.services.user.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl){
        this.userServiceImpl = userServiceImpl;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/name")
    @ApiOperation("Get the name of the user")
    public ResponseEntity<?> getName(@CurrentSecurityContext(expression="authentication?.name") String owner){
        return userServiceImpl.getName(owner);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/")
    @ApiOperation("Create new user")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto){

        if (userDto.getUsername() == null ||
            userDto.getPassword() == null ||
            userDto.getName() == null)
            return ResponseEntity.badRequest().body(new MessageResponse("Missing parameters"));

        return userServiceImpl.createUser(userDto);
    }
}
