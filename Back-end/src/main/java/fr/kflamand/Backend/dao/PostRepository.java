package fr.kflamand.Backend.dao;

import fr.kflamand.Backend.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository< Post , Long > {

    Post findPostByIdEquals ( long id );

    Post findPostByTitleEquals ( String title );


}
