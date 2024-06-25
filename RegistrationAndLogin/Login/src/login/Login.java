
package login;

import java.util.Scanner;


public class Login {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String username, password;

        System.out.print("Enter your username (5 characters with an underscore): ");
        username = scanner.nextLine();

        if (username.length() != 5 || !username.contains("_")) {
            System.out.println("Invalid username format.");
            return;
        }

        System.out.print("Enter your password (more than 8 characters with a capital letter, a number, and a special character): ");
        password = scanner.nextLine();

        if (password.length() <= 8 || !password.matches(".*[A-Z].*") || !password.matches(".*[0-9].*") || !password.matches(".*[!@#$%^&*()].*")) {
            System.out.println("Invalid password format.");
            return;
        }

        System.out.println("Login successful!");
    }
}
    
    

