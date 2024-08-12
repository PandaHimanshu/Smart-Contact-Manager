package com.scm.SmartControlManager.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.SmartControlManager.Entities.Providers;
import com.scm.SmartControlManager.Entities.User;
import com.scm.SmartControlManager.Repository.UserRepository;
import com.scm.SmartControlManager.helpers.AppConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OAuthenticationSuccessHandler.class);

    @Autowired
    private UserRepository userRepository;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
                logger.info("OAuthenticationSuccessHandler");

                DefaultOAuth2User user =(DefaultOAuth2User)authentication.getPrincipal();
                // logger.info(user.getName());
                // user.getAttributes().forEach((key,value)->{
                //     logger.info("{} => {}",key,value);
                // });


                //save data to database
                String email = user.getAttribute("email").toString();
                String name = user.getAttribute("name").toString();
                String picture = user.getAttribute("picture").toString();

                User user1 = new User();
                user1.setEmail(email);
                user1.setName(name);
                user1.setProfilePic(picture);
                user1.setPassword("password");
                user1.setUserId(UUID.randomUUID().toString());
                user1.setProvider(Providers.GOOGLE);
                user1.setProviderUserId(user.getName());
                user1.setEnabled(true);
                user1.setEmailVerified(true);
                user1.setRoleList(List.of(AppConstants.ROLE_USER));
                user1.setAbout("This account is created via Google");

                User user2 = userRepository.findByEmail(email).orElse(null);
                if (user2==null) {
                    userRepository.save(user1);
                    logger.info("User Saved : "+email);
                }


                response.sendRedirect("/user/dashboard");
    }
    

}
