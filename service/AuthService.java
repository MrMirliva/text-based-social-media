package service;

import repositories.UserRepository;

public class AuthService {
     private final UserRepository userRepository;
    

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        
    }

    
   public ResponseEntity<User> login(String username, String password) {
       
    }

    public ResponseEntity<User> register(String username, String password) {
        
    }

    public void logout() {
        
    }

    public boolean isAuthenticated() {
       
    }

   
}
