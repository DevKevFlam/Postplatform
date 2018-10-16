package fr.kflamand.PostPlatform.persistance.Dao;

import fr.kflamand.PostPlatform.Exception.UserNotFoundException;
import fr.kflamand.PostPlatform.persistance.models.User;;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserDao extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Override
    void delete(User user);

    @Override
    List<User> findAll();

    User findUserByIdEquals ( long id );

}
