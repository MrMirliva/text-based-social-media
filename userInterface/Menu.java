package userInterface;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import service.PostService;
import service.UserService;
import service.LikeService;


public class Menu {
    Profile profile;
    private UserService userService;
    private PostService postService;
    private LikeService likeService;

    

    public Menu(Profile profile) {
        this.profile = profile;
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
                seeProfile();
                break;
            case 2:
                System.out.println("Enter your post: ");
                String post = scanner.nextLine();
                createPost(post);
                break;
            case 3:
                seePosts();
                break;
            case 4:
                profile.seeFallowers();
                break;
            case 5:
                refreshTimeLine();
                break;
            case 6:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    public void seePosts() {
        ArrayList<String> posts = profile.getPosts();
        for (int i = 0; i < posts.size(); i++) {
            System.out.println(posts.get(i));
        }
    }
    public void seeProfile() {
        System.out.println("UserName: " + profile.getUserName());
        System.out.println("Password: " + profile.getPassword());
        System.out.println("Fallowers: " + profile.getFallowers());
        System.out.println("Posts: " + profile.getPosts());
    }
    public void createPost(String post) {
        ArrayList<String> posts = profile.getPosts();
        posts.add(post);
        profile.setPosts(posts);
    }
    public void seeFallowingPosts() {
    }
    public void refreshTimeLine() {
        ArrayList<String> posts = profile.getPosts();
        for (int i = 0; i < posts.size(); i++) {
            System.out.println(posts.get(i));
        }
    }
    
}
