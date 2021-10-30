package com.fantasticfour.shareyourrecipes.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
@Order(1)
public class AdminConfigurationAdapter extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.antMatcher("/admin/**").authorizeRequests().anyRequest().hasRole("ADMIN").and()
        // .formLogin(Customizer.withDefaults());
        http.antMatcher("/admin/**").authorizeRequests().anyRequest().hasRole("ADMIN").and().httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint());
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
        entryPoint.setRealmName("admin realm");
        return entryPoint;
    }
}