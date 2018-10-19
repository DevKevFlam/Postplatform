package fr.kflamand.PostPlatform.services;

import fr.kflamand.PostPlatform.persistance.Dao.PostDao;
import fr.kflamand.PostPlatform.persistance.models.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class PostService {

    //Logger du Service
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PostDao postDao;

    public List<Post> getAllPost() {
        return postDao.findAll();
    }

    public Optional<Post> getOneById(long id) {
        return postDao.findById(id);
    }

    public void addNewPost(Post post) {
        log.info(post.toString());
        postDao.save(post);
    }

    public void updateOnePost(Post post) {
        log.info(post.toString());
        postDao.save(post);
    }

    public void updateLoveItsOfOnePost(Post post) {
        log.info(post.toString());
        postDao.save(post);
    }

    public void deletePostById(long id) {
        Post post = postDao.findPostByIdEquals(id);
        postDao.delete(post);
    }

}
