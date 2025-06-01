package service;

import repositories.UserRepository;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import models.User;


public class AuthService {
     private final UserRepository userRepository;
    

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        
    }

    
   public User login(String username, String password) {

        Optional<User> user = userRepository.findByUsername(username);

        if (user == null) {
            return new ResponseEntity<>(null, "User not found", false);
        }

        if (!user.get().getPassword().equals(password)) {
            return new ResponseEntity<>(null, "Incorrect password", false);
        }

        this.sessionUser = user;
        return new ResponseEntity<>(user, "Login successful", true);

    }

    public ResponseEntity<User> register(String fullName, String username, String password) {
        if (userRepository.findByUsername(username) != null) {
            return new ResponseEntity<>(null, "Username already exists", false);
        }

        User newUser = new User(fullName, username, password); 
        userRepository.add(newUser);
        return new ResponseEntity<>(newUser, "Registration successful", true);
    }

    public void logout() {
        
    }

    public boolean isAuthenticated() {
       return false;
    }

}
