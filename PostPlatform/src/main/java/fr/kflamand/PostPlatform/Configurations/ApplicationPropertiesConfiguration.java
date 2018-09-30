package fr.kflamand.PostPlatform.Configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("mes-configs")
public class ApplicationPropertiesConfiguration {

        private int limitDePosts;

        // TODO limite de post par page

        public int getLimitDePosts() {
            return limitDePosts;
        }

        public void setLimitDePosts(int limitDePosts) {
            this.limitDePosts = limitDePosts;
        }

}
