package com.scm.SmartControlManager.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.SmartControlManager.Entities.Contact;
import com.scm.SmartControlManager.Entities.User;
import com.scm.SmartControlManager.forms.ContactForm;
import com.scm.SmartControlManager.helpers.Helper;
import com.scm.SmartControlManager.helpers.Message;
import com.scm.SmartControlManager.helpers.MessageType;
import com.scm.SmartControlManager.services.ContactService;
import com.scm.SmartControlManager.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;
    
    //add contact page : handler
    @RequestMapping("/add")
    public String addContactView(Model model){

        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm);
        // contactForm.setName("himanshu");
        return "user/add_contact";
    }

    @RequestMapping(value = "/add",method=RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm,BindingResult bindingResult,Authentication authentication,HttpSession httpSession){

        // System.out.println(contactForm);

        //validate the form 
        if (bindingResult.hasErrors()) {
            httpSession.setAttribute("message", Message.builder()
            .content("please correct the following errors")
            .type(MessageType.red)
            .build());
            return "user/add_contact";
        }

        String username = Helper.getEmailFromLoggedUser(authentication);
        User user =userService.getUserByEmail(username);

        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setFavorite(contactForm.isFavorite());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setLinkedInLink(contactForm.getLinkedInLink());

        contact.setUser(user);

        contactService.save(contact);
        httpSession.setAttribute("message", Message.builder()
            .content("you have successfully added a new contact")
            .type(MessageType.green)
            .build());
        return "redirect:/user/contacts/add";
    }
}
