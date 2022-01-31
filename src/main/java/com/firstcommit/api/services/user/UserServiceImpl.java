package com.firstcommit.api.services.user;

import com.firstcommit.api.dto.NewPassDto;
import com.firstcommit.api.dto.UserDto;
import com.firstcommit.api.entities.Role;
import com.firstcommit.api.entities.User;
import com.firstcommit.api.repositories.UserRepository;
import com.firstcommit.api.security.payload.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Implementación de la interfaz de UserService.
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder){
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public ResponseEntity<?> createUser(UserDto userDto) {

        if (userRepository.existsByUsername(userDto.getUsername()))
            return ResponseEntity.badRequest().body(new MessageResponse("Username already in use"));

        Set<Role> roles = new HashSet<>();
        Role roleAdmin = new Role(1L, "ADMIN");
        roles.add(roleAdmin);
        User newUser = new User(1L,
                userDto.getUsername(),
                encoder.encode(userDto.getPassword()),
                userDto.getName(),
                roles);

        userRepository.save(newUser);

        return ResponseEntity.ok(newUser);
    }

    /**
     * Método para recuperación del nombre del usuário
     * @param owner String
     * @return String name
     */
    @Override
    public ResponseEntity<?> getName(String owner) {
        Optional<User> optUser = userRepository.findByUsername(owner);

        if (optUser.isPresent())
            return ResponseEntity.ok().body(new MessageResponse(optUser.get().getName()));

        return ResponseEntity.notFound().build();
    }

    /**
     * Método para creación de una nueva contraseña
     * @param newPassDto username (email) y password
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity<?> newPass(NewPassDto newPassDto) {
        List<User> users = userRepository.findAll();

        for (User user : users){
            if(user.getUsername().equalsIgnoreCase(newPassDto.getUsername())){
                user.setPassword(encoder.encode(newPassDto.getPassword()));
                userRepository.save(user);
                return ResponseEntity.ok().body(new MessageResponse("New password saved"));
            }
        }

        return ResponseEntity.notFound().build();
    }
}
