package com.scm.SmartControlManager.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.SmartControlManager.Entities.User;
import com.scm.SmartControlManager.Entities.UserForm;
import com.scm.SmartControlManager.services.UserService;


@Controller
public class PageController {

    @Autowired
    private UserService userService;
    
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
    public String signup(Model model){

        UserForm userForm = new UserForm();
        // userForm.setName("hello");
        model.addAttribute("userForm", userForm);
        return "signup";
    }

    //processing register

    @RequestMapping(value = "/do-register",method = RequestMethod.POST)
    public String processRegister(@ModelAttribute UserForm userForm){
        System.out.println(userForm);

        // User user = User.builder()
        // .name(userForm.getName())
        // .email(userForm.getEmail())
        // .password(userForm.getPassword())
        // .about(userForm.getAbout())
        // .phoneNumber(userForm.getPhoneNumber())
        // .profilePic("hello")
        // .build();

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic("profile pic");
        User savedUser = userService.saveUser(user);

        System.out.println("user saved:");

        return "redirect:/signup";
    }
}
