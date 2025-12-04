package com.zavattieri.RememberMe.repository.user;

import com.zavattieri.RememberMe.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> { //extends JpaRepository to provide CRUD operations for User entity, I pass <Class, Type of the primary key>
    UserDetails findByEmail(String email); //JPA query method automatically implemented by Spring Data JPA to find a user by email

    //methods created by JpaRepository: findAll, findById, save, deleteById, delete, count, etc.
    //there is no need to write the implementation of these methods, JPA does it automatically, I can call UserRepository.save(user) to save a user, for example

}
