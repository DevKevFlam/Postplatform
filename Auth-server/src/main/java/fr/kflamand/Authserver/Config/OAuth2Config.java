package fr.kflamand.Authserver.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    // API Identification
    private String clientId = "PostPlatform";
    private String clientSecret = "my-secret";

    // Keys for JWT Token
    private String privateKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEpAIBAAKCAQEAuAGArDD20tCIw2vw2FWrKyNf9FE96dwymqwROaxTyl/cjG7g\n" +
            "VGFkGkvBELIXtQfBK830l8RdE4YrLA4+SyLTRLB+kclhqg7UOiA7PTIaJwWyep8m\n" +
            "VhaJvIObCFAS5hso9E6YLO1BxgN7OZ513TCVfxnD1ukVxvt6fbNOXJ9U0vIewRG8\n" +
            "6Bidj44mmHxMEAHP4T9ZOU/F44OiL0n30xTWcCel9QxDi6+4ys24LdOcwcpqQNzX\n" +
            "zSJrO55YcJGcY6K/VrFMe0MkcU93eCow1b7lz1FwlWmbE6cZsGi1Yu96y5T9idJs\n" +
            "eZVaxbqYDUHmYPlv9wkzLVlxxwVRlJN9uWi3rQIDAQABAoIBAGckcHwU9kY1nnPs\n" +
            "jZS2cLw9mAvbflkYlUUeLomlj1Jhab9OZ3DlJreWvyIcsi1Hq4BicjigWYwYKRsI\n" +
            "4OI9Iy/dvxT5TUWGCnCKoKos63v0NZeGnnajK5IAM2b1e6KAlvRbGfIVDElyqiAi\n" +
            "Mu9JzILyblSJUyu2y3Kqj8JbjbCwE6eaFE0PPty5i42GNZ5/FSKgdHz3uonacP7P\n" +
            "woCNDQT2fSvQy5DCBRiQmQtPfL5f57tfXQvwA0L/A3ka7C6KefgjgWSiTh4W1qSB\n" +
            "g/UHkjGnMbT9enCw+1bevlB53JvNXqaqZJMvgrODW+v2Isi8waqIPqfZ0oWkdESc\n" +
            "XtJfggECgYEA6IERI6pqs+6Md0fURKyLJV0GezSRy9iEnLSeGPxP9RLvcbELQVkD\n" +
            "X7SqTCSE1TSSbXKwiFBlkC15nICbwgrP2zRnk3JFug1Dzp5LLRiN0Vr+eRKa5dvA\n" +
            "CMykoeP1gwNHAwW/dPbyyLW3dUm0CchP1vpM81kEjZtumwKiF5tVEOcCgYEAypnG\n" +
            "M7pPR21TBVerIQAV+Ma/ZWU25miCLeN5ds8ULCpaFbL1eQtUx/QXewFxgsWpARNA\n" +
            "p/Aorhm5NGkPA8Ikq+ur4ubYfZDVU7LmwrCfosX8pM5MJbudK5g2Gk2GlmetdbGA\n" +
            "mlCLOfU13xbUOdEVdJaIrFEKAPDH0Jj2gP9QnEsCgYEAwnK+/M8Xsif9ffyqwEx1\n" +
            "iugD9aJ/lLYeIljEKC1MSbBuZO0mdp6VPcOA8XFRTmZLWBGlJjvm1O/TV1oP6fbU\n" +
            "44UuYpgdTH6viOvF/48bfE3XqTYwKbDhJ/rObT6TI+Dn0NVLy+yEG+wt5H+hfNT0\n" +
            "QNzQ4mw+bJfjpuxK1+dm+10CgYATxDD8Nv8pHtdsvALsM0/xd6KQx/E1RZj91SWy\n" +
            "m72CGZe0akNczeg1ofDwhVGHyrUUb8ZtHBc79TKGlIFsgnKFe3bHqnIQviKyCdBN\n" +
            "ymGoxdxCUAZJ4CYTbMRKiKnK3s6jnu/HHniW7P+WhmmqQmXvfAhVl4p8UQGNrn7O\n" +
            "UxqQRQKBgQDOqchmJBjR7q8X1m0BS6R1rLWjP8fsq4f9KPYJWXGCQot3YTghNSun\n" +
            "yTlPJZj2B9jPzF0kp+g53B+xUgg9djTpv3F3zGXdVmyWVc4g+Fia5HTNeUp3f/WD\n" +
            "rE4L0vk+khPSkGeZe8OsRBfO2xb2iDdUyCTwLcc//K8iWFPkSkJNhg==\n" +
            "-----END RSA PRIVATE KEY-----";
    
    private String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuAGArDD20tCIw2vw2FWr\n" +
            "KyNf9FE96dwymqwROaxTyl/cjG7gVGFkGkvBELIXtQfBK830l8RdE4YrLA4+SyLT\n" +
            "RLB+kclhqg7UOiA7PTIaJwWyep8mVhaJvIObCFAS5hso9E6YLO1BxgN7OZ513TCV\n" +
            "fxnD1ukVxvt6fbNOXJ9U0vIewRG86Bidj44mmHxMEAHP4T9ZOU/F44OiL0n30xTW\n" +
            "cCel9QxDi6+4ys24LdOcwcpqQNzXzSJrO55YcJGcY6K/VrFMe0MkcU93eCow1b7l\n" +
            "z1FwlWmbE6cZsGi1Yu96y5T9idJseZVaxbqYDUHmYPlv9wkzLVlxxwVRlJN9uWi3\n" +
            "rQIDAQAB\n" +
            "-----END PUBLIC KEY-----";


    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Bean
    public JwtAccessTokenConverter tokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(privateKey);
        converter.setVerifierKey(publicKey);
        return converter;
    }


    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(tokenEnhancer());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
                .accessTokenConverter(tokenEnhancer());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory().withClient(clientId).secret(clientSecret).scopes("read", "write")
                .authorizedGrantTypes("password", "refresh_token").accessTokenValiditySeconds(20000)
                .refreshTokenValiditySeconds(20000);

    }
}
