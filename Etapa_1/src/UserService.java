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
        for (int j = 0; j < librarian.getUsers().size(); j++)
            if (librarian.getUsers().get(j).getUserEmail().equals(userEmail) && !librarian.getUsers().get(j).getIsDeleted()) {
                userId = librarian.getUsers().get(j).getUserId();
                ok = 1;
            }

        if (ok == 0) {
            throw new EmailNotFoundException("Could not find user with this email! \n");
        }

        if (librarian.getUsers().get(userId - 1).getNumOfLogins() == 0) {
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

            librarian.getUsers().get(userId - 1).setUserPassword(userPassword1);

        } else {
            System.out.println("Enter your password: ");
            char[] passwordChars3 = console.readPassword();
            String userPassword2 = new String(passwordChars3);

            if (!librarian.getUsers().get(userId - 1).getUserPassword().equals(userPassword2))
                throw new IncorrectPasswordException("You entered the wrong password!\n");
        }

        librarian.getUsers().get(userId - 1).setNumOfLogins(++i);
    }

    public void printAvailableBooks() {
        int ok1 = 0;
        int ok2 = 0;
        for (int i = 0; i < librarian.getPartners().size(); i++)
            if (librarian.getPartnerBooks().get(i).size() > 0) {
                ok1 = 1;
                break;
            }
        if (ok1 == 0)
            System.out.println("There are no books! \n");
        else {
            for (int i = 0; i < librarian.getPartners().size(); i++)
                for (int j = 0; j < librarian.getPartnerBooks().get(i).size(); j++)
                    if (!librarian.getPartnerBooks().get(i).get(j).getIsLent() && !librarian.getPartnerBooks().get(i).get(j).getIsDeleted()) {
                        ok2 = 1;
                        break;
                    }
            if (ok2 == 0)
                System.out.println("There are no available books! \n");
            else {
                System.out.println("The books in the library: ");
                for (int i = 0; i < librarian.getPartners().size(); i++)
                    for (int j = 0; j < librarian.getPartnerBooks().get(i).size(); j++)
                        if (!librarian.getPartnerBooks().get(i).get(j).getIsLent() && !librarian.getPartnerBooks().get(i).get(j).getIsDeleted()) {
                            librarian.getPartnerBooks().get(i).get(j).print();
                            System.out.print("\n");
                        }
            }
        }
    }

    public void printLentBooks() {
        int ok = 0;
        if (librarian.getUserLentBooks().get(userId - 1).size() > 0) {
            for (int j = 0; j < librarian.getUserLentBooks().get(userId - 1).size(); j++)
                if (librarian.getUserLentBooks().get(userId - 1).get(j).getIsLent()) {
                    ok = 1;
                    break;
                }
            if (ok == 0)
                System.out.println("You didn't lend any books! \n");
            else {
                System.out.println("Currently lent books: ");
                for (int j = 0; j < librarian.getUserLentBooks().get(userId - 1).size(); j++)
                    if (librarian.getUserLentBooks().get(userId - 1).get(j).getIsLent()) {
                        librarian.getUserLentBooks().get(userId - 1).get(j).print();
                        System.out.print("\n");
                    }
            }
        }
        else {
            System.out.println("You didn't lend any books! \n");
        }
    }

    public void printReturnedBooks(){
        int ok = 0;
        if (librarian.getUserLentBooks().get(userId - 1).size() > 0) {
            for (int j = 0; j < librarian.getUserLentBooks().get(userId - 1).size(); j++)
                if (!librarian.getUserLentBooks().get(userId - 1).get(j).getIsLent()) {
                    ok = 1;
                    break;
                }
            if(ok == 0)
                System.out.println("You didn't return any books! \n");
            else {
                System.out.println("Returned books: ");
                for (int j = 0; j < librarian.getUserLentBooks().get(userId - 1).size(); j++)
                    if (!librarian.getUserLentBooks().get(userId - 1).get(j).getIsLent()) {
                        librarian.getUserLentBooks().get(userId - 1).get(j).print();
                        System.out.print("\n");
                    }
            }
        }
        else {
            System.out.println("You didn't return any books! \n");
        }
    }

    public void printDiscounts() {
        int ok = 0;
        if (librarian.getDiscounts().size() > 0) {
            for (int j = 0; j < librarian.getDiscounts().get(userId - 1).size(); j++)
                if (!librarian.getDiscounts().get(userId - 1).get(j).getIsUsed()) {
                    if(librarian.getLastDate().isBiggerThan(librarian.getDiscounts().get(userId - 1).get(j).getExpirationDate()))
                        librarian.getDiscounts().get(userId - 1).get(j).setIsUsed(true);
                    else {
                        ok = 1;
                    }
                }
            if(ok == 0)
                System.out.println("You haven't earned any discounts yet! \n");
            else {
                System.out.println("Current discounts: ");
                for (int j = 0; j < librarian.getDiscounts().get(userId - 1).size(); j++)
                    if (!librarian.getDiscounts().get(userId - 1).get(j).getIsUsed()) {
                        librarian.getDiscounts().get(userId - 1).get(j).print();
                        System.out.print("\n");
                    }
            }
        }
        else {
            System.out.println("You haven't earned any discounts yet! \n");
        }
    }
}