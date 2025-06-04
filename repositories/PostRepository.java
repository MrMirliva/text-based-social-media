/**
 * PostRepository is a repository class responsible for managing Post entities.
 * It extends the generic MACRepository to provide CRUD operations for Post objects.
 * This repository includes custom methods for querying posts, such as retrieving
 * all posts associated with a specific user.
 * 
 * @author Mirliva (Abdullah Gündüz)
 * @version 1.0
 * @since 2025-06-04
 */
package repositories;

import java.util.List;

import memento.core.MACRepository;
import models.Post;

public class PostRepository extends MACRepository<Post> {

    public PostRepository() {
        super(Post.class);
    }

    /**
     * Finds all posts by a specific user ID.
     * This method filters the posts in the repository
     * and returns a list of posts that belong to the specified user.
     * @param userId the ID of the user whose posts are to be retrieved
     * @return a list of posts associated with the specified user ID
     */
    public List<Post> findByUserId(int userId) {
        List<Post> posts = getAll().stream()
            .filter(post -> post != null && post.getUserId() == userId)
            .collect(java.util.stream.Collectors.toList());
        return posts;
    }
    
}
