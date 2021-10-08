package com.fantasticfour.shareyourrecipes.configs;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fantasticfour.shareyourrecipes.configs.oath2.CustomOAuth2User;
import com.fantasticfour.shareyourrecipes.configs.oath2.CustomOAuth2UserService;
import com.fantasticfour.shareyourrecipes.domains.Provider;
import com.fantasticfour.shareyourrecipes.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomOAuth2UserService oauthUserService;
    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // disable security config for dev purpose only
        // http.authorizeRequests().antMatchers("/greeting").authenticated().anyRequest().permitAll().and()
        http.authorizeRequests().antMatchers("/", "/login", "/oauth/**").permitAll();
        http.authorizeRequests().anyRequest().permitAll();
        http.formLogin().loginProcessingUrl("/login").loginPage("/login").defaultSuccessUrl("/")
                .failureUrl("/login?error").and().logout().logoutUrl("/logout").logoutSuccessUrl("/login").permitAll()
                .and().httpBasic().disable();

        http.oauth2Login().loginPage("/login").userInfoEndpoint().userService(oauthUserService).and()
                .successHandler(successHandler()).defaultSuccessUrl("/").failureUrl("/login?error").and().logout()
                .logoutUrl("/logout").logoutSuccessUrl("/login").permitAll();

    }

    @Bean
    AuthenticationSuccessHandler successHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                    Authentication authentication) throws IOException, ServletException {
                CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
                userService.processOAuthPostLogin(oauthUser.getEmail(), Provider.GOOGLE);
                response.sendRedirect("/");
            }
        };
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
