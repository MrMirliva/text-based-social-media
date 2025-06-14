package repositories;

import java.util.List;
import java.util.stream.Collectors;

import memento.core.MACRepository;
import models.Follow;

/**
 * The FollowRepository class provides data access operations for Follow entities.
 * It extends the MACRepository to leverage generic CRUD operations and adds
 * specific query methods for retrieving Follow relationships based on follower or following user IDs.
 * <p>
 * This repository allows you to:
 * <ul>
 *   <li>Find all Follow instances where a specific user is the follower.</li>
 *   <li>Find all Follow instances where a specific user is being followed.</li>
 *   <li>Check if a specific follower is following a specific user.</li>
 *   <li>Delete Follow entries based on follower and following user IDs.</li>
 * </ul>
 * </p>
 * 
 * @see memento.core.MACRepository
 * @see models.Follow
 * 
 * @author Mirliva (Abdullah Gündüz)
 * @version 1.0
 * @since 2025-06-04
 */
public class FollowRepository extends MACRepository<Follow> {

    public FollowRepository() {
        super(Follow.class);
    }

    /**
     * Finds all Follow instances where the followerId matches the given followerId.
     * This method retrieves all follows where a user is following another user.
     * 
     * @param followerId the ID of the user who is following
     * @return a list of Follow instances where the followerId matches the given followerId
     */
    public List<Follow> findByFollowerId(int followerId) {
        return getAll().stream()
                .filter(follow -> follow.getFollowerId() == followerId)
                .collect(Collectors.toList());
    }

    


    /**
     * Finds all Follow instances where the followingId matches the given followingId.
     * This method retrieves all follows where the user is being followed by another user.
     * 
     * @param followingId the ID of the user who is being followed
     * @return a list of Follow instances where the followingId matches the given followingId
     */
    public List<Follow> findByFollowingId(int followingId) {
        return getAll().stream()
                .filter(follow -> follow.getFollowingId() == followingId)
                .collect(Collectors.toList());
    }
    

    /**
     * The `exists` function checks if a specific follower is following a specific user.
     * 
     * @param followerId The `followerId` parameter represents the ID of the user who is following
     * another user.
     * @param followingId The `followingId` parameter represents the ID of the user that another user
     * is following. In the context of the `exists` method you provided, it is used to check if a
     * specific user (identified by `followerId`) is following another user (identified by
     * `followingId`).
     * @return The method `exists` returns a boolean value indicating whether there is a follow
     * relationship between the `followerId` and `followingId` in the list of all follows.
     */
    public boolean exists(int followerId, int followingId) {
        return getAll().stream()
                .anyMatch(follow -> follow.getFollowerId() == followerId && follow.getFollowingId() == followingId);
    }


    /**
     * This function deletes all follow entries where the followerId and followingId match the provided
     * values.
     * 
     * @param followerId The `followerId` parameter represents the ID of the user who is following
     * another user.
     * @param followingId The `followingId` parameter represents the ID of the user that is being
     * followed by another user. In the context of the `deleteByFollowerAndTarget` method, it is used
     * to identify the user who is being followed by the user with the `followerId`.
     */
    public void deleteByFollowerAndTarget(int followerId, int followingId) {
        List<Follow> followsToDelete = getAll().stream()
                .filter(follow -> follow.getFollowerId() == followerId && follow.getFollowingId() == followingId)
                .collect(Collectors.toList());
        for (Follow follow : followsToDelete) {
            deleteById(follow.getId());
        }
    }

    /**
     * This function counts the number of followers a specific user has.
     * @param followersId The `followersId` parameter represents the ID of the user whose followers are being counted.
     * It is used in the `numOfFollowers` method to filter the list of follows and count how many users
     * are following the user with the specified `followersId`.
     * @return The method `numOfFollowers` returns the count of follows where the `followingId` matches
     * the provided `followersId`.
     */
    public int numOfFollowers(int followersId) {
        return (int) getAll().stream()
                .filter(follow -> follow.getFollowingId() == followersId)
                .count();
    }

    /**
     * This function counts the number of users that a specific user is following.
     * @param followingId The `followingId` parameter represents the ID of the user whose following count
     * is being calculated. In the context of the `numOfFollowing` method, it is used to count how many
     * users the specified user is following.   
     * @return The method `numOfFollowing` returns the count of follows where the `followerId` matches
     * the provided `followingId`.
     */
    public int numOfFollowing(int followingId) {
        return (int) getAll().stream()
                .filter(follow -> follow.getFollowerId() == followingId)
                .count();
    }
}
