package service;

import models.User;
import models.Post;

import java.util.Optional;

import models.Like;
import repositories.LikeRepository;
import repositories.PostRepository;
import responses.ResponseEntity;

/**
 * LikeService is a service class responsible for managing the like and unlike operations on posts
 * in a social media application. It provides methods for liking and unliking posts, as well as retrieving
 * the like count for a specific post. The service interacts with LikeRepository and PostRepository to persist
 * and query like relationships and post existence.
 * <p>
 * Key features include:
 * <ul>
 *   <li>Allowing users to like and unlike posts with appropriate validation</li>
 *   <li>Preventing users from liking the same post multiple times</li>
 *   <li>Ensuring users can only unlike posts they have previously liked</li>
 *   <li>Providing methods to retrieve the number of likes for a post</li>
 *   <li>Returning informative response messages for each operation</li>
 * </ul>
 * <p>
 * This class is intended to be used as a core component in applications that require social networking features,
 * such as liking and unliking posts, and displaying like statistics.
 *
 * @author Muhammed Yasin Eroğlu
 * @version 1.0
 * @since 2025-06-14
 */
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    public LikeService(LikeRepository likeRepository, PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
    }

    /**
     * This function allows a user to like a post.
     *
     * @param user The user who is liking the post.
     * @param postId The ID of the post to be liked.
     * @return Returns a ResponseEntity indicating success or failure of the like operation.
     */
    public ResponseEntity<Boolean> likePost(User user, int postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (!post.isPresent()) {
            return new ResponseEntity<>(false, false, "Post not found");
        }

        if (likeRepository.exists( postId,user.getId())) {
            return new ResponseEntity<>(false, false, "You already liked this post");
        }

        Like like = new Like( postId,user.getId());
        likeRepository.add(like);
        return new ResponseEntity<>(true, true, "Post liked successfully");
    }

    /**
     * This function allows a user to unlike a post.
     *
     * @param user The user who is unliking the post.
     * @param postId The ID of the post to be unliked.
     * @return Returns a ResponseEntity indicating success or failure of the unlike operation.
     */
    public ResponseEntity<Boolean> unlikePost(User user, int postId) {
        if (!likeRepository.exists(postId,user.getId())) {
            return new ResponseEntity<>(false, false, "You have not liked this post");
        }

        likeRepository.deleteByUserIdAndPostId(user.getId(), postId);
        return new ResponseEntity<>(true, true, "Like removed successfully");
    }

    /**
     * This function retrieves the count of likes for a specific post.
     *
     * @param postId The ID of the post whose like count is to be retrieved.
     * @return Returns the number of likes for the specified post.
     */
    public ResponseEntity<Integer> getLikeCount(int postId) {
        return new ResponseEntity<>(likeRepository.countByPostId(postId), true, "Like count retrieved successfully");
    }

}

