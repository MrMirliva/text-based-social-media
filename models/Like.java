package models;

import memento.core.MACModel;

public class Like extends MACModel {
    private int postId;
    private int userId;

    // Default constructor
    public Like(int postId, int userId) {
        this.postId = postId;
        this.userId = userId;
    }

    public Like() {
        // Default constructor for serialization/deserialization
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
}
