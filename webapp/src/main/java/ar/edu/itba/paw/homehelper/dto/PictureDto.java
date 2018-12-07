package ar.edu.itba.paw.homehelper.dto;

public class PictureDto {
    private byte[] image;

    public PictureDto(byte[] image) {
        this.image = image;
    }

    public PictureDto() {
    }

    public byte[] getImage() {
        return image;
    }
}
