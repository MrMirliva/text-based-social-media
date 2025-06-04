/**
 * The {@code MACModel} class serves as an abstract base model for entities that require
 * a unique identifier and timestamp tracking for creation and updates.
 * <p>
 * This class implements {@link Cloneable} to allow object cloning and {@link Comparable}
 * to provide natural ordering based on the {@code id} field.
 * </p>
 *
 * <p>
 * Fields:
 * <ul>
 *   <li>{@code id}: A unique identifier for the model, annotated with {@link memento.anatation.Unique}.</li>
 *   <li>{@code createdAt}: The timestamp when the model was created.</li>
 *   <li>{@code updatedAt}: The timestamp when the model was last updated.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Subclasses should extend this class to inherit common model properties and behaviors.
 * </p>
 * 
 * @author Mirliva (Abdullah Gündüz)
 * @version 1.0
 * @since 2025-06-04
 */
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
