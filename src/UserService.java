import java.io.Console;
import java.util.Scanner;

public class UserService {
    private static LibrarianService librarian = new LibrarianService();
    private static String userEmail;
    private static int userId;
    private static int i;
    private Scanner myObj = new Scanner(System.in);
    private Console console = System.console();

    public UserService() {

    }

    public void loginInformation() throws EmailNotFoundException, IncorrectPasswordException, IncorrectPassFormatException {
        int ok = 0;
        System.out.println("Enter your email: ");
        userEmail = myObj.nextLine();
        for (int j = 0; j < LibrarianService.getUsers().size(); j++)
            if (LibrarianService.getUsers().get(j).getUserEmail().equals(userEmail)) {
                userId = LibrarianService.getUsers().get(j).getUserId();
                ok = 1;
            }

        if (ok == 0) {
            throw new EmailNotFoundException("Could not find user with this email! \n");
        }

        if (LibrarianService.getUsers().get(userId - 1).getNumOfLogins() == 0) {
            System.out.println("Because this is the first time you login, we generated a default password for you.");
            System.out.println("It consists of all the characters before the @ in your email.");
            System.out.println("Enter default password: ");

            char[] passwordChars1 = console.readPassword();
            String defaultPassword = new String(passwordChars1);

            String[] password = userEmail.split("@");
            String aux = password[0];

            if (!defaultPassword.equals(aux))
                throw new IncorrectPasswordException("You entered the wrong default password!\n");

            System.out.println("Enter your new password in the following format: ");
            System.out.println("At least 8 characters, minimum 1 lowercase, 1 uppercase, 1 special character and 1 digit");

            char[] passwordChars2 = console.readPassword();
            String userPassword1 = new String(passwordChars2);

            if (!userPassword1.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[_\\.#\\*\\+\\-\\$&!\\?@='\\[\\]\\{\\},`~\\(\\)]).{8,}"))
                throw new IncorrectPassFormatException("This password doesn't have the required format! \n");

            LibrarianService.getUsers().get(userId - 1).setUserPassword(userPassword1);

        } else {
            System.out.println("Enter your password: ");
            char[] passwordChars3 = console.readPassword();
            String userPassword2 = new String(passwordChars3);

            if (!LibrarianService.getUsers().get(userId - 1).getUserPassword().equals(userPassword2))
                throw new IncorrectPasswordException("You entered the wrong password!\n");
        }

        LibrarianService.getUsers().get(userId - 1).setNumOfLogins(++i);
    }

    public void printAvailableBooks() {
        librarian.printBooks();
    }

    public void printLentBooks() {
        if (LibrarianService.getUsers().get(userId - 1).getUserLentBooks().size() < 1) {
                System.out.println("There are no books lent by you! \n");
            }
        else {
            System.out.println("The books lent by you: ");
            for (int j = 0; j < LibrarianService.getUsers().get(userId - 1).getUserLentBooks().size(); j++)
                LibrarianService.getUsers().get(userId - 1).getUserLentBooks().get(j).print();
                    System.out.print("\n");
        }
    }
}
