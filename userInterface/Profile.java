package userInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.lang.model.util.ElementScanner14;

import models.Post;
import models.User;
import responses.ProfileResponse;
import responses.ResponseEnity;
import service.PostService;
import service.AuthService;
import service.UserService;
import service.FollowService;

public class Profile {

    private final UserService userService;
    private final PostService postService;
    private final FollowService followService;
    private final AuthService authService;
    private final HashMap<String,String> cookieHashMap;
    private Boolean loggedOutBoolean = false;

    public Profile(HashMap<String,String> cookiHashMap,
                    UserService userService, 
                    PostService postService,
                    FollowService followService, 
                    AuthService authService
        ) {
        this.userService = userService;
        this.postService = postService;
        this.followService = followService;
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
        ResponseEnity<ProfileResponse> profileResponseEntity;
        ResponseEnity<User> profileSettings;

        System.out.println("1. Change UserName");
        System.out.println("2. Change Password");
        System.out.println("3. See Posts");
        System.out.println("4. See friends count");
        System.out.println("5. follow/unfollow");
        System.out.println("6. Back to main menu");
        System.out.println("7. Log Out");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt(); 
        switch (choice) {
            case 1:
                System.out.println("Enter new UserName: ");
                String newUserName = scanner.next();
                profileSettings = userService.updateUsername(authUser, newUserName);
                if (profileSettings.isError()) {
                    System.out.println("Failed to change UserName: " + profileSettings.getMessage());
                    profileMenu();
                } else{
                    System.out.println("UserName changed to: " + newUserName);
                    logOut();
                    return;
                }
                break;
            case 2:
                System.out.println("Enter new Password: ");
                String newPassword = scanner.next();
                profileSettings = userService.updatePassword(authUser, newPassword);
                if (profileSettings.isError()) {
                    System.out.println("Failed to change Password: " + profileSettings.getMessage());
                    profileMenu();
                }else{
                    System.out.println("Password changed successfully.");
                    logOut();
                    return;
                }

                break;
            case 4:
                profileResponseEntity = userService.viewProfile(authUser);
                if (profileResponseEntity.isOk()) {
                    ProfileResponse profileResponse = profileResponseEntity.getData();
                    System.out.println("Number of Followers: " + profileResponse.getNumOfFollowers());
                } else {
                    System.out.println("Failed to retrieve profile: " + profileResponseEntity.getMessage());
                }
                //seeFallowing 
                profileMenu();
                break;
            case 3:
                profileResponseEntity = userService.viewProfile(authUser);
                if (profileResponseEntity.isOk()) {
                    ProfileResponse profileResponse = profileResponseEntity.getData();
                    System.out.println("Posts:");
                    for (Post post : profileResponse.getPosts()) {
                        System.out.println(" - Post ID: " + post.getId() + ", Content: " + post.getContent());
                        System.out.println("Posted by User ID: " + post.getUserId());
                        System.out.println("Posted at: " + post.getCreatedAt());
                        System.out.println("--------------------------------------------------");
                    }
                } else {
                    System.out.println("Failed to retrieve profile: " + profileResponseEntity.getMessage());}
                profileMenu();    
                break;
            case 5:
                followThings();
                profileMenu();
                return; // or menu.showMenu();  
            case 7:
                logOut();
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

        public boolean logOut() {
        ResponseEnity<?> response = authService.logout(cookieHashMap);
        if (response.isOk()) {
            System.out.println("You have been logged out successfully.");
        } else {
            System.out.println("Failed to log out: " + response.getMessage());
        }
        loggedOutBoolean = true;
        return false;
    }


        public Boolean getLoggedOutBoolean() {
            return loggedOutBoolean;
        }
public void unfollow(String username) {
    /*ResponseEnity<User> response = followService.follow(null, 0);
    User targetUser = userRepository.findByUsername(username);
    if (targetUser == null) {
        System.out.println("User not found: " + username);
        return;
    }

    ResponseEnity<?> response = fallowService.unfollow(currentUser, targetUser.getId());
    if (response.isOk()) {
        System.out.println("Unfollowed user: " + targetUser.getUsername());
    } else {
        System.out.println("Failed to unfollow user: " + response.getMessage());
    }*/
}

public void follow(String username) {
    /*User targetUser = userRepository.findByUsername(username);
    if (targetUser == null) {
        System.out.println("User not found: " + username);
        return;
    }

    ResponseEnity<Boolean> response = fallowService.follow(currentUser, targetUser.getId());
    if (response.isOk()) {
        System.out.println("Followed user: " + targetUser.getUsername());
    } else {
        System.out.println("Failed to follow user: " + response.getMessage());
    }*/
}

public void followThings() {
    System.out.println("1: follow ");
    System.out.println("2: unfollow ");
    System.out.println("3: Back to profile menu");
    Scanner scanner = new Scanner(System.in);
    int choice = scanner.nextInt();
    scanner.nextLine(); // Consume newline

    System.out.println("Enter the username: ");
    String username = scanner.nextLine();

    if (choice == 1) {
        follow(username);
    } else if (choice == 2) {
        unfollow(username);}
        else if (choice == 3) {
        System.out.println("Exiting to profile menu.");
        profileMenu();
    } else {
        System.out.println("Invalid choice.");
        followThings();
    }
}


    
}
