package service;

import models.User;
import repositories.UserRepository;

public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public FollowService(FollowRepository followRepository, UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<Boolean> follow(User follower, String targetUserId) {
       return null;
    }

    public ResponseEntity<Boolean> unfollow(User follower, String targetUserId) {
      return null;
    }

    public int getFollowerCount(String userId) {
        return 0;
    }   
    
}
