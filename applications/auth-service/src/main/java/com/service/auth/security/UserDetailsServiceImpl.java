package com.service.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.service.user.service.AllUsersService;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private AllUsersService allUsersService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        final List<com.service.common.domain.User> users = allUsersService.getAllUsers();

        for(com.service.common.domain.User appUser: users) {
            if(appUser.getEmail().equals(email)) {
                return new User(appUser.getEmail(), appUser.getPassword(), new ArrayList<>());
            }
        }

        throw new UsernameNotFoundException("Email: " + email + " not found");
    }
}