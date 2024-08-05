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
}
