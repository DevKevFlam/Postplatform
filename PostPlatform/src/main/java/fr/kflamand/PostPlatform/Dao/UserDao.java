package fr.kflamand.PostPlatform.Dao;

import fr.kflamand.PostPlatform.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface UserDao extends JpaRepository<User, Integer> {
}
