package com.fantasticfour.shareyourrecipes.configs;

import com.fantasticfour.shareyourrecipes.user.UserDetailsServiceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // disable security config for dev purpose only
        // http.authorizeRequests().antMatchers("/greeting").authenticated().anyRequest().permitAll().and()

        http.authorizeRequests().anyRequest().permitAll();
        http.formLogin().loginProcessingUrl("/login").loginPage("/ui/login").failureUrl("/ui/login?error")
                .defaultSuccessUrl("/").permitAll().and().httpBasic().disable();

        http.oauth2Login().loginPage("/ui/login").failureUrl("/ui/login?error");

    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());

    }

    @Bean
    protected UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Override
    // @Bean
    // protected UserDetailsService userDetailsService() {
    // InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    // manager.createUser(
    // User.builder().username("lvl").password(passwordEncoder().encode("1234")).roles("USER").build());
    // return manager;
    // }

}
