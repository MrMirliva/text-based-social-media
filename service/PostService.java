package service;

import repositories.UserRepository;

public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<Post> createPost(User user, String content) {
    }

    public ResponseEntity<Post> editPost(User user, int postId, String newContent) {
        
    }

    public ResponseEntity<Boolean> deletePost(User user, int postId) {
       
    }

    public List<Post> getPostsByUserId(String userId) {
    }

    public List<Post> getLimitedPosts() {
       
    }
}
