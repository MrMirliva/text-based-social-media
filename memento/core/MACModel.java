package memento.core;

import java.time.LocalDateTime;

import memento.anatation.Unique;

public abstract class MACModel implements Cloneable, Comparable<MACModel> {
    @Unique
    protected int id;

    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public MACModel clone() throws CloneNotSupportedException {
        return (MACModel) super.clone();
    }

    @Override
    public int compareTo(MACModel other) {
        return Integer.compare(this.id, other.id);
    }
}
