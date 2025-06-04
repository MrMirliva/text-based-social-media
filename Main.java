import java.util.List;
import java.util.Scanner;

import models.User;
import models.Post;

import repositories.UserRepository;
import repositories.PostRepository;

public class Main {
    ///DEPRECATED
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Test Methodları Menüsü:");
        System.out.println("1 - UserRepository test et");
        System.out.println("2 - PostRepository test et");
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
            case 9:
            System.out.println("Çıkış yapılıyor...");
            System.exit(0);
            break;
            default:
            System.out.println("Farklı bir sayı girdiniz: " + sayi);
        }
        scanner.close();
    }


    ///DEPRECATED
    private static void testUserRepository() {
        UserRepository userRepository = new UserRepository();
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
        PostRepository postRepository = new PostRepository();

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
}
