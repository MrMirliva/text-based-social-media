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
import service.LikeService;

public class Profile {

    private final UserService userService;
    private final PostService postService;
    private final FollowService followService;
    private final AuthService authService;
    private final LikeService likeService;
    private final HashMap<String,String> cookieHashMap;
    private Boolean loggedOutBoolean = false;

    public Profile(HashMap<String,String> cookiHashMap,
                    UserService userService, 
                    PostService postService,
                    FollowService followService, 
                    AuthService authService,
                    LikeService likeService
        ) {
        this.userService = userService;
        this.postService = postService;
        this.followService = followService;
        this.authService = authService;
        this.likeService = likeService;
        this.cookieHashMap = cookiHashMap;
    }

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
        System.out.println("4. Delete/edit Post");
        System.out.println("5. See friends count");
        System.out.println("6. follow/unfollow");
        System.out.println("7. Back to main menu");
        System.out.println("8. Log Out");
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
            case 5:
                profileResponseEntity = userService.viewProfile(authUser);
                ResponseEnity<Integer> followerCount = followService.getFollowerCount(authUser.getId());


                if (profileResponseEntity.isOk()) {
                    ProfileResponse profileResponse = profileResponseEntity.getData();
                    System.out.println("Number of Followers: " + profileResponse.getNumOfFollowers());
                if (followerCount.isOk()) {
                    System.out.println("Number of Followers: " + followerCount.getData());
                }
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
                    ResponseEnity<User> user = authService.getAuthenticatedUser(cookieHashMap);
                    ResponseEnity<Integer> likeCount = likeService.getLikeCount(profileResponse.getPosts().get(0).getId());

                    for (Post post : profileResponse.getPosts()) {
                        System.out.println("Posted by User ID: " + post.getUserId() + " Username: " + (user.isOk() ? user.getData().getUsername() : "Unknown User"));
                        System.out.println("Post ID: " + post.getId());
                        System.out.println("Content: " + post.getContent());
                        System.out.println("Likes: " + (likeCount.isOk() ? likeCount.getData() : "Error retrieving like count"));
                        System.out.println();
                        System.out.println("Posted at: " + post.getCreatedAt());

                        System.out.println("--------------------------------------------------");
                    }
                    if (profileResponse.getPosts().isEmpty()) {
                        System.out.println("You have no posts yet.");
                    }
                } else {
                    System.out.println("Failed to retrieve profile: " + profileResponseEntity.getMessage());}
                profileMenu();    
                break;
            case 4:
                deleteEditPost();
                profileMenu();
                break;    
            case 6:
                followThings();
                profileMenu();
                break; // or menu.showMenu();  
            case 7:
                System.out.println("Exiting to main menu.");
                System.out.println();
                break; // Exit the profile menu    
            case 8:
                logOut();
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
                profileMenu();
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
public void unfollow() {
     System.out.println("Enter userId to unfollow: ");
     Scanner scanner = new Scanner(System.in);
     int targetUserId = scanner.nextInt();
    ResponseEnity<Boolean> response = followService.unfollow(authService.getAuthenticatedUser(cookieHashMap).getData(), targetUserId);
    if (response.isOk()) {
        System.out.println("Unfollowed user with ID: " + targetUserId);
    } else {
        System.out.println("Failed to unfollow user: " + response.getMessage());
    }
     
}

public void follow() {
     System.out.println("Enter userId to follow: ");
     Scanner scanner = new Scanner(System.in);
     int targetUserId = scanner.nextInt();
    ResponseEnity<Boolean> response = followService.follow(authService.getAuthenticatedUser(cookieHashMap).getData(), targetUserId);
    if (response.isOk()) {
        System.out.println("Followod user with ID: " + targetUserId);
    } else {
        System.out.println("Failed to follow user: " + response.getMessage());
    }
}

public void followThings() {
    System.out.println("1: follow ");
    System.out.println("2: unfollow ");
    System.out.println("3: Back to profile menu");
    Scanner scanner = new Scanner(System.in);
    int choice = scanner.nextInt();
    scanner.nextLine(); // Consume newline


    if (choice == 1) {
        follow();
    } else if (choice == 2) {
        unfollow();}
        else if (choice == 3) {
        System.out.println("Exiting to profile menu.");
        profileMenu();
    } else {
        System.out.println("Invalid choice.");
        followThings();
    }
}
    public void deleteEditPost() {
        System.out.println("1. Edit Post");
        System.out.println("2. Delete Post");
        System.out.println("3. Back to Profile Menu");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        switch (choice) {
            case 1:
                System.out.println("Enter Post ID to edit: ");
                int postIdToEdit = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                System.out.println("Enter new content for the post: ");
                String newContent = scanner.nextLine();
                ResponseEnity<Post> editResponse = postService.editPost(authService.getAuthenticatedUser(cookieHashMap).getData(), postIdToEdit, newContent);
                if (editResponse.isOk()) {
                    System.out.println("Post edited successfully: " + editResponse.getData().getContent());
                } else {
                    System.out.println("Failed to edit post: " + editResponse.getMessage());
                }
                break;
            case 2:
                System.out.println("Enter Post ID to delete: ");
                int postIdToDelete = scanner.nextInt();
                ResponseEnity<Boolean> deleteResponse = postService.deletePost(authService.getAuthenticatedUser(cookieHashMap).getData(), postIdToDelete);
                if (deleteResponse.isOk()) {
                    System.out.println("Post deleted successfully.");
                } else {
                    System.out.println("Failed to delete post: " + deleteResponse.getMessage());
                }
                break;
            case 3:
                profileMenu();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                deleteEditPost();
        }
    }

    

}