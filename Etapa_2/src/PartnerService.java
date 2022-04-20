import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class PartnerService {
    private static ArrayList<String> author = new ArrayList<>();
    private static ArrayList<String> title = new ArrayList<>();
    private static ArrayList<String> description = new ArrayList<>();
    private static String partnerEmail;
    private static LibrarianService librarian = new LibrarianService();
    private static int partnerId = librarian.getPartners().size();
    private static int i;
    private static int bookId;
    private static int counter;
    private Scanner myObj = new Scanner(System.in);
    private Console console = System.console();
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public PartnerService(){
    }

    public void loginInformation() throws EmailNotFoundException, IncorrectPasswordException, IncorrectPassFormatException{
        librarian.setCounter(librarian.getCounter() + 1);
        librarian.getActions1().set(librarian.getCounter(), new MyAction("partner: login", dtf.format(LocalDateTime.now())));

        int ok = 0;
        System.out.println("Enter your email: ");
        partnerEmail = myObj.nextLine();
        for (int j = 0; j < librarian.getPartners().size(); j++)
            if (librarian.getPartners().get(j).getPartnerEmail().equals(partnerEmail) && !librarian.getPartners().get(j).getIsDeleted()) {
                partnerId = librarian.getPartners().get(j).getPartnerId();
                ok = 1;
            }

        if (ok == 0) {
            throw new EmailNotFoundException("Could not find partner with this email! \n");
        }

        if (librarian.getPartners().get(partnerId - 1).getNumOfLogins() == 0) {
            System.out.println("Because this is the first time you login, we generated a default password for you.");
            System.out.println("It consists of all the characters before the @ in your email.");
            System.out.println("Enter default password: ");

            char[] passwordChars1 = console.readPassword();
            String defaultPassword = new String(passwordChars1);

            String[] password = partnerEmail.split("@");
            String aux = password[0];

            if (!defaultPassword.equals(aux))
                throw new IncorrectPasswordException("You entered the wrong default password!\n");
            System.out.println("Enter your new password in the following format: ");
            System.out.println("At least 8 characters, minimum 1 lowercase, 1 uppercase, 1 special character and 1 digit");

            char[] passwordChars2 = console.readPassword();
            String partnerPassword1 = new String(passwordChars2);

            if (!partnerPassword1.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[_\\.#\\*\\+\\-\\$&!\\?@='\\[\\]\\{\\},`~\\(\\)]).{8,}"))
                throw new IncorrectPassFormatException("This password doesn't have the required format! \n");

            librarian.getPartners().get(partnerId - 1).setPartnerPassword(partnerPassword1);
        } else {

            System.out.println("Enter your password: ");
            char[] passwordChars3 = console.readPassword();
            String partnerPassword2 = new String(passwordChars3);

            if (!librarian.getPartners().get(partnerId - 1).getPartnerPassword().equals(partnerPassword2))
                throw new IncorrectPasswordException("You entered the wrong password!\n");
        }

        librarian.getPartners().get(partnerId - 1).setNumOfLogins(++i);
    }

    public void addNewBook() throws SectionNotFoundException, BookAlreadyAddedException{
        librarian.setCounter(librarian.getCounter() + 1);
        librarian.getActions1().set(librarian.getCounter(), new MyAction("partner: add new book", dtf.format(LocalDateTime.now())));

        for (ArrayList<Books> partnerBook : librarian.getPartnerBooks())
            for (int j = 0; j < partnerBook.size(); j++)
                ++counter;

        bookId = counter;
        String aut;
        String t;
        String desc;
        int sectionId;
        int ok = 0;
        System.out.println("Enter the section ID of the book you want to add: ");
        sectionId = myObj.nextInt();
        myObj.nextLine();

        for(int i = 0; i < librarian.getSections().size(); i++)
            if(librarian.getSections().get(i).getSectionId() == sectionId && !librarian.getSections().get(i).getIsDeleted()) {
                ok = 1;
                break;
            }

        if (ok == 0) {
            throw new SectionNotFoundException("Could not find section with this ID! \n");
        }

        System.out.println("Enter the title of the book you want to add: ");
        t = myObj.nextLine();
        title.add(t);
        System.out.println("Enter the author of the book you want to add: ");
        aut = myObj.nextLine();
        author.add(aut);

        if (librarian.getPartners().size() > 1) {
            for (int i = 0; i < librarian.getPartners().size(); i++) {
                for (int j = 0; j < librarian.getPartnerBooks().get(i).size(); j++)
                    if (librarian.getPartnerBooks().get(i).get(j).getAuthor().equals(author.get(author.size() - 1)) && librarian.getPartnerBooks().get(i).get(j).getTitle().equals(title.get(title.size() - 1)) && !librarian.getPartnerBooks().get(i).get(j).getIsDeleted())
                        throw new BookAlreadyAddedException("This book has already been added! \n");
            }
        }
        else
        {
            if(librarian.getPartnerBooks().get(partnerId - 1).size() > 1)
                for (int j = 0; j < librarian.getPartnerBooks().get(partnerId - 1).size(); j++)
                    if (librarian.getPartnerBooks().get(partnerId - 1).get(j).getAuthor().equals(author.get(author.size() - 1)) && librarian.getPartnerBooks().get(partnerId - 1).get(j).getTitle().equals(title.get(title.size() - 1)) && librarian.getPartnerBooks().get(partnerId - 1).get(j).getIsDeleted())
                        throw new BookAlreadyAddedException("This book has already been added! \n");
        }

        System.out.println("Enter the description of the book you want to add: ");
        desc = myObj.nextLine();
        description.add(desc);

        System.out.print("\n");
        librarian.getSectionBooks().get(sectionId - 1).add(new Books(++bookId, author.get(author.size() - 1), title.get(title.size() - 1), description.get(description.size() - 1), false, false, sectionId, partnerId));
        librarian.getPartnerBooks().get(partnerId - 1).add(new Books(bookId, author.get(author.size() - 1), title.get(title.size() - 1), description.get(description.size() - 1), false, false,  sectionId, partnerId));
        counter = 0;
    }

    public void printPartnerBooks(){
        librarian.setCounter(librarian.getCounter() + 1);
        librarian.getActions1().set(librarian.getCounter(), new MyAction("partner: print books", dtf.format(LocalDateTime.now())));

        int ok = 0;

        if(librarian.getPartnerBooks().get(partnerId - 1).size() > 0){
            for (int j = 0; j < librarian.getPartnerBooks().get(partnerId - 1).size(); j++)
                if(!librarian.getPartnerBooks().get(partnerId - 1).get(j).getIsDeleted()) {
                    ok = 1;
                    break;
                }
            if(ok == 0)
                System.out.println("You did not add any books!\n");
            else {
                System.out.println("The books added by you: ");
                for (int j = 0; j < librarian.getPartnerBooks().get(partnerId - 1).size(); j++)
                    if(!librarian.getPartnerBooks().get(partnerId - 1).get(j).getIsDeleted()) {
                        librarian.getPartnerBooks().get(partnerId - 1).get(j).print();
                        System.out.print("\n");
                    }
            }
        }
        else{
            System.out.println("You did not add any books!\n");
        }
    }
}
