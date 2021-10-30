package com.fantasticfour.shareyourrecipes.configs;

import com.fantasticfour.shareyourrecipes.configs.oath2.CustomOAuth2UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Order(2)
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;
    @Autowired
    public AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // disable security config for dev purpose only
        // http.authorizeRequests().antMatchers("/greeting").authenticated().anyRequest().permitAll().and()
        http.cors().and().csrf().disable();
        // http.cors().disable();

        // http.exceptionHandling().authenticationEntryPoint(unauthorizedHandler);

        http.authorizeRequests().antMatchers("/", "/favicon.ico", "/login/**", "/signup/**", "/oauth/**").permitAll();
        http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/test/**").permitAll();
        http.authorizeRequests().antMatchers("/api/comment/**").permitAll();
        http.authorizeRequests().antMatchers("/account/**").permitAll();
        http.authorizeRequests().antMatchers("/test-role", "/newfunc").authenticated();
        http.exceptionHandling().defaultAuthenticationEntryPointFor(unauthorizedHandler,
                new AntPathRequestMatcher("/api/**"));
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
        http.authorizeRequests().anyRequest().permitAll();
        http.formLogin().loginProcessingUrl("/login").loginPage("/login").defaultSuccessUrl("/")
                .failureHandler(authenticationFailureHandler()).and().logout().logoutUrl("/logout")
                .logoutSuccessUrl("/login").permitAll().and().httpBasic().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);

        http.oauth2Login().loginPage("/login").userInfoEndpoint().userService(customOAuth2UserService).and()
                .defaultSuccessUrl("/").failureUrl("/login?error").and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login").permitAll();

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

}
