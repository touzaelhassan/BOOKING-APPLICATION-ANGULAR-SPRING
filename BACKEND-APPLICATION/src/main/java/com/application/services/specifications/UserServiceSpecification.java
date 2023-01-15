package com.application.services.specifications;

import com.application.entities.User;
import com.application.exceptions.classes.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserServiceSpecification {

    User register(String firstname, String lastname, String username, String email, String role) throws UserNotFoundException, EmailExistException, UsernameExistException;
    User addUser(String firstname, String lastname, String username, String email, String role, boolean isNotLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException, NotAnImageFileException;
    User updateUser(String currentUsername, String newFirstname, String newLastname, String newUsername, String newEmail, String role, boolean isNotLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException, NotAnImageFileException;
    User updateProfileImage(String username, MultipartFile newProfileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException, NotAnImageFileException;
    void resetPassword(String email) throws EmailNotFoundException;
    User findUserById(Integer id);
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    List<User> getUsers();
    void deleteUser(Integer id) throws IOException;
    void updateUserRole(Integer id, String role);
}
