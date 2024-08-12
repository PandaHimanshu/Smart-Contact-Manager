package com.scm.SmartControlManager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.scm.SmartControlManager.impl.SecurityCustomUserDetailService;

@Configuration
public class SecurityConfig {
    

    //user create and login using java with in memory service
    // @Bean
    // public UserDetailsService UserDetailsService(){
        
    //     UserDetails user1 = User
    //     .withUsername("admin123")
    //     .password("admin123")
    //     .roles("ADMIN","USER")
    //     .build();
    //     UserDetails user2= User
    //     .withUsername("user123")
    //     .password("password")
    //     .build();
    //     var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1,user2);
    //     return inMemoryUserDetailsManager;
    // }

    @Autowired
    private SecurityCustomUserDetailService securityCustomUserDetailService;

    @Autowired
    private OAuthenticationSuccessHandler oAuthenticationSuccessHandler;
    

    // configuration of authentication provider
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        //user detail service object
        daoAuthenticationProvider.setUserDetailsService(securityCustomUserDetailService);
        //user detail service object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        //configuration
        // security in routes
        httpSecurity.authorizeHttpRequests(authorize->{
            authorize.requestMatchers("/home","/signup","/services","/contacts").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        //form default login
        httpSecurity .formLogin(formLogin -> {
                formLogin.loginPage("/login")
                         .loginProcessingUrl("/authenticate")
                         .defaultSuccessUrl("/user/dashboard", true)
                         .failureUrl("/login?error=true")
                         .usernameParameter("email")
                         .passwordParameter("password");
            })

            .csrf(AbstractHttpConfigurer::disable)
            .logout(logout -> {
                logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true");
        })

        //oauth2 with google
            .oauth2Login(oauth->{
                oauth.loginPage("/login")
                    .successHandler(oAuthenticationSuccessHandler);
            });



        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
