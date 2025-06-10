package service;

import repositories.UserRepository;
import requests.LoginRequest;

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
    public ResponseEnity<User> login(LoginRequest loginRequest) {
        if (isAuthenticated(loginRequest.getCookie()).isOk()) {
            return new ResponseEnity<>(null, false, "User is already authenticated");
        }

        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());

        if (user == null) {
            return new ResponseEnity<User>(null, false, "User not found");
        }

        if (!user.get().getPassword().equals(loginRequest.getPassword())) {
            return new ResponseEnity<User>(null, false, "Incorrect password");
        }

        return new ResponseEnity<User>(user.get(), true, "Login successful");

    }

    ///TO_VERIFY
    public ResponseEnity<User> register(String fullName , LoginRequest loginRequest) {
        if(isAuthenticated(loginRequest.getCookie()).isOk()) {
            return new ResponseEnity<>(null, false, "User is already authenticated");
        }
        if (userRepository.findByUsername(loginRequest.getUsername()) != null) {
            return new ResponseEnity<>(null, false, "Username already exists");
        }

        User newUser = new User(fullName, loginRequest.getUsername(), loginRequest.getPassword());
        userRepository.add(newUser);

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
    public ResponseEnity<User> getAuthenticatedUser(HashMap<String, String> cookie) {
        if (isAuthenticated(cookie).isError()) {
            return new ResponseEnity<>(null, false, "User is not authenticated");
        }

        Integer userId = Integer.valueOf(cookie.get("userId"));
    
        Optional<User> user = userRepository.findById(userId);

        return new ResponseEnity<>(user.get(), true, "User is authenticated");
    }

}
