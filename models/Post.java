package models;

import memento.anatation.Default;
import memento.core.MACModel;

public class Post extends MACModel {
    @Default(value = "Hello World!")
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

    @Override
    public Post clone() {
        Post clonedPost = new Post();
        clonedPost.setId(this.getId());
        clonedPost.setCreatedAt(this.getCreatedAt());
        clonedPost.setUpdatedAt(this.getUpdatedAt());
        clonedPost.setContent(this.getContent());
        clonedPost.setUserId(this.getUserId());
        return clonedPost;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Post)) return false;
        Post post = (Post) obj;
        return id == post.id &&
               userId == post.userId &&
               content.equals(post.content);
    }
}