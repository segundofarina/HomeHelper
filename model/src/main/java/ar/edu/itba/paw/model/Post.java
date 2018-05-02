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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;

        Post post = (Post) o;

        if (getIdPost() != post.getIdPost()) return false;
        if (getTitle() != null ? !getTitle().equals(post.getTitle()) : post.getTitle() != null) return false;
        if (getDescription() != null ? !getDescription().equals(post.getDescription()) : post.getDescription() != null)
            return false;
        return getServiceType() != null ? getServiceType().equals(post.getServiceType()) : post.getServiceType() == null;
    }

    @Override
    public int hashCode() {
        int result = getIdPost();
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getServiceType() != null ? getServiceType().hashCode() : 0);
        return result;
    }
}
