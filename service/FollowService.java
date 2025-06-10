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
    /**
     * This function allows a user to follow another user.
     *
     * @param follower The user who wants to follow another user.
     * @param targetUserId The ID of the user to be followed.
     * @return Returns a ResponseEnity indicating success or failure of the follow operation.
     *         If the follower is trying to follow themselves, or if they are already following the target user, it returns an error message.
     */
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
    /**
     * This function allows a user to unfollow another user.
     *
     * @param follower The user who wants to unfollow another user.
     * @param targetUserId The ID of the user to be unfollowed.
     * @return Returns a ResponseEnity indicating success or failure of the unfollow operation.
     *         If the follower is not following the target user, it returns an error message.
     */
    public ResponseEnity<Boolean> unfollow(User follower, int targetUserId) {
        if (!followRepository.exists(follower.getId(), targetUserId)) {
            return new ResponseEnity<>(false, false, "You are not following this user");
        }

        followRepository.deleteByFollowerAndTarget(follower.getId(), targetUserId);
        return new ResponseEnity<>(true, true, "Unfollowed successfully");
    }
    ///TO_VERIFY
    /**
     * This function retrieves the count of users that a specific user is following.
     *
     * @param userId The ID of the user whose following count is to be retrieved.
     * @return Returns the number of users that the specified user is following.
     */
    public int getFollowerCount(int userId) {
        return followRepository.findByFollowerId(userId).size();

    }

    

}
