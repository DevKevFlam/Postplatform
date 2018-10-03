package fr.kflamand.PostPlatform.Dao;

import fr.kflamand.PostPlatform.Exception.UserNotFoundException;
import fr.kflamand.PostPlatform.models.User;;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor= UserNotFoundException.class)
public interface UserDao extends /*JpaRepository*/  CrudRepository<User, Long> {

    public User findByEmail(String email);
}
