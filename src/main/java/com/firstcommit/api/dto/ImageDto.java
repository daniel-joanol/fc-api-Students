package com.firstcommit.api.dto;

import org.springframework.web.multipart.MultipartFile;

/**
 * Dto utilizado en el upload de imagenes
 */
public class ImageDto {

    private MultipartFile image;

    public ImageDto() {
    }

    public ImageDto(MultipartFile image) {
        this.image = image;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
