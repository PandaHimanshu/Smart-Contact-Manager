package com.scm.SmartControlManager.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

import com.scm.SmartControlManager.Entities.Contact;
import com.scm.SmartControlManager.Entities.User;
import com.scm.SmartControlManager.forms.ContactForm;
import com.scm.SmartControlManager.forms.ContactSearchForm;
import com.scm.SmartControlManager.helpers.AppConstants;
import com.scm.SmartControlManager.helpers.Helper;
import com.scm.SmartControlManager.helpers.Message;
import com.scm.SmartControlManager.helpers.MessageType;
import com.scm.SmartControlManager.services.ContactService;
import com.scm.SmartControlManager.services.ImageService;
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
    private ImageService imageService;

    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(ContactController.class);

    // add contact page : handler
    @RequestMapping("/add")
    public String addContactView(Model model) {

        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm);
        // contactForm.setName("himanshu");
        return "user/add_contact";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult bindingResult,
            Authentication authentication, HttpSession httpSession) {

        // System.out.println(contactForm);

        // validate the form
        if (bindingResult.hasErrors()) {

            bindingResult.getAllErrors().forEach(error -> logger.info("error is", error.toString()));
            httpSession.setAttribute("message", Message.builder()
                    .content("please correct the following errors")
                    .type(MessageType.red)
                    .build());
            return "user/add_contact";
        }

        String username = Helper.getEmailFromLoggedUser(authentication);
        User user = userService.getUserByEmail(username);

        // image process
        // logger.info("file information:
        // {}",contactForm.getContactImage().getOriginalFilename());

        // image upload

        String fileName = UUID.randomUUID().toString();
        String fileUrl = imageService.uploadImage(contactForm.getContactImage(), fileName);

        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setFavorite(contactForm.isFavorite());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setPicture(fileUrl);
        contact.setCloudinaryImagePublicId(fileName);
        contact.setUser(user);

        contactService.save(contact);

        httpSession.setAttribute("message", Message.builder()
                .content("you have successfully added a new contact")
                .type(MessageType.green)
                .build());
        return "redirect:/user/contacts/add";
    }

    // view contact
    @RequestMapping
    public String viewContacts(
            @ModelAttribute ContactSearchForm contactSearchForm,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "6") int size,
            @RequestParam(value = "sortBy", defaultValue = "email") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction, Authentication authentication,
            Model model) {

        String userName = Helper.getEmailFromLoggedUser(authentication);
        User user = userService.getUserByEmail(userName);
        Page<Contact> pageContact = contactService.getByUser(user, page, size, sortBy, direction);

        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        return "user/contacts";
    }

    //search handler
    @RequestMapping("/search")
    public String searchHandler(
        @ModelAttribute ContactSearchForm contactSearchForm,
        @RequestParam(value = "size",defaultValue = AppConstants.PAGE_SIZE+"")int size,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
        @RequestParam(value = "direction", defaultValue = "asc") String direction,
        Model model,
        Authentication authentication
    ){
        logger.info("field {} keyword {}",contactSearchForm.getField(),contactSearchForm.getValue());

        var user = userService.getUserByEmail(Helper.getEmailFromLoggedUser(authentication));

        Page<Contact> pageContact = null;
        if (contactSearchForm.getField().equalsIgnoreCase("name")) {
            pageContact = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction,
                    user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("email")) {
            pageContact = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction,
                    user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("phone")) {
            pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy,
                    direction, user);
        }

        logger.info("pageContact {}", pageContact);

        model.addAttribute("contactSearchForm", contactSearchForm);

        model.addAttribute("pageContact", pageContact);

        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        return "user/search";
    }

    //Delete contact
    @RequestMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable("contactId") String contactId,HttpSession session) {

        contactService.delete(contactId);
        logger.info("contactId {} deleted",contactId);

        session.setAttribute("message", Message.builder()
        .content("contact deleted sucessfully")
        .type(MessageType.green)
        .build());
        return "redirect:/user/contacts";
    }
    
}
