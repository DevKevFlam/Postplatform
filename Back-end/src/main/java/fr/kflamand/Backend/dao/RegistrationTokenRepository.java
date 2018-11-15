package fr.kflamand.Backend.dao;


import fr.kflamand.Backend.entities.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationTokenRepository  extends JpaRepository<RegistrationToken, Long> {

    RegistrationToken findByToken(String token);
}