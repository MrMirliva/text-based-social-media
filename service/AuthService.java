package service;

import repositories.UserRepository;
import requests.LoginRequest;

import java.util.HashMap;
import java.util.Optional;
import responses.ResponseEnity;
import models.User;


public class AuthService {
    private final UserRepository userRepository;
    private final String DELIMINATOR = "<-!->";

    

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        
    }

    ///TO_VERIFY
    /**
     * This function handles the user login process.
     *
     * @param loginRequest The login request object containing the username, password, and cookie information.
     * @return Returns a ResponseEnity containing the authenticated user and a success message if the login is successful.
     *         If the user is already authenticated, not found, or the password is incorrect, returns a ResponseEnity with an error message.
     */
    public ResponseEnity<User> login(LoginRequest loginRequest) {
        if (isAuthenticated(loginRequest.getCookie()).isOk()) {
            return new ResponseEnity<>(null, false, "User is already authenticated");
        }

        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());

        if (!user.isPresent()) {
            return new ResponseEnity<User>(null, false, "User not found");
        }

        if (!user.get().getPassword().equals(loginRequest.getPassword())) {
            return new ResponseEnity<User>(null, false, "Incorrect password");
        }

        return new ResponseEnity<User>(user.get(), true, "Login successful");

    }

    ///TO_VERIFY
    /**
     * This function handles the user registration process.
     *
     * @param fullName The full name of the user.
     * @param loginRequest The login request object containing the username and password.
     * @return Returns a ResponseEnity containing the registered user and a success message if the registration is successful.
     *         If the user is already authenticated, or the username already exists, returns a ResponseEnity with an error message.
     */
    public ResponseEnity<User> register(String fullName , LoginRequest loginRequest) {
        if(isAuthenticated(loginRequest.getCookie()).isOk()) {
            return new ResponseEnity<>(null, false, "User is already authenticated");
        }
        if (userRepository.findByUsername(loginRequest.getUsername()) != null) {
            return new ResponseEnity<>(null, false, "Username already exists");
        }
        if(fullName.contains(DELIMINATOR)) {
            return new ResponseEnity<>(null, false, "Full name cannot contain the delimiter: " + DELIMINATOR);
        }
        if(loginRequest.getUsername().contains(DELIMINATOR)) {
            return new ResponseEnity<>(null, false, "Username cannot contain the delimiter: " + DELIMINATOR);
        }

        User newUser = new User(fullName, loginRequest.getUsername(), loginRequest.getPassword());
        userRepository.add(newUser);

        return new ResponseEnity<User>(newUser, true, "Registration successful");
    }

    ///TO_VERIFY
    /**
     * This function handles the user logout process.
     *
     * @param cookie The cookie containing the userId of the authenticated user.
     * @return Returns a ResponseEnity indicating whether the logout was successful or not.
     *         If the user is not authenticated, returns a ResponseEnity with an error message.
     */
    public ResponseEnity<?> logout(HashMap<String, String> cookie) {

        if (!isAuthenticated(cookie).isOk()) {
            return new ResponseEnity<>(null, false, "User is not authenticated");
        }
        
        cookie.remove("userId");

        return new ResponseEnity<>(true, true, "Logout successful");
    }

    ///TO_VERIFY
    /**
     * This function checks if the user is authenticated based on the provided cookie.
     *
     * @param cookie The cookie containing the userId of the authenticated user.
     * @return Returns a ResponseEnity indicating whether the user is authenticated or not.
     *         If the userId is not present or invalid, returns a ResponseEnity with an error message.
     */
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
    /**
     * This function retrieves the authenticated user based on the provided cookie.
     *
     * @param cookie The cookie containing the userId of the authenticated user.
     * @return Returns a ResponseEnity containing the authenticated user and a success message if the user is authenticated.
     *         If the user is not authenticated, returns a ResponseEnity with an error message.
     */
    public ResponseEnity<User> getAuthenticatedUser(HashMap<String, String> cookie) {
        if (isAuthenticated(cookie).isError()) {
            return new ResponseEnity<>(null, false, "User is not authenticated");
        }

        Integer userId = Integer.valueOf(cookie.get("userId"));
    
        Optional<User> user = userRepository.findById(userId);

        return new ResponseEnity<>(user.get(), true, "User is authenticated");
    }

}
