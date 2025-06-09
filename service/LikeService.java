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

    public ResponseEnity<Boolean> likePost(User user, int postId) {
         Optional<Post> post = postRepository.findById(postId);
        if (post == null) {
            return new ResponseEnity<>(false, false, "Post not found");
        }

        if (likeRepository.exists(user.getId(), postId)) {
            return new ResponseEnity<>(false, false, "You already liked this post");
        }

        Like like = new Like(user.getId(), postId);
        likeRepository.add(like);
        return new ResponseEnity<>(true, true, "Post liked successfully");
    }

    public ResponseEnity<Boolean> unlikePost(User user, int postId) {
         if (!likeRepository.exists(user.getId(), postId)) {
            return new ResponseEnity<>(false, false, "You have not liked this post");
        }

        likeRepository.deleteByUserIdAndPostId(user.getId(), postId);
        return new ResponseEnity<>(true, true, "Like removed successfully");
    }

    public int getLikeCount(int postId) {
        return likeRepository.countByPostId(postId);
    }
    
}

