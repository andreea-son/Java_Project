import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class PartnerService {
    private static ArrayList<ArrayList<Books>> sectionBooks = new ArrayList<>(100);
    private static ArrayList<ArrayList<Books>> partnerBooks = new ArrayList<>(100);
    private static ArrayList<String> author = new ArrayList<>();
    private static ArrayList<String> title = new ArrayList<>();
    private static ArrayList<String> description = new ArrayList<>();
    private static String partnerEmail;
    private static String partnerName;
    private static int partnerId = LibrarianService.getPartners().size();
    private static int i;
    private static int bookId;
    private static int counter;
    private Scanner myObj = new Scanner(System.in);
    private Console console = System.console();
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public static ArrayList<ArrayList<Books>> getPartnerBooks() {
        for(int i = 0; i < 100; i++)
            partnerBooks.add(new ArrayList<>());
        return partnerBooks;
    }

    public static ArrayList<ArrayList<Books>> getSectionBooks() {
        for(int i = 0; i < 100; i++)
            sectionBooks.add(new ArrayList<>());
        return sectionBooks;
    }

    public PartnerService(){
    }

    public void loginInformation() throws EmailNotFoundException, IncorrectPasswordException, IncorrectPassFormatException{
        LibrarianService.setCounter(LibrarianService.getCounter() + 1);
        LibrarianService.getActions1().set(LibrarianService.getCounter(), new MyAction("partner: login", dtf.format(LocalDateTime.now())));

        int ok = 0;
        System.out.println("Enter your email: ");
        partnerEmail = myObj.nextLine();
        for (int j = 0; j < LibrarianService.getPartners().size(); j++)
            if (LibrarianService.getPartners().get(j).getPartnerEmail().equals(partnerEmail)) {
                partnerId = LibrarianService.getPartners().get(j).getPartnerId();
                partnerName = LibrarianService.getPartners().get(j).getPartnerName();
                ok = 1;
            }

        if (ok == 0) {
            throw new EmailNotFoundException("Could not find partner with this email! \n");
        }

        if (LibrarianService.getPartners().get(partnerId - 1).getNumOfLogins() == 0) {
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

            LibrarianService.getPartners().get(partnerId - 1).setPartnerPassword(partnerPassword1);
        } else {

            System.out.println("Enter your password: ");
            char[] passwordChars3 = console.readPassword();
            String partnerPassword2 = new String(passwordChars3);

            if (!LibrarianService.getPartners().get(partnerId - 1).getPartnerPassword().equals(partnerPassword2))
                throw new IncorrectPasswordException("You entered the wrong password!\n");
        }

        LibrarianService.getPartners().get(partnerId - 1).setNumOfLogins(++i);
    }

    public void addNewBook() throws SectionNotFoundException, BookAlreadyAddedException{
        LibrarianService.setCounter(LibrarianService.getCounter() + 1);
        LibrarianService.getActions1().set(LibrarianService.getCounter(), new MyAction("partner: add new book", dtf.format(LocalDateTime.now())));

        for(int i = 0; i < 100; i++)
            sectionBooks.add(new ArrayList<>());
        for(int i = 0; i < 100; i++)
            partnerBooks.add(new ArrayList<>());

        for (ArrayList<Books> partnerBook : partnerBooks)
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

        for(int i = 0; i < LibrarianService.getSections().size(); i++)
            if(LibrarianService.getSections().get(i).getSectionId() == sectionId) {
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

        if (LibrarianService.getPartners().size() > 1) {
            for (int i = 0; i < LibrarianService.getPartners().size(); i++) {
                for (int j = 0; j < LibrarianService.getPartners().get(i).getPartnerBooks().size(); j++)
                    if (LibrarianService.getPartners().get(i).getPartnerBooks().get(j).getAuthor().equals(author.get(author.size() - 1)) && LibrarianService.getPartners().get(i).getPartnerBooks().get(j).getTitle().equals(title.get(title.size() - 1)))
                        throw new BookAlreadyAddedException("This book has already been added! \n");
            }
        }
        else
        {
            if(LibrarianService.getPartners().get(partnerId - 1).getPartnerBooks().size() > 1)
                for (int j = 0; j < LibrarianService.getPartners().get(partnerId - 1).getPartnerBooks().size(); j++)
                    if (LibrarianService.getPartners().get(partnerId - 1).getPartnerBooks().get(j).getAuthor().equals(author.get(author.size() - 1)) && LibrarianService.getPartners().get(partnerId - 1).getPartnerBooks().get(j).getTitle().equals(title.get(title.size() - 1)))
                        throw new BookAlreadyAddedException("This book has already been added! \n");
        }

        System.out.println("Enter the description of the book you want to add: ");
        desc = myObj.nextLine();
        description.add(desc);

        System.out.print("\n");
        sectionBooks.get(sectionId - 1).add(0, new Books(++bookId, author.get(author.size() - 1), title.get(title.size() - 1), description.get(description.size() - 1), false));
        LibrarianService.getSections().set(sectionId - 1, new Sections(sectionId, LibrarianService.getSections().get(sectionId - 1).getSectionName(), sectionBooks.get(sectionId - 1)));
        partnerBooks.get(partnerId - 1).add(0, new Books(bookId, author.get(author.size() - 1), title.get(title.size() - 1), description.get(description.size() - 1), false));
        LibrarianService.getPartners().set(partnerId - 1, new Partners(partnerId, i, partnerName, LibrarianService.getPartners().get(partnerId - 1).getPartnerPassword(), partnerEmail, partnerBooks.get(partnerId - 1)));
        counter = 0;
    }

    public void printPartnerBooks(){
        LibrarianService.setCounter(LibrarianService.getCounter() + 1);
        LibrarianService.getActions1().set(LibrarianService.getCounter(), new MyAction("partner: print books", dtf.format(LocalDateTime.now())));

        if(LibrarianService.getPartners().get(partnerId - 1).getPartnerBooks().size() > 0){
            System.out.println("The books added by you: ");
            for(int j = 0; j < LibrarianService.getPartners().get(partnerId - 1).getPartnerBooks().size(); j++) {
                LibrarianService.getPartners().get(partnerId - 1).getPartnerBooks().get(j).print();
                System.out.print("\n");
            }
        }
        else{
            System.out.println("You did not add any books!");
        }
    }
}
