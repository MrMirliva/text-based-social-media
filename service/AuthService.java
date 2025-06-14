package service;

import repositories.UserRepository;
import requests.LoginRequest;

import java.util.HashMap;
import java.util.Optional;
import responses.ResponseEntity;
import models.User;

/**
 * AuthService is a service class responsible for handling user authentication and authorization logic.
 * It provides methods for user login, registration, logout, authentication status checking, and retrieving
 * the currently authenticated user. The service interacts with a UserRepository to manage user data and
 * uses a cookie-based mechanism to track authenticated sessions.
 * <p>
 * Key features include:
 * <ul>
 *   <li>Validating user credentials during login and registration</li>
 *   <li>Enforcing unique usernames and input constraints (such as delimiter and space restrictions)</li>
 *   <li>Managing session state via a cookie map containing user identifiers</li>
 *   <li>Providing informative response messages for each authentication action</li>
 *   <li>Ensuring secure handling of user authentication status and session termination</li>
 * </ul>
 * <p>
 * This class is intended to be used as a core component in applications requiring user authentication
 * and session management.
 *
 * @author Muhammed Yasin Eroğlu
 * @version 1.0
 * @since 2025-06-14
 */
public class AuthService {
    private final UserRepository userRepository;
    private final String DELIMINATOR = "<-!->";

    

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        
    }

    /**
     * This function handles the user login process.
     *
     * @param loginRequest The login request object containing the username, password, and cookie information.
     * @return Returns a ResponseEntity containing the authenticated user and a success message if the login is successful.
     *         If the user is already authenticated, not found, or the password is incorrect, returns a ResponseEntity with an error message.
     */
    public ResponseEntity<User> login(LoginRequest loginRequest) {
        if (isAuthenticated(loginRequest.getCookie()).isOk()) {
            return new ResponseEntity<>(null, false, "User is already authenticated");
        }

        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());

        if (!user.isPresent()) {
            return new ResponseEntity<User>(null, false, "User not found");
        }

        if (!user.get().getPassword().equals(loginRequest.getPassword())) {
            return new ResponseEntity<User>(null, false, "Incorrect password");
        }

        loginRequest.getCookie().put("userId", String.valueOf(user.get().getId()));
        return new ResponseEntity<User>(user.get(), true, "Login successful");

    }

    /**
     * This function handles the user registration process.
     *
     * @param fullName The full name of the user.
     * @param loginRequest The login request object containing the username and password.
     * @return Returns a ResponseEntity containing the registered user and a success message if the registration is successful.
     *         If the user is already authenticated, or the username already exists, returns a ResponseEntity with an error message.
     */
    public ResponseEntity<User> register(String fullName , LoginRequest loginRequest) {
        if(isAuthenticated(loginRequest.getCookie()).isOk()) {
            return new ResponseEntity<>(null, false, "User is already authenticated");
        }
        if (userRepository.findByUsername(loginRequest.getUsername()).isPresent()) {
            return new ResponseEntity<>(null, false, "Username already exists");
        }
        if(fullName.contains(DELIMINATOR)) {
            return new ResponseEntity<>(null, false, "Full name cannot contain the delimiter: " + DELIMINATOR);
        }
        if(loginRequest.getUsername().contains(DELIMINATOR)) {
            return new ResponseEntity<>(null, false, "Username cannot contain the delimiter: " + DELIMINATOR);
        }
        if(loginRequest.getUsername().contains(" ")) {
            return new ResponseEntity<>(null, false, "Username cannot contain spaces");
        }


        if (loginRequest.getPassword() == null || loginRequest.getPassword().length() < 4) {
            return new ResponseEntity<>(null, false, "Password must be at least 4 characters");
        }

        if(loginRequest.getPassword().contains(" ")) {
            return new ResponseEntity<>(null, false, "Password cannot contain spaces");
        }

        User newUser = new User(fullName, loginRequest.getUsername(), loginRequest.getPassword());
        userRepository.add(newUser);

        return new ResponseEntity<User>(newUser, true, "Registration successful");
    }

    /**
     * This function handles the user logout process.
     *
     * @param cookie The cookie containing the userId of the authenticated user.
     * @return Returns a ResponseEntity indicating whether the logout was successful or not.
     *         If the user is not authenticated, returns a ResponseEntity with an error message.
     */
    public ResponseEntity<?> logout(HashMap<String, String> cookie) {

        if (!isAuthenticated(cookie).isOk()) {
            return new ResponseEntity<>(null, false, "User is not authenticated");
        }
        
        cookie.remove("userId");

        return new ResponseEntity<>(true, true, "Logout successful");
    }

    /**
     * This function checks if the user is authenticated based on the provided cookie.
     *
     * @param cookie The cookie containing the userId of the authenticated user.
     * @return Returns a ResponseEntity indicating whether the user is authenticated or not.
     *         If the userId is not present or invalid, returns a ResponseEntity with an error message.
     */
    public ResponseEntity<Boolean> isAuthenticated(HashMap<String, String> cookie) {
        if (!cookie.containsKey("userId")) {
            return new ResponseEntity<>(false, false, "User is not authenticated");
        }

        
        if (cookie.get("userId") == null || cookie.get("userId").isEmpty()) {
            return new ResponseEntity<>(false, false, "User is not authenticated");
        }

        Integer userId = null;
        try {
            userId = Integer.valueOf(cookie.get("userId"));
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(false, false, "Invalid userId format");
        }
        Optional<User> user = userRepository.findById(userId);
        if (user == null || !user.isPresent()) {
            return new ResponseEntity<>(false, false, "User not found");
        }

        return new ResponseEntity<>(true, true, "User is authenticated");
    }

    /**
     * This function retrieves the authenticated user based on the provided cookie.
     *
     * @param cookie The cookie containing the userId of the authenticated user.
     * @return Returns a ResponseEntity containing the authenticated user and a success message if the user is authenticated.
     *         If the user is not authenticated, returns a ResponseEntity with an error message.
     */
    public ResponseEntity<User> getAuthenticatedUser(HashMap<String, String> cookie) {
        if (isAuthenticated(cookie).isError()) {
            return new ResponseEntity<>(null, false, "User is not authenticated");
        }

        Integer userId = Integer.valueOf(cookie.get("userId"));
    
        Optional<User> user = userRepository.findById(userId);

        return new ResponseEntity<>(user.get(), true, "User is authenticated");
    }

}
