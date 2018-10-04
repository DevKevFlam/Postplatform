package fr.kflamand.PostPlatform.Dao;

import fr.kflamand.PostPlatform.models.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PostDao extends CrudRepository<Post, Long> {


    @Override
    List<Post> findAll();
}
