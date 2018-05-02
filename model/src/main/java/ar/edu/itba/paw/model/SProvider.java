package ar.edu.itba.paw.model;

import java.util.List;
import java.util.Set;

public class SProvider extends User{
    private List<Post> posts;

    public SProvider( List<Post> posts, User user) {
        super(user.getUsername(),user.getId(),user.getPassword(),user.getFirstname(),user.getLastname(),user.getEmail(),user.getPhone());
        this.posts = posts;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
