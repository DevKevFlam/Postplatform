package fr.kflamand.PostPlatform.Configurations;

import fr.kflamand.PostPlatform.persistance.Dao.UserDao;
import fr.kflamand.PostPlatform.security.google2fa.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@ComponentScan(basePackages = {"fr.kflamand.PostPlatform.security"})
// @ImportResource({ "classpath:webSecurityConfig.xml" })
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private LogoutSuccessHandler myLogoutSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    //Pour double Auth
    // @Autowired
    // private CustomWebAuthenticationDetailsSource authenticationDetailsSource;

    @Autowired
    private UserDao userDao;

    public SecSecurityConfig() {
        super();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }
/*
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("user").roles("USER")
                .and()
                .withUser("admin").password("admin").roles("ADMIN");
    }
*/
    //TODO check for rooter
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection
                .csrf().disable()

                // Set default configurations from Spring Security
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/Posts", "/Posts/{id}", "/Users", "/Users/{id}").permitAll()

                //AUTHENTIFICATION PATH -  SIGNUP  -  SIGNIN  -  SIGNOUT
                .antMatchers(HttpMethod.POST, "/user/registration", "/user/signIn").permitAll()
                .antMatchers(HttpMethod.POST, "user/logOut").authenticated()


                //TODO bloqué a un seul acces par ip
                //Foncttions LoveITS
                .antMatchers("/Posts/loveIts").permitAll()

                //CRUD Posts
                .antMatchers(HttpMethod.POST, "/Posts").hasAuthority("WRITE")  // TODO Limité acces au poster corespondant
                .antMatchers(HttpMethod.PATCH, "/Posts").hasAuthority("CHANGE") // TODO Limité acces au poster corespondant
                .antMatchers(HttpMethod.DELETE, "/Posts/{id}").hasAuthority("DELETE")
;/*
                //LogIn SecConf
                .and()
                .formLogin()
                .loginPage("/user/signIn")
                //.defaultSuccessUrl("/homepage.html")
                //.failureUrl("/login?error=true")
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                //.authenticationDetailsSource(authenticationDetailsSource)
                .permitAll()

                // SessionManager
                .and()
                .sessionManagement()
                //.invalidSessionUrl("/invalidSession.html")
                .maximumSessions(1).sessionRegistry(sessionRegistry()).and()
                .sessionFixation().none()

                //LogOut SecConf
                .and()
                .logout()
                .logoutUrl("/user/logOut")
                .logoutSuccessHandler(myLogoutSuccessHandler)
                .invalidateHttpSession(false)
                //.logoutSuccessUrl("/logout.html?logSucc=true")
                .deleteCookies("JSESSIONID")
                .permitAll();
*/
                // TODO Pour RememberMe
                //.and()
                //.rememberMe().rememberMeServices(rememberMeServices()).key("theKey");

        // @formatter:off
        // CSRF pour auth registaion login etc
        /*
        http
                .csrf().disable()
                .authorizeRequests()*/
/*
                .antMatchers("/login*", "/login*", "/logout*", "/signin/**", "/signup/**", "/customLogin",
                         "/registrationConfirm*", "/expiredAccount*", "/registration*",
                        "/badUser*", "/user/resendRegistrationToken*", "/forgetPassword*", "/user/resetPassword*",
                        "/user/changePassword*", "/emailError*", "/resources/**", "/old/user/registration*", "/successRegister*", "/qrcode*")
                .permitAll()

                .antMatchers("/invalidSession*")
                .anonymous()

                .antMatchers("/user/updatePassword*", "/user/savePassword*", "/updatePassword*")
                .hasAuthority("CHANGE_PASSWORD")
                .anyRequest().hasAuthority("READ")


                /////OK
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/homepage.html")
                .failureUrl("/login?error=true")
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
        /*.authenticationDetailsSource(authenticationDetailsSource)*/
/*            .permitAll()

                /////OK
                .and()
            .sessionManagement()
                .invalidSessionUrl("/invalidSession.html")
                .maximumSessions(1).sessionRegistry(sessionRegistry()).and()
                .sessionFixation().none()

                 /////OK
            .and()
            .logout()
                .logoutSuccessHandler(myLogoutSuccessHandler)
                .invalidateHttpSession(false)
                .logoutSuccessUrl("/logout.html?logSucc=true")
                .deleteCookies("JSESSIONID")
                .permitAll();
             /*.and()
                .rememberMe().rememberMeServices(rememberMeServices()).key("theKey");*/
        // @formatter:on

    }

    // beans

    @Bean
    public DaoAuthenticationProvider authProvider() {
        final CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

/*
    @Bean
    public RememberMeServices rememberMeServices() {
        CustomRememberMeServices rememberMeServices = new CustomRememberMeServices("theKey", userDetailsService, new InMemoryTokenRepositoryImpl());
        return rememberMeServices;
    }
*/
}