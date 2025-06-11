package userInterface;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import models.Post;
import models.User;
import responses.ProfileResponse;
import responses.ResponseEnity;
import service.PostService;
import service.UserService;
import service.AuthService;
import service.LikeService;


public class Menu {
    private final Profile profile;
    private final UserService userService;
    private final PostService postService;
    private final LikeService likeService;
    private final HashMap<String,String> cookieHashMap;
    private final AuthService authService;
    private boolean willExit = true;

    public Menu(HashMap<String,String> cookieHashMap,UserService userService, PostService postService, LikeService likeService, Profile profile, AuthService authService) {
        this.cookieHashMap = cookieHashMap;
        this.userService = userService;
        this.postService = postService;
        this.likeService = likeService;
        this.profile = profile;
        this.authService = authService;
    }

    public void showMenu() {
        willExit = profile.getLoggedOutBoolean();
        if(!willExit){
                    System.out.println("1. See Profile");
        System.out.println("2. Create Post");
        System.out.println("3. Post Things");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        ResponseEnity<User> user = authService.getAuthenticatedUser(cookieHashMap);
        switch (choice) {
            case 1:
                seeProfile(user.getData());
                showMenu();
                break;
            case 2:
                System.out.println("Enter your post: ");
                Scanner scannerP = new Scanner(System.in);
                String post = scannerP.nextLine();
                
                
                ResponseEnity<Post> status = postService.createPost(user.getData(), post);
                if (status.isOk()) {
                    System.out.println("Post created successfully: " + status.getData().getContent());
                }
                 else
                    System.out.println("Failed to create post: " + status.getMessage());

                showMenu();

                break;
            case 3:
                postThings();
                showMenu();
                break;
            case 5:
                //refreshTimeLine();
                break;
            default:
                System.out.println("Invalid choice.");
                showMenu();
        }
        }else{
            return;
        }
    }

    public void timeLine() {
        postService.getLimitedPosts();
        ResponseEnity<List<Post>> status = postService.getLimitedPosts();
        
        if (status.isOk()) {
            List<Post> posts = status.getData();
            for (Post post : posts) {
                HashMap<String, String> postUser = new HashMap<>();
                postUser.put("userId", String.valueOf(post.getUserId()));                
                ResponseEnity<User> user = authService.getAuthenticatedUser(postUser);
                ResponseEnity<Integer> likeCount = likeService.getLikeCount(post.getId());

                System.out.println("Posted by User ID: " + post.getUserId() + " Username: " + (user.isOk() ? user.getData().getUsername() : "Unknown User"));
                System.out.println("Post ID: " + post.getId());
                System.out.println("Content: " + post.getContent());
                System.out.println("Likes: " + (likeCount.isOk() ? likeCount.getData() : "Error retrieving like count"));
                System.out.println();
                System.out.println("Posted at: " + post.getCreatedAt());

                System.out.println("--------------------------------------------------");
            }
        } else {
            System.out.println("Failed to retrieve posts: " + status.getMessage());
        }
    }
    public void seeProfile(User user) {
        ResponseEnity<ProfileResponse> response = userService.viewProfile(user);
        if (response.isOk()) {
            ProfileResponse profileResponse = response.getData();
            System.out.println("User ID: " + profileResponse.getUserId());
            System.out.println("Username: " + profileResponse.getUsername());
            System.out.println("Full Name: " + profileResponse.getFullName());
            
            profile.profileMenu();

        } else {
            System.out.println("Failed to retrieve profile: " + response.getMessage());
    };
        
    }
    public void seeFallowingPosts() {
    }

    public void postThings() {
        System.out.println("1. see timeline posts");
        System.out.println("2. like/unlike posts");
        System.out.println("3. see posts by User ID");       
        System.out.println("3. see posts by Fallowing");
        System.out.println("4. Back to Main Menu");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        ResponseEnity<User> user = authService.getAuthenticatedUser(cookieHashMap);
        switch (choice) {
            case 1:
                timeLine();
                postThings();
                break;
            case 2:
                likeThings(user.getData());
                postThings();
                break;
            case 3:
                System.out.println("Enter User ID to see posts: ");
                Scanner scannerId = new Scanner(System.in);
                int userId = scannerId.nextInt();
                ResponseEnity<List<Post>> status = postService.getPostsByUserId(userId);
                if (status.isOk()) {
                    List<Post> posts = status.getData();
                    if (posts.isEmpty()) {
                        System.out.println("No posts found for User ID: " + userId);
                    } else {
                        for (Post post : posts) {
                            System.out.println("Post ID: " + post.getId() + ", Content: " + post.getContent());
                            System.out.println("Likes: " + likeService.getLikeCount(post.getId()));
                        }
                    }
                } else {
                    System.out.println("Failed to retrieve posts: " + status.getMessage());
                }
                postThings();
                break;
            case 4:
                //seeFallowingPosts();
                //postThings();
                break;
            case 5:
                showMenu();
                break;
            default:
                System.out.println("Invalid choice.");
                postThings();
        }
    }
    public void likePost(User user) {
        System.out.println("Enter Post ID to like: ");
        Scanner scanner = new Scanner(System.in);
        int postId = scanner.nextInt();
        ResponseEnity<Boolean> status = likeService.likePost(user, postId);
                ResponseEnity<Integer> likeCountStatus = likeService.getLikeCount(postId);
        if (!likeCountStatus.isOk()) {
            System.out.println("Failed to retrieve like count: " + likeCountStatus.getMessage());
            return;
        }
        if (status.isOk()) {
            System.out.println("Post liked successfully. Like count: " + likeCountStatus.getData());
        } else {
            System.out.println("Failed to like post: " + status.getMessage());
        }
    }
    public void unlikePost(User user) {
        System.out.println("Enter Post ID to unlike: ");
        Scanner scanner = new Scanner(System.in);
        int postId = scanner.nextInt();
        ResponseEnity<Boolean> status = likeService.unlikePost(user, postId);
        ResponseEnity<Integer> likeCountStatus = likeService.getLikeCount(postId);
        if (!likeCountStatus.isOk()) {
            System.out.println("Failed to retrieve like count: " + likeCountStatus.getMessage());
            return;
        }
        if (status.isOk()) {
            System.out.println("Post unliked successfully. Like count: " + likeCountStatus.getData());
        } else {
            System.out.println("Failed to unlike post: " + status.getMessage());
        }
    }
    public void likeThings(User user) {
        System.out.println("1. Like a Post");
        System.out.println("2. Unlike a Post");
        System.out.println("3. Back to Main Menu");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                likePost(user);
                break;
            case 2:
                unlikePost(user);
                break;
            case 3:
                showMenu();
                break;
            default:
                System.out.println("Invalid choice.");
                likeThings(user);
        }
    }

    public boolean isWillExit() {
        return willExit;
    }    
}
   /* public void refreshTimeLine() {
        ArrayList<String> posts = profile.getPosts();
        for (int i = 0; i < posts.size(); i++) {
            System.out.println(posts.get(i));
        }
    }*/