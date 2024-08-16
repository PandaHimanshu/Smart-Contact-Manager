package com.scm.SmartControlManager.Controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/user")
public class UserControler {



    //user dashboard
    @RequestMapping(value = "/dashboard", method=RequestMethod.GET)
    public String userDashboard() {
        return "user/dashboard";
    }
    
    //user profile
    @RequestMapping(value = "/profile", method=RequestMethod.GET)
    public String userProfile(Model model,Authentication authentication) {
        return "user/profile";
    }

    //user add contacts page

    //user view contacts

    //user edit contact

    //user delete contact

    //user search contact
    
}
