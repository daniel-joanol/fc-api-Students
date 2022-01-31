package com.firstcommit.api.services.sparkpost;

import com.firstcommit.api.security.payload.MessageResponse;
import com.sparkpost.Client;
import com.sparkpost.exception.SparkPostException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Servicio que gerencia SparkPost (envio de emails)
 */
@Service
public class SparkPost implements SparkPostService{

    private final String API_KEY = System.getenv("SPARK_API_KEY");
    Client client = new Client(API_KEY);

    public SparkPost() {}

    /**
     * Para crear una nueva contraseña el usuario debe primer acceder a este método donde solicita
     *  un enlace para creación de la nueva contraseña
     * Si el email és válido el usuario recibirá un email para cambiar la contraseña
     * @param email (username)
     * @return ResponseEntity
     */
    public ResponseEntity<?> sendMessage(String email) throws SparkPostException {
        try {
            client.sendMessage(
                    "contacto@ob.danieljoanol.com",
                    email,
                    "Forgot your password?",
                    "Hello, to create a new password please click here",
                    "Hello, to create a new password please click <a href='http://localhost:3000/new-pass'>here</a>");
        } catch (SparkPostException e){
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(new MessageResponse("Check your email box"));
    }
}
