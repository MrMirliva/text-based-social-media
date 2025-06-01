package service;

import models.User;

public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    public LikeService(LikeRepository likeRepository, PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
    }

    public ResponseEntity<Boolean> likePost(User user, int postId) {
         Post post = postRepository.findById(postId);
        if (post == null) {
            return new ResponseEntity<>(false, "Post not found", false);
        }

        if (likeRepository.exists(user.getId(), postId)) {
            return new ResponseEntity<>(false, "You already liked this post", false);
        }

        Like like = new Like(user.getId(), postId);
        likeRepository.add(like);
        return new ResponseEntity<>(true, "Post liked successfully", true);
    }

    public ResponseEntity<Boolean> unlikePost(User user, int postId) {
         if (!likeRepository.exists(user.getId(), postId)) {
            return new ResponseEntity<>(false, "You have not liked this post", false);
        }

        likeRepository.deleteByUserIdAndPostId(user.getId(), postId);
        return new ResponseEntity<>(true, "Like removed successfully", true);
    }

    public int getLikeCount(int postId) {
        return likeRepository.countByPostId(postId);
    }
    
}

