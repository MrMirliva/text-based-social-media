package service;

import java.util.List;
import java.util.Optional;

import models.User;
import repositories.UserRepository;
import models.Post;
import repositories.PostRepository;
import responses.ResponseEnity;

public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public ResponseEnity<Post> createPost(User user, String content) {
        if (user == null || content == null || content.isEmpty()) {
            return new ResponseEnity<>(null, false, "Invalid user or content");
        }

        Post newPost = new Post( content, user.getId());
        postRepository.add(newPost);
        return new ResponseEnity<>(newPost, true, "Post created successfully");
    }

    public ResponseEnity<Post> editPost(User user, int postId, String newContent) {
         Optional<Post> post = postRepository.findById(postId);
        if (post == null) {
            return new ResponseEnity<>(null, false, "Post not found");
        }

        if (post.get().getUserId() != user.getId()) {
            return new ResponseEnity<>(null, false, "You can only edit your own posts");
        }

        post.get().setContent(newContent);
        postRepository.update(post.get());
        return new ResponseEnity<>(post.get(), true, "Post updated successfully");
    }

    public ResponseEnity<Boolean> deletePost(User user, int postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post == null) {
            return new ResponseEnity<>(false, false, "Post not found");
        }

        if (post.get().getUserId() != user.getId()) {
            return new ResponseEnity<>(false, false, "You can only delete your own posts");
        }

        postRepository.deleteById(postId);
        return new ResponseEnity<>(true, true, "Post deleted successfully");
    }

    public List<Post> getPostsByUserId(int userId) {
        return postRepository.findByUserId(userId);
    }

    public List<Post> getLimitedPosts() {
        return null;
    }
}
