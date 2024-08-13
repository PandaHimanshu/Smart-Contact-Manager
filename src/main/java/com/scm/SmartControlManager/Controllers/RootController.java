package com.scm.SmartControlManager.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.SmartControlManager.Entities.User;
import com.scm.SmartControlManager.helpers.Helper;
import com.scm.SmartControlManager.services.UserService;

@ControllerAdvice
public class RootController {

    @Autowired
    private UserService userService;
    
    @ModelAttribute
    public void addLoggedInUserInformation(Model model,Authentication authentication){
        if (authentication==null) {
            return;
        }
        System.out.println("Adding logged in user information to the model");
        String username = Helper.getEmailFromLoggedUser(authentication);
        System.out.println("Logged in :"+username);

        //get user from database
        User user = userService.getUserByEmail(username);
        System.out.println(user);
        if (user==null) {
            model.addAttribute("loggedInUser", null); 
        }else{
            System.out.println(user.getName());
            System.out.println(user.getEmail());
            
            model.addAttribute("loggedInUser", user);
        }
    }
}
