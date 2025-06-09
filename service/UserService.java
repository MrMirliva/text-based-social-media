package service;

import repositories.UserRepository;
import models.User;
import responses.ResponseEnity;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEnity<User> viewProfile(User user) {
        if (user == null) {
            return new ResponseEnity<>(null, false, "User not found");
        }
        return new ResponseEnity<>(user, true, "Profile retrieved successfully");
    }

    public ResponseEnity<User> updateUsername(User user, String newUsername) {
        if (newUsername == null || newUsername.trim().isEmpty()) {
            return new ResponseEnity<>(null, false, "Username cannot be empty");
        }

        if (userRepository.findByUsername(newUsername) != null) {
            return new ResponseEnity<>(null, false, "Username already exists");
        }

        user.setUsername(newUsername);
        userRepository.update(user);
        return new ResponseEnity<>(user, true, "Username updated successfully");
    }

    public ResponseEnity<User> updatePassword(User user, String newPassword) {
        if (newPassword == null || newPassword.length() < 4) {
            return new ResponseEnity<>(null, false, "Password must be at least 4 characters");
        }

        user.setPassword(newPassword);
        userRepository.update(user);
        return new ResponseEnity<>(user, true, "Password updated successfully");
    }
}
    
    

