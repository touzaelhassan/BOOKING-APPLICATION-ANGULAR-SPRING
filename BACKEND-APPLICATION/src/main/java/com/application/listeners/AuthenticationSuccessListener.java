package com.application.listeners;

import com.application.classes.UserPrincipal;
import com.application.services.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component("authenticationSuccessListenerBean")
public class AuthenticationSuccessListener {

    private LoginAttemptService loginAttemptServiceBean;

    @Autowired
    public AuthenticationSuccessListener(LoginAttemptService loginAttemptServiceBean) { this.loginAttemptServiceBean = loginAttemptServiceBean; }

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        if(principal instanceof UserPrincipal) {
            UserPrincipal userPrincipal = (UserPrincipal) event.getAuthentication().getPrincipal();
            loginAttemptServiceBean.evictUserFromLoginAttemptCache(userPrincipal.getUsername());
        }
    }
}
