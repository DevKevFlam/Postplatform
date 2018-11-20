package fr.kflamand.Backend.dao;

import fr.kflamand.Backend.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends CrudRepository<User, Long> {

     User findByUsername(String username);

}
