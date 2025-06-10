package service;

import repositories.UserRepository;

import java.util.HashMap;
import java.util.Optional;
import responses.ResponseEnity;
import models.User;


public class AuthService {
    private final UserRepository userRepository;
    

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        
    }

    ///TO_VERIFY
    public ResponseEnity<User> login(HashMap<String, String> cookie ,String username, String password) {
        if (isAuthenticated(cookie).isOk()) {
            return new ResponseEnity<>(null, false, "User is already authenticated");
        }

        Optional<User> user = userRepository.findByUsername(username);

        if (user == null) {
            return new ResponseEnity<User>(null, false, "User not found");
        }

        if (!user.get().getPassword().equals(password)) {
            return new ResponseEnity<User>(null, false, "Incorrect password");
        }

        return new ResponseEnity<User>(user.get(), true, "Login successful");

    }

    ///TO_VERIFY
    public ResponseEnity<User> register(HashMap<String, String> cookie ,String fullName, String username, String password) {
        if(isAuthenticated(cookie).isOk()) {
            return new ResponseEnity<>(null, false, "User is already authenticated");
        }
        if (userRepository.findByUsername(username) != null) {
            return new ResponseEnity<>(null, false, "Username already exists");
        }

        User newUser = new User(fullName, username, password); 
        userRepository.add(newUser);

        ResponseEnity<User> a = login(cookie, username, password);

        if (a.isError()) {
            return new ResponseEnity<>(null, false, "Registration failed");
        }

        return new ResponseEnity<User>(newUser, true, "Registration successful");
    }

    ///TO_VERIFY
    public ResponseEnity<?> logout(HashMap<String, String> cookie) {

        if (!isAuthenticated(cookie).isOk()) {
            return new ResponseEnity<>(null, false, "User is not authenticated");
        }
        
        cookie.remove("userId");

        return new ResponseEnity<>(true, true, "Logout successful");
    }

    ///TO_VERIFY
    public ResponseEnity<Boolean> isAuthenticated(HashMap<String, String> cookie) {
        if (!cookie.containsKey("userId")) {
            return new ResponseEnity<>(false, false, "User is not authenticated");
        }

        
        if (cookie.get("userId") == null || cookie.get("userId").isEmpty()) {
            return new ResponseEnity<>(false, false, "User is not authenticated");
        }

        Integer userId = null;
        try {
            userId = Integer.valueOf(cookie.get("userId"));
        } catch (NumberFormatException e) {
            return new ResponseEnity<>(false, false, "Invalid userId format");
        }
        Optional<User> user = userRepository.findById(userId);
        if (user == null || !user.isPresent()) {
            return new ResponseEnity<>(false, false, "User not found");
        }

        return new ResponseEnity<>(true, true, "User is authenticated");
    }

    ///TO_VERIFY
    private ResponseEnity<User> getAuthenticatedUser(HashMap<String, String> cookie) {
        if (isAuthenticated(cookie).isError()) {
            return new ResponseEnity<>(null, false, "User is not authenticated");
        }

        Integer userId = Integer.valueOf(cookie.get("userId"));
    
        Optional<User> user = userRepository.findById(userId);

        return new ResponseEnity<>(user.get(), true, "User is authenticated");
    }

}
