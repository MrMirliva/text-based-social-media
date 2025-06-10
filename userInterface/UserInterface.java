package userInterface;
import java.util.HashMap;
import java.util.Scanner;

import models.User;
import repositories.UserRepository;
import requests.LoginRequest;
import responses.ResponseEnity;
import service.AuthService;

public class UserInterface {    
    private final AuthService authService;
    private final Menu menu;
    private final HashMap<String,String> cookieHashMap;

    public UserInterface(HashMap<String,String> cookiHashMap,AuthService authService, Menu menu) {
        this.cookieHashMap = cookiHashMap;
        this.menu = menu;
        this.authService = authService;
        // Initialize the AuthService or any other services if needed
    }
    
   // HashMap<String, String> cookieHashMap;  
   



    public void login() {
        //boolean loggedIn = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();

        LoginRequest loginRequest = new LoginRequest(username, password, cookieHashMap);


         ResponseEnity<User> status = authService.login( loginRequest);

         if(status.isOk())
          menu.showMenu();
          else{
          System.out.println("login failed: " + status.getMessage());
          showMenu();
        }

        //return loggedIn;
    }
    public void register() {
        //cookieHashMap = new HashMap<>();
       // boolean registered = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your full name: ");
        String fullName = scanner.nextLine();
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();

        LoginRequest loginRequest = new LoginRequest(username, password, cookieHashMap);

        ResponseEnity<User> status = authService.register(fullName, loginRequest);

        if(status.isOk()) {
            System.out.println("Registration successful!");

            //registered = true;
        } else {
            System.out.println("Registration failed: " + status.getMessage());
        }
        showMenu();
        //menu = new Menu(new Profile(username, password));
        //return registered;
    }

    public void showMenu() {
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        Scanner scanner = new Scanner(System.in);
        int choice;
        if(!menu.isWillExit())
            choice = 3; // If the user is logged out, default to exit
        else {
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
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
            default:
                System.out.println("Invalid choice.");
                showMenu();
        }
    }
    public void run() {
        System.out.println("Welcome to the User Interface!");
        showMenu();
    }
}