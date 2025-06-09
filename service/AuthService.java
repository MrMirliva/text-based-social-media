package service;

import repositories.UserRepository;

import java.util.Optional;
import responses.ResponseEnity;
import models.User;


public class AuthService {
     private final UserRepository userRepository;
    

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        
    }

    
   public ResponseEnity<User> login(String username, String password) {

        Optional<User> user = userRepository.findByUsername(username);

        if (user == null) {
            return new ResponseEnity<User>(null, false, "User not found");
        }

        if (!user.get().getPassword().equals(password)) {
            return new ResponseEnity<User>(null, false, "Incorrect password");
        }

        return new ResponseEnity<User>(user.get(), true, "Login successful");

    }

    public ResponseEnity<User> register(String fullName, String username, String password) {
        if (userRepository.findByUsername(username) != null) {
            return new ResponseEnity<>(null, false, "Username already exists");
        }

        User newUser = new User(fullName, username, password); 
        userRepository.add(newUser);
        return new ResponseEnity<User>(newUser, true, "Registration successful");
    }

    public void logout() {
        
    }

    public boolean isAuthenticated() {
       return false;
    }

}
