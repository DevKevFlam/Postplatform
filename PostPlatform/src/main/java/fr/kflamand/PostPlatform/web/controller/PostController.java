package fr.kflamand.PostPlatform.web.controller;

import fr.kflamand.PostPlatform.Configurations.ApplicationPropertiesConfiguration;
import fr.kflamand.PostPlatform.Dao.PostDao;
import fr.kflamand.PostPlatform.Exception.PostNotFoundException;
import fr.kflamand.PostPlatform.models.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PostController {

    //Logger du Controller
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PostDao postsDao;

    @Autowired
    ApplicationPropertiesConfiguration appProperties;

    // Affiche la liste de tous les posts
    @GetMapping(value = "/Posts")
    public List<Post> listeDesPosts() {

        List<Post> posts = postsDao.findAll();

        if (posts.isEmpty()) {

            log.info("Posts not found");
            throw new PostNotFoundException("List des Posts Vide");
        }
        return posts;

    }

    //Récuperer un post par son id
    @GetMapping(value = "/Posts/{id}")
    public Optional<Post> recupererUnPost(@PathVariable long id) {

        Optional<Post> post = postsDao.findById(id);

        if (!post.isPresent())
            throw new PostNotFoundException("Le post correspondant à l'id " + id + " n'existe pas");

        return post;
    }

    @PostMapping("/Posts")
    @ResponseBody
    public void addUser(@RequestBody Post post) {

        log.info(post.toString());
        postsDao.save(post);

    }

}
