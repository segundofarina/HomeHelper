package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.Post;

import java.util.List;

public interface PostDao {
    List<Post> getPostWithUserId(int userId);
}
