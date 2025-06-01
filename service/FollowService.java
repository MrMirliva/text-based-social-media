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
        if (follower.getId().equals(targetUserId)) {
            return new ResponseEntity<>(false, "You cannot follow yourself", false);
        }

        if (followRepository.exists(follower.getId(), targetUserId)) {
            return new ResponseEntity<>(false, "You are already following this user", false);
        }

        Follow follow = new Follow(follower.getId(), targetUserId);
        followRepository.add(follow);
        return new ResponseEntity<>(true, "Followed successfully", true);
    }

    public ResponseEntity<Boolean> unfollow(User follower, String targetUserId) {
      if (!followRepository.exists(follower.getId(), targetUserId)) {
            return new ResponseEntity<>(false, "You are not following this user", false);
        }

        followRepository.deleteByFollowerAndTarget(follower.getId(), targetUserId);
        return new ResponseEntity<>(true, "Unfollowed successfully", true);
    }

    public int getFollowerCount(String userId) {
          return followRepository.countFollowingByUserId(userId);

    }   
    
}
