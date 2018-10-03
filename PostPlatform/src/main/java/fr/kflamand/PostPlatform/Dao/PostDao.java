package fr.kflamand.PostPlatform.Dao;

import fr.kflamand.PostPlatform.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostDao extends JpaRepository<Post, Integer> {

}
