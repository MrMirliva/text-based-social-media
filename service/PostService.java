package service;

import java.util.List;

import models.User;
import repositories.UserRepository;

public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<Post> createPost(User user, String content) {
        if (user == null || content == null || content.isEmpty()) {
            return new ResponseEntity<>(null, "Invalid user or content", false);
        }

        Post newPost = new Post(user.getId(), content);
        postRepository.add(newPost);
        return new ResponseEntity<>(newPost, "Post created successfully", true);
    }

    public ResponseEntity<Post> editPost(User user, int postId, String newContent) {
         Post post = postRepository.findById(postId);
        if (post == null) {
            return new ResponseEntity<>(null, "Post not found", false);
        }

        if (!post.getUserId().equals(user.getId())) {
            return new ResponseEntity<>(null, "You can only edit your own posts", false);
        }

        post.setContent(newContent);
        postRepository.update(post);
        return new ResponseEntity<>(post, "Post updated successfully", true);
    }

    public ResponseEntity<Boolean> deletePost(User user, int postId) {
        Post post = postRepository.findById(postId);
        if (post == null) {
            return new ResponseEntity<>(false, "Post not found", false);
        }

        if (!post.getUserId().equals(user.getId())) {
            return new ResponseEntity<>(false, "You can only delete your own posts", false);
        }

        postRepository.delete(post);
        return new ResponseEntity<>(true, "Post deleted successfully", true);
    }

    public List<Post> getPostsByUserId(String userId) {
        return postRepository.getPostsByUserId(userId);
    }

    public List<Post> getLimitedPosts() {
        return null;
    }
}
