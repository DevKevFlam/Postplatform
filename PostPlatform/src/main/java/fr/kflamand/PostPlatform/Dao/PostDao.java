package fr.kflamand.PostPlatform.Dao;

import fr.kflamand.PostPlatform.models.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PostDao extends CrudRepository<Post, Long> {

    Post findPostByIdEquals ( long id );

    Post findPostByTitleEquals ( String title );

    @Override
    List<Post> findAll();
}
