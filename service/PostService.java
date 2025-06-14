package service;

import java.util.List;
import java.util.Optional;

import models.User;
import models.Post;
import repositories.PostRepository;
import responses.ResponseEntity;
import helper.RandomNumberGenerator;

/**
 * PostService is a service class responsible for managing post-related operations
 * in a text-based social media application. It provides methods for creating, editing,
 * deleting, and retrieving posts, as well as fetching a limited number of random posts.
 * The service interacts with the PostRepository to persist and query post data.
 * <p>
 * Key features include:
 * <ul>
 *   <li>Creating new posts with validation to prevent use of reserved delimiters</li>
 *   <li>Editing and deleting posts with ownership checks to ensure only the post owner can modify or remove their posts</li>
 *   <li>Retrieving all posts made by a specific user</li>
 *   <li>Fetching up to 30 random posts for display or discovery features</li>
 *   <li>Returning informative response messages and status for each operation</li>
 * </ul>
 * <p>
 * This class is intended to be used as a core component in applications that require
 * social networking features, such as posting, editing, deleting, and browsing posts.
 *
 * @author Muhammed Yasin Eroğlu
 * @version 1.0
 * @since 2025-06-14
 */
public class PostService {
    private final PostRepository postRepository;
    private final String DELIMINATOR = "<-!->";

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * This function creates a new post for a user.
     *
     * @param user The user who is creating the post.
     * @param content The content of the post.
     * @return Returns a ResponseEntity containing the created post and a success message if the creation is successful.
     *         If the content contains the delimiter or if the user or content is invalid, returns a ResponseEntity with an error message.
     */
    public ResponseEntity<Post> createPost(User user, String content) {

        if(content.contains(DELIMINATOR)) {
            return new ResponseEntity<>(null, false, "Content cannot contain the delimiter: " + DELIMINATOR);
        }

        if (user == null || content == null || content.isEmpty()) {
            return new ResponseEntity<>(null, false, "Invalid user or content");
        }

        Post newPost = new Post( content, user.getId());
        postRepository.add(newPost);
        return new ResponseEntity<>(newPost, true, "Post created successfully");
    }

    /**
      * This function allows a user to edit their post.
      *
      * @param user The user who is editing the post.
      * @param postId The ID of the post to be edited.
      * @param newContent The new content for the post.
      * @return Returns a ResponseEntity containing the updated post and a success message if the edit is successful.
      *         If the post is not found, or if the user does not own the post, or if the new content contains the delimiter, returns a ResponseEntity with an error message.
      */
    public ResponseEntity<Post> editPost(User user, int postId, String newContent) {
        Optional<Post> post = postRepository.findById(postId);
        if (post == null) {
            return new ResponseEntity<>(null, false, "Post not found");
        }

        if (post.get().getUserId() != user.getId()) {
            return new ResponseEntity<>(null, false, "You can only edit your own posts");
        }

        if(newContent.contains(DELIMINATOR)) {
            return new ResponseEntity<>(null, false, "Content cannot contain the delimiter: " + DELIMINATOR);
        }
        post.get().setContent(newContent);
        postRepository.update(post.get());
        return new ResponseEntity<>(post.get(), true, "Post updated successfully");
    }

    /**
      * This function allows a user to delete their post.
      *
      * @param user The user who is deleting the post.
      * @param postId The ID of the post to be deleted.
      * @return Returns a ResponseEntity indicating success or failure of the delete operation.
      *         If the post is not found or if the user does not own the post, returns a ResponseEntity with an error message.
      */
    public ResponseEntity<Boolean> deletePost(User user, int postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post == null) {
            return new ResponseEntity<>(false, false, "Post not found");
        }

        if (post.get().getUserId() != user.getId()) {
            return new ResponseEntity<>(false, false, "You can only delete your own posts");
        }

        postRepository.deleteById(postId);
        return new ResponseEntity<>(true, true, "Post deleted successfully");
    }

    /**
      * This function retrieves all posts made by a specific user.
      *
      * @param userId The ID of the user whose posts are to be retrieved.
      * @return Returns a list of posts made by the specified user.
      */
    public ResponseEntity<List<Post>> getPostsByUserId(int userId) {
        return new ResponseEntity<>(postRepository.findByUserId(userId), true, "Posts retrieved successfully");
    }

    /**
      * This function retrieves a limited number of random posts from the repository.
      *
      * @return Returns a list of up to 30 random posts.
      *         If there are fewer than 30 posts, it returns all available posts.
      */
    public ResponseEntity<List<Post>> getLimitedPosts() {
        int min = 0;
        int max = postRepository.getMaxId();
        int numberOf = Math.min(30, postRepository.count());
        
        List<Integer> randomIds = RandomNumberGenerator.generateRandomInt(min, max, numberOf, new int[]{});

        return new ResponseEntity<>(postRepository.getByIds(randomIds), true, "Posts retrieved successfully");
    }
}
