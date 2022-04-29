import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class PartnerService {
    private static ArrayList<String> author = new ArrayList<>();
    private static ArrayList<String> title = new ArrayList<>();
    private static ArrayList<String> description = new ArrayList<>();
    private LibrarianService librarianService = new LibrarianService();
    private static int partnerId = LibrarianService.getPartners().size();
    private static int i;
    private static int counter;
    private Scanner myObj = new Scanner(System.in);

    public PartnerService(){

    }

    public void loginInformation() throws EmailNotFoundException, IncorrectPasswordException, IncorrectPassFormatException, SQLException {
        int ok = 0;
        Connection connection = librarianService.getConnection();

        System.out.println("\nEmail: ");
        String partnerEmail = myObj.nextLine();
        for (int j = 0; j < LibrarianService.getPartners().size(); j++)
            if (LibrarianService.getPartners().get(j).getPartnerEmail().equals(partnerEmail) && !LibrarianService.getPartners().get(j).getIsDeleted()) {
                partnerId = LibrarianService.getPartners().get(j).getPartnerId();
                ok = 1;
            }

        if (ok == 0) {
            throw new EmailNotFoundException("Could not find partner with this email!");
        }

        if (LibrarianService.getPartners().get(partnerId - 1).getNumOfLogins() == 0) {
            System.out.println("Because this is the first time you login, we generated a default password for you.");
            System.out.println("It consists of all the characters before the @ in your email.");
            System.out.println("Default password: ");

            String defaultPassword = myObj.nextLine();

            String[] password = partnerEmail.split("@");
            String aux = password[0];

            if (!defaultPassword.equals(aux))
                throw new IncorrectPasswordException("You entered the wrong default password!");
            System.out.println("Enter your new password in the following format: ");
            System.out.println("At least 8 characters, minimum 1 lowercase, 1 uppercase, 1 special character and 1 digit");

            String partnerPassword1 = myObj.nextLine();

            if (!partnerPassword1.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[_\\.#\\*\\+\\-\\$&!\\?@='\\[\\]\\{\\},`~\\(\\)]).{8,}"))
                throw new IncorrectPassFormatException("This password doesn't have the required format!");

            LibrarianService.getPartners().get(partnerId - 1).setPartnerPassword(partnerPassword1);

            Statement statement1 = connection.createStatement();
            statement1.executeUpdate("UPDATE PARTNER SET PARTNER_PASS = '" + partnerPassword1 + "' WHERE PARTNER_ID = " + partnerId);
            statement1.close();
        } else {

            System.out.println("Password: ");
            String partnerPassword2 = myObj.nextLine();

            if (!LibrarianService.getPartners().get(partnerId - 1).getPartnerPassword().equals(partnerPassword2))
                throw new IncorrectPasswordException("You entered the wrong password!");
        }

        LibrarianService.getPartners().get(partnerId - 1).setNumOfLogins(++i);

        Statement statement2 = connection.createStatement();
        statement2.executeUpdate("UPDATE PARTNER SET NUM_OF_LOGINS = " + i + " WHERE PARTNER_ID = " + partnerId);
        statement2.close();

    }

    public void addNewBook() throws SectionNotFoundException, SQLException, BookAlreadyAddedException {
        String aut;
        String t;
        String desc;
        int sectionId;
        int bookId;
        int ok = 0;
        Connection connection = librarianService.getConnection();

        for (ArrayList<Books> partnerBook : LibrarianService.getPartnerBooks())
            for (int j = 0; j < partnerBook.size(); j++)
                ++counter;

        bookId = counter;

        System.out.println("\nSection ID: ");
        sectionId = myObj.nextInt();
        myObj.nextLine();

        for(int i = 0; i < LibrarianService.getSections().size(); i++)
            if(LibrarianService.getSections().get(i).getSectionId() == sectionId && !LibrarianService.getSections().get(i).getIsDeleted()) {
                ok = 1;
                break;
            }

        if (ok == 0) {
            throw new SectionNotFoundException("Could not find section with this ID!");
        }

        System.out.println("Title: ");
        t = myObj.nextLine();
        title.add(t);
        System.out.println("Author: ");
        aut = myObj.nextLine();
        author.add(aut);

        if (LibrarianService.getPartners().size() > 1) {
            for (int i = 0; i < LibrarianService.getPartners().size(); i++) {
                for (int j = 0; j < LibrarianService.getPartnerBooks().get(i).size(); j++)
                    if (LibrarianService.getPartnerBooks().get(i).get(j).getAuthor().equals(author.get(author.size() - 1)) && LibrarianService.getPartnerBooks().get(i).get(j).getTitle().equals(title.get(title.size() - 1)) && !LibrarianService.getPartnerBooks().get(i).get(j).getIsDeleted())
                        throw new BookAlreadyAddedException("This book has already been added!");
            }
        }
        else
        {
            if(LibrarianService.getPartnerBooks().get(partnerId - 1).size() > 1)
                for (int j = 0; j < LibrarianService.getPartnerBooks().get(partnerId - 1).size(); j++)
                    if (LibrarianService.getPartnerBooks().get(partnerId - 1).get(j).getAuthor().equals(author.get(author.size() - 1)) && LibrarianService.getPartnerBooks().get(partnerId - 1).get(j).getTitle().equals(title.get(title.size() - 1)) && LibrarianService.getPartnerBooks().get(partnerId - 1).get(j).getIsDeleted())
                        throw new BookAlreadyAddedException("This book has already been added!");
        }

        System.out.println("Description: ");
        desc = myObj.nextLine();
        description.add(desc);

        LibrarianService.getSectionBooks().get(sectionId - 1).add(new Books(++bookId, author.get(author.size() - 1), title.get(title.size() - 1), description.get(description.size() - 1), false, false, sectionId, partnerId));
        LibrarianService.getPartnerBooks().get(partnerId - 1).add(new Books(bookId, author.get(author.size() - 1), title.get(title.size() - 1), description.get(description.size() - 1), false, false,  sectionId, partnerId));

        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO BOOK VALUES(" + bookId + ", '" + author.get(author.size() - 1) + "', '" + title.get(title.size() - 1) + "', '" + description.get(description.size() - 1) + "', 'FALSE', " + sectionId + ", " + partnerId + ")");
        statement.close();

        counter = 0;
    }

    public void printPartnerBooks() throws SQLException {
        int countPartnerBooks;
        Connection connection = librarianService.getConnection();

        Statement statement1 = connection.createStatement();
        ResultSet result1 = statement1.executeQuery("SELECT COUNT(*) FROM BOOK WHERE PARTNER_ID = " + partnerId);

        result1.next();
        countPartnerBooks = result1.getInt(1);

        statement1.close();

        if (countPartnerBooks == 0)
            System.out.println("\nYou didn't add any books!");
        else {
            Statement statement2 = connection.createStatement();
            ResultSet result2 = statement2.executeQuery("SELECT * FROM BOOK WHERE PARTNER_ID = " + partnerId);

            System.out.println("\nYour books:\n");

            while (result2.next() && countPartnerBooks > 1) {
                int bookId = result2.getInt(1);
                String author = result2.getString(2);
                String title = result2.getString(3);
                String description = result2.getString(4);
                System.out.println("book id: " + bookId);
                System.out.println("\tauthor: " + author);
                System.out.println("\ttitle: " + title);
                System.out.println("\tdescription: " + description);
                System.out.print("\n");
                countPartnerBooks--;
            }
            if(countPartnerBooks == 1){
                int bookId = result2.getInt(1);
                String author = result2.getString(2);
                String title = result2.getString(3);
                String description = result2.getString(4);
                System.out.println("book id: " + bookId);
                System.out.println("\tauthor: " + author);
                System.out.println("\ttitle: " + title);
                System.out.println("\tdescription: " + description);
            }
            statement2.close();
        }
    }
}
