package service;

import repositories.FollowRepository;
import repositories.PostRepository;
import repositories.UserRepository;

import java.util.List;

import models.Post;
import models.User;
import responses.ProfileResponse;
import responses.ResponseEntity;

public class UserService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final PostRepository postRepository;
    private final String DELIMINATOR = "<-!->";

    public UserService(UserRepository userRepository, FollowRepository followRepository, PostRepository postRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    /**
     * This function retrieves the profile of a user.
     *
     * @param user The user whose profile is to be viewed.
     * @return Returns a ResponseEntity containing the user's profile information, including the number of followers and their posts.
     *         If the user is null, returns a ResponseEntity with an error message.
     */
    public ResponseEntity<ProfileResponse> viewProfile(User user) {
        if (user == null) {
            return new ResponseEntity<>(null, false, "User not found");
        }
        

        int numOfFollowers = followRepository.numOfFollowers(user.getId());
        List<Post> posts = postRepository.findByUserId(user.getId());

        ProfileResponse profileResponse = new ProfileResponse(user.getId(), user.getUsername(), user.getFullName(), numOfFollowers, posts);
        return new ResponseEntity<>(profileResponse, true, "Profile retrieved successfully");
    }

    /**
     * This function updates the username of a user.
     *
     * @param user The user whose username is to be updated.
     * @param newUsername The new username to be set.
     * @return Returns a ResponseEntity containing the updated user and a success message if the update is successful.
     *         If the new username is empty or already exists, returns a ResponseEntity with an error message.
     */
    public ResponseEntity<User> updateUsername(User user, String newUsername) {
        if (newUsername == null || newUsername.trim().isEmpty()) {
            return new ResponseEntity<>(null, false, "Username cannot be empty");
        }

        if(newUsername.contains(DELIMINATOR)) {
            return new ResponseEntity<>(null, false, "Username cannot contain the delimiter: " + DELIMINATOR);
        }

        if (userRepository.findByUsername(newUsername).isPresent()) {
            return new ResponseEntity<>(null, false, "Username already exists");
        }

        user.setUsername(newUsername);
        userRepository.update(user);
        return new ResponseEntity<>(user, true, "Username updated successfully");
    }

    /**
     * This function updates the password of a user.
     *
     * @param user The user whose password is to be updated.
     * @param newPassword The new password to be set.
     * @return Returns a ResponseEntity containing the updated user and a success message if the update is successful.
     *         If the new password is null or less than 4 characters, returns a ResponseEntity with an error message.
     */
    public ResponseEntity<User> updatePassword(User user, String newPassword) {
        if (newPassword == null || newPassword.length() < 4) {
            return new ResponseEntity<>(null, false, "Password must be at least 4 characters");
        }

        if(newPassword.contains(DELIMINATOR)) {
            return new ResponseEntity<>(null, false, "Password cannot contain the delimiter: " + DELIMINATOR);
        }

        user.setPassword(newPassword);
        userRepository.update(user);
        return new ResponseEntity<>(user, true, "Password updated successfully");
    }
}
    
    

