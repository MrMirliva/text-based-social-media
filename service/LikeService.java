package service;

import models.User;
import models.Post;

import java.util.Optional;

import models.Like;
import repositories.LikeRepository;
import repositories.PostRepository;
import responses.ResponseEntity;

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

