package service;

import repositories.FollowRepository;
import repositories.PostRepository;
import repositories.UserRepository;

import java.util.List;

import models.Post;
import models.User;
import responses.ProfileResponse;
import responses.ResponseEnity;

public class UserService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final PostRepository postRepository;

    public UserService(UserRepository userRepository, FollowRepository followRepository, PostRepository postRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    ///TO_VERIFY
    /**
     * This function retrieves the profile of a user.
     *
     * @param user The user whose profile is to be viewed.
     * @return Returns a ResponseEnity containing the user's profile information, including the number of followers and their posts.
     *         If the user is null, returns a ResponseEnity with an error message.
     */
    public ResponseEnity<ProfileResponse> viewProfile(User user) {
        if (user == null) {
            return new ResponseEnity<>(null, false, "User not found");
        }
        

        int numOfFollowers = followRepository.numOfFollowers(user.getId());
        List<Post> posts = postRepository.findByUserId(user.getId());

        ProfileResponse profileResponse = new ProfileResponse(user.getId(), user.getUsername(), user.getFullName(), numOfFollowers, posts);
        return new ResponseEnity<>(profileResponse, true, "Profile retrieved successfully");
    }

    ///TO_VERIFY
    /**
     * This function updates the username of a user.
     *
     * @param user The user whose username is to be updated.
     * @param newUsername The new username to be set.
     * @return Returns a ResponseEnity containing the updated user and a success message if the update is successful.
     *         If the new username is empty or already exists, returns a ResponseEnity with an error message.
     */
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

    ///TO_VERIFY
    /**
     * This function updates the password of a user.
     *
     * @param user The user whose password is to be updated.
     * @param newPassword The new password to be set.
     * @return Returns a ResponseEnity containing the updated user and a success message if the update is successful.
     *         If the new password is null or less than 4 characters, returns a ResponseEnity with an error message.
     */
    public ResponseEnity<User> updatePassword(User user, String newPassword) {
        if (newPassword == null || newPassword.length() < 4) {
            return new ResponseEnity<>(null, false, "Password must be at least 4 characters");
        }

        user.setPassword(newPassword);
        userRepository.update(user);
        return new ResponseEnity<>(user, true, "Password updated successfully");
    }
}
    
    

