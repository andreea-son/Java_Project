import java.io.Console;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UserService {
    private static LibrarianService librarian = new LibrarianService();
    private static String userEmail;
    private static int userId;
    private static int i;
    private Scanner myObj = new Scanner(System.in);
    private Console console = System.console();
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public UserService() {

    }

    public void loginInformation() throws EmailNotFoundException, IncorrectPasswordException, IncorrectPassFormatException {
        LibrarianService.setCounter(LibrarianService.getCounter() + 1);
        LibrarianService.getActions1().set(LibrarianService.getCounter(), new MyAction("user: login", dtf.format(LocalDateTime.now())));

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
        LibrarianService.setCounter(LibrarianService.getCounter() + 1);
        LibrarianService.getActions1().set(LibrarianService.getCounter(), new MyAction("user: print available books", dtf.format(LocalDateTime.now())));

        int ok1 = 0;
        int ok2 = 0;
        for (Partners partner : LibrarianService.getPartners())
            if (partner.getPartnerBooks().size() > 0) {
                ok1 = 1;
                break;
            }
        if (ok1 == 0)
            System.out.println("There are no available books! \n");
        else {
            for (Partners partner : LibrarianService.getPartners())
                for (int j = 0; j < partner.getPartnerBooks().size(); j++)
                    if (!partner.getPartnerBooks().get(j).getIsLent()) {
                        ok2 = 1;
                        break;
                    }
            if (ok2 == 0)
                System.out.println("There are no available books! \n");
            else {
                System.out.println("The books in the library: ");
                for (Partners partner : LibrarianService.getPartners())
                    for (int j = 0; j < partner.getPartnerBooks().size(); j++)
                        if (!partner.getPartnerBooks().get(j).getIsLent()) {
                            partner.getPartnerBooks().get(j).print();
                            System.out.print("\n");
                        }
            }
        }
    }

    public void printLentBooks() {
        LibrarianService.setCounter(LibrarianService.getCounter() + 1);
        LibrarianService.getActions1().set(LibrarianService.getCounter(), new MyAction("user: print lent books", dtf.format(LocalDateTime.now())));

        int ok = 0;
        if (LibrarianService.getUsers().get(userId - 1).getUserLentBooks().size() > 0) {
            for (int j = 0; j < LibrarianService.getUsers().get(userId - 1).getUserLentBooks().size(); j++)
                if (LibrarianService.getUsers().get(userId - 1).getUserLentBooks().get(j).getIsLent()) {
                    ok = 1;
                    break;
                }
            if (ok == 0)
                System.out.println("You didn't lend any books! \n");
            else {
                System.out.println("Currently lent books: ");
                for (int j = 0; j < LibrarianService.getUsers().get(userId - 1).getUserLentBooks().size(); j++)
                    if (LibrarianService.getUsers().get(userId - 1).getUserLentBooks().get(j).getIsLent()) {
                        LibrarianService.getUsers().get(userId - 1).getUserLentBooks().get(j).print();
                        System.out.print("\n");
                    }
            }
        }
        else {
            System.out.println("You didn't lend any books! \n");
        }
    }

    public void printReturnedBooks(){
        LibrarianService.setCounter(LibrarianService.getCounter() + 1);
        LibrarianService.getActions1().set(LibrarianService.getCounter(), new MyAction("user: print returned books", dtf.format(LocalDateTime.now())));

        int ok = 0;
        if (LibrarianService.getUsers().get(userId - 1).getUserLentBooks().size() > 0) {
            for (int j = 0; j < LibrarianService.getUsers().get(userId - 1).getUserLentBooks().size(); j++)
                if (!LibrarianService.getUsers().get(userId - 1).getUserLentBooks().get(j).getIsLent()) {
                    ok = 1;
                    break;
                }
            if(ok == 0)
                System.out.println("You didn't return any books! \n");
            else {
                System.out.println("Returned books: ");
                for (int j = 0; j < LibrarianService.getUsers().get(userId - 1).getUserLentBooks().size(); j++)
                    if (!LibrarianService.getUsers().get(userId - 1).getUserLentBooks().get(j).getIsLent()) {
                        LibrarianService.getUsers().get(userId - 1).getUserLentBooks().get(j).print();
                        System.out.print("\n");
                    }
            }
        }
        else {
            System.out.println("You didn't return any books! \n");
        }
    }

    public void printDiscounts() {
        LibrarianService.setCounter(LibrarianService.getCounter() + 1);
        LibrarianService.getActions1().set(LibrarianService.getCounter(), new MyAction("user: print discounts", dtf.format(LocalDateTime.now())));

        int ok = 0;
        if (LibrarianService.getUsers().get(userId - 1).getDiscounts().size() > 0) {
            for (int j = 0; j < LibrarianService.getUsers().get(userId - 1).getDiscounts().size(); j++)
                if (!LibrarianService.getUsers().get(userId - 1).getDiscounts().get(j).getIsUsed()) {
                    if(LibrarianService.getLastDate().isBiggerThan(LibrarianService.getUsers().get(userId - 1).getDiscounts().get(j).getExpirationDate()))
                        LibrarianService.getUsers().get(userId - 1).getDiscounts().get(j).setIsUsed(true);
                    else {
                        ok = 1;
                    }
                }
            if(ok == 0)
                System.out.println("You haven't earned any discounts yet! \n");
            else {
                System.out.println("Current discounts: ");
                for (int j = 0; j < LibrarianService.getUsers().get(userId - 1).getDiscounts().size(); j++)
                    if (!LibrarianService.getUsers().get(userId - 1).getDiscounts().get(j).getIsUsed()) {
                        LibrarianService.getUsers().get(userId - 1).getDiscounts().get(j).print();
                        System.out.print("\n");
                    }
            }
        }
        else {
            System.out.println("You haven't earned any discounts yet! \n");
        }
    }
}