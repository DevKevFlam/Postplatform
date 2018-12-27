package fr.kflamand.Backend.web.controller;

import fr.kflamand.Backend.Exceptions.PostNotFoundException;
import fr.kflamand.Backend.entities.Post;
import fr.kflamand.Backend.services.PostService;
import fr.kflamand.Backend.web.exception.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("post")
public class PostController {

    //Logger du Controller
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PostService postService;

    @CrossOrigin
    @GetMapping(value = "/all")
    public ResponseEntity<List<Post>> getAllPosts() {

        try {
            List<Post> posts = postService.getAllPost();
            return new ResponseEntity<>( posts , HttpStatus.ACCEPTED);
        } catch (PostNotFoundException e) {
            return new ResponseEntity(new CustomErrorType("Aucun posts trouvée"),HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Post> getOnePost(@PathVariable long id) {

        try {
            Post post = postService.getOneById(id);
            return new ResponseEntity<Post>( post , HttpStatus.ACCEPTED);
        } catch (PostNotFoundException e) {
            return new ResponseEntity(new CustomErrorType("Le post correspondant à l'id " + id + " n'existe pas"),HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<?> addPost(@RequestBody Post post) {

        try {
             postService.addNewPost(post);
            return new ResponseEntity<Post>( post , HttpStatus.ACCEPTED);
        } catch (PostNotFoundException e) {
            return new ResponseEntity(new CustomErrorType("impossible d\'ajoiuté ce post"),HttpStatus.CONFLICT);
        }

    }

    @PatchMapping("/")
    @ResponseBody
    public ResponseEntity<?> updatePost(@RequestBody Post post) {

        try {
            postService.updateOnePost(post);
            return new ResponseEntity<Post>( post , HttpStatus.ACCEPTED);
        } catch (PostNotFoundException e) {
            return new ResponseEntity(new CustomErrorType("impossible de modifier ce post"),HttpStatus.UNAUTHORIZED);
        }

    }

    @PatchMapping("/loveIts")
    @ResponseBody
    public ResponseEntity<?> updateLoveItsPost(@RequestBody Post post) {

        // TODO Bloqué modif autre que loveIts
        try {
            postService.updateLoveItsOfOnePost(post);
            return new ResponseEntity<Post>( post , HttpStatus.ACCEPTED);
        } catch (PostNotFoundException e) {
            return new ResponseEntity(new CustomErrorType("impossible de modifier ce les loveIts de ce post"),HttpStatus.UNAUTHORIZED);
        }

    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deletePost(@PathVariable long id) {


        try {
            postService.deletePostById(id);
            return new ResponseEntity<Boolean>( true , HttpStatus.ACCEPTED);
        } catch (PostNotFoundException e) {
            return new ResponseEntity(new CustomErrorType("impossible de suprimmer ce post"),HttpStatus.UNAUTHORIZED);
        }


    }


}
