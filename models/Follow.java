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
    
    @Override
    public Follow clone() {
        Follow clonedFollow = new Follow();
        clonedFollow.setId(this.getId());
        clonedFollow.setCreatedAt(this.getCreatedAt());
        clonedFollow.setUpdatedAt(this.getUpdatedAt());
        clonedFollow.setFollowerId(this.getFollowerId());
        clonedFollow.setFollowingId(this.getFollowingId());
        return clonedFollow;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Follow)) return false;
        Follow follow = (Follow) obj;
        return id == follow.id &&
               followerId == follow.followerId &&
               followingId == follow.followingId;
    }
}
