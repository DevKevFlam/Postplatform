package fr.kflamand.PostPlatform.Configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("mes-configs")
public class ApplicationPropertiesConfiguration {

        private int limitDePosts;

        // TODO limite de post par page

        public int getLimitDeProduits() {
            return limitDePosts;
        }

        public void setLimitDeProduits(int limitDePosts) {
            this.limitDePosts = limitDePosts;
        }

}
