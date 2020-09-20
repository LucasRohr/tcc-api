package com.service.auth.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // TO DO: change to use application Account model
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class AppUser {
        private Integer id;
        private String email;
        private String password;
    }

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // hard coding the users. All passwords have to be encoded. Must retrieve from database later
        final List<AppUser> users = Arrays.asList(
          new AppUser(1, "lucasrc17@live.com", encoder.encode("Senha123"))
        );

        for(AppUser appUser: users) {
            if(appUser.getEmail().equals(email)) {
                return new User(appUser.getEmail(), appUser.getPassword(), new ArrayList<>());
            }
        }

        throw new UsernameNotFoundException("Email: " + email + " not found");
    }
}