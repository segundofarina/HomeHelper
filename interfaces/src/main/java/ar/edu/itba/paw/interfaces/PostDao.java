package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.Post;
import javafx.geometry.Pos;

import java.util.List;

public interface PostDao {
    List<Post> getPostWithUserId(int userId);

    Post create(int userId, int serviceTypeId, String title, String description);

    Post getPostWithId(int postId);

    List<Post> getPosts();

    Post updatePostWithId(int postId, int serviceTypeId, String title, String description);

    boolean deletePostWithId(int postId);
}
