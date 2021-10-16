package com.fantasticfour.shareyourrecipes.configs;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fantasticfour.shareyourrecipes.exception.LocalAuthenticationFailException;

import org.apache.catalina.startup.FailedContext;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    Logger logger = org.slf4j.LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);
    @Autowired
    private MessageSource messages;

    @Autowired
    private LocaleResolver localeResolver;

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response,
            final AuthenticationException exception) throws IOException, ServletException {
        setDefaultFailureUrl("/login?error");

        super.onAuthenticationFailure(request, response, exception);
        final Locale locale = localeResolver.resolveLocale(request);

        String errorMessage = messages.getMessage("message.badCredentials", null, locale);
        String failEmailUser = request.getParameter("username");
        if (exception.getMessage().equalsIgnoreCase("User is disabled")) {

            errorMessage = messages.getMessage("auth.message.disabled", null, locale);
            errorMessage = String.format(errorMessage, failEmailUser);
        } else if (exception.getMessage().equalsIgnoreCase("User account has expired")) {
            errorMessage = messages.getMessage("auth.message.expired", null, locale);
        } else if (exception.getMessage().equalsIgnoreCase("User account is locked")) {
            errorMessage = messages.getMessage("auth.message.blocked", null, locale);
        } else if (exception.getMessage().equalsIgnoreCase("unusual location")) {
            errorMessage = messages.getMessage("auth.message.unusual.location", null, locale);
        }
        // logger.info();

        logger.info(errorMessage);
        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,
                new LocalAuthenticationFailException(errorMessage));
    }
}