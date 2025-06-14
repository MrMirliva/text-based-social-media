package service;

import models.User;
import models.Follow;
import responses.ResponseEntity;
import repositories.FollowRepository;

/**
 * FollowService is a service class responsible for managing the follow and unfollow operations between users
 * in a social media application. It provides methods for following and unfollowing users, as well as retrieving
 * follower and following counts for a given user. The service interacts with a FollowRepository to persist and
 * query follow relationships.
 * <p>
 * Key features include:
 * <ul>
 *   <li>Allowing users to follow and unfollow other users with appropriate validation</li>
 *   <li>Preventing users from following themselves or following the same user multiple times</li>
 *   <li>Ensuring users can only unfollow users they are currently following</li>
 *   <li>Providing methods to retrieve the number of followers and followings for a user</li>
 *   <li>Returning informative response messages for each operation</li>
 * </ul>
 * <p>
 * This class is intended to be used as a core component in applications that require social networking features,
 * such as following and unfollowing users, and displaying follower/following statistics.
 *
 * @author Muhammed Yasin Eroğlu
 * @version 1.0
 * @since 2025-06-14
 */
public class FollowService {
    private final FollowRepository followRepository;

    public FollowService(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    /**
     * This function allows a user to follow another user.
     *
     * @param follower The user who wants to follow another user.
     * @param targetUserId The ID of the user to be followed.
     * @return Returns a ResponseEntity indicating success or failure of the follow operation.
     *         If the follower is trying to follow themselves, or if they are already following the target user, it returns an error message.
     */
    public ResponseEntity<Boolean> follow(User follower, int targetUserId) {
        if (follower.getId() == targetUserId) {
            return new ResponseEntity<Boolean>(false, false, "You cannot follow yourself");
        }

        if (followRepository.exists(follower.getId(), targetUserId)) {
            return new ResponseEntity<>(false, false, "You are already following this user");
        }

        Follow follow = new Follow(follower.getId(), targetUserId);
        followRepository.add(follow);
        return new ResponseEntity<>(true, true, "Followed successfully");
    }

    /**
     * This function allows a user to unfollow another user.
     *
     * @param follower The user who wants to unfollow another user.
     * @param targetUserId The ID of the user to be unfollowed.
     * @return Returns a ResponseEntity indicating success or failure of the unfollow operation.
     *         If the follower is not following the target user, it returns an error message.
     */
    public ResponseEntity<Boolean> unfollow(User follower, int targetUserId) {
        if (!followRepository.exists(follower.getId(), targetUserId)) {
            return new ResponseEntity<>(false, false, "You are not following this user");
        }

        followRepository.deleteByFollowerAndTarget(follower.getId(), targetUserId);
        return new ResponseEntity<>(true, true, "Unfollowed successfully");
    }
    
    /**
     * This function retrieves the count of users that a specific user is following.
     *
     * @param userId The ID of the user whose following count is to be retrieved.
     * @return Returns the number of users that the specified user is following.
     */
    public ResponseEntity<Integer> getFollowerCount(int userId) {
        return new ResponseEntity<>(followRepository.findByFollowerId(userId).size(), true, "Follower count retrieved successfully");

    }
    /**
     * This function retrieves the count of users that a specific user is following.
     *
     * @param userId The ID of the user whose following count is to be retrieved.
     * @return Returns the number of users that the specified user is following.
     */
    public ResponseEntity<Integer> getFollowingCount(int userId) {
        return new ResponseEntity<>(followRepository.findByFollowingId(userId).size(), true, "Following count retrieved successfully");
    }

    

}
