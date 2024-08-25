package com.scm.SmartControlManager.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm.SmartControlManager.Entities.Contact;
import com.scm.SmartControlManager.Entities.User;
import com.scm.SmartControlManager.Repository.ContactRepository;
import com.scm.SmartControlManager.helpers.ResourceNotFoundException;
import com.scm.SmartControlManager.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService{

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public Contact save(Contact contact) {
        String contactId = UUID.randomUUID().toString();

        contact.setId(contactId);
        return contactRepository.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public List<Contact> getAll() {
        return contactRepository.findAll();
    }

    @Override
    public Contact getById(String id) {
        return contactRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Contact not found with given id"+id));
    }

    @Override
    public void delete(String id) {
        var contact = contactRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Contact not found with given id"+id));

        contactRepository.delete(contact);
    }

    @Override
    public List<Contact> search(String name, String email, String phoneNumber) {
        throw new UnsupportedOperationException("Unimplemented method 'getByUserId'");

    }

    @Override
    public List<Contact> getByUserId(String userId) {
         return contactRepository.findByUserId(userId);      
    }

    @Override
    public Page<Contact> getByUser(User user,int page,int size,String sortBy,String direction) {
    
        Sort sort = direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size,sort);

       return contactRepository.findByUser(user,pageable);
    }
    
}
