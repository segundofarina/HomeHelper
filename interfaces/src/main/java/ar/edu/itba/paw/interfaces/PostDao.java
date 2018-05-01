package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.Post;
import javafx.geometry.Pos;

import java.util.List;
import java.util.Optional;

public interface PostDao {
    List<Post> getPostWithUserId(int userId);

    Optional<Post> create(int userId, int serviceTypeId, String title, String description);

    Optional<Post> getPostWithId(int postId);

    List<Post> getPosts();

    Post updatePostWithId(int postId, int serviceTypeId, String title, String description);

    boolean deletePostWithId(int postId);
}
