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

    @Override
    public Like clone() {
        Like clonedLike = new Like();
        clonedLike.setId(this.getId());
        clonedLike.setCreatedAt(this.getCreatedAt());
        clonedLike.setUpdatedAt(this.getUpdatedAt());
        clonedLike.setPostId(this.getPostId());
        clonedLike.setUserId(this.getUserId());
        return clonedLike;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Like)) return false;
        Like like = (Like) obj;
        return id == like.id &&
               postId == like.postId &&
               userId == like.userId;
    }
    
}
