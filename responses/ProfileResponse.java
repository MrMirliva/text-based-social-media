/**
 * Represents the response object for a user's profile in the social media application.
 * <p>
 * This class encapsulates profile information such as user ID, username, full name,
 * number of followers, and a list of the user's posts. It is typically used to transfer
 * profile data between the backend and frontend or for API responses.
 * </p>
 * 
 * @see models.Post
 * @author Mirliva (Abdullah Gündüz)
 * @version 1.0
 */
package responses;

import java.util.List;

import models.Post;

public class ProfileResponse {
    private int userId;
    private String username;
    private String fullName;
    private int numOfFollowers;
    private List<Post> posts;

    public ProfileResponse(int userId, String username, String fullName, int numOfFollowers, List<Post> posts) {
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.numOfFollowers = numOfFollowers;
        this.posts = posts;
    }
    public ProfileResponse() {
        // Default constructor for serialization/deserialization
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getNumOfFollowers() {
        return numOfFollowers;
    }
    public void setNumOfFollowers(int numOfFollowers) {
        this.numOfFollowers = numOfFollowers;
    }

    public List<Post> getPosts() {
        return posts;
    }
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
