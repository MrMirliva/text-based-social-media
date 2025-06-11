package repositories;

import java.util.List;
import java.util.stream.Collectors;

import memento.core.MACRepository;
import models.Post;

/**
 * PostRepository is a repository class responsible for managing Post entities.
 * It extends the generic MACRepository to provide CRUD operations for Post objects.
 * This repository includes custom methods for querying posts, such as retrieving
 * all posts associated with a specific user.
 * 
 * @see memento.core.MACRepository
 * @see models.Post
 * 
 * @author Mirliva (Abdullah Gündüz)
 * @version 1.1
 * @since 2025-06-04
 */
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
            .collect(Collectors.toList());
        return posts;
    }

    /**
     * Retrieves the maximum ID of all posts in the repository.
     * This method scans through all posts and returns the highest ID found.
     * If no posts are present, it returns 0.
     * @return the maximum post ID, or 0 if no posts exist
     */
    public int getMaxId() {
        return getAll().stream()
            .mapToInt(Post::getId)
            .max()
            .orElse(0);
    }

    /**
     * Retrieves a list of posts by their IDs.
     * This method filters the posts in the repository
     * and returns a list of posts that match the specified IDs.
     * @param ids a list of post IDs to retrieve
     * @return a list of posts that match the specified IDs
     */
    public List<Post> getByIds(List<Integer> ids) {
        return getAll().stream()
            .filter(post -> post != null && ids.contains(post.getId()))
            .collect(Collectors.toList());
    }
   

}
