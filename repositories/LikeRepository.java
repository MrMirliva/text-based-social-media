/**
 * LikeRepository is a repository class responsible for managing data access operations for Like entities.
 * It extends MACRepository<Like> and provides methods specific to like operations.
 * 
 * Main functionalities:
 * <ul>
 *   <li>Checks if a like exists for a specific post and user</li>
 *   <li>Counts the total number of likes for a specific post</li>
 *   <li>Deletes all likes for a specific user and post</li>
 * </ul>
 * 
 * This class offers specialized methods to facilitate the management of the Like model.
 *
 * @author Mirliva (Abdullah Gündüz)
 * @version 1.0
 * @since 2025-06-04
 */

package repositories;

import java.util.List;

import memento.core.MACRepository;
import models.Like;

public class LikeRepository extends MACRepository<Like> {

    public LikeRepository() {
        super(Like.class);
    }

    /**
     * Checks if a like exists for a specific post and user.
     * This method retrieves all likes and checks if any like matches the given postId and userId.
     * @param postId the ID of the post to check likes for
     * @param userId the ID of the user who liked the post
     * @return true if a like exists for the specified post and user, false otherwise
     */
    public boolean exists(int postId, int userId) {
        return getAll().stream()
                .anyMatch(like -> like != null && like.getPostId() == postId && like.getUserId() == userId);
    }

    /**
     * Counts the number of likes for a specific post.
     * This method retrieves all likes and filters them by the given postId,
     * then counts the number of likes found.
     * @param postId the ID of the post to count likes for
     * @return the count of likes for the specified post
     */
    public int countByPostId(int postId) {
        return (int) getAll().stream()
                .filter(like -> like != null && like.getPostId() == postId)
                .count();
    }

    /**
     * Deletes all likes for a specific user and post.
     * This method retrieves all likes associated with the given userId and postId,
     * then deletes each like found.
     * If no likes are found, it returns false.
     * @param userId 
     * @param postId 
     * @return true if likes were successfully deleted, false if no likes were found or deletion failed
     */
    public boolean deleteByUserIdAndPostId(int userId, int postId) {
        List<Like> likes = getAll().stream()
                .filter(like -> like != null && like.getUserId() == userId && like.getPostId() == postId)
                .toList();

        if (likes.isEmpty()) {
            return false; // No likes found to delete
        }

        for (Like like : likes) {
            if (!deleteById(like.getId())) {
                return false; // Failed to delete a like
            }
        }

        return true; // Successfully deleted likes
    }
    
}
