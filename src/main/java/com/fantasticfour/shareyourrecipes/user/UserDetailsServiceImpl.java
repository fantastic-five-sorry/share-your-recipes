package com.fantasticfour.shareyourrecipes.user;

import java.util.ArrayList;
import java.util.Collection;

import com.fantasticfour.shareyourrecipes.domains.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    private static Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepo.findByEmail(email).orElseThrow(() -> {
            log.error("Email " + email + " not found in db");
            return new UsernameNotFoundException("Email " + email + " not found in db");
        });

        log.info("Email " + email + " found in db");

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName().toString())));

        // org.springframework.security.core.userdetails.User logged = new
        // org.springframework.security.core.userdetails.User();

        return
        org.springframework.security.core.userdetails.User.builder().authorities(authorities).username(email)
        .password(user.getPassword()).disabled(!user.isEnable()).accountLocked(user.isBlocked())
        .build();

        // return new CustomPrincipleUser(user, authorities);
    }

}
