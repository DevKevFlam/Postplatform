package fr.kflamand.Backend.services;

import fr.kflamand.Backend.Exceptions.PostNotFoundException;
import fr.kflamand.Backend.dao.PostRepository;
import fr.kflamand.Backend.entities.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    //Logger du Service
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPost() {

        List<Post> posts = postRepository.findAll();
        log.info(posts.toString());

        if (posts.isEmpty()) {
            log.info("List des Posts Vide");
            throw new PostNotFoundException("List des Posts Vide");
        }

        return posts;
    }

    public Post getOneById(long id) {
        return postRepository.findPostByIdEquals(id);
    }

    public void addNewPost(Post post) {
        log.info(post.toString());
        postRepository.save(post);
    }

    public void updateOnePost(Post post) {
        log.info(post.toString());
        postRepository.save(post);
    }

    public void updateLoveItsOfOnePost(Post post) {
        log.info(post.toString());
        postRepository.save(post);
    }

    public void deletePostById(long id) {
        Post post = postRepository.findPostByIdEquals(id);
        postRepository.delete(post);
    }

}
