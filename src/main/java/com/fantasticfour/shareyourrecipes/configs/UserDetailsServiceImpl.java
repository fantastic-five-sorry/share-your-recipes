package com.fantasticfour.shareyourrecipes.configs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import com.fantasticfour.shareyourrecipes.account.UserPrincipal;
import com.fantasticfour.shareyourrecipes.account.UserRepo;
import com.fantasticfour.shareyourrecipes.domains.auth.Provider;
import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.exception.LocalAuthenticationFailException;
import com.fantasticfour.shareyourrecipes.exception.OAuth2AuthenticationProcessingException;

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

        if (!user.getProvider().equals(Provider.local))
            throw new OAuth2AuthenticationProcessingException("not local user");
        log.info("Email " + email + " found in db");

        return UserPrincipal.create(user);

        // Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        // user.getRoles().forEach(role -> authorities.add(new
        // SimpleGrantedAuthority(role.getName().toString())));

        // return
        // org.springframework.security.core.userdetails.User.builder().authorities(authorities).username(email)
        // .password(user.getPassword()).disabled(!user.isEnable()).accountLocked(user.isBlocked()).build();
    }

}
