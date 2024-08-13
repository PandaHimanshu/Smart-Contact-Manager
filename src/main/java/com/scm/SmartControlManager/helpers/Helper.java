package com.scm.SmartControlManager.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {
    @SuppressWarnings("null")
    public static String getEmailFromLoggedUser(Authentication authentication){

        //if it looged in using email and password returning email

        if (authentication instanceof OAuth2AuthenticationToken) {

            var oauth2AuthenticationToken=(OAuth2AuthenticationToken)authentication;
            var clientId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
            var oauth2User =(OAuth2User)authentication.getPrincipal();
            String username="";

            if (clientId.equalsIgnoreCase("google")) {
                //if it looged in using google returning email
                System.out.println("from google");
                username=oauth2User.getAttribute("email").toString();

            }else if (clientId.equalsIgnoreCase("github")){

                //if it looged in using github returning email
                System.out.println("from github");
                username = oauth2User.getAttribute("email")!=null? oauth2User.getAttribute("email").toString()
                            :oauth2User.getAttribute("login").toString()+"@gmail.com";
            }
            
                return username;

        }else{
            System.out.println("from local");
            return authentication.getName();
        }

    }
}
