package service;

import java.util.List;

import repositories.UserRepository;

public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<Post> createPost(User user, String content) {
        return null;
    }

    public ResponseEntity<Post> editPost(User user, int postId, String newContent) {
        return null;
    }

    public ResponseEntity<Boolean> deletePost(User user, int postId) {
        return null;
    }

    public List<Post> getPostsByUserId(String userId) {
        return null;
    }

    public List<Post> getLimitedPosts() {
        return null;
    }
}
