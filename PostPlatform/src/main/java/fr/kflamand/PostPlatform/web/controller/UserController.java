package fr.kflamand.PostPlatform.web.controller;

import fr.kflamand.PostPlatform.Dao.UserDao;
import fr.kflamand.PostPlatform.Exception.UserNotFoundException;
import fr.kflamand.PostPlatform.models.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@Controller
public class UserController implements HealthIndicator {

    @Autowired
   private UserDao userDao;

           Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public Health health() {

        List<User> users = userDao.findAll();

        if (users.isEmpty()) {
            return Health.down().build();
        }
        return Health.up().build();
    }

    // Affiche la liste de tous les produits disponibles
    @GetMapping(value = "/Users")
    public List<User> listeDesProduits() {

        List<User> users = userDao.findAll();

        if (users.isEmpty()) throw new UserNotFoundException("Aucun user n'est disponible à la vente");

        return users;

    }

    //Récuperer un produit par son id
    @GetMapping(value = "/Users/{id}")
    public Optional<User> recupererUnProduit(@PathVariable int id) {

        Optional<User> user = userDao.findById(id);

        if (!user.isPresent())
            throw new UserNotFoundException("User correspondant à l'id " + id + " n'existe pas");

        return user;
    }


    //Récuperer un produit par son id
    @PostMapping(value = "/User")
    public void ajouterUser(User user) {

        // TODO Ajjout d'un user

    }

}
