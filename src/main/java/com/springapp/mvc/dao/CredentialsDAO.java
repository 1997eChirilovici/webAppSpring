package com.springapp.mvc.dao;

import com.springapp.mvc.dto.CredentialsDTO;
import com.springapp.mvc.model.Credentials;
import com.springapp.mvc.model.User;
import com.springapp.mvc.model.enums.RoleType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CredentialsDAO {

    private static List<Credentials> listOfCredentials = new ArrayList<>();
    private static long id = 0L;

    static {
        listOfCredentials.add(new Credentials(id++, "echirilovici", "test", RoleType.ROLE_ADMIN));
        listOfCredentials.add(new Credentials(id++, "cnicuta", "test", RoleType.ROLE_USER));
        listOfCredentials.add(new Credentials(id++, "frosca", "test", RoleType.ROLE_USER));
    }

    public Credentials findCredentialsById(Long userId) {
        for (Credentials credentials : listOfCredentials) {
            if (credentials.getId() == userId) {
                return credentials;
            }
        }
        return null;
    }

    public boolean findIfLoginExist(String login) {
        for (Credentials credentials : listOfCredentials) {
            if (credentials.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    public void deleteCredentials(Credentials credentials){
        listOfCredentials.remove(credentials);
    }

    public Long addCredential(Credentials credentials, RoleType roleType) {
        long credentialsId = id++;
        listOfCredentials.add(new Credentials(credentialsId, credentials.getLogin(), credentials.getPassword(), roleType));
        return credentialsId;
    }

    public List<Credentials> validateUser(CredentialsDTO credentials) {
        return listOfCredentials.stream()
                .filter(t -> t.getLogin().equals(credentials.getLogin()) &&
                        t.getPassword().equals(credentials.getPassword()))
                .collect(Collectors.toList());
    }

}
