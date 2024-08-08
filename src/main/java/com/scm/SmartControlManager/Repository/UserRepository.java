package com.scm.SmartControlManager.Repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scm.SmartControlManager.Entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    
    Optional <User> findByEmail(String email);

    Optional <User> findByEmailAndPassword(String email,String password);
}
