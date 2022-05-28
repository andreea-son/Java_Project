import java.util.ArrayList;
import java.util.Scanner;

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

    public PartnerService(){
    }

    public void loginInformation() throws EmailNotFoundException, IncorrectPasswordException, IncorrectPassFormatException{
        int ok = 0;
        String defaultPassword;
        String partnerPassword1;
        String partnerPassword2;

        System.out.println("\nEmail: ");
        partnerEmail = myObj.nextLine();
        for (int j = 0; j < librarian.getPartners().size(); j++)
            if (librarian.getPartners().get(j).getPartnerEmail().equals(partnerEmail) && !librarian.getPartners().get(j).getIsDeleted()) {
                partnerId = librarian.getPartners().get(j).getPartnerId();
                ok = 1;
            }

        if (ok == 0) {
            throw new EmailNotFoundException("Could not find partner with this email!");
        }

        if (librarian.getPartners().get(partnerId - 1).getNumOfLogins() == 0) {
            System.out.println("Because this is the first time you login, we generated a default password for you.");
            System.out.println("It consists of all the characters before the @ in your email.");
            System.out.println("Default password: ");

            defaultPassword = myObj.nextLine();

            String[] password = partnerEmail.split("@");
            String aux = password[0];

            if (!defaultPassword.equals(aux))
                throw new IncorrectPasswordException("You entered the wrong default password!");
            System.out.println("Enter your new password in the following format: ");
            System.out.println("At least 8 characters, minimum 1 lowercase, 1 uppercase, 1 special character and 1 digit");

            partnerPassword1 = myObj.nextLine();

            if (!partnerPassword1.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[_\\.#\\*\\+\\-\\$&!\\?@='\\[\\]\\{\\},`~\\(\\)]).{8,}"))
                throw new IncorrectPassFormatException("This password doesn't have the required format!");

            librarian.getPartners().get(partnerId - 1).setPartnerPassword(partnerPassword1);
        } else {

            System.out.println("Password: ");

            partnerPassword2 = myObj.nextLine();

            if (!librarian.getPartners().get(partnerId - 1).getPartnerPassword().equals(partnerPassword2))
                throw new IncorrectPasswordException("You entered the wrong password!");
        }

        librarian.getPartners().get(partnerId - 1).setNumOfLogins(++i);
    }

    public void addNewBook() throws SectionNotFoundException, BookAlreadyAddedException{
        for (ArrayList<Books> partnerBook : librarian.getPartnerBooks())
            for (int j = 0; j < partnerBook.size(); j++)
                ++counter;

        bookId = counter;
        String aut;
        String t;
        String desc;
        int sectionId;
        int ok = 0;
        System.out.println("\nSection ID: ");
        sectionId = myObj.nextInt();
        myObj.nextLine();

        for(int i = 0; i < librarian.getSections().size(); i++)
            if(librarian.getSections().get(i).getSectionId() == sectionId && !librarian.getSections().get(i).getIsDeleted()) {
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

        if (librarian.getPartners().size() > 1) {
            for (int i = 0; i < librarian.getPartners().size(); i++) {
                for (int j = 0; j < librarian.getPartnerBooks().get(i).size(); j++)
                    if (librarian.getPartnerBooks().get(i).get(j).getAuthor().equals(author.get(author.size() - 1)) && librarian.getPartnerBooks().get(i).get(j).getTitle().equals(title.get(title.size() - 1)) && !librarian.getPartnerBooks().get(i).get(j).getIsDeleted())
                        throw new BookAlreadyAddedException("This book has already been added!");
            }
        }
        else
        {
            if(librarian.getPartnerBooks().get(partnerId - 1).size() > 1)
                for (int j = 0; j < librarian.getPartnerBooks().get(partnerId - 1).size(); j++)
                    if (librarian.getPartnerBooks().get(partnerId - 1).get(j).getAuthor().equals(author.get(author.size() - 1)) && librarian.getPartnerBooks().get(partnerId - 1).get(j).getTitle().equals(title.get(title.size() - 1)) && librarian.getPartnerBooks().get(partnerId - 1).get(j).getIsDeleted())
                        throw new BookAlreadyAddedException("This book has already been added!");
        }

        System.out.println("Description: ");
        desc = myObj.nextLine();
        description.add(desc);

        librarian.getSectionBooks().get(sectionId - 1).add(new Books(++bookId, author.get(author.size() - 1), title.get(title.size() - 1), description.get(description.size() - 1), false, false, sectionId, partnerId));
        librarian.getPartnerBooks().get(partnerId - 1).add(new Books(bookId, author.get(author.size() - 1), title.get(title.size() - 1), description.get(description.size() - 1), false, false,  sectionId, partnerId));
        counter = 0;
        System.out.println("Book added successfully!");

    }

    public void printPartnerBooks(){
        int ok = 0;

        if(librarian.getPartnerBooks().get(partnerId - 1).size() > 0){
            for (int j = 0; j < librarian.getPartnerBooks().get(partnerId - 1).size(); j++)
                if(!librarian.getPartnerBooks().get(partnerId - 1).get(j).getIsDeleted()) {
                    ok = 1;
                    break;
                }
            if(ok == 0)
                System.out.println("\nYou didn't add any books!");
            else {
                System.out.println("\nYour books:\n");
                for (int j = 0; j < librarian.getPartnerBooks().get(partnerId - 1).size(); j++)
                    if(!librarian.getPartnerBooks().get(partnerId - 1).get(j).getIsDeleted()) {
                        librarian.getPartnerBooks().get(partnerId - 1).get(j).print();
                        System.out.print("\n");
                    }
            }
        }
        else{
             System.out.println("\nYou didn't add any books!");
        }
    }
}
