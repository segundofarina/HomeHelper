package ar.edu.itba.paw.model;

public class Post {
    private int idPost;
    private String title;
    private String description;
    private String serviceType;

    public Post(int idPost, String title, String description, String serviceType) {
        this.idPost = idPost;
        this.title = title;
        this.description = description;
        this.serviceType = serviceType;
    }

    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}
