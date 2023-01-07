package com.application.controllers;

import com.application.classes.HttpResponse;
import com.application.classes.UserPrincipal;
import com.application.dtos.LoginRequest;
import com.application.dtos.RegisterRequest;
import com.application.entities.User;
import com.application.exceptions.ExceptionHandlingController;
import com.application.exceptions.classes.*;
import com.application.security.jwt.JWTTokenProvider;
import com.application.services.specifications.UserServiceSpecification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.application.constants.FileConstants.*;
import static com.application.constants.SecurityConstants.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@RestController
@RequestMapping("/api")
public class UserController extends ExceptionHandlingController {

    public static final String EMAIL_SENT = "An email with a new password was sent to : ";
    public static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully";
    public static final String USER_ROLE_UPDATED_SUCCESSFULLY = "User role updated successfully !!.";
    private final UserServiceSpecification userServiceBean;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(UserServiceSpecification userServiceBean, AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider) {
        this.userServiceBean = userServiceBean;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<User > register(@RequestBody RegisterRequest registerRequest) throws UserNotFoundException, EmailExistException, UsernameExistException {
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

    @PostMapping("/user/add")
    public ResponseEntity<User> addUser(
            @RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname,
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("role") String role,
            @RequestParam("isActive") String isActive,
            @RequestParam("isNotLocked") String isNotLocked,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage
    ) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
        User newUser = userServiceBean.addUser(firstname, lastname, username,email, role, Boolean.parseBoolean(isNotLocked), Boolean.parseBoolean(isActive), profileImage);
        return new ResponseEntity<>(newUser, OK);
    }

    @PutMapping("/user/update")
    public ResponseEntity<User> update(
            @RequestParam("currentUsername") String currentUsername,
            @RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname,
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("role") String role,
            @RequestParam("isActive") String isActive,
            @RequestParam("isNotLocked") String isNotLocked,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage
    ) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
        User updatedUser = userServiceBean.updateUser(currentUsername, firstname, lastname, username,email, role, Boolean.parseBoolean(isNotLocked), Boolean.parseBoolean(isActive), profileImage);
        return new ResponseEntity<>(updatedUser, OK);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username) {
        User user = userServiceBean.findUserByUsername(username);
        return new ResponseEntity<>(user, OK);
    }

    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('user:read')")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userServiceBean.getUsers();
        return new ResponseEntity<>(users, OK);
    }

    @GetMapping("/user/reset-password/{email}")
    public ResponseEntity<HttpResponse> resetPassword(@PathVariable("email") String email) throws  EmailNotFoundException {
        userServiceBean.resetPassword(email);
        return response(OK, EMAIL_SENT + email);
    }

    @DeleteMapping("/user/delete/{username}")
    @PreAuthorize("hasAnyAuthority('user:delete')")
    public ResponseEntity<HttpResponse> deleteUser(@PathVariable("username") String username) throws IOException {
        userServiceBean.deleteUser(username);
        return response(OK, USER_DELETED_SUCCESSFULLY);
    }

    @PutMapping("/user/update/user-role/{id}")
    //@PreAuthorize("hasAnyAuthority('user:update')")
    public ResponseEntity<HttpResponse> updateUserRole(@PathVariable("id") Long id,   @RequestParam("role") String role) throws IOException {
        userServiceBean.updateUserRole(id, role);
        return response(OK, USER_ROLE_UPDATED_SUCCESSFULLY);
    }

    @PutMapping("/user/update/profile-image")
    public ResponseEntity<User> updateProfileImage(
            @RequestParam("username") String username,
            @RequestParam(value = "user-profile-image") MultipartFile profileImage
    ) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
        User user = userServiceBean.updateProfileImage(username, profileImage);
        return new ResponseEntity<>(user, OK);
    }

    @GetMapping(path = "/user/image/{username}/{fileName}", produces = IMAGE_JPEG_VALUE)
    public byte[] getProfileImage(@PathVariable("username") String username, @PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(USER_FOLDER + username + FORWARD_SLASH + fileName));
    }

    @GetMapping(path = "/user/image/profile/{username}", produces = IMAGE_JPEG_VALUE)
    public byte[] getTempProfileImage(@PathVariable("username") String username) throws IOException {
        URL url = new URL(TEMP_PROFILE_IMAGE_BASE_URL + username);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (InputStream inputStream = url.openStream()) {
            int bytesRead;
            byte[] chunk = new byte[1024];
            while((bytesRead = inputStream.read(chunk)) > 0) { byteArrayOutputStream.write(chunk, 0, bytesRead); }
        }
        return byteArrayOutputStream.toByteArray();
    }

    private void authentication(String username, String password) { authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password)); }
    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) { return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message), httpStatus); }
    private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJWTToken(userPrincipal));
        return headers;
    }

}
