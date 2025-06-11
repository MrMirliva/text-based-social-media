package service;

import models.User;
import models.Post;

import java.util.Optional;

import models.Like;
import repositories.LikeRepository;
import repositories.PostRepository;
import responses.ResponseEnity;

public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    public LikeService(LikeRepository likeRepository, PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
    }

    ///TO_VERIFY
    /**
     * This function allows a user to like a post.
     *
     * @param user The user who is liking the post.
     * @param postId The ID of the post to be liked.
     * @return Returns a ResponseEnity indicating success or failure of the like operation.
     */
    public ResponseEnity<Boolean> likePost(User user, int postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (!post.isPresent()) {
            return new ResponseEnity<>(false, false, "Post not found");
        }

        if (likeRepository.exists(user.getId(), postId)) {
            return new ResponseEnity<>(false, false, "You already liked this post");
        }

        Like like = new Like(user.getId(), postId);
        likeRepository.add(like);
        return new ResponseEnity<>(true, true, "Post liked successfully");
    }

    ///TO_VERIFY
    /**
     * This function allows a user to unlike a post.
     *
     * @param user The user who is unliking the post.
     * @param postId The ID of the post to be unliked.
     * @return Returns a ResponseEnity indicating success or failure of the unlike operation.
     */
    public ResponseEnity<Boolean> unlikePost(User user, int postId) {
        if (!likeRepository.exists(user.getId(), postId)) {
            return new ResponseEnity<>(false, false, "You have not liked this post");
        }

        likeRepository.deleteByUserIdAndPostId(user.getId(), postId);
        return new ResponseEnity<>(true, true, "Like removed successfully");
    }

    ///TO_VERIFY
    /**
     * This function retrieves the count of likes for a specific post.
     *
     * @param postId The ID of the post whose like count is to be retrieved.
     * @return Returns the number of likes for the specified post.
     */
    public ResponseEnity<Integer> getLikeCount(int postId) {
        return new ResponseEnity<>(likeRepository.countByPostId(postId), true, "Like count retrieved successfully");
    }

}

