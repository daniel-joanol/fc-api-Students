package com.firstcommit.api.services.cloud;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.firstcommit.api.exceptions.EmptyImageException;
import com.firstcommit.api.exceptions.InvalidImageFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * Clase que implementa la subida de una imagen al
 * servicio de alojamiento en la nube Cloudinary
 */
@Service
public class CloudinaryServiceImpl implements CloudinaryService{

    private final String cloudName = System.getenv("CLOUDINARY_CLOUD_NAME");
    private final String apiKey = System.getenv("CLOUDINARY_API_KEY");
    private final String apiSecret = System.getenv("CLOUDINARY_API_SECRET");
    private final Map params = ObjectUtils.asMap(
            "cloud_name", cloudName,
            "api_key", apiKey,
            "api_secret", apiSecret
    );

    Logger log = LoggerFactory.getLogger(CloudinaryServiceImpl.class);

    Cloudinary cloudinary = new Cloudinary(params);

    /**
     * Método que efectúa la subida del archivo
     *
     * @param image Imagen que se recibe
     * @return String con la url de la imagen alojada
     */
    @Override
    public String uploadImage(MultipartFile image) throws EmptyImageException, InvalidImageFormatException, IOException {
        log.warn(image.getContentType());
        if (image.isEmpty()) {
            String message = "Error: El archivo está vacío";
            log.error(message);
            throw new EmptyImageException(message);
        } else if (!Objects.requireNonNull(image.getContentType()).equalsIgnoreCase("image/png") &&
                !image.getContentType().equalsIgnoreCase("image/jpeg") &&
                !image.getContentType().equalsIgnoreCase("image/jpg") &&
                !image.getContentType().equalsIgnoreCase("application/pdf")){
            String message = "Error: El formato del archivo es incorrecto. Formatos admitidos '.pdf', '.png', 'jpg' y '.jpeg'";
            log.error(message);
            throw new InvalidImageFormatException(message);
        }
        Map response = cloudinary.uploader().upload((image.getBytes()),
                ObjectUtils.emptyMap());

        return response.get("secure_url").toString();
    }
}
