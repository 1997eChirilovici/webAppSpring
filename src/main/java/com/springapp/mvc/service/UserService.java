package com.springapp.mvc.service;

import com.springapp.mvc.dao.CredentialsDAO;
import com.springapp.mvc.dao.UsersDAO;
import com.springapp.mvc.dto.UserDTO;
import com.springapp.mvc.model.Credentials;
import com.springapp.mvc.model.User;
import com.springapp.mvc.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UsersDAO usersDAO;

    @Autowired
    private CredentialsDAO credentialsDAO;

    public List<User> getAllUsers() {
        return UsersDAO.getListOfUsers();
    }

    public User getUserById(Long userId) {
        return usersDAO.findUserById(userId);
    }

    public UserDTO getUserInformationById(Long userId) {
        User user = usersDAO.findUserById(userId);
        UserDTO userDTO = new UserDTO();
        userDTO.setUser(user);
        return userDTO;
    }

    public User getUserByCredentials(Credentials userCredentials) {
        return usersDAO
                .findUserByCredentialsId(userCredentials.getId())
                .orElse(null);
    }

    public User getUserByUserName(String username) {
        Optional<Credentials> userByScreenName = credentialsDAO.findByUsername(username);
        return userByScreenName
                .map(credentials ->
                        usersDAO.findUserByCredentialsId(credentials.getId())
                                .orElse(null))
                .orElse(null);
    }

    public boolean deleteUserById(long id) {
        User user = this.getUserById(id);
        Credentials credentials = credentialsDAO.findCredentialsById(id);
        if(Validation.validUser(user) && !Validation.incorrectCredentials(credentials)){
            usersDAO.deleteUser(user);
            credentialsDAO.deleteCredentials(credentials);
            return true;
        }

        return false;
    }
}
