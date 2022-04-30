import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class UserService {
    private static LibrarianService librarianService = new LibrarianService();
    private static String userEmail;
    private static int userId;
    private static int i;
    private Scanner myObj = new Scanner(System.in);
    private String defaultPassword;
    private String userPassword1;
    private String userPassword2;

    public UserService() {

    }

    public void loginInformation() throws EmailNotFoundException, IncorrectPasswordException, IncorrectPassFormatException, SQLException {
        Connection connection = librarianService.getConnection();
        int ok = 0;

        System.out.println("\nEmail: ");
        userEmail = myObj.nextLine();

        for (int j = 0; j < LibrarianService.getUsers().size(); j++)
            if (LibrarianService.getUsers().get(j).getUserEmail().equals(userEmail) && !LibrarianService.getUsers().get(j).getIsDeleted()) {
                userId = LibrarianService.getUsers().get(j).getUserId();
                ok = 1;
            }

        if (ok == 0) {
            throw new EmailNotFoundException("Could not find user with this email!");
        }

        if (LibrarianService.getUsers().get(userId - 1).getNumOfLogins() == 0) {
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

            LibrarianService.getUsers().get(userId - 1).setUserPassword(userPassword1);

            Statement statement1 = connection.createStatement();
            statement1.executeUpdate("UPDATE \"USER\" SET USER_PASS = '" + userPassword1 + "' WHERE USER_ID = " + userId);
            statement1.close();

        } else {
            System.out.println("Password: ");

            userPassword2 = myObj.nextLine();

            if (!LibrarianService.getUsers().get(userId - 1).getUserPassword().equals(userPassword2))
                throw new IncorrectPasswordException("You entered the wrong password!");
        }

        LibrarianService.getUsers().get(userId - 1).setNumOfLogins(++i);

        Statement statement2 = connection.createStatement();
        statement2.executeUpdate("UPDATE \"USER\" SET NUM_OF_LOGINS = " + i + " WHERE USER_ID = " + userId);
        statement2.close();
    }

    public void printAvailableBooks() throws SQLException {
        int countBook;
        Connection connection = librarianService.getConnection();
        Statement statement1 = connection.createStatement();
        ResultSet result1 = statement1.executeQuery("SELECT COUNT(*) FROM BOOK WHERE UPPER(IS_LENT) = 'FALSE'");

        result1.next();
        countBook = result1.getInt(1);

        statement1.close();

        if(countBook == 0){
            System.out.println("\nThere are no available books! ");
        }
        else {
            Statement statement2 = connection.createStatement();
            ResultSet result3 = statement2.executeQuery("SELECT * FROM BOOK WHERE UPPER(IS_LENT) = 'FALSE'");

            System.out.println("\nAvailable books:\n");

            while (result3.next() && countBook > 1) {
                int bookId = result3.getInt(1);
                String author = result3.getString(2);
                String title = result3.getString(3);
                String description = result3.getString(4);
                System.out.println("book ID: " + bookId);
                System.out.println("\tauthor: " + author);
                System.out.println("\ttitle: " + title);
                System.out.println("\tdescription: " + description);
                System.out.print("\n");
                countBook--;
            }
            if(countBook == 1){
                int bookId = result3.getInt(1);
                String author = result3.getString(2);
                String title = result3.getString(3);
                String description = result3.getString(4);
                System.out.println("book ID: " + bookId);
                System.out.println("\tauthor: " + author);
                System.out.println("\ttitle: " + title);
                System.out.println("\tdescription: " + description);
            }
            statement2.close();
        }
    }

    public void printLentBooks() throws SQLException {
        int countLentBooks;
        Connection connection = librarianService.getConnection();

        Statement statement1 = connection.createStatement();
        ResultSet result1 = statement1.executeQuery("SELECT COUNT(*) FROM LENT_BOOK WHERE USER_ID = " + userId + " AND UPPER(IS_LENT) = 'TRUE'");

        result1.next();
        countLentBooks = result1.getInt(1);

        statement1.close();
        if (countLentBooks == 0)
            System.out.println("\nYou didn't lend any books!");
        else {
            Statement statement2 = connection.createStatement();
            ResultSet result2 = statement2.executeQuery("SELECT * FROM LENT_BOOK WHERE USER_ID = " + userId + " AND UPPER(IS_LENT) = 'TRUE'");

            System.out.println("\nYour lent books:\n");

            while (result2.next() && countLentBooks > 1) {
                int bookId = result2.getInt(1);
                String author = result2.getString(2);
                String title = result2.getString(3);
                String description = result2.getString(4);
                String issuedDate = result2.getString(5);
                String returnDate = result2.getString(7);
                float price = result2.getFloat(12);
                System.out.println("book ID: " + bookId);
                System.out.println("\tauthor: " + author);
                System.out.println("\ttitle: " + title);
                System.out.println("\tdescription: " + description);
                System.out.println("\tissued date: " + issuedDate);
                System.out.println("\treturn date: " + returnDate);
                System.out.print("\tprice: ");
                System.out.printf("%.02f", price);
                System.out.print(" ron\n");
                System.out.print("\n");
                countLentBooks--;
            }
            if(countLentBooks == 1){
                int bookId = result2.getInt(1);
                String author = result2.getString(2);
                String title = result2.getString(3);
                String description = result2.getString(4);
                String issuedDate = result2.getString(5);
                String returnDate = result2.getString(7);
                float price = result2.getFloat(12);
                System.out.println("book ID: " + bookId);
                System.out.println("\tauthor: " + author);
                System.out.println("\ttitle: " + title);
                System.out.println("\tdescription: " + description);
                System.out.println("\tissued date: " + issuedDate);
                System.out.println("\treturn date: " + returnDate);
                System.out.print("\tprice: ");
                System.out.printf("%.02f", price);
                System.out.print(" ron\n");
            }
            statement2.close();
        }
    }

    public void printReturnedBooks() throws SQLException{
        int countReturnedBooks;
        Connection connection = librarianService.getConnection();

        Statement statement1 = connection.createStatement();
        ResultSet result1 = statement1.executeQuery("SELECT COUNT(*) FROM LENT_BOOK WHERE USER_ID = " + userId + " AND UPPER(IS_LENT) = 'FALSE'");

        result1.next();
        countReturnedBooks = result1.getInt(1);

        statement1.close();
        if (countReturnedBooks == 0)
            System.out.println("\nYou didn't return any books!");
        else {
            Statement statement2 = connection.createStatement();
            ResultSet result2 = statement2.executeQuery("SELECT * FROM LENT_BOOK WHERE USER_ID = " + userId + " AND UPPER(IS_LENT) = 'FALSE'");

            System.out.println("\nYour returned books:\n");

            while (result2.next() && countReturnedBooks > 1) {
                int bookId = result2.getInt(1);
                String author = result2.getString(2);
                String title = result2.getString(3);
                String description = result2.getString(4);
                String issuedDate = result2.getString(5);
                String returnDate = result2.getString(7);
                float price = result2.getFloat(12);
                int exceededDays = result2.getInt(13);
                float exceededPrice = result2.getFloat(14);
                System.out.println("book ID: " + bookId);
                System.out.println("\tauthor: " + author);
                System.out.println("\ttitle: " + title);
                System.out.println("\tdescription: " + description);
                System.out.print("\tissue date: " + issuedDate);
                System.out.print("\n");
                if (exceededDays > 0) {
                    System.out.print("\treturn date: " + returnDate);
                    System.out.print(" + exceeded with " + exceededDays + " days\n");
                    System.out.print("\tinitial price: ");
                    System.out.printf("%.02f", price);
                    System.out.print(" ron\n");
                    System.out.print("\tprice added after exceeding the return date: ");
                    System.out.printf("%.02f", exceededPrice);
                    System.out.print(" ron\n");
                } else {
                    System.out.println("\treturn date: " + returnDate);
                    System.out.print("\tprice: ");
                    System.out.printf("%.02f", price);
                    System.out.print(" ron\n");
                }
                System.out.print("\n");
                countReturnedBooks--;
            }
            if(countReturnedBooks == 1){
                int bookId = result2.getInt(1);
                String author = result2.getString(2);
                String title = result2.getString(3);
                String description = result2.getString(4);
                String issuedDate = result2.getString(5);
                String returnDate = result2.getString(7);
                float price = result2.getFloat(12);
                int exceededDays = result2.getInt(13);
                float exceededPrice = result2.getFloat(14);
                System.out.println("book ID: " + bookId);
                System.out.println("\tauthor: " + author);
                System.out.println("\ttitle: " + title);
                System.out.println("\tdescription: " + description);
                System.out.print("\tissue date: " + issuedDate);
                System.out.print("\n");
                if (exceededDays > 0) {
                    System.out.print("\treturn date: " + returnDate);
                    System.out.print(" + exceeded with " + exceededDays + " days\n");
                    System.out.print("\tinitial price: ");
                    System.out.printf("%.02f", price);
                    System.out.print(" ron\n");
                    System.out.print("\tprice added after exceeding the return date: ");
                    System.out.printf("%.02f", exceededPrice);
                    System.out.print(" ron\n");
                } else {
                    System.out.println("\treturn date: " + returnDate);
                    System.out.print("\tprice: ");
                    System.out.printf("%.02f", price);
                    System.out.print(" ron\n");
                }
            }
            statement2.close();
        }
    }

    public void printDiscounts() throws SQLException {
        int countDiscount = 0;
        Connection connection = librarianService.getConnection();

        Statement statement1 = connection.createStatement();
        ResultSet result1 = statement1.executeQuery("SELECT * FROM DISCOUNT WHERE USER_ID = " + userId + " AND UPPER(IS_USED) = 'FALSE'");

        if(LibrarianService.getLastDate().getDate().isEmpty()){
            LibrarianService.setLastDate(LibrarianService.getInvoice().get(LibrarianService.getInvoice().size() - 1).getInvoiceDate());
        }

        while (result1.next()) {
            String code = result1.getString(1);
            MyDate expirationDate = new MyDate();
            expirationDate.setDate(result1.getString(2));
            if (LibrarianService.getLastDate().isBiggerThan(expirationDate)) {
                Statement statement3 = connection.createStatement();
                statement3.executeUpdate("UPDATE DISCOUNT SET IS_USED = 'TRUE' WHERE EXP_DATE = '" + expirationDate.getDate() + "'");
                statement3.close();
                for (int i = 0; i < LibrarianService.getDiscounts().get(userId - 1).size(); i++)
                    if (LibrarianService.getDiscounts().get(userId - 1).get(i).getCode().equals(code))
                        LibrarianService.getDiscounts().get(userId - 1).get(i).setIsUsed(true);
            } else
                ++countDiscount;
        }
        statement1.close();

        if (countDiscount == 0)
            System.out.println("\nYou haven't earned any discounts yet!");
        else {
            System.out.println("\nYour discounts: ");
            Statement statement2 = connection.createStatement();
            ResultSet result2 = statement2.executeQuery("SELECT * FROM DISCOUNT WHERE USER_ID = " + userId + " AND UPPER(IS_USED) = 'FALSE'");
            while (result2.next()) {
                String code = result2.getString(1);
                MyDate expirationDate = new MyDate();
                expirationDate.setDate(result2.getString(2));
                System.out.print("\ncode: " + code + ", expiration date: ");
                expirationDate.print();
            }
            System.out.print("\n");
            statement2.close();
        }
    }
}
