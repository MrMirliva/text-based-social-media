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
       return null;
    }

    public ResponseEntity<User> updateUsername(User user, String newUsername) {
       return null;
    }

    public ResponseEntity<User> updatePassword(User user, String newPassword) {
       return null
    }
}
    
    

