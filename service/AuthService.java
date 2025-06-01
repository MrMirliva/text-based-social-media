package service;

import repositories.UserRepository;
import org.springframework.http.ResponseEntity;


public class AuthService {
     private final UserRepository userRepository;
    

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        
    }

    
   public ResponseEntity<User> login(String username, String password) {
       return null;
    }

    public ResponseEntity<User> register(String username, String password) {
        return null;
    }

    public void logout() {
        
    }

    public boolean isAuthenticated() {
       return false;
    }

}
