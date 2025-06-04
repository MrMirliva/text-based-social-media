import java.util.List;

import models.User;
import repositories.UserRepository;

public class Main {
    public static void main(String[] args) {
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
    }

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
}
