package com.scm.SmartControlManager.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.SmartControlManager.Entities.User;
import com.scm.SmartControlManager.forms.UserForm;
import com.scm.SmartControlManager.helpers.Message;
import com.scm.SmartControlManager.helpers.MessageType;
import com.scm.SmartControlManager.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }
    
    
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
    @GetMapping("/contact")
    public String contact(){
        return "contacts";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/signup")
    public String signup(Model model){

        UserForm userForm = new UserForm();
        // userForm.setName("hello");
        model.addAttribute("userForm", userForm);
        return "signup";
    }

    //processing register form

    @RequestMapping(value = "/do-register",method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm,BindingResult rBindingResult,HttpSession session){
        System.out.println(userForm);

        // User user = User.builder()
        // .name(userForm.getName())
        // .email(userForm.getEmail())
        // .password(userForm.getPassword())
        // .about(userForm.getAbout())
        // .phoneNumber(userForm.getPhoneNumber())
        // .profilePic("hello")
        // .build();
        if (rBindingResult.hasErrors()) {
            return "signup";
        }

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic("profile pic");
        User savedUser = userService.saveUser(user);

        System.out.println("user saved:");

        Message message = Message.builder().content("Registration Successful").type(MessageType.green).build();

        session.setAttribute("message", message);

        return "redirect:/signup";
    }
}
