package ar.edu.itba.paw.model;

import java.util.List;

public class SProvider {
    private int userId;
    private List<Post> posts;

    public SProvider(int userId, List<Post> posts) {
        this.userId = userId;
        this.posts = posts;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
