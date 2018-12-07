package ar.edu.itba.paw.homehelper.dto;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.validation.constraints.NotNull;

public class PictureDto {
    @NotNull
    @FormDataParam("file")
    private FormDataBodyPart image;

    public PictureDto(FormDataBodyPart image) {
        this.image = image;
    }

    public PictureDto() {
    }

    public FormDataBodyPart getImage() {
        return image;
/*    public byte[] getImage() {
        return image.getValueAs(byte[].class);*/
    }
    public byte[] getImageAsByte() {
        return image.getValueAs(byte[].class);
    }
}
