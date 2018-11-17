package fr.kflamand.Backend.config;

import fr.kflamand.Backend.services.RegistrationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration

public class MailServerConfig {

    @Bean
    public RegistrationTokenService getRegistrationTokenService() {
        return new RegistrationTokenService();
    }


}
