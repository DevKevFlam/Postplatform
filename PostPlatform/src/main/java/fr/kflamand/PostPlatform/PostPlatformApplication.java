package fr.kflamand.PostPlatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
//@EnableConfigurationProperties
public class PostPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostPlatformApplication.class, args);
	}

	/*
    @Bean
    public Sampler defaultSampler(){
        return Sampler.ALWAYS_SAMPLE;
    }*/
}
