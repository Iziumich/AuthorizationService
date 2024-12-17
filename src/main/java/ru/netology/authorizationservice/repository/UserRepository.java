package ru.netology.authorizationservice.repository;


import org.springframework.stereotype.Repository;
import ru.netology.authorizationservice.model.Authorities;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {

    private final Map<String, User> users = new ConcurrentHashMap<>();

    public UserRepository() {
        users.put("user1", new User("user1", "password1", List.of(Authorities.READ)));
        users.put("user2", new User("user2", "password2", List.of(Authorities.READ, Authorities.WRITE)));
        users.put("user3", new User("user3", "password3", List.of(Authorities.READ, Authorities.WRITE, Authorities.DELETE)));
    }

    public List<Authorities> getUserAuthorities(String user, String password) {
        User storedUser = users.get(user);
        if (storedUser != null && storedUser.getPassword().equals(password)) {
            return storedUser.getAuthorities();
        }
        return Collections.emptyList();
    }

    private static class User {
        private final String username;
        private final String password;
        private final List<Authorities> authorities;

        public User(String username, String password, List<Authorities> authorities) {
            this.username = username;
            this.password = password;
            this.authorities = authorities;
        }

        public String getPassword() {
            return password;
        }

        public List<Authorities> getAuthorities() {
            return authorities;
        }
    }
}