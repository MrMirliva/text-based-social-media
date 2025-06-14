package userInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import models.Post;
import models.User;
import responses.ProfileResponse;
import responses.ResponseEntity;
import service.PostService;
import service.UserService;
import service.AuthService;
import service.LikeService;

public class Menu {
    private final Profile profile;
    private final UserService userService;
    private final PostService postService;
    private final LikeService likeService;
    private final HashMap<String, String> cookieHashMap;
    private final AuthService authService;
    private boolean willExit = false;
    private boolean systemExit = false;

    public Menu(HashMap<String, String> cookieHashMap, UserService userService, PostService postService,
            LikeService likeService, Profile profile, AuthService authService) {
        this.cookieHashMap = cookieHashMap;
        this.userService = userService;
        this.postService = postService;
        this.likeService = likeService;
        this.profile = profile;
        this.authService = authService;
    }

    public void showMenu() {
        willExit = profile.getLoggedOutBoolean();

        if (!willExit) {
            System.out.println("1. See Profile");
            System.out.println("2. Create Post");
            System.out.println("3. Post Things");
            System.out.println("4. exit System");
            Scanner scanner = new Scanner(System.in);
            int choice;
            while (true) {
                System.out.print("Enter your choice: ");
                String input = scanner.nextLine();
                try {
                    choice = Integer.parseInt(input);
                    if (choice < 1 || choice > 4) {
                        System.out.println("Invalid choice. Please enter 1, 2, 3.or 4.");
                    } else {
                        break; // geçerli seçim, çık döngüden
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number (1-4).");
                }
            }
            ResponseEntity<User> user = authService.getAuthenticatedUser(cookieHashMap);

            switch (choice) {
                case 1:
                    seeProfile(user.getData());
                    showMenu();
                    break;
                case 2:
                    System.out.println("Enter your post: ");
                    Scanner scannerP = new Scanner(System.in);
                    String post = scannerP.nextLine();

                    ResponseEntity<Post> status = postService.createPost(user.getData(), post);
                    if (status.isOk()) {
                        System.out.println("Post created successfully: " + status.getData().getContent());
                    } else
                        System.out.println("Failed to create post: " + status.getMessage());

                    showMenu();

                    break;
                case 3:
                    postThings();
                    showMenu();
                    break;
                case 4:
                    systemExit = true;
                    return;
                default:
                    System.out.println("Invalid choice.");
                    showMenu();
            }
        } else {
            profile.setLoggedOutBoolean(false);
            return;
        }
    }

    public void timeLine() {
        postService.getLimitedPosts();
        ResponseEntity<List<Post>> status = postService.getLimitedPosts();

        if (status.isOk()) {
            List<Post> posts = status.getData();
            for (Post post : posts) {
                HashMap<String, String> postUser = new HashMap<>();
                postUser.put("userId", String.valueOf(post.getUserId()));
                ResponseEntity<User> user = authService.getAuthenticatedUser(postUser);
                ResponseEntity<Integer> likeCount = likeService.getLikeCount(post.getId());
                System.out.println();
                System.out.println("Full name: " + user.getData().getFullName() + " User ID: " + post.getUserId() + " Username: "
                        + (user.isOk() ? user.getData().getUsername() : "Unknown User"));
                System.out.println("Post ID: " + post.getId());
                System.out.println("Content: " + post.getContent());
                System.out
                        .println("Likes: " + (likeCount.isOk() ? likeCount.getData() : "Error retrieving like count"));
                System.out.println();
                System.out.println("Posted at: " + post.getCreatedAt());

                System.out.println("--------------------------------------------------");
            }
        } else {
            System.out.println("Failed to retrieve posts: " + status.getMessage());
        }
    }

    public void seeProfile(User user) {
        ResponseEntity<ProfileResponse> response = userService.viewProfile(user);
        if (response.isOk()) {
            ProfileResponse profileResponse = response.getData();
            System.out.println("User ID: " + profileResponse.getUserId());
            System.out.println("Username: " + profileResponse.getUsername());
            System.out.println("Full Name: " + profileResponse.getFullName());

            profile.profileMenu();

        } else {
            System.out.println("Failed to retrieve profile: " + response.getMessage());
        }
        ;

    }

    public void postThings() {
        System.out.println("1. see timeline posts");
        System.out.println("2. like/unlike posts");
        System.out.println("3. see posts by User ID");
        System.out.println("4. Back to Main Menu");
        Scanner scanner = new Scanner(System.in);
        int choice;
        while (true) {
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > 4) {
                    System.out.println("Invalid choice. Please enter 1, 2, 3.or 4.");
                } else {
                    break; // geçerli seçim, çık döngüden
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number (1-4).");
            }
        }
        ResponseEntity<User> user = authService.getAuthenticatedUser(cookieHashMap);
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
                System.out.println("Enter User ID to see their posts: ");
                Scanner scannerP = new Scanner(System.in);
                int userId = scannerP.nextInt();
                ResponseEntity<List<Post>> status = postService.getPostsByUserId(userId);
                if (status.isOk()) {
                    List<Post> posts = status.getData();
                    if (posts.isEmpty()) {
                        System.out.println("No posts found for User ID: " + userId);
                    } else {
                        for (Post post : posts) {
                            HashMap<String, String> postUser = new HashMap<>();
                            postUser.put("userId", String.valueOf(post.getUserId()));
                            ResponseEntity<User> postUserResponse = authService.getAuthenticatedUser(postUser);
                            ResponseEntity<Integer> likeCount = likeService.getLikeCount(post.getId());

                            System.out.println("Posted by User ID: " + post.getUserId() + " Username: "
                                    + (postUserResponse.isOk() ? postUserResponse.getData().getUsername()
                                            : "Unknown User"));
                            System.out.println("Post ID: " + post.getId());
                            System.out.println("Content: " + post.getContent());
                            System.out.println("Likes: "
                                    + (likeCount.isOk() ? likeCount.getData() : "Error retrieving like count"));
                            System.out.println();
                            System.out.println("Posted at: " + post.getCreatedAt());
                            System.out.println("--------------------------------------------------");
                        }
                    }
                } else {
                    System.out.println(
                            "Failed to retrieve posts for User ID: " + userId + ". Error: " + status.getMessage());
                }
                break;
            case 4:
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
        ResponseEntity<Boolean> status = likeService.likePost(user, postId);
        ResponseEntity<Integer> likeCountStatus = likeService.getLikeCount(postId);
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
        ResponseEntity<Boolean> status = likeService.unlikePost(user, postId);
        ResponseEntity<Integer> likeCountStatus = likeService.getLikeCount(postId);
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
        int choice;
        while (true) {
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > 2) {
                    System.out.println("Invalid choice. Please enter 1, or 2.");
                } else {
                    break; // geçerli seçim, çık döngüden
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number (1-2).");
            }
        }
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

    public boolean isSystemExit() {
        return systemExit;
    }

    public void setWillExit(boolean willExit) {
        this.willExit = willExit;
    }

    public boolean isWillExit() {
        return willExit;
    }
}
