package com.firstcommit.api.controller;

import com.firstcommit.api.dto.EmailDto;
import com.firstcommit.api.dto.NewPassDto;
import com.firstcommit.api.entities.User;
import com.firstcommit.api.repositories.UserRepository;
import com.firstcommit.api.security.jwt.JwtTokenUtil;
import com.firstcommit.api.security.payload.JwtResponse;
import com.firstcommit.api.security.payload.LoginRequest;
import com.firstcommit.api.security.payload.MessageResponse;
import com.firstcommit.api.services.sparkpost.SparkPost;
import com.firstcommit.api.services.user.UserServiceImpl;
import com.sparkpost.exception.SparkPostException;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador para el login de usuario
 * Si las credenciales son válidas se genera un token JWT como respuesta
 */

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final SparkPost sparkPost;
    private final UserServiceImpl userServiceImpl;

    public AuthController(AuthenticationManager authManager,
                          JwtTokenUtil jwtTokenUtil,
                          UserRepository userRepository,
                          SparkPost sparkPost,
                          UserServiceImpl userServiceImpl){
        this.authManager = authManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
        this.sparkPost = sparkPost;
        this.userServiceImpl = userServiceImpl;
    }

    /**
     * Método que permite iniciar sesión de usuario.
     * @param loginRequest username y password
     * @return Token jwt
     */
    @PostMapping("/login")
    @ApiOperation("Login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenUtil.generateJwtToken(authentication, loginRequest.getRemember());

        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    /**
     * Para crear una nueva contraseña el usuario debe primer acceder a este método donde solicita
     *  un enlace para creación de la nueva contraseña
     * Si el email és válido el usuario recibirá un email para cambiar la contraseña
     * @param emailDto username (email)
     * @return ResponseEntity
     */
    @PostMapping("/forgot")
    @ApiOperation("Sends an email to the user who forgot the password to create a new one")
    public ResponseEntity<?> forgotPass(@RequestBody EmailDto emailDto){
        List<User> users = userRepository.findAll();

        for (User user : users){
            if (emailDto.getEmail().equalsIgnoreCase(user.getUsername()))
                try {
                    return sparkPost.sendMessage(emailDto.getEmail());
                } catch (SparkPostException e){
                    e.printStackTrace();
                    return ResponseEntity
                            .internalServerError()
                            .body(new MessageResponse("Error while sending email to " + emailDto.getEmail()));
                }

        }

        return ResponseEntity.badRequest().body(new MessageResponse("Email not found"));
    }

    /**
     * Método para creación de una nueva contraseña
     * @param newPassDto username (email) y password
     * @return ResponseEntity
     */
    @PostMapping("/new-pass")
    @ApiOperation("Create new password")
    public ResponseEntity<?> newPassword(@RequestBody NewPassDto newPassDto){
        if (newPassDto.getUsername() == null || newPassDto.getPassword() == null)
            return ResponseEntity.badRequest().body(new MessageResponse(("Missing parameters")));

        return userServiceImpl.newPass(newPassDto);
    }
}
