import java.util.List;
import java.util.Scanner;

import models.User;
import models.Post;
import models.Follow;
import models.Like;

import repositories.UserRepository;
import repositories.PostRepository;
import repositories.FollowRepository;
import repositories.LikeRepository;

public class Main {

    private static final UserRepository userRepository = new UserRepository();
    private static final PostRepository postRepository = new PostRepository();
    private static final FollowRepository followRepository = new FollowRepository();
    private static final LikeRepository likeRepository = new LikeRepository();

    ///DEPRECATED
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Test Methodları Menüsü:");
            System.out.println("1 - UserRepository test et");
            System.out.println("2 - PostRepository test et");
            System.out.println("3 - FollowRepository test et");
            System.out.println("4 - LikeRepository test et");
            System.out.println("5 - Tüm Repository'leri test et");
            System.out.println("9 - Çıkış");
            System.out.print("Bir seçim yapınız: ");
            int sayi = scanner.nextInt();

            switch (sayi) {
                case 1:
                System.out.println("UserRepository test ediliyor...");
                testUserRepository();
                break;
                case 2:
                System.out.println("PostRepository test ediliyor...");
                testPostRepository();
                break;
                case 3:
                System.out.println("FollowRepository test ediliyor...");
                testFollowRepository();
                break;
                case 4:
                System.out.println("LikeRepository test ediliyor...");
                testLikeRepository();
                break;
                case 5:
                System.out.println("Tüm Repository'ler test ediliyor...");
                testAllRepositories();
                break;
                case 9:
                System.out.println("Çıkış yapılıyor...");
                userRepository.close();
                postRepository.close();
                followRepository.close();
                likeRepository.close();
                System.out.println("Tüm repository'ler kapatıldı.");
                System.out.println("Program sonlandırılıyor...");
                scanner.close();
                System.exit(0);
                break;
                default:
                System.out.println("Farklı bir sayı girdiniz: " + sayi);
            }
        }
    }

    ///START OF TEST REPOSITORY METHODS

    ///DEPRECATED
    private static void testUserRepository() {
        User u1 = new User();
        u1.setFullName("John Doe");
        u1.setUsername("johndoe");
        u1.setPassword("securepassword123");

        User u2 = new User();
        u2.setFullName("Jane Smith");
        u2.setUsername("janesmith");
        u2.setPassword("anothersecurepassword456");

        u1 = userRepository.add(u1);
        u2 = userRepository.add(u2);

        printUser(u1);
        printUser(u2);

        u1.setPassword("securePSWSADA123");

        u1 = userRepository.update(u1);

        printUser(u1);

        User u3 = userRepository.findById(u1.getId()).orElse(null);
        User u4 = userRepository.findById(u2.getId()).orElse(null);

        printUser(u3);
        printUser(u4);

        if(userRepository.contains(u1.getId())) {
            System.out.println("User with ID " + u1.getId() + " exists in the repository.");
        } else {
            System.out.println("User with ID " + u1.getId() + " does not exist in the repository.");
        }

        if(userRepository.contains(u2.getId())) {
            System.out.println("User with ID " + u2.getId() + " exists in the repository.");
        } else {
            System.out.println("User with ID " + u2.getId() + " does not exist in the repository.");
        }

        System.out.println("Total users in repository: " + userRepository.count());

        userRepository.deleteById(u1.getId());

        System.out.println("After deletion, total users in repository: " + userRepository.count());

        User u5 = userRepository.findById(0).orElse(null);

        printUser(u5);

        User u6 = new User();
        u6.setFullName("New User");
        u6.setUsername("newuser");
        u6.setPassword("newpassword");

        u6 = userRepository.add(u6);

        printUser(u6);

        List<User> allUsers = userRepository.getAll();

        System.out.println("All users in repository:");
        for(User user : allUsers) {
            printUser(user);
        }

        u6.setFullName("Deleted User");

        allUsers = userRepository.getAll();

        System.out.println("All users after modification:");
        for(User user : allUsers) {
            printUser(user);
        }

        userRepository.update(u6);

        System.out.println("After updating, all users in repository:");
        allUsers = userRepository.getAll();
        for(User user : allUsers) {
            printUser(user);
        }

        User u7 = userRepository.findByUsername("janesmith").orElse(null);
        printUser(u7);
    }

    ///DEPRECATED
    private static void testPostRepository() {

        Post p1 = new Post();
        p1.setContent("This is the first post.");
        p1.setUserId(0);

        Post p2 = new Post();
        p2.setContent("This is the second post.");
        p2.setUserId(1);

        p1 = postRepository.add(p1);
        p2 = postRepository.add(p2);

        printPost(p1);
        printPost(p2);

        List<Post> l1 = postRepository.findByUserId(0);

        System.out.println("Posts for user ID 0:");
        for(Post post : l1) {
            printPost(post);
        }
    }

    ///DEPRECATED
    private static void testFollowRepository() {

        Follow f0 = new Follow(1, 2);
        Follow f1 = new Follow(1, 3);
        Follow f2 = new Follow(2, 4);
        Follow f3 = new Follow(3, 2);
        Follow f4 = new Follow(4, 2);
        Follow f5 = new Follow(4, 3);

        f0 = followRepository.add(f0);
        f1 = followRepository.add(f1);
        f2 = followRepository.add(f2);
        f3 = followRepository.add(f3);
        f4 = followRepository.add(f4);
        f5 = followRepository.add(f5);

        printFollow(f0);
        printFollow(f1);
        printFollow(f2);
        printFollow(f3);
        printFollow(f4);
        printFollow(f5);

        System.out.println("-----------------------------------------------------");
        List<Follow> followsByUser1 = followRepository.findByFollowerId(1);
        System.out.println("Follows by user ID 1:");
        for(Follow follow : followsByUser1) {
            printFollow(follow);
        }

        System.out.println("-----------------------------------------------------");
        List<Follow> followsByUser2 = followRepository.findByFollowerId(2);
        System.out.println("Follows by user ID 2:");
        for(Follow follow : followsByUser2) {
            printFollow(follow);
        }

        System.out.println("-----------------------------------------------------");
        List<Follow> followsByUser3 = followRepository.findByFollowerId(3);
        System.out.println("Follows by user ID 3:");
        for(Follow follow : followsByUser3) {
            printFollow(follow);
        }

        System.out.println("-----------------------------------------------------");
        List<Follow> followsByUser4 = followRepository.findByFollowerId(4);
        System.out.println("Follows by user ID 4:");
        for(Follow follow : followsByUser4) {
            printFollow(follow);
        }

        System.out.println("-----------------------------------------------------");
        List<Follow> followsByUser5 = followRepository.findByFollowerId(5);
        System.out.println("Follows by user ID 5:");
        for(Follow follow : followsByUser5) {
            printFollow(follow);
        }


        System.out.println("-----------------------------------------------------");
        List<Follow> followingByUser6 = followRepository.findByFollowingId(1);
        System.out.println("Following by user ID 1:");
        for(Follow follow : followingByUser6) {
            printFollow(follow);
        }

        System.out.println("-----------------------------------------------------");
        List<Follow> followingByUser7 = followRepository.findByFollowingId(2);
        System.out.println("Following by user ID 2:");
        for(Follow follow : followingByUser7) {
            printFollow(follow);
        }

        System.out.println("-----------------------------------------------------");
        List<Follow> followingByUser8 = followRepository.findByFollowingId(3);
        System.out.println("Following by user ID 3:");
        for(Follow follow : followingByUser8) {
            printFollow(follow);
        }

        System.out.println("-----------------------------------------------------");
        List<Follow> followingByUser9 = followRepository.findByFollowingId(4);
        System.out.println("Following by user ID 4:");
        for(Follow follow : followingByUser9) {
            printFollow(follow);
        }

        System.out.println("-----------------------------------------------------");
        List<Follow> followingByUser10 = followRepository.findByFollowingId(5);
        System.out.println("Following by user ID 5:");
        for(Follow follow : followingByUser10) {
            printFollow(follow);
        }
    }

    ///DEPRECATED
    private static void testLikeRepository() {

        Like l1 = new Like(0, 0);
        Like l2 = new Like(1, 1);
        Like l3 = new Like(2, 2);
        Like l4 = new Like(0, 3);

        l1 = likeRepository.add(l1);
        l2 = likeRepository.add(l2);
        l3 = likeRepository.add(l3);
        l4 = likeRepository.add(l4);

        printLike(l1);
        printLike(l2);
        printLike(l3);

        if(likeRepository.exists(0, 0)) {
            System.out.println("Like exists for post ID 0 and user ID 0.");
        } else {
            System.out.println("Like does not exist for post ID 0 and user ID 0.");
        }

        if(likeRepository.exists(1, 1)) {
            System.out.println("Like exists for post ID 1 and user ID 1.");
        } else {
            System.out.println("Like does not exist for post ID 1 and user ID 1.");
        }

        if(likeRepository.exists(2, 2)) {
            System.out.println("Like exists for post ID 2 and user ID 2.");
        } else {
            System.out.println("Like does not exist for post ID 2 and user ID 2.");
        }

        if(likeRepository.exists(3, 3)) {
            System.out.println("Like exists for post ID 3 and user ID 3.");
        } else {
            System.out.println("Like does not exist for post ID 3 and user ID 3.");
        }


        System.out.println("Total likes for post ID 0: " + likeRepository.countByPostId(0));
        System.out.println("Total likes for post ID 1: " + likeRepository.countByPostId(1));
        System.out.println("Total likes for post ID 2: " + likeRepository.countByPostId(2));
        System.out.println("Total likes for post ID 3: " + likeRepository.countByPostId(3));

        System.out.println("Deleting likes for user ID 0 and post ID 0: " + likeRepository.deleteByUserIdAndPostId(0, 0));

        System.out.println("Total likes for post ID 0 after deletion: " + likeRepository.countByPostId(0));
    }
    
    private static void testAllRepositories() {
        List<User> allUsers = userRepository.getAll();
        System.out.println("All users in repository:");
        for(User user : allUsers) {
            printUser(user);
        }
        List<Post> allPosts = postRepository.getAll();
        System.out.println("All posts in repository:");
        for(Post post : allPosts) {
            printPost(post);
        }
        List<Follow> allFollows = followRepository.getAll();
        System.out.println("All follows in repository:");
        for(Follow follow : allFollows) {
            printFollow(follow);
        }
        List<Like> allLikes = likeRepository.getAll();
        System.out.println("All likes in repository:");
        for(Like like : allLikes) {
            printLike(like);
        }
    }
    ///END OF TEST REPOSITORY METHODS

    ///START OF PRINT METHODS

    ///DEPRECATED
    private static void printUser(User a) {
        if(a != null) {
            System.out.println("User Full Name : " + a.getFullName() +
                                ",\t ID: " + a.getId() +
                                ",\t Password: " + a.getPassword() + 
                                ",\t Username: " + a.getUsername() + 
                                ",\t Created At: " + a.getCreatedAt().toString() + 
                                ",\t Update At: " + a.getUpdatedAt().toString()
            );
            return;
        }

        System.out.println("User not found.");
    }

    ///DEPRECATED
    private static void printPost(Post a) {
        if(a != null) {
            System.out.println("Post ID: " + a.getId() +
                               ",\t User ID: " + a.getUserId() +
                               ",\t Content: " + a.getContent() +
                               ",\t Created At: " + a.getCreatedAt().toString() +
                               ",\t Updated At: " + a.getUpdatedAt().toString()
            );
            return;
        }

        System.out.println("Post not found.");
    }

    ///DEPRECATED
    private static void printFollow(Follow a) {
        if(a != null) {
            System.out.println("ID: " + a.getId() +
                               ",\t Follower ID: " + a.getFollowerId() +
                               ",\t Following ID: " + a.getFollowingId() +
                               ",\t Created At: " + a.getCreatedAt().toString() +
                               ",\t Updated At: " + a.getUpdatedAt().toString()
            );
            return;
        }

        System.out.println("Follow not found.");
    }

    ///DEPRECATED
    private static void printLike(Like a) {
        if(a != null) {
            System.out.println("Like ID: " + a.getId() +
                               ",\t User ID: " + a.getUserId() +
                               ",\t Post ID: " + a.getPostId() +
                               ",\t Created At: " + a.getCreatedAt().toString() +
                               ",\t Updated At: " + a.getUpdatedAt().toString()
            );
            return;
        }

        System.out.println("Like not found.");
}
    ///END OF PRINT METHODS

}
