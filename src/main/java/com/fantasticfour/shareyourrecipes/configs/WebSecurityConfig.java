package com.fantasticfour.shareyourrecipes.configs;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fantasticfour.shareyourrecipes.configs.oath2.CustomOAuth2UserService;
import com.fantasticfour.shareyourrecipes.domains.enums.ERole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // disable security config for dev purpose only
        // http.authorizeRequests().antMatchers("/greeting").authenticated().anyRequest().permitAll().and()
        http.cors().and().csrf().disable();
        http.authorizeRequests()
                .antMatchers("/", "/default-avatar.png", "/favicon.ico", "/login/**", "/signup/**", "/oauth/**")
                .permitAll();
        http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/test/**").permitAll();
        http.authorizeRequests().antMatchers("/test-role").authenticated();
        http.authorizeRequests().anyRequest().permitAll();
        http.formLogin().loginProcessingUrl("/login").loginPage("/login").defaultSuccessUrl("/")
                .failureHandler(authenticationFailureHandler()).and().logout().logoutUrl("/logout")
                .logoutSuccessUrl("/login").permitAll().and().httpBasic().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        http.oauth2Login().loginPage("/login").userInfoEndpoint().userService(customOAuth2UserService).and()
                .successHandler(successHandler()).failureUrl("/login?error").and().logout().logoutUrl("/logout")
                .logoutSuccessUrl("/login").permitAll();
    }

    @Bean
    AuthenticationSuccessHandler successHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                    Authentication authentication) throws IOException, ServletException {
                // com.fantasticfour.shareyourrecipes.user.UserPrincipal oauthUser =
                // (com.fantasticfour.shareyourrecipes.user.UserPrincipal) authentication
                // .getPrincipal();
                // System.out.println(oauthUser.getAttribute("email").toString());
                response.sendRedirect("/home");
            }
        };
    }

    @Bean
    AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    };

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
