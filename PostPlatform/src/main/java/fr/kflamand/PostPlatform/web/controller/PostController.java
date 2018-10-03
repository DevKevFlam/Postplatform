package fr.kflamand.PostPlatform.web.controller;

import fr.kflamand.PostPlatform.Configurations.ApplicationPropertiesConfiguration;
import fr.kflamand.PostPlatform.Dao.PostDao;
import fr.kflamand.PostPlatform.Exception.PostNotFoundException;
import fr.kflamand.PostPlatform.models.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class PostController implements HealthIndicator {

    @Autowired
    PostDao postsDao;

    @Autowired
    ApplicationPropertiesConfiguration appProperties;

    @Override
    public Health health() {

        List<Post> posts = postsDao.findAll();

        if (posts.isEmpty()) {
            return Health.down().build();
        }
        return Health.up().build();
    }

    // Affiche la liste de tous les produits disponibles
    @GetMapping(value = "/Posts")
    public List<Post> listeDesProduits() {

        List<Post> products = postsDao.findAll();

        if (products.isEmpty()) throw new PostNotFoundException("Aucun post n'est disponible à la vente");

        List<Post> listeLimitee = products.subList(0, appProperties.getLimitDeProduits());


        //log.info("Récupération de la liste des produits");

        return listeLimitee;

    }

    //Récuperer un produit par son id
    @GetMapping(value = "/Posts/{id}")
    public Optional<Post> recupererUnProduit(@PathVariable int id) {

        Optional<Post> post = postsDao.findById(id);

        if (!post.isPresent())
            throw new PostNotFoundException("Le produit correspondant à l'id " + id + " n'existe pas");

        return post;
    }


}
