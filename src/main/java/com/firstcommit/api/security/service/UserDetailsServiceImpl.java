package com.firstcommit.api.security.service;

import com.firstcommit.api.entities.User;
import com.firstcommit.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Servicio que gestiona los detalles de usuarios
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Método que devuelve los detalles de un usuario guardado en la base de datos.
     *
     * @param username Nombre de usuario del usuario.
     * @return Detalles del usuario.
     * @throws UsernameNotFoundException Si el nombre de usuario no existe en la base de datos.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),user.getPassword(), getAuthority(user));
    }

    /**
     * Método que devuelve un listado de los roles que el usuario posee.
     * @param user Usuario a consultar.
     * @return Roles del usuario
     */
    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return authorities;
    }
}

