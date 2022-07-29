package com.dakual;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@Service
public class UserService implements IUserService {

    public List<User> retrieveUsers() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<User>> typeReference = new TypeReference<List<User>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/db.json");
        try {
            Thread.sleep(5000);
            return mapper.readValue(inputStream,typeReference);
        } catch (IOException e){
            System.out.println("Unable to get users: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
    @Cacheable(cacheNames = "users")
    @Override
    public List<User> getAll() {
        List<User> users = retrieveUsers();
        return users;
    }

    @Cacheable(cacheNames = "user", key = "'id#' + #id")
    @Override
    public User getUserById(String id) {
        List<User> users = retrieveUsers();
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }

        return null;
    }
}
