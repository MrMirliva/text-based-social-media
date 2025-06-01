package service;

public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    public LikeService(LikeRepository likeRepository, PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
    }

    public ResponseEntity<Boolean> likePost(User user, int postId) {
        return null;
    }

    public ResponseEntity<Boolean> unlikePost(User user, int postId) {
        return null;
    }

    public int getLikeCount(int postId) {
        return 0;
    }
}

