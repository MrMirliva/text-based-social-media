package userInterface;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;

import models.Post;
import models.User;
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

    public Menu(HashMap<String,String> cookieHashMap,UserService userService, PostService postService, LikeService likeService, Profile profile, AuthService authService) {
        this.cookieHashMap = cookieHashMap;
        this.userService = userService;
        this.postService = postService;
        this.likeService = likeService;
        this.profile = profile;
        this.authService = authService;
    }

    public void showMenu() {
        System.out.println("1. See Profile");
        System.out.println("2. Create Post");
        System.out.println("3. See Posts");
        System.out.println("4. See Fallowers");
        System.out.println("5. Refresh TimeLine");
        System.out.println("6. Exit");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                seeProfile();;
                break;
            case 2:
                System.out.println("Enter your post: ");
                Scanner scannerP = new Scanner(System.in);
                String post = scannerP.nextLine();
                ResponseEnity<User> user = authService.getAuthenticatedUser(cookieHashMap);
                
                 ResponseEnity<Post> status = postService.createPost(user.getData(), post);
                if (status.isOk()) {
                    System.out.println("Post created successfully: " + status.getData().getContent());
                }
                 else
                    System.out.println("Failed to create post: " + status.getMessage());

                showMenu();

                break;
            case 3:
                seePosts();
                showMenu();
                break;
            case 4:
                //profile.seeFallowers();
                break;
            case 5:
                refreshTimeLine();
                break;
            case 6:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    public void seePosts() {
        postService.getLimitedPosts();
    }
    public void seeProfile() {
        userService.viewProfile(null);
    }
    public void seeFallowingPosts() {
    }
    public void refreshTimeLine() {
       /*  ArrayList<String> posts = profile.getPosts();
        for (int i = 0; i < posts.size(); i++) {
            System.out.println(posts.get(i));
        }*/
    }
    public void seePostsMenu() {
        System.out.println("1. see a post by Post ID");
        System.out.println("2. see posts by User ID");
        System.out.println("3. see your posts");
        System.out.println("4. see timeline posts");
        System.out.println("5. see posts by Fallowing");
        System.out.println("6. Back to Main Menu");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                System.out.println("Enter Post ID: ");
                int postId = scanner.nextInt();
               // postService.getPostById(postId);
                break;
            case 2:
                System.out.println("Enter User ID: ");
                int userId = scanner.nextInt();
                postService.getPostsByUserId(userId);
                break;
            case 3:
                //postService.getPostsByUserId(profile.getUser().getId());
                break;
            case 4:
                postService.getLimitedPosts();
                break;
            case 5:
                //profile.seeFallowingPosts();
                break;
            case 6:
                showMenu();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
}
