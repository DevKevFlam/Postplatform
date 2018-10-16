package fr.kflamand.PostPlatform.persistance.Dao;


import fr.kflamand.PostPlatform.persistance.models.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeDao extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);

    @Override
    void delete(Privilege privilege);

}
