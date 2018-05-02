package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.Post;
import java.util.List;

public interface SProviderService {

    List<Post> getPosts(int providerId);
}
