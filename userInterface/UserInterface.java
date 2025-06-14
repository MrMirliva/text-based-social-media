package userInterface;

import java.util.HashMap;
import java.util.Scanner;

import models.User;
import requests.LoginRequest;
import responses.ResponseEntity;
import service.AuthService;

public class UserInterface {
    private final AuthService authService;
    private final Menu menu;
    private final HashMap<String, String> cookieHashMap;
    private Profile profile;

    public UserInterface(HashMap<String, String> cookiHashMap, AuthService authService, Menu menu) {
        this.cookieHashMap = cookiHashMap;
        this.menu = menu;
        this.authService = authService;
        // Initialize the AuthService or any other services if needed
    }

    // HashMap<String, String> cookieHashMap;

    public void showMenu() {
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        Scanner scanner = new Scanner(System.in);
        int choice;

        if (menu.isSystemExit())
            choice = 3;
        else {
            while (true) {
                System.out.print("Enter your choice: ");
                String input = scanner.nextLine();
                try {
                    choice = Integer.parseInt(input);
                    if (choice < 1 || choice > 3) {
                        System.out.println("Invalid choice. Please enter 1, 2 or 3.");
                    } else {
                        break; // geçerli seçim, çık döngüden
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number (1-3).");
                }
            }
        }

        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            case 3:
                return; // Exit the application
        }
        if (menu.isWillExit() && !!menu.isWillExit()) {
            choice = 4; // If the user is already logged in, default to login
            
            showMenu();
        }
    }

    

    public void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String username = scanner.nextLine().trim();
        while (username.isEmpty()) {
            System.out.println("Username cannot be empty. Please enter your username: ");
            username = scanner.nextLine().trim();
        }

        System.out.println("Enter your password: ");
        String password = scanner.nextLine().trim();
        while (password.isEmpty()) {
            System.out.println("Password cannot be empty. Please enter your password: ");
            password = scanner.nextLine().trim();
        }

        LoginRequest loginRequest = new LoginRequest(username, password, cookieHashMap);

        ResponseEntity<User> status = authService.login(loginRequest);

        if (status.isOk())
            menu.showMenu();
        else {
            System.out.println("Login failed: " + status.getMessage());
            showMenu();
        }
    }

    public void register() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your full name: ");
        String fullName = scanner.nextLine().trim();
        while (fullName.isEmpty()) {
            System.out.println("Full name cannot be empty. Please enter your full name: ");
            fullName = scanner.nextLine().trim();
        }

        System.out.println("Enter your username: ");
        String username = scanner.nextLine().trim();
        while (username.isEmpty()) {
            System.out.println("Username cannot be empty. Please enter your username: ");
            username = scanner.nextLine().trim();
        }

        System.out.println("Enter your password: ");
        String password = scanner.nextLine().trim();
        while (password.isEmpty()) {
            System.out.println("Password cannot be empty. Please enter your password: ");
            password = scanner.nextLine().trim();
        }

        LoginRequest loginRequest = new LoginRequest(username, password, cookieHashMap);

        ResponseEntity<User> status = authService.register(fullName, loginRequest);

        if (status.isOk()) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed: " + status.getMessage());
        }

        showMenu();
    }

    public void run() {
        System.out.println("Welcome to the User Interface!");
        showMenu();
    }
}