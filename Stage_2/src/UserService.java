import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UserService {
    private static LibrarianService librarian = new LibrarianService();
    private static String userEmail;
    private static int userId;
    private static int i;
    private Scanner myObj = new Scanner(System.in);
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public UserService() {

    }

    public void loginInformation() throws EmailNotFoundException, IncorrectPasswordException, IncorrectPassFormatException {
        librarian.setCounter(librarian.getCounter() + 1);
        librarian.getActions1().set(librarian.getCounter(), new MyAction("user: login", dtf.format(LocalDateTime.now())));

        int ok = 0;
        String defaultPassword;
        String userPassword1;
        String userPassword2;
        System.out.println("\nEmail: ");
        userEmail = myObj.nextLine();
        for (int j = 0; j < librarian.getUsers().size(); j++)
            if (librarian.getUsers().get(j).getUserEmail().equals(userEmail) && !librarian.getUsers().get(j).getIsDeleted()) {
                userId = librarian.getUsers().get(j).getUserId();
                ok = 1;
            }

        if (ok == 0) {
            throw new EmailNotFoundException("Could not find user with this email!");
        }

        if (librarian.getUsers().get(userId - 1).getNumOfLogins() == 0) {
            System.out.println("Because this is the first time you login, we generated a default password for you.");
            System.out.println("It consists of all the characters before the @ in your email.");
            System.out.println("Default password: ");

            defaultPassword = myObj.nextLine();

            String[] password = userEmail.split("@");
            String aux = password[0];

            if (!defaultPassword.equals(aux))
                throw new IncorrectPasswordException("You entered the wrong default password!");

            System.out.println("Enter your new password in the following format: ");
            System.out.println("At least 8 characters, minimum 1 lowercase, 1 uppercase, 1 special character and 1 digit");

            userPassword1 = myObj.nextLine();

            if (!userPassword1.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[_\\.#\\*\\+\\-\\$&!\\?@='\\[\\]\\{\\},`~\\(\\)]).{8,}"))
                throw new IncorrectPassFormatException("This password doesn't have the required format!");

            librarian.getUsers().get(userId - 1).setUserPassword(userPassword1);

        } else {
            System.out.println("Password: ");
            userPassword2 = myObj.nextLine();

            if (!librarian.getUsers().get(userId - 1).getUserPassword().equals(userPassword2))
                throw new IncorrectPasswordException("You entered the wrong password!");
        }

        librarian.getUsers().get(userId - 1).setNumOfLogins(++i);
    }

    public void printAvailableBooks() {
        librarian.setCounter(librarian.getCounter() + 1);
        librarian.getActions1().set(librarian.getCounter(), new MyAction("user: print available books", dtf.format(LocalDateTime.now())));

        int ok1 = 0;
        int ok2 = 0;
        for (int i = 0; i < librarian.getPartners().size(); i++)
            if (librarian.getPartnerBooks().get(i).size() > 0) {
                ok1 = 1;
                break;
            }
        if (ok1 == 0)
            System.out.println("\nThere are no available books! ");
        else {
            for (int i = 0; i < librarian.getPartners().size(); i++)
                for (int j = 0; j < librarian.getPartnerBooks().get(i).size(); j++)
                    if (!librarian.getPartnerBooks().get(i).get(j).getIsLent() && !librarian.getPartnerBooks().get(i).get(j).getIsDeleted()) {
                        ok2 = 1;
                        break;
                    }
            if (ok2 == 0)
                System.out.println("\nThere are no available books! ");
            else {
                System.out.println("\nAvailable books:\n");
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
        librarian.setCounter(librarian.getCounter() + 1);
        librarian.getActions1().set(librarian.getCounter(), new MyAction("user: print lent books", dtf.format(LocalDateTime.now())));

        int ok = 0;
        if (librarian.getUserLentBooks().get(userId - 1).size() > 0) {
            for (int j = 0; j < librarian.getUserLentBooks().get(userId - 1).size(); j++)
                if (librarian.getUserLentBooks().get(userId - 1).get(j).getIsLent()) {
                    ok = 1;
                    break;
                }
            if (ok == 0)
                System.out.println("\nYou didn't lend any books!");
            else {
                System.out.println("\nYour lent books:\n");
                for (int j = 0; j < librarian.getUserLentBooks().get(userId - 1).size(); j++)
                    if (librarian.getUserLentBooks().get(userId - 1).get(j).getIsLent()) {
                        librarian.getUserLentBooks().get(userId - 1).get(j).print();
                        System.out.print("\n");
                    }
            }
        }
        else {
            System.out.println("\nYou didn't lend any books!");
        }
    }

    public void printReturnedBooks(){
        librarian.setCounter(librarian.getCounter() + 1);
        librarian.getActions1().set(librarian.getCounter(), new MyAction("user: print returned books", dtf.format(LocalDateTime.now())));

        int ok = 0;
        if (librarian.getUserLentBooks().get(userId - 1).size() > 0) {
            for (int j = 0; j < librarian.getUserLentBooks().get(userId - 1).size(); j++)
                if (!librarian.getUserLentBooks().get(userId - 1).get(j).getIsLent()) {
                    ok = 1;
                    break;
                }
            if(ok == 0)
                System.out.println("\nYou didn't return any books!");
            else {
                System.out.println("\nYour returned books:\n");
                for (int j = 0; j < librarian.getUserLentBooks().get(userId - 1).size(); j++)
                    if (!librarian.getUserLentBooks().get(userId - 1).get(j).getIsLent()) {
                        librarian.getUserLentBooks().get(userId - 1).get(j).print();
                        System.out.print("\n");
                    }
            }
        }
        else {
            System.out.println("\nYou didn't return any books!");
        }
    }

    public void printDiscounts() {
        librarian.setCounter(librarian.getCounter() + 1);
        librarian.getActions1().set(librarian.getCounter(), new MyAction("user: print discounts", dtf.format(LocalDateTime.now())));

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
                System.out.println("\nYou haven't earned any discounts yet!");
            else {
                System.out.println("\nYour discounts: ");
                for (int j = 0; j < librarian.getDiscounts().get(userId - 1).size(); j++)
                    if (!librarian.getDiscounts().get(userId - 1).get(j).getIsUsed()) {
                        librarian.getDiscounts().get(userId - 1).get(j).print();
                        System.out.print("\n");
                    }
            }
        }
        else {
            System.out.println("\nYou haven't earned any discounts yet!");
        }
    }
}