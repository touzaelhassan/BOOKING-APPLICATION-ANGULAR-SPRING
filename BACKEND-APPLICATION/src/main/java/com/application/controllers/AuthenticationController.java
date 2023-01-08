package com.application.controllers;

import com.application.classes.HttpResponse;
import com.application.classes.UserPrincipal;
import com.application.dtos.LoginRequest;
import com.application.dtos.RegisterRequest;
import com.application.entities.User;
import com.application.exceptions.ExceptionHandlingController;
import com.application.exceptions.classes.EmailExistException;
import com.application.exceptions.classes.UserNotFoundException;
import com.application.exceptions.classes.UsernameExistException;
import com.application.security.jwt.JWTTokenProvider;
import com.application.services.specifications.UserServiceSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.application.constants.SecurityConstants.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController extends ExceptionHandlingController {

    private final UserServiceSpecification userServiceBean;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;

    @Autowired
    public AuthenticationController(UserServiceSpecification userServiceBean, AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider) {
        this.userServiceBean = userServiceBean;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) throws UserNotFoundException, EmailExistException, UsernameExistException {
        String firstname = registerRequest.getFirstname();
        String lastname = registerRequest.getLastname();
        String username = registerRequest.getUsername();
        String email = registerRequest.getEmail();
        User registeredUser =   userServiceBean.register(firstname, lastname, username, email);
        return  new ResponseEntity<>(registeredUser, OK);
    }

    @PostMapping("/login")
    public ResponseEntity<User > login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        authentication(username, password);
        User loggedUser = userServiceBean.findUserByUsername(username);
        UserPrincipal userPrincipal = new UserPrincipal(loggedUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return  new ResponseEntity<>(loggedUser, jwtHeader, OK);
    }

    private void authentication(String username, String password) { authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password)); }
    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) { return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message), httpStatus); }
    private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJWTToken(userPrincipal));
        return headers;
    }
}
