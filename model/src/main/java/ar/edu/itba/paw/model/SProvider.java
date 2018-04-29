package ar.edu.itba.paw.model;

import java.util.Set;

public class SProvider {
    private int userId;
    private Set<Integer> posts;

    public SProvider(int userId, Set<Integer> posts) {
        this.userId = userId;
        this.posts = posts;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Set<Integer> getPosts() {
        return posts;
    }

    public void setPosts(Set<Integer> posts) {
        this.posts = posts;
    }
}
