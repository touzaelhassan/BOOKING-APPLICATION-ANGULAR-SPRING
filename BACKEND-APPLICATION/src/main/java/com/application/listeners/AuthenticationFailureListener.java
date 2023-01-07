package com.application.listeners;

import com.application.services.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component("authenticationFailureListenerBean")
public class AuthenticationFailureListener {

    private LoginAttemptService loginAttemptServiceBean;

    @Autowired
    public AuthenticationFailureListener(LoginAttemptService loginAttemptServiceBean) { this.loginAttemptServiceBean = loginAttemptServiceBean; }

    @EventListener
    public void onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        if(principal instanceof String) {
            String username = (String) event.getAuthentication().getPrincipal();
            loginAttemptServiceBean.addUserToLoginAttemptCache(username);
        }
    }
}
