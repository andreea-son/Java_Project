import java.util.ArrayList;
import java.util.Scanner;
public class Menu {
    private static int partnerId;
    private static int bookId1;
    private static int userId1;
    private static int invoiceId;
    private static int counter1;
    private static int counter2;
    private int numOfBooksUser;
    private String typeOfUser;
    private char yn;
    private Scanner myObj = new Scanner(System.in);
    private ArrayList<Partners> partners = new ArrayList<Partners>();
    private ArrayList<Sections> sections = new ArrayList<Sections>();
    private ArrayList<Users> users = new ArrayList<Users>();
    private ArrayList<String> author = new ArrayList<String>();
    private ArrayList<String> title = new ArrayList<String>();
    private ArrayList<String> description = new ArrayList<String>();
    private ArrayList<String> sectionName = new ArrayList<String>();
    private ArrayList<Invoice> invoice = new ArrayList<Invoice>();
    private ArrayList<ArrayList<Books>> sectionBooks = new ArrayList<ArrayList<Books>>(100);
    private ArrayList<ArrayList<Books>> partnerBooks = new ArrayList<ArrayList<Books>>(100);
    private ArrayList<ArrayList<LendedBooks>> userLendedBooks = new ArrayList<ArrayList<LendedBooks>>(100);

    public Menu(){}

    //adding new section
    public void addNewSection() throws AlreadyUsedNameException{
        ++counter1;
        String author;
        String title;
        String description;
        String sn;

        System.out.println("Enter the name of the new section: ");
        sn = myObj.nextLine();
        sectionName.add(sn);
        if(sections.size() >= 1)
            for(int j = 0; j < sections.size(); j++)
                if(sections.get(j).getSectionName().equals(sectionName.get(counter1 - 1))){
                    throw new AlreadyUsedNameException("This name has already been used for another section! \n");
                }
        sections.add(new Sections(counter1, sectionName.get(counter1 - 1)));
    }

    //printing sections
    public void printSections(){
        for(int i = 0; i < sections.size(); i++)
            sections.get(i).print();
    }

