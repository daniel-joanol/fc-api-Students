package com.firstcommit.api.services.cloud;

import org.springframework.web.multipart.MultipartFile;

/**
 * Interfaz que define el método para subir una imagen
 * a un servicio de alojamiento en la nube
 */
public interface CloudinaryService {
    String uploadImage(MultipartFile photo) throws Exception;
}
