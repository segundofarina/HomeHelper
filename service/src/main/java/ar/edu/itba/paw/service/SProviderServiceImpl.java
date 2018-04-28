package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.PostDao;
import ar.edu.itba.paw.interfaces.SProviderService;
import ar.edu.itba.paw.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SProviderServiceImpl implements SProviderService {

    @Autowired
    private PostDao postDao;

    public List<Post> getPosts(int providerId) {
        return postDao.getPostWithUserId(providerId);
    }
}
