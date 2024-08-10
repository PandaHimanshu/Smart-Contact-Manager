package com.scm.SmartControlManager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
            // authorize.requestMatchers("/home","/signup","/services").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        //form default login
        httpSecurity.formLogin(Customizer.withDefaults());


        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
