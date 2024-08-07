package com.scm.SmartControlManager.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    
    @RequestMapping("/home")
    public String home(Model model){
        System.out.println("Home page");
        model.addAttribute("name","Himanshu");
        return "home";
    }
    @RequestMapping("/about")
    public String about(Model model){
        System.out.println("About page");
        model.addAttribute("name","Himanshu");
        return "about";
    }
    @RequestMapping("/services")
    public String service(){
        return "services";
    }
    @RequestMapping("/contacts")
    public String contact(){
        return "contacts";
    }
    @RequestMapping("/login")
    public String login(){
        return new String("login");
    }
    @RequestMapping("/signup")
    public String signup(){
        return "signup";
    }
}
