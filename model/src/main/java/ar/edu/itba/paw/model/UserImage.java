package ar.edu.itba.paw.model;


import javax.persistence.*;

@Entity
@Table(name = "userImages")
public class UserImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userImages_imageId_seq")
    @SequenceGenerator(sequenceName = "userImages_imageId_seq", name = "userImages_imageId_seq", allocationSize = 1)
    @Column(name = "imageid")
    private int imageId;

    @Column(name = "userId")
    private int userId;

    @Column(name = "image")
    private byte[] image;


    /* defalut */ UserImage(){

    }

    public UserImage(int userId, byte[] image) {
        this.userId = userId;
        this.image = image;
    }

    public int getUserId() {
        return userId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public byte[] getImage() {
        return image;
    }

}
