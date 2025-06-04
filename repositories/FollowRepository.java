/**
 * The FollowRepository class provides data access operations for Follow entities.
 * It extends the MACRepository to leverage generic CRUD operations and adds
 * specific query methods for retrieving Follow relationships based on follower or following user IDs.
 * <p>
 * This repository allows you to:
 * <ul>
 *   <li>Find all Follow instances where a specific user is the follower.</li>
 *   <li>Find all Follow instances where a specific user is being followed.</li>
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
package repositories;

import java.util.List;
import java.util.stream.Collectors;

import memento.core.MACRepository;
import models.Follow;

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
    
}
