package models;

import memento.core.MACModel;

public class Post extends MACModel {
    private String content;
    private int userId;
    
    // Default constructor
    public Post(String content, int userId) {
        this.content = content;
        this.userId = userId;
    }
    public Post() {
        // Default constructor for serialization/deserialization
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
}