package service;

import repositories.UserRepository;
import models.User;
import utils.ResponseEntity;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<User> viewProfile(User user) {
        if (user == null) {
            return new ResponseEntity<>(null, "User not found", false);
        }
        return new ResponseEntity<>(user, "Profile retrieved successfully", true);
    }

    public ResponseEntity<User> updateUsername(User user, String newUsername) {
        if (newUsername == null || newUsername.trim().isEmpty()) {
            return new ResponseEntity<>(null, "Username cannot be empty", false);
        }

        if (userRepository.findByUsername(newUsername) != null) {
            return new ResponseEntity<>(null, "Username already exists", false);
        }

        user.setUsername(newUsername);
        userRepository.update(user);
        return new ResponseEntity<>(user, "Username updated successfully", true);
    }

    public ResponseEntity<User> updatePassword(User user, String newPassword) {
        if (newPassword == null || newPassword.length() < 4) {
            return new ResponseEntity<>(null, "Password must be at least 4 characters", false);
        }

        user.setPassword(newPassword);
        userRepository.update(user);
        return new ResponseEntity<>(user, "Password updated successfully", true);
    }
}
    
    

