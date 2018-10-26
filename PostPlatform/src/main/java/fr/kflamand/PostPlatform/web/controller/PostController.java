package fr.kflamand.PostPlatform.web.controller;

import fr.kflamand.PostPlatform.Configurations.ApplicationPropertiesConfiguration;
import fr.kflamand.PostPlatform.Exception.PostNotFoundException;
import fr.kflamand.PostPlatform.persistance.Dao.PostDao;
import fr.kflamand.PostPlatform.persistance.models.Post;
import fr.kflamand.PostPlatform.services.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:4200")
@RestController
public class PostController {

    //Logger du Controller
    Logger log = LoggerFactory.getLogger(this.getClass());
/*
    @Autowired
    PostDao postsDao;
*/
    @Autowired
    PostService postService;

    @Autowired
    ApplicationPropertiesConfiguration appProperties;

    // Affiche la liste de tous les posts
    @GetMapping(value = "/Posts")
    public List<Post> listeDesPosts() {

        List<Post> posts = postService.getAllPost();

        if (posts.isEmpty()) {

            log.info("Posts not found");
            throw new PostNotFoundException("List des Posts Vide");
        }
        return posts;

    }

    //Récuperer un post par son id
    @GetMapping(value = "/Posts/{id}")
    public Post recupererUnPost(@PathVariable long id) {

        Optional<Post> postOp = postService.getOneById(id);

        if (!postOp.isPresent()) {
            throw new PostNotFoundException("Le post correspondant à l'id " + id + " n'existe pas");
        }

        Post post = postOp.get();

        if (post == null) {

            log.info("User not found");
            throw new PostNotFoundException("Le post correspondant à l'id " + id + " n'existe pas");
        }

        return post;
    }

    @PostMapping("/Posts")
    @ResponseBody
    public void addPost(@RequestBody Post post) {

        postService.addNewPost(post);

    }

    @PatchMapping("/Posts")
    @ResponseBody
    public void updatePost(@RequestBody Post post) {

        postService.updateOnePost(post);

    }

    @PatchMapping("/Posts/loveIts")
    @ResponseBody
    public void updateLoveItsPost(@RequestBody Post post) {

        // TODO Bloqué modif autre que loveIts

    postService.updateLoveItsOfOnePost(post);

    }

    @DeleteMapping("/Posts/{id}")
    @ResponseBody
    public void deletePost(@PathVariable long id) {

        postService.deletePostById(id);

    }


}
