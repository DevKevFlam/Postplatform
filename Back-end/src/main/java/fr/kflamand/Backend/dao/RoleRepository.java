package fr.kflamand.Backend.dao;

import fr.kflamand.Backend.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
