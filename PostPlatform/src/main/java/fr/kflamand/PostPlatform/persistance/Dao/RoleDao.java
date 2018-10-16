package fr.kflamand.PostPlatform.persistance.Dao;

import fr.kflamand.PostPlatform.persistance.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RoleDao extends JpaRepository<RoleUser, Long> {

    RoleUser findByName(String name);

    @Override
    void delete(RoleUser role);

}
