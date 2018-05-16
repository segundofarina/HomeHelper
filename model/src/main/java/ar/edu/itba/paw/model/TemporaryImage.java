package ar.edu.itba.paw.model;

public class TemporaryImage {
    private int imageId;
    private byte[] image;

    public TemporaryImage(int imageId, byte[] image) {
        this.imageId = imageId;
        this.image = image;
    }

    public int getImageId() {
        return imageId;
    }

    public byte[] getImage() {
        return image;
    }

}
