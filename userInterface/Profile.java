package userInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

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

    public String getPassword() {
        return password;
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
    public void profileMenu() {
        System.out.println("1. Change UserName");
        System.out.println("2. Change Password");
        System.out.println("3. See Fallowers");
        System.out.println("4. See Fallowing");
        System.out.println("5. See Posts");
        System.out.println("6. Log Out");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt(); 
        switch (choice) {
            case 1:
                System.out.println("Enter new UserName: ");
                String newUserName = scanner.next();
                changeUserName(newUserName);
                System.out.println("UserName changed to: " + newUserName);
                break;
            case 2:
                System.out.println("Enter new Password: ");
                String newPassword = scanner.next();
                changePassword(newPassword);
                System.out.println("Password changed successfully.");
                break;
            case 3:
                seeFallowers();
                break;
            case 4:
                // seeFallowing(); // Implement this method if needed
                break;
            case 5:
                seePosts();
                break;
            case 6:
                logOut();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
}
