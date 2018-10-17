package fr.kflamand.PostPlatform.Configurations;

import fr.kflamand.PostPlatform.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "fr.kflamand.PostPlatform.services" })
public class ServiceConfig {

    @Bean
    public UserService userService() {
        return new UserService();
    }
}
