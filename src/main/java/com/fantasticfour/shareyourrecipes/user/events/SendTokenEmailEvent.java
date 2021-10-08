package com.fantasticfour.shareyourrecipes.user.events;

import com.fantasticfour.shareyourrecipes.domains.User;
import com.fantasticfour.shareyourrecipes.domains.enums.ETokenPurpose;

import org.springframework.context.ApplicationEvent;

public class SendTokenEmailEvent extends ApplicationEvent {
    private final User user;

    private final ETokenPurpose emailPurpose;

    public SendTokenEmailEvent(final User user, final ETokenPurpose emailPurpose) {
        super(user);
        this.user = user;
        // this.locale = locale;
        this.emailPurpose = emailPurpose;
    }

    // public Locale getLocale() {
    // return locale;
    // }

    public User getUser() {
        return user;
    }

    public ETokenPurpose getEmailPurpose() {
        return this.emailPurpose;
    }

}
