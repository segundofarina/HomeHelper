package ar.edu.itba.paw.homehelper.dto;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;

public class PictureDto {
    private FormDataBodyPart image;

    public PictureDto(FormDataBodyPart image) {
        this.image = image;
    }

    public PictureDto() {
    }

    public byte[] getImage() {
        return image.getValueAs(byte[].class);
    }
}
