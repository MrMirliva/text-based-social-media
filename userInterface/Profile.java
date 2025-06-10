package userInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import models.User;
import responses.ResponseEnity;
import service.PostService;
import service.AuthService;
import service.UserService;
import service.FollowService;

public class Profile {

    private final UserService userService;
    private final PostService postService;
    private final FollowService fallowService;
    private final AuthService authService;
    private final HashMap<String,String> cookieHashMap;

    public Profile(HashMap<String,String> cookiHashMap,
                    UserService userService, 
                    PostService postService,
                    FollowService fallowService, 
                    AuthService authService
        ) {
        this.userService = userService;
        this.postService = postService;
        this.fallowService = fallowService;
        this.authService = authService;
        this.cookieHashMap = cookiHashMap;
    }


   /*  public ArrayList<String> getFallowers() {
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
    }*/
    public void profileMenu() {
        // Use a visible method that returns ResponseEntity<User>
        ResponseEnity<User> response = authService.getAuthenticatedUser(cookieHashMap);
        User authUser = null;
        if (response.isOk()) {
            authUser = response.getData();
        } else {
            //TODO: Handle the case where the user is not authenticated
            System.out.println("You are not authenticated.");
            return;
        }

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
                userService.updateUsername(authUser, newUserName);
                System.out.println("UserName changed to: " + newUserName);
                break;
            case 2:
                System.out.println("Enter new Password: ");
                String newPassword = scanner.next();
                userService.updatePassword(authUser, newPassword);
                System.out.println("Password changed successfully.");
                break;
            case 3:
                int followerCount = fallowService.getFollowerCount(authUser.getId());
                System.out.println("You have " + followerCount + " followers.");
                break;
            case 4:
                // seeFallowing(); // Implement this method if needed
                break;
            case 5:
                postService.getPostsByUserId(authUser.getId()).forEach(post -> {
                    System.out.println("Post ID: " + post.getId());
                    System.out.println("Content: " + post.getContent());
                });
                break;
            case 6:
                authService.logout(cookieHashMap);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
}
