package com.zavattieri.RememberMe.service.auth;

import com.zavattieri.RememberMe.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService{
        @Autowired
        private UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //method that loads user details by username (email in this case, defined in the User class, method getUsername)
            return userRepository.findByEmail(username); //UserRepository method to find user by email, returns a UserDetails object
        }
}
