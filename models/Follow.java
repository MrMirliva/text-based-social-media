package models;

import memento.core.MACModel;

public class Follow extends MACModel {
    private int followerId;
    private int followingId;

    // Default constructor
    public Follow(int followerId, int followingId) {
        this.followerId = followerId;
        this.followingId = followingId;
    }

    public Follow() {
        // Default constructor for serialization/deserialization
    }

    public int getFollowerId() {
        return followerId;
    }

    public void setFollowerId(int followerId) {
        this.followerId = followerId;
    }

    public int getFollowingId() {
        return followingId;
    }

    public void setFollowingId(int followingId) {
        this.followingId = followingId;
    }
    
}
