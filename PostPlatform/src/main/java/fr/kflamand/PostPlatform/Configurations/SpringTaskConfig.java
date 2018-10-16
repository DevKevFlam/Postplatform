package fr.kflamand.PostPlatform.Configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan({ "fr.kflamand.PostPlatform.task" })
public class SpringTaskConfig {

}
