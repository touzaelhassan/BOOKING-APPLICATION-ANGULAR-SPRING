package com.application.services.implementations;

import com.application.classes.UserPrincipal;
import com.application.entities.Admin;
import com.application.entities.Client;
import com.application.entities.Owner;
import com.application.entities.User;
import com.application.enums.Role;
import com.application.exceptions.classes.*;
import com.application.repositories.UserRepository;
import com.application.services.LoginAttemptService;
import com.application.services.specifications.UserServiceSpecification;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import static com.application.controllers.constants.FileConstants.*;
import static com.application.controllers.constants.UserServiceImplementationConstants.*;
import static com.application.enums.Role.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service("userServiceBean")
@Transactional
public class UserServiceImplementation implements UserServiceSpecification, UserDetailsService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepositoryBean;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final LoginAttemptService loginAttemptServiceBean;

    @Autowired
    public UserServiceImplementation(UserRepository userRepositoryBean, BCryptPasswordEncoder bCryptPasswordEncoder, LoginAttemptService loginAttemptServiceBean) {
        this.userRepositoryBean = userRepositoryBean;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.loginAttemptServiceBean = loginAttemptServiceBean;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepositoryBean.findUserByUsername(username);
        if(user == null){
            LOGGER.error(NO_USER_FOUND_BY_USERNAME + username);
            throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + username);
        }else{
            validateLoginAttempt(user);
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userRepositoryBean.save(user);
            UserPrincipal userPrincipal = new UserPrincipal(user);
            LOGGER.info(FOUND_USER_BY_USERNAME + username);
            return userPrincipal;
        }
    }

    @Override
    public User register(String firstname, String lastname, String username, String email, String role) throws UserNotFoundException, EmailExistException, UsernameExistException {

        validateNewUsernameAndEmail(EMPTY, username, email);

        if(role.equals("ROLE_CLIENT")){

            Client client = new Client();
            client.setUserId(generateUserId());
            //String password = generatePassword();
            String password = "123456";
            String encodedPassword = encodePassword(password);
            client.setFirstname(firstname);
            client.setLastname(lastname);
            client.setUsername(username);
            client.setEmail(email);
            client.setJoinDate(new Date());
            client.setPassword(encodedPassword);
            client.setActive(true);
            client.setNotLocked(true);
            client.setRole(ROLE_CLIENT.name());
            client.setAuthorities(ROLE_CLIENT.getAuthorities());
            client.setProfileImageUrl(getTemporaryProfileImageUrl(username));
            userRepositoryBean.save(client);
            LOGGER.info("The user password : " + password);
            return client;

        }

        if(role.equals("ROLE_OWNER")){

            Owner owner = new Owner();
            owner.setUserId(generateUserId());
            //String password = generatePassword();
            String password = "123456";
            String encodedPassword = encodePassword(password);
            owner.setFirstname(firstname);
            owner.setLastname(lastname);
            owner.setUsername(username);
            owner.setEmail(email);
            owner.setJoinDate(new Date());
            owner.setPassword(encodedPassword);
            owner.setActive(true);
            owner.setNotLocked(true);
            owner.setRole(ROLE_OWNER.name());
            owner.setAuthorities(ROLE_OWNER.getAuthorities());
            owner.setProfileImageUrl(getTemporaryProfileImageUrl(username));
            userRepositoryBean.save(owner);
            LOGGER.info("The user password : " + password);
            return owner;

        }

        if(role.equals("ROLE_SUPER_ADMIN")){

            Admin admin = new Admin();
            admin.setUserId(generateUserId());
            //String password = generatePassword();
            String password = "123456";
            String encodedPassword = encodePassword(password);
            admin.setFirstname(firstname);
            admin.setLastname(lastname);
            admin.setUsername(username);
            admin.setEmail(email);
            admin.setJoinDate(new Date());
            admin.setPassword(encodedPassword);
            admin.setActive(true);
            admin.setNotLocked(true);
            admin.setRole(ROLE_OWNER.name());
            admin.setAuthorities(ROLE_OWNER.getAuthorities());
            admin.setProfileImageUrl(getTemporaryProfileImageUrl(username));
            userRepositoryBean.save(admin);
            LOGGER.info("The user password : " + password);
            return admin;

        }

        User user = new User();
        user.setUserId(generateUserId());
        //String password = generatePassword();
        String password = "123456";
        String encodedPassword = encodePassword(password);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setUsername(username);
        user.setEmail(email);
        user.setJoinDate(new Date());
        user.setPassword(encodedPassword);
        user.setActive(true);
        user.setNotLocked(true);
        user.setRole(ROLE_USER.name());
        user.setAuthorities(ROLE_USER.getAuthorities());
        user.setProfileImageUrl(getTemporaryProfileImageUrl(username));
        userRepositoryBean.save(user);
        LOGGER.info("The user password : " + password);
        return user;

    }

    @Override
    public User addUser(String firstname, String lastname, String username, String email, String role, boolean isNotLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException, NotAnImageFileException {
        validateNewUsernameAndEmail(EMPTY, username, email);
        User user = new User();
        //String password = generatePassword();
        String password = "123456";
        user.setUserId(generateUserId());
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setJoinDate(new Date());
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encodePassword(password));
        user.setActive(isActive);
        user.setNotLocked(isNotLocked);
        user.setRole(getRoleEnumName(role).name());
        user.setAuthorities(getRoleEnumName(role).getAuthorities());
        user.setProfileImageUrl(getTemporaryProfileImageUrl(username));
        userRepositoryBean.save(user);
        saveProfileImage(user, profileImage);
        LOGGER.info("New user password: " + password);
        return user;
    }

    @Override
    public User updateUser(String currentUsername, String newFirstname, String newLastname, String newUsername, String newEmail, String role, boolean isNotLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException, NotAnImageFileException {
        User currentUser = validateNewUsernameAndEmail(currentUsername, newUsername, newEmail);
        currentUser.setFirstname(newFirstname);
        currentUser.setLastname(newLastname);
        currentUser.setUsername(newUsername);
        currentUser.setEmail(newEmail);
        currentUser.setActive(isActive);
        currentUser.setNotLocked(isNotLocked);
        currentUser.setRole(getRoleEnumName(role).name());
        currentUser.setAuthorities(getRoleEnumName(role).getAuthorities());
        userRepositoryBean.save(currentUser);
        saveProfileImage(currentUser, profileImage);
        return currentUser;
    }

    @Override
    public User updateProfileImage(String username, MultipartFile newProfileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException, NotAnImageFileException {
        User user = validateNewUsernameAndEmail(username, null, null);
        saveProfileImage(user, newProfileImage);
        return user;
    }

    @Override
    public void resetPassword(String email) throws EmailNotFoundException {
        User user = userRepositoryBean.findUserByEmail(email);
        if (user == null) { throw new EmailNotFoundException(NO_USER_FOUND_BY_EMAIL + email); }
        String password = generatePassword();
        user.setPassword(encodePassword(password));
        userRepositoryBean.save(user);
        LOGGER.info("New user password: " + password);
    }

    @Override
    public User findUserById(Integer id) { return userRepositoryBean.findById(id).orElse(null); }

    @Override
    public User findUserByUsername(String username) { return userRepositoryBean.findUserByUsername(username);}
    @Override
    public User findUserByEmail(String email) {
        return userRepositoryBean.findUserByEmail(email);
    }
    @Override
    public List<User> getUsers() {
        return userRepositoryBean.findAll();
    }

    @Override
    public void deleteUser(Integer id) throws IOException {
        User user = userRepositoryBean.findById(id).orElse(null);
        Path userFolder = Paths.get(USER_FOLDER + user.getUsername()).toAbsolutePath().normalize();
        FileUtils.deleteDirectory(new File(userFolder.toString()));
        userRepositoryBean.deleteById(id);
    }

    @Override
    public void updateUserRole(Integer id, String role) {
        User user = userRepositoryBean.findById(id).orElse(null);
        user.setRole(getRoleEnumName(role).name());
        user.setAuthorities(getRoleEnumName(role).getAuthorities());
        userRepositoryBean.save(user);
    }

    private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail) throws UserNotFoundException, UsernameExistException, EmailExistException {

        User userByNewUsername = findUserByUsername(newUsername);
        User userByNewEmail = findUserByEmail(newEmail);

        if(StringUtils.isNotBlank(currentUsername)){

            User currentUser = findUserByUsername(currentUsername);
            if(currentUser == null){ throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME + currentUsername); }

            if(userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId())){ throw new UsernameExistException(USERNAME_ALREADY_EXISTS); }
            if(userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId())){ throw new EmailExistException(EMAIL_ALREADY_EXISTS); }

            return currentUser;

        }else{
            if(userByNewUsername != null ){ throw new UsernameExistException(USERNAME_ALREADY_EXISTS); }
            if(userByNewEmail != null ){ throw new EmailExistException(EMAIL_ALREADY_EXISTS); }
            return null;
        }
    }

    private String generateUserId() { return RandomStringUtils.randomNumeric(10); }
    private String generatePassword() { return RandomStringUtils.randomAlphanumeric(10); }
    private String encodePassword(String password){ return bCryptPasswordEncoder.encode(password); }
    private String getTemporaryProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH + username).toUriString();
    }

    private void validateLoginAttempt(User user) {
        if(user.isNotLocked()){
           if(loginAttemptServiceBean.hasExceededMaxAttempts(user.getUsername())){
               user.setNotLocked(false);
           }else{
               user.setNotLocked(true);
           }
        }else{
            loginAttemptServiceBean.evictUserFromLoginAttemptCache(user.getUsername());
        }
    }

    private void saveProfileImage(User user, MultipartFile profileImage) throws IOException, NotAnImageFileException {

        if (profileImage != null) {
            Path userFolder = Paths.get(USER_FOLDER + user.getUsername()).toAbsolutePath().normalize();
            if(!Files.exists(userFolder)) {
                Files.createDirectories(userFolder);
                LOGGER.info(DIRECTORY_CREATED + userFolder);
            }
            Files.deleteIfExists(Paths.get(userFolder + user.getUsername() + DOT + JPG_EXTENSION));
            Files.copy(profileImage.getInputStream(), userFolder.resolve(user.getUsername() + DOT + JPG_EXTENSION), REPLACE_EXISTING);
            user.setProfileImageUrl(setProfileImageUrl(user.getUsername()));
            userRepositoryBean.save(user);
            LOGGER.info(FILE_SAVED_IN_FILE_SYSTEM + profileImage.getOriginalFilename());
        }

    }

    private String setProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path( USER_IMAGE_PATH + username + FORWARD_SLASH + username + DOT + JPG_EXTENSION).toUriString();
    }

    private Role getRoleEnumName(String role) { return Role.valueOf(role.toUpperCase()); }
}
