import java.util.HashMap;

import repositories.*;
import service.*;
import userInterface.*;

public class Main {

    /**
     * This code is the entry point of a text-based social media application.
     *  It initializes the core components of the application's architecture,
     *  including repositories for users, posts, follows, and likes,
     *  as well as the corresponding service layers that encapsulate business logic.
     * The code then sets up the user interface components,
     *  linking them together with shared state 
     *  (such as the cookie map for session management).
     * After displaying initialization messages,
     *  it starts the main user interface loop. 
     * Once the application finishes running,
     *  it gracefully closes all repository resources 
     *  and prints exit messages before terminating the program.
     */
    public static void main(String[] args) {

        System.out.println("Welcome to the Social Media Application!");
        System.out.println("User Interface is being initialized...");

        UserRepository userRepository = new UserRepository();
        PostRepository postRepository = new PostRepository();
        FollowRepository followRepository = new FollowRepository();
        LikeRepository likeRepository = new LikeRepository();

        AuthService authService = new AuthService(userRepository);
        UserService userService = new UserService(userRepository, followRepository, postRepository);
        FollowService followService = new FollowService(followRepository);
        PostService postService = new PostService(postRepository);
        LikeService likeService = new LikeService(likeRepository, postRepository);

        HashMap<String, String> cookieHashMap = new HashMap<>();

        Profile profile = new Profile(cookieHashMap, userService, postService, followService, authService, likeService);
        Menu menu = new Menu(cookieHashMap, userService, postService, likeService, profile, authService);
        UserInterface userInterface = new UserInterface(cookieHashMap, authService, menu);

        System.out.println("User Interface initialized successfully!");
        userInterface.run();

        userRepository.close();
        postRepository.close();
        followRepository.close();
        likeRepository.close();
        System.out.println("Thank you for using our Social Media Application!");
        System.out.println("Exiting the application...");
        System.exit(0);
    }
}
