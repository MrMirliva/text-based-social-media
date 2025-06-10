package service;

import models.User;
import models.Follow;
import responses.ResponseEnity;
import repositories.FollowRepository;
public class FollowService {
    private final FollowRepository followRepository;

    public FollowService(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    ///TO_VERIFY
    public ResponseEnity<Boolean> follow(User follower, int targetUserId) {
        if (follower.getId() == targetUserId) {
            return new ResponseEnity<Boolean>(false, false, "You cannot follow yourself");
        }

        if (followRepository.exists(follower.getId(), targetUserId)) {
            return new ResponseEnity<>(false, false, "You are already following this user");
        }

        Follow follow = new Follow(follower.getId(), targetUserId);
        followRepository.add(follow);
        return new ResponseEnity<>(true, true, "Followed successfully");
    }

    ///TO_VERIFY
    public ResponseEnity<Boolean> unfollow(User follower, int targetUserId) {
      if (!followRepository.exists(follower.getId(), targetUserId)) {
            return new ResponseEnity<>(false, false, "You are not following this user");
        }

        followRepository.deleteByFollowerAndTarget(follower.getId(), targetUserId);
        return new ResponseEnity<>(true, true, "Unfollowed successfully");
    }

    public int getFollowerCount(int userId) {
          return followRepository.findByFollowerId(userId).size();

    }

    

}