    //adding new partner
    public void addNewPartner() throws AlreadyUsedNameException, AlreadyUsedEmailException, IncorrectMailFormatException, IncorrectPassFormatException{
        ++partnerId;
        String partnerName;
        String partnerPassword;
        String partnerEmail = "";
        System.out.println("Enter the name of the new partner: ");
        partnerName = myObj.nextLine();

        if(partners.size() >= 1)
            for(int j = 0; j < partners.size(); j++)
                if(partners.get(j).getPartnerName().equals(partnerName)){
                    throw new AlreadyUsedNameException("This name has already been used for another partner! \n");
                }

        System.out.println("Enter the password of the new partner in the following format: ");
        System.out.println("At least 8 characters, minimum 1 lowercase, 1 uppercase, 1 special character and 1 digit");
        partnerPassword = myObj.nextLine();

        if(!partnerPassword.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[_\\.#\\*\\+\\-\\$&!\\?@='\\[\\]\\{\\},`~\\(\\)]).{8,}")){
            throw new IncorrectPassFormatException("This password doesn't have the required format! \n");
        }

        System.out.println("Enter the email of the new partner in the following format: ");
        System.out.println("Any uppercase/lowercase letter or digit or the special characters \"_\", \".\" and \"@\"");
        partnerEmail = myObj.nextLine();
        System.out.print("\n");

        if(!partnerEmail.matches("([a-z|A-Z|0-9]+[_|\\.]?[a-z|A-Z|0-9]+)@([a-z|A-Z]+[_|\\.]?[a-z|A-Z]+)\\.([a-z]+)")){
            throw new IncorrectMailFormatException("This email doesn't have the required format! \n");
        }

        if(partners.size() >= 1)
            for(int j = 0; j < partners.size(); j++)
                if(partners.get(j).getPartnerEmail().equals(partnerEmail)){
                    throw new AlreadyUsedEmailException("This email has already been used for another partner! \n");
                }
        partners.add(new Partners(partnerId, partnerName, partnerPassword, partnerEmail));
    }

    //add new book
    public void addNewBook() throws SectionNotFoundException, WrongInputException, EmailNotFoundException, IncorrectPasswordException, BookAlreadyAddedException{
        for(int i = 0; i < 100; i++)
            sectionBooks.add(new ArrayList<Books>());
        for(int i = 0; i < 100; i++)
            partnerBooks.add(new ArrayList<Books>());
        String partnerName = "";
        String partnerPassword;
        String partnerEmail;
        String aut;
        String t;
        String desc;
        int sectionId;
        int partnerId2 = 0;
        int ok = 0;
        System.out.println("Are you a partner? (y/n): ");
        yn = myObj.next().charAt(0);
        myObj.nextLine();
        if(yn != 'y' && yn != 'n') {
            throw new WrongInputException("There is no such option! Please type y or n \n");
        }
        if(yn == 'y'){
            System.out.println("Enter your email: ");
            partnerEmail = myObj.nextLine();

            for(int j = 0; j < partners.size(); j++)
                if(partners.get(j).getPartnerEmail().equals(partnerEmail)){
                    partnerId2 = partners.get(j).getPartnerId();
                    partnerName = partners.get(j).getPartnerName();
                }

            for(int j = 0; j < partners.size(); j++)
                if(partners.get(j).getPartnerEmail().equals(partnerEmail)){
                    ok = 1;
                }
            if(ok == 0)
                throw new EmailNotFoundException("Could not find partner with this email! \n");

            System.out.println("Enter your password: ");
            partnerPassword = myObj.nextLine();

            if(!partners.get(partnerId2 - 1).getPartnerPassword().equals(partnerPassword))
                throw new IncorrectPasswordException("You introduced the wrong password! \n");

            System.out.println("Enter the section id of the book you want to add: ");
            sectionId = myObj.nextInt();
            myObj.nextLine();
            if (sectionId > sections.size()) {
                throw new SectionNotFoundException("Could not find section with this ID! \n");
            }
            System.out.println("Enter the title of the book you want to add: ");
            t = myObj.nextLine();
            title.add(t);
            System.out.println("Enter the author of the book you want to add: ");
            aut = myObj.nextLine();
            author.add(aut);

            if(partners.size() >= 1)
                for(int i = 0; i < partners.size(); i++)
                    for(int j = 0; j < partners.get(i).getPartnerBooks().size(); j++)
                        if(partners.get(j).getPartnerBooks().get(j).getAuthor().equals(author.get(author.size() - 1)) && partners.get(j).getPartnerBooks().get(j).getTitle().equals(title.get(title.size() - 1))){
                            throw new BookAlreadyAddedException("This book has already been added! \n");
                    }

            System.out.println("Enter the description of the book you want to add: ");
            desc = myObj.nextLine();
            description.add(desc);
            System.out.print("\n");
            sectionBooks.get(sectionId).add(0, new Books(++bookId1, author.get(author.size() - 1), title.get(title.size() - 1), description.get(description.size() - 1), false));
            sections.set(sectionId - 1, new Sections(sectionId, sectionName.get(sectionId - 1), sectionBooks.get(sectionId)));
            partnerBooks.get(partnerId2).add(0, new Books(bookId1, author.get(author.size() - 1), title.get(title.size() - 1), description.get(description.size() - 1), false));
            partners.set(partnerId2 - 1, new Partners(partnerId2, partnerName, partnerPassword, partnerEmail, partnerBooks.get(partnerId2)));
        }
    }

    //printing partners
    public void printPartners(){
        for(int i = 0; i < partners.size(); i++)
            partners.get(i).print();
    }

    //adding new user
    public void addNewUser() throws BookNotFoundException, AlreadyUsedNameException, AlreadyUsedEmailException, IncorrectPasswordException, IncorrectMailFormatException, IncorrectPassFormatException{
        ++userId1;
        String userName;
        String userEmail;
        String userPassword;

        System.out.println("Enter the name of the new user: ");
        userName = myObj.nextLine();
        if(users.size() >= 1)
            for(int j = 0; j < users.size(); j++)
                if(users.get(j).getUserName().equals(userName)){
                    throw new AlreadyUsedNameException("This username has already been used! \n");
                }

        System.out.println("Enter the password of the new user in the following format: ");
        System.out.println("At least 8 characters, minimum 1 lowercase, 1 uppercase, 1 special character and 1 digit ");
        userPassword = myObj.nextLine();

        if(!userPassword.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[_\\.#\\*\\+\\-\\$&!\\?@='\\[\\]\\{\\},`~\\(\\)]).{8,}")){
            throw new IncorrectPassFormatException("This password doesn't have the required format! \n");
        }

        System.out.println("Enter the email of the new user in the following format: ");
        System.out.println("Any uppercase/lowercase letter or digit or the special characters \"_\", \".\" and \"@\"");
        userEmail = myObj.nextLine();
        System.out.print("\n");

        if(!userEmail.matches("([a-z|A-Z|0-9]+[_|\\.]?[a-z|A-Z|0-9]+)@([a-z|A-Z]+[_|\\.]?[a-z|A-Z]+)\\.([a-z]+)")){
            throw new IncorrectMailFormatException("This email doesn't have the required format! \n");
        }

        if(users.size() >= 1)
            for(int j = 0; j < users.size(); j++)
                if(users.get(j).getUserEmail().equals(userEmail)){
                    throw new AlreadyUsedEmailException("This email has already been used! \n");
                }
        users.add(new Users(userId1, userName, userPassword, userEmail));
    }

    //lend a book
    public void lendNewBook() throws BookNotFoundException, BookAlreadyLendedException, EmailNotFoundException, IncorrectPasswordException, IncorrectDateFormatException, MaxNumOfDaysException{
        for(int i = 0; i < 100; i++)
            userLendedBooks.add(new ArrayList<LendedBooks>());
        String userName = "";
        String userPassword;
        String userEmail;
        int userId2 = 0;
        int bookId2;
        int daysFromIssue;
        String issuedDate;
        int ok = 0;

        System.out.println("Enter your email: ");
        userEmail = myObj.nextLine();

        for(int j = 0; j < users.size(); j++)
            if(users.get(j).getUserEmail().equals(userEmail)){
                userId2 = users.get(j).getUserId();
                userName = users.get(j).getUserName();
            }

        for(int j = 0; j < users.size(); j++)
            if(users.get(j).getUserEmail().equals(userEmail)){
                ok = 1;
            }

        if(ok == 0)
            throw new EmailNotFoundException("Could not find user with this email! \n");

        System.out.println("Enter your password: ");
        userPassword = myObj.nextLine();

        if(!users.get(userId2 - 1).getUserPassword().equals(userPassword))
            throw new IncorrectPasswordException("You introduced the wrong password! \n");

        System.out.println("Enter the id of the book you want to lend: ");
        bookId2 = myObj.nextInt();
        myObj.nextLine();

        if (bookId2 > bookId1) {
            throw new BookNotFoundException("Could not find book with this ID! \n");
        }

        for(int j = 0; j < partners.size(); j++){
            int nob = partners.get(j).getPartnerBooks().size();
            for(int k = 0; k < nob; k++)
                if((partners.get(j).getPartnerBooks().get(k).getBookId() == bookId2) && (partners.get(j).getPartnerBooks().get(k).getIsLended() == true)) {
                    throw new BookAlreadyLendedException("This book is not available for lending right now! \n");
                }
        }

        System.out.println("Enter issue date (yyyy-MM-dd): ");
        issuedDate = myObj.nextLine();

        if(!issuedDate.matches("(((200)|(201))[0-9]|(202)[012])-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")){
            throw new IncorrectDateFormatException("This date doesn't match the requested date format! \n");
        }

        System.out.println("Enter how many days you want to keep the book (max 60 days): ");
        daysFromIssue = myObj.nextInt();
        myObj.nextLine();
        if(daysFromIssue > 60){
            throw new MaxNumOfDaysException("Too many days! \n");
        }
        System.out.print("\n");

        for(int j = 0; j < partners.size(); j++){
            int nob = partners.get(j).getPartnerBooks().size();
            for(int k = 0; k < nob; k++)
                if((partners.get(j).getPartnerBooks().get(k).getBookId() == bookId2)) {
                    partners.get(j).getPartnerBooks().get(k).setIsLended(true);
                }
        }
        userLendedBooks.get(userId2).add(0, new LendedBooks(bookId2, author.get(bookId2 - 1), title.get(bookId2 - 1), description.get(bookId2 - 1), issuedDate, daysFromIssue, true));
        users.set(userId2 - 1, new Users(userId2, userName, userPassword, userEmail, userLendedBooks.get(userId2)));
    }

    //printing users
    public void printUsers(){
        for(int i = 0; i < users.size(); i++)
            users.get(i).print();
    }

    //return a book
    public void returnBook() throws EmailNotFoundException, IncorrectPasswordException, BookNotFoundException, IncorrectDateFormatException, BookNotLendedException{
        ++invoiceId;
        String userName = "";
        String userPassword;
        String userEmail;
        String invoiceDate;
        String cardOrCash;
        int userId3 = 0;
        int bookId3;
        int ok = 0;
        int ok2 = 0;
        LendedBooks returnedBook = new LendedBooks();
        Users userThatLended = new Users();

        System.out.println("Enter your email: ");
        userEmail = myObj.nextLine();

        for(int j = 0; j < users.size(); j++)
            if(users.get(j).getUserEmail().equals(userEmail)){
                userId3 = users.get(j).getUserId();
                userName = users.get(j).getUserName();
                userThatLended = users.get(j);
            }

        for(int j = 0; j < users.size(); j++)
            if(users.get(j).getUserEmail().equals(userEmail)){
                ok = 1;
            }

        if(ok == 0)
            throw new EmailNotFoundException("Could not find user with this email! \n");

        System.out.println("Enter your password: ");
        userPassword = myObj.nextLine();

        if(!users.get(userId3 - 1).getUserPassword().equals(userPassword))
            throw new IncorrectPasswordException("You introduced the wrong password! \n");

        System.out.println("Enter the id of the book you want to return: ");
        bookId3 = myObj.nextInt();
        myObj.nextLine();

        for(int j = 0; j < users.size(); j++){
            int nob = users.get(j).getUserLendedBooks().size();
            for(int k = 0; k < nob; k++)
                if((users.get(j).getUserLendedBooks().get(k).getBookId() == bookId3 && users.get(j).getUserLendedBooks().get(k).getIsLended() == true)) {
                    ok2 = 1;
                }
        }

        if(ok2 == 0){
            throw new BookNotLendedException("This book is not on the lended books list of the user! You can't return it!\n");
        }
        else{
            if (bookId3 > bookId1) {
                throw new BookNotFoundException("Could not find book with this ID! \n");
            }

            System.out.println("Enter current date (yyyy-MM-dd): ");
            invoiceDate = myObj.nextLine();

            System.out.println("Do you want to pay with card or cash: ");
            cardOrCash = myObj.nextLine();

            if(!invoiceDate.matches("(((200)|(201))[0-9]|(202)[012])-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")){
                throw new IncorrectDateFormatException("This date doesn't match the requested date format! \n");
            }

            for(int j = 0; j < partners.size(); j++){
                int nob = partners.get(j).getPartnerBooks().size();
                for(int k = 0; k < nob; k++)
                    if((partners.get(j).getPartnerBooks().get(k).getBookId() == bookId3)) {
                        partners.get(j).getPartnerBooks().get(k).setIsLended(false);
                    }
            }

            for(int j = 0; j < users.size(); j++){
                int nob = users.get(j).getUserLendedBooks().size();
                for(int k = 0; k < nob; k++)
                    if((users.get(j).getUserLendedBooks().get(k).getBookId() == bookId3)) {
                        users.get(j).getUserLendedBooks().get(k).setIsLended(false);
                        returnedBook = users.get(j).getUserLendedBooks().get(k);
                    }
            }
            invoice.add(new Invoice(invoiceId, invoiceDate, returnedBook, userThatLended, cardOrCash));
            invoice.get(invoiceId - 1).print();
        }
    }
}