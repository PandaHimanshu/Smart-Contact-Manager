package com.scm.SmartControlManager.services;

import com.scm.SmartControlManager.Entities.User;
import java.util.*;

import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User saveUser(User user);
    Optional<User> getUserById(String id);
    Optional<User> updatUser(User user);
    void deleteUser(String id);
    boolean isUserExist(String userId);
    boolean isUserExistByEmail(String email);
    List<User>getAllUsers();
    
}
