import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
public class PartnerMenu {
    private Scanner myObj = new Scanner(System.in);
    private static ArrayList<ArrayList<Books>> sectionBooks = new ArrayList<ArrayList<Books>>(100);
    private static ArrayList<ArrayList<Books>> partnerBooks = new ArrayList<ArrayList<Books>>(100);
    private static ArrayList<String> author = new ArrayList<String>();
    private static ArrayList<String> title = new ArrayList<String>();
    private static ArrayList<String> description = new ArrayList<String>();
    private static LibrarianMenu librarian = new LibrarianMenu();
    private static int bookId;
    private static String partnerEmail;
    private static String partnerName = "";
    private static String partnerPassword;
    private static String aux;
    private static int partnerId = 0;
    private static Console console = System.console();
    private static int i;

    public PartnerMenu(){
    }

    public void loginInformation() throws EmailNotFoundException, IncorrectPasswordException, IncorrectPassFormatException{
        System.out.println("Enter your email: ");
        partnerEmail = myObj.nextLine();
        for(int j = 0; j < librarian.getPartners().size(); j++)
            if(librarian.getPartners().get(j).getPartnerEmail().equals(partnerEmail)){
                partnerId = librarian.getPartners().get(j).getPartnerId();
                partnerName = librarian.getPartners().get(j).getPartnerName();
            }

        if(!librarian.getPartners().get(partnerId - 1).getPartnerEmail().equals(partnerEmail)){
            throw new EmailNotFoundException("Could not find partner with this email! \n");
        }

        if(librarian.getPartners().get(partnerId - 1).getNumOfLogins() == 0) {
            System.out.println("Because this is the first time you login, we generated a default password for you.");
            System.out.println("It consists of all the characters before the @ in your email.");
            System.out.println("Enter default password: ");

            char[] passwordChars1 = console.readPassword();
            String defaultPassword = new String(passwordChars1);

            String password[] = partnerEmail.split("@");
            aux = password[0];
            if(!defaultPassword.equals(aux))
                throw new IncorrectPasswordException("You entered the wrong default password!\n");
            System.out.println("Enter your new password in the following format: ");
            System.out.println("At least 8 characters, minimum 1 lowercase, 1 uppercase, 1 special character and 1 digit");

            char[] passwordChars2 = console.readPassword();
            String partnerPassword1 = new String(passwordChars2);

            if (!partnerPassword1.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[_\\.#\\*\\+\\-\\$&!\\?@='\\[\\]\\{\\},`~\\(\\)]).{8,}"))
                throw new IncorrectPassFormatException("This password doesn't have the required format! \n");

            librarian.getPartners().get(partnerId - 1).setPartnerPassword(partnerPassword1);
        }
        else{

            System.out.println("Enter your password: ");
            char[] passwordChars3 = console.readPassword();
            String partnerPassword2 = new String(passwordChars3);

            if(!librarian.getPartners().get(partnerId - 1).getPartnerPassword().equals(partnerPassword2))
                throw new IncorrectPasswordException("You entered the wrong password!\n");
        }

        librarian.getPartners().get(partnerId - 1).setNumOfLogins(++i);
    }

    public void addNewBook() throws SectionNotFoundException, WrongInputException, BookAlreadyAddedException{
        for(int i = 0; i < 100; i++)
            sectionBooks.add(new ArrayList<Books>());
        for(int i = 0; i < 100; i++)
            partnerBooks.add(new ArrayList<Books>());
        String aut;
        String t;
        String desc;
        int sectionId;
        System.out.println("Enter the section id of the book you want to add: ");
        sectionId = myObj.nextInt();
        myObj.nextLine();
        if (sectionId > librarian.getSections().size()) {
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
                for (int j = 0; j < librarian.getPartners().get(i).getPartnerBooks().size(); j++)
                    if (librarian.getPartners().get(i).getPartnerBooks().get(j).getAuthor().equals(author.get(author.size() - 1)) && librarian.getPartners().get(i).getPartnerBooks().get(j).getTitle().equals(title.get(title.size() - 1)))
                        throw new BookAlreadyAddedException("This book has already been added! \n");
            }
        }
        else
        {
            if(librarian.getPartners().get(partnerId - 1).getPartnerBooks().size() > 1)
                for (int j = 0; j < librarian.getPartners().get(partnerId - 1).getPartnerBooks().size(); j++)
                    if (librarian.getPartners().get(partnerId - 1).getPartnerBooks().get(j).getAuthor().equals(author.get(author.size() - 1)) && librarian.getPartners().get(partnerId - 1).getPartnerBooks().get(j).getTitle().equals(title.get(title.size() - 1)))
                        throw new BookAlreadyAddedException("This book has already been added! \n");
        }

        System.out.println("Enter the description of the book you want to add: ");
        desc = myObj.nextLine();
        description.add(desc);

        System.out.print("\n");
        sectionBooks.get(sectionId).add(0, new Books(++bookId, author.get(author.size() - 1), title.get(title.size() - 1), description.get(description.size() - 1), false));
        librarian.getSections().set(sectionId - 1, new Sections(sectionId, librarian.getSections().get(sectionId - 1).getSectionName(), sectionBooks.get(sectionId)));
        partnerBooks.get(partnerId).add(0, new Books(bookId, author.get(author.size() - 1), title.get(title.size() - 1), description.get(description.size() - 1), false));
        librarian.getPartners().set(partnerId - 1, new Partners(partnerId, i, partnerName, partnerPassword, partnerEmail, partnerBooks.get(partnerId)));
    }

    public void printPartnerBooks(){
        System.out.println("The books added by you: ");
        if(librarian.getPartners().get(partnerId - 1).getPartnerBooks().size() > 0){
            for(int j = 0; j < librarian.getPartners().get(partnerId - 1).getPartnerBooks().size(); j++)
                librarian.getPartners().get(partnerId - 1).getPartnerBooks().get(j).print();
        }
        else{
            System.out.println("You did not add any books!");
        }
    }
}
