package userInterface;

import java.util.ArrayList;
import java.util.HashMap;

public class Profile {
    private String userName;
    private String password;
    private ArrayList<String> fallowers;
    private ArrayList<String> posts;
    private HashMap cookieHashMap;//????

    
    public Profile() {
    }
    public Profile(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public ArrayList<String> getFallowers() {
        return fallowers;
    }
    public void setFallowers(ArrayList<String> fallowers) {
        this.fallowers = fallowers;
    }
    public ArrayList<String> getPosts() {
        return posts;
    }
    public void setPosts(ArrayList<String> posts) {
        this.posts = posts;
    }

    
    public void changeUserName(String userName) {
        this.userName = userName;
    }
    public void changePassword(String password) {
        this.password = password;
    }
    public void seeFallowers() {
        for (int i = 0; i < fallowers.size(); i++) {
            System.out.println(fallowers.get(i));
        }
    }
    public void seePosts() {
        for (int i = 0; i < posts.size(); i++) {
            System.out.println(posts.get(i));
        }
    }
    public void logOut() {
        System.out.println("You have been logged out");
    }
    
}
