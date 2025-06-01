package userInterface;

import java.util.HashMap;
import java.util.Scanner;

public class UserInterface {
    Menu menu;
    HashMap<String, String> cookieHashMap;  




    public boolean login() {
        boolean loggedIn = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();
        if (cookieHashMap.containsKey(username) && cookieHashMap.get(username).equals(password)) {
            System.out.println("Login successful!");
            menu.seeProfile();
            loggedIn = true;
        } else {
            System.out.println("Invalid username or password.");
        }
        scanner.close();
        return loggedIn;
    }
    public boolean register() {
        cookieHashMap = new HashMap<>();
        boolean registered = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();
        if (!cookieHashMap.containsKey(username)) {
            cookieHashMap.put(username, password);
            System.out.println("Registration successful!");
            registered = true;
        } else {
            System.out.println("Username already exists.");
        }
        scanner.close();
        menu = new Menu(new Profile(username, password));
        return registered;
    }

    public void showMenu() {
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                if (login()) {
                    System.out.println("You are now logged in.");
                    menu.showMenu();
                } else {
                    System.out.println("Login failed.");
                }
                break;
            case 2:
                if (register()) {
                    System.out.println("You can now login.");
                    showMenu();
                }
                break;
            case 3:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice.");
        }
        scanner.close();
    }
}