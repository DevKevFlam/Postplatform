package fr.kflamand.PostPlatform.persistance.Dao;

import fr.kflamand.PostPlatform.persistance.models.*;
import org.springframework.data.repository.CrudRepository;

public interface RoleDao extends CrudRepository<RoleUser, Long> {

}
