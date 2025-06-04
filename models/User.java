package models;

import memento.anatation.Encrypted;
import memento.anatation.Unique;
import memento.core.MACModel;

public class User extends MACModel{
    @Unique
    private String username;
    private String fullName;
    @Encrypted
    private String password;

    // Default constructor
    public User(String fullName, String username, String password) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
    }

    public User() {
        // Default constructor for serialization/deserialization
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

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public User clone() {
        try {
            return (User) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User)) return false;
        User user = (User) obj;
        return id == user.id &&
               username.equals(user.username) &&
               fullName.equals(user.fullName) &&
               password.equals(user.password);
    }
}
