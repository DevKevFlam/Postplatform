package fr.kflamand.PostPlatform.persistance.Dao;

import fr.kflamand.PostPlatform.Exception.UserNotFoundException;
import fr.kflamand.PostPlatform.persistance.models.User;;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor= UserNotFoundException.class)
public interface UserDao extends /*JpaRepository*/  CrudRepository<User, Long> {

    public User findByEmail(String email);

    @Override
    List<User> findAll();

    User findUserByIdEquals ( long id );


}
