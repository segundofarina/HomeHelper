package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "temporaryImages")
public class TemporaryImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "temporaryImages_imageid_seq")
    @SequenceGenerator(sequenceName = "temporaryImages_imageid_seq", name = "temporaryImages_imageid_seq", allocationSize = 1)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TemporaryImage)) return false;
        TemporaryImage that = (TemporaryImage) o;
        return getImageId() == that.getImageId() &&
                Arrays.equals(getImage(), that.getImage());
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(getImageId());
        result = 31 * result + Arrays.hashCode(getImage());
        return result;
    }
}
