package ar.edu.itba.paw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "temporaryImages")
public class TemporaryImage {

    @Id
    @Column(name = "imageid")
    private int imageId;

    @Column(name = "image")
    private byte[] image;

    /*package*/ TemporaryImage() {
        /* For Hibernate */
    }

    public TemporaryImage(byte[] image) {
        this.image = image;
    }

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
