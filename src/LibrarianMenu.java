import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.io.*;
public class LibrarianMenu {
    private Scanner myObj = new Scanner(System.in);
    private static int partnerId;
    private static int sectionId;
    private static int userId;
    private static int invoiceId;
    private static ArrayList<Partners> partners = new ArrayList<>();
    private static ArrayList<Sections> sections = new ArrayList<>();
    private static ArrayList<Users> users = new ArrayList<>();
    private static ArrayList<String> sectionName = new ArrayList<>();
    private static LinkedList<Invoice> invoice = new LinkedList<>();
    private static ArrayList<ArrayList<LentBooks>> userLentBooks = new ArrayList<ArrayList<LentBooks>>(100);
    private static Books partnerBook = new Books();
    private static ArrayList<LentBooks> returnedBooks = new ArrayList<>();
    private static Console console = System.console();

    public LibrarianMenu() {

    }

    public static ArrayList<Partners> getPartners() {
        return partners;
    }

    public static ArrayList<Sections> getSections() {
        return sections;
    }

    public void loginInformation() throws UsernameNotFoundException, IncorrectPasswordException{
        String librarianEmail;

        System.out.println("Enter your username: ");
        librarianEmail = myObj.nextLine();
        if (!librarianEmail.equals("admin")) {
            throw new UsernameNotFoundException("Incorrect username! \n");
        }

        System.out.println("Enter your password: ");

        char[] passwordChars = console.readPassword();
        String librarianPassword = new String(passwordChars);

        if (!librarianPassword.equals("admin")) {
            throw new IncorrectPasswordException("Incorrect password! \n");
        }
    }

    public void addNewSection() throws AlreadyUsedNameException{
        ++sectionId;
        String sn;

        System.out.println("Enter the name of the new section: ");
        sn = myObj.nextLine();
        sectionName.add(sn);

        if(sections.size() >= 1)
            for(int j = 0; j < sections.size(); j++)
                if(sections.get(j).getSectionName().equals(sectionName.get(sectionId - 1))){
                    throw new AlreadyUsedNameException("This name has already been used for another section! \n");
                }

        sections.add(new Sections(sectionId, sectionName.get(sectionId - 1)));
    }

    public void addNewPartner() throws AlreadyUsedNameException, AlreadyUsedEmailException, IncorrectMailFormatException, IncorrectPassFormatException{
        ++partnerId;
        String partnerName;
        String partnerPassword;
        String partnerEmail;

        System.out.println("Enter the name of the new partner: ");
        partnerName = myObj.nextLine();

        if (partners.size() >= 1)
            for (int j = 0; j < partners.size(); j++)
                if (partners.get(j).getPartnerName().equals(partnerName)) {
                    throw new AlreadyUsedNameException("This name has already been used for another partner! \n");
                }

        System.out.println("Enter the email of the new partner in the following format: ");
        System.out.println("Any uppercase/lowercase letter or digit or the special characters \"_\", \".\" and \"@\"");
        partnerEmail = myObj.nextLine();

        if (partners.size() >= 1)
            for (int j = 0; j < partners.size(); j++)
                if (partners.get(j).getPartnerEmail().equals(partnerEmail)) {
                    throw new AlreadyUsedEmailException("This email has already been used for another partner! \n");
                }

        if (!partnerEmail.matches("([a-z|A-Z|0-9|_|\\.]+)@([a-z|A-Z|0-9|_|\\.]+)[^\\.]\\.([a-z]+)")) {
            throw new IncorrectMailFormatException("This email doesn't have the required format! \n");
        }

        String password[] = partnerEmail.split("@");
        partnerPassword = password[0];

        partners.add(new Partners(partnerId, 0, partnerName, partnerPassword, partnerEmail));
    }

    public void addNewUser() throws BookNotFoundException, AlreadyUsedNameException, AlreadyUsedEmailException, IncorrectPasswordException, IncorrectMailFormatException, IncorrectPassFormatException{
        ++userId;
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

        System.out.println("Enter the email of the new user in the following format: ");
        System.out.println("Any uppercase/lowercase letter or digit or the special characters \"_\", \".\" and \"@\"");
        userEmail = myObj.nextLine();

        if(users.size() >= 1)
            for(int j = 0; j < users.size(); j++)
                if(users.get(j).getUserEmail().equals(userEmail)){
                    throw new AlreadyUsedEmailException("This email has already been used! \n");
                }

        if(!userEmail.matches("([a-z|A-Z|0-9|_|\\.]+)@([a-z|A-Z|0-9|_|\\.]+)[^\\.]\\.([a-z]+)")){
            throw new IncorrectMailFormatException("This email doesn't have the required format! \n");
        }

        System.out.println("Enter the password of the new user in the following format: ");
        System.out.println("At least 8 characters, minimum 1 lowercase, 1 uppercase, 1 special character and 1 digit ");
        userPassword = myObj.nextLine();

        if(!userPassword.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[_\\.#\\*\\+\\-\\$&!\\?@='\\[\\]\\{\\},`~\\(\\)]).{8,}")){
            throw new IncorrectPassFormatException("This password doesn't have the required format! \n");
        }

        users.add(new Users(userId, userName, userPassword, userEmail));
    }

    public void lendNewBook() throws BookNotFoundException, BookAlreadyLentException, UserNotFoundException, IncorrectDateFormatException, MaxNumOfDaysException, WrongInputException{
        ++invoiceId;
        for(int i = 0; i < 100; i++)
            userLentBooks.add(new ArrayList<LentBooks>());
        String userName;
        String userPassword;
        String userEmail;
        String issuedDate;
        String cardOrCash;
        LentBooks userLentBook = new LentBooks();
        int userId1;
        int bookId;
        int daysFromIssue;
        int ok1 = 0;
        int ok2 = 0;

        for (int j = 0; j < partners.size(); j++)
            if(partners.get(j).getPartnerBooks().size() > 0) {
                ok1 = 1;
                break;
            }
        if (users.size() < 1)
            System.out.println("There are no added users that could lend!");
        else if (ok1 == 0)
            System.out.println("There are no books!");
        else {
            System.out.println("Enter the id of the user that wants to lend the book: ");
            userId1 = myObj.nextInt();
            myObj.nextLine();

            if (userId1 > users.size() || userId1 < 1)
                throw new UserNotFoundException("Could not find user with this id! \n");

            System.out.println("Enter the id of the book this user wants to lend: ");
            bookId = myObj.nextInt();
            myObj.nextLine();

            for (int i = 0; i < partners.size(); i++)
                for (int j = 0; j < partners.get(i).getPartnerBooks().size(); j++)
                    if (bookId == partners.get(i).getPartnerBooks().get(j).getBookId()) {
                        ok2 = 1;
                        break;
                    }

            if(ok2 == 0 || bookId < 1)
                throw new BookNotFoundException("Could not find book with this ID! \n");

            for (int j = 0; j < partners.size(); j++)
                for (int k = 0; k < partners.get(j).getPartnerBooks().size(); k++)
                    if ((partners.get(j).getPartnerBooks().get(k).getBookId() == bookId) && (partners.get(j).getPartnerBooks().get(k).getIsLent() == true)) {
                        partnerBook = partners.get(j).getPartnerBooks().get(k);
                        throw new BookAlreadyLentException("This book is not available for lending right now! \n");
                    }

            System.out.println("Enter issue date (yyyy-MM-dd): ");
            issuedDate = myObj.nextLine();

            if (!issuedDate.matches("(((200)|(201))[0-9]|(202)[012])-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")) {
                throw new IncorrectDateFormatException("This date doesn't match the requested date format! \n");
            }

            System.out.println("Enter how many days the user wants to keep the book (max 60 days): ");
            daysFromIssue = myObj.nextInt();
            myObj.nextLine();

            if (daysFromIssue > 60) {
                throw new MaxNumOfDaysException("Too many days! \n");
            }

            for (int j = 0; j < partners.size(); j++)
                for (int k = 0; k < partners.get(j).getPartnerBooks().size(); k++)
                    if (partners.get(j).getPartnerBooks().get(k).getBookId() == bookId) {
                        partnerBook = partners.get(j).getPartnerBooks().get(k);
                    }

            partnerBook.setIsLent(true);

            userName = users.get(userId1 - 1).getUserName();
            userPassword = users.get(userId1 - 1).getUserPassword();
            userEmail = users.get(userId1 - 1).getUserEmail();

            userLentBooks.get(userId1).add(0, new LentBooks(bookId, partnerBook.getAuthor(), partnerBook.getTitle(), partnerBook.getDescription(), issuedDate, daysFromIssue, true));

            for(int i = 0; i < userLentBooks.get(userId1).size(); i++)
                if(userLentBooks.get(userId1).get(i).getBookId() == bookId && userLentBooks.get(userId1).get(i).getIsLent() == true)
                    userLentBook = userLentBooks.get(userId1).get(i);

            System.out.print("\n");
            System.out.println("The price you have to pay: " + userLentBook.getPrice());
            System.out.print("\n");

            System.out.println("Do you want to pay with card or cash? (card/cash): ");
            cardOrCash = myObj.nextLine();

            if(!cardOrCash.toLowerCase().equals("card") && !cardOrCash.toLowerCase().equals("cash"))
                throw new WrongInputException("You did not enter the right option! \n");

            users.set(userId1 - 1, new Users(userId1, userName, userPassword, userEmail, userLentBooks.get(userId1)));
            invoice.add(new Invoice(invoiceId, issuedDate, userLentBook, users.get(userId1 - 1), cardOrCash));
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("We generated an invoice for your purchase: ");
            invoice.get(invoiceId - 1).print();
            System.out.print("\n");
        }
    }

    public void returnBook() throws UserNotFoundException, BookNotFoundException, IncorrectDateFormatException, BookNotLentException, NoLentBooksException, WrongInputException{
        String actualReturnDate;
        String cardOrCash;
        int userId1;
        int bookId;
        int ok1 = 0;
        int ok2 = 0;
        int ok3 = 0;
        int ok4 = 0;

        LentBooks lentBook = new LentBooks();
        Books book = new Books();
        Users userThatLent;

        for (int j = 0; j < users.size(); j++)
            for (int k = 0; k < users.get(j).getUserLentBooks().size(); k++)
                if(users.get(j).getUserLentBooks().get(k).getIsLent() == true) {
                    ok3 = 1;
                    break;
                }
        if (users.size() < 1)
            System.out.println("There are no added users that could return!");
        else if (ok3 == 0)
            System.out.println("There are no lent books in the system!");
        else {

            System.out.println("Enter the id of the user that wants to return the book: ");
            userId1 = myObj.nextInt();
            myObj.nextLine();

            if (userId1 > users.size() || userId1 < 1)
                throw new UserNotFoundException("Could not find user with this id! \n");

            for (int k = 0; k < users.get(userId1 - 1).getUserLentBooks().size(); k++)
                if(users.get(userId1 - 1).getUserLentBooks().get(k).getIsLent() == true) {
                    ok4 = 1;
                    break;
                }
            if(ok4 == 0)
                throw new NoLentBooksException("This user has no lent books at the moment! \n");

            userThatLent = users.get(userId1 - 1);

            System.out.println("Enter the id of the book this user wants to return: ");
            bookId = myObj.nextInt();
            myObj.nextLine();

            for (int i = 0; i < partners.size(); i++)
                for (int j = 0; j < partners.get(i).getPartnerBooks().size(); j++)
                    if (bookId == partners.get(i).getPartnerBooks().get(j).getBookId()) {
                        ok1 = 1;
                        book = partners.get(i).getPartnerBooks().get(j);
                    }

            if (ok1 == 0)
                throw new BookNotFoundException("Could not find book with this ID! \n");

            for (int i = 0; i < userThatLent.getUserLentBooks().size(); i++)
                if (bookId == userThatLent.getUserLentBooks().get(i).getBookId() && userThatLent.getUserLentBooks().get(i).getIsLent() == true) {
                    ok2 = 1;
                    lentBook = userThatLent.getUserLentBooks().get(i);
                }

            if (ok2 == 0)
                throw new BookNotLentException("This book is not on the current lent book list of the user! You can't return it!\n");

            System.out.println("Enter current date (yyyy-MM-dd): ");
            actualReturnDate = myObj.nextLine();

            if (!actualReturnDate.matches("(((200)|(201))[0-9]|(202)[012])-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")) {
                throw new IncorrectDateFormatException("This date doesn't match the requested date format! \n");
            }

            book.setIsLent(false);
            lentBook.setIsLent(false);

            String date1[] = lentBook.getIssuedDate().split("-0|-");
            int year1 = Integer.parseInt(date1[0]);
            int month1 = Integer.parseInt(date1[1]);
            int day1 = Integer.parseInt(date1[2]);

            String date2[] = actualReturnDate.split("-0|-");
            int year2 = Integer.parseInt(date2[0]);
            int month2 = Integer.parseInt(date2[1]);
            int day2 = Integer.parseInt(date2[2]);

            LocalDate aux1 = LocalDate.of(year1, month1, day1).plusDays(lentBook.getDaysFromIssue());
            LocalDate aux2 = LocalDate.of(year2, month2, day2);

            lentBook.setExceededDays1(ChronoUnit.DAYS.between(aux1, aux2));
            lentBook.setExceededPrice1(3 * lentBook.getExceededDays());

            if(lentBook.getExceededDays() > 0) {
                System.out.print("\n");
                System.out.println("You exceeded the return date with " + lentBook.getExceededDays() + " days!");
                System.out.println("You have to pay an additional price: " + lentBook.getExceededPrice() + " ron");
                System.out.print("\n");
                System.out.println("Do you want to pay with card or cash? (card/cash): ");
                cardOrCash = myObj.nextLine();

                if(!cardOrCash.toLowerCase().equals("card") && !cardOrCash.toLowerCase().equals("cash"))
                    throw new WrongInputException("You did not enter the right option! \n");

                invoice.add(new Invoice(++invoiceId, actualReturnDate, lentBook, userThatLent, cardOrCash));
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println("We generated an invoice for your purchase: ");
                invoice.get(invoiceId - 1).print();
                System.out.print("\n");
            }
            returnedBooks.add(lentBook);
        }
    }

    public void printPartners(){
        if(partners.size() > 0) {
            System.out.println("The partners added in the system: ");
            for (int i = 0; i < partners.size(); i++) {
                partners.get(i).print();
                System.out.print("\n");
            }
        }
        else{
            System.out.println("There are no partners! \n");
        }

    }

    public void printPartnerBooks() throws PartnerNotFoundException{
        if(partners.size() < 1)
            System.out.println("There are no partners! \n");
        else {
            int partnerId;
            System.out.println("Enter the id of the partner: ");
            partnerId = myObj.nextInt();
            myObj.nextLine();
            if (partnerId > partners.size() || partnerId < 1)
                throw new PartnerNotFoundException("Could not find partner with this id! \n");
            if (partners.get(partnerId - 1).getPartnerBooks().size() > 0) {
                System.out.println("The books added by the partner: ");
                for (int j = 0; j < partners.get(partnerId - 1).getPartnerBooks().size(); j++) {
                    partners.get(partnerId - 1).getPartnerBooks().get(j).print();
                    System.out.print("\n");
                }
            } else {
                System.out.println("There are no books added by this partner! \n");
            }
        }
    }

    public void printSections(){
        if(sections.size() > 0) {
            System.out.println("The sections added in the system: ");
            for (int i = 0; i < sections.size(); i++) {
                sections.get(i).print();
                System.out.print("\n");
            }
        }
        else{
            System.out.println("There are no sections! \n");
        }
    }

    public void printSectionBooks() throws SectionNotFoundException{
        if(sections.size() < 1)
            System.out.println("There are no sections! \n");
        else {
            int sectionId;
            System.out.println("Enter the id of the section: ");
            sectionId = myObj.nextInt();
            myObj.nextLine();
            if (sectionId > sections.size() || sectionId < 1)
                throw new SectionNotFoundException("Could not find section with this id! \n");
            if (sections.get(sectionId - 1).getSectionBooks().size() > 0) {
                System.out.println("This section's books: ");
                for (int j = 0; j < sections.get(sectionId - 1).getSectionBooks().size(); j++) {
                    sections.get(sectionId - 1).getSectionBooks().get(j).print();
                    System.out.print("\n");
                }
            } else {
                System.out.println("There are no books in this section! \n");
            }
        }
    }

    public void printUsers(){
        if(users.size() > 0) {
            System.out.println("The users added in the system: ");
            for (int i = 0; i < users.size(); i++) {
                users.get(i).print();
                System.out.print("\n");
            }
        }
        else {
            System.out.println("There are no users! \n");
        }
    }

    public void printUserLentBooks() throws UserNotFoundException{
        int userId;
        if(users.size() < 1)
            System.out.println("There are no users! \n");
        else {
            System.out.println("Enter the id of the user: ");
            userId = myObj.nextInt();
            myObj.nextLine();
            if (userId > users.size() || userId < 1)
                throw new UserNotFoundException("Could not find user with this id! \n");
            if (users.get(userId - 1).getUserLentBooks().size() > 0) {
                System.out.println("The books lent by this user: ");
                for (int j = 0; j < users.get(userId - 1).getUserLentBooks().size(); j++) {
                    users.get(userId - 1).getUserLentBooks().get(j).print();
                    System.out.print("\n");
                }
            }
            else {
                System.out.println("There are no books lent by this user! \n");
            }
        }
    }

    public void printIssuedBooks(){
        int ok1 = 0;
        int ok2 = 0;
        for (int i = 0; i < users.size(); i++)
            if (users.get(i).getUserLentBooks().size() > 0) {
                ok1 = 1;
            }
        if(ok1 == 0)
            System.out.println("There are no issued books! \n");
        else {
            for (int i = 0; i < users.size(); i++)
                for (int j = 0; j < users.get(i).getUserLentBooks().size(); j++)
                    if (users.get(i).getUserLentBooks().get(j).getIsLent() == true) {
                        ok2 = 1;
                        break;
                    }
            if (ok2 == 0)
                System.out.println("There are no issued books! \n");
            else {
                System.out.println("The issued books: ");
                for (int i = 0; i < users.size(); i++)
                    for (int j = 0; j < users.get(i).getUserLentBooks().size(); j++)
                        if (users.get(i).getUserLentBooks().get(j).getIsLent() == true) {
                            users.get(i).getUserLentBooks().get(j).print();
                            System.out.println("The user that lent this book: ");
                            users.get(i).print();
                            System.out.print("\n");
                        }
            }
        }
    }

    public void printBooks(){
        int ok1 = 0;
        int ok2 = 0;
        for (int i = 0; i < partners.size(); i++)
            if (partners.get(i).getPartnerBooks().size() > 0)
                ok1 = 1;
        if (ok1 == 0)
            System.out.println("There are no available books! \n");
        else {
            for (int i = 0; i < partners.size(); i++)
                for (int j = 0; j < partners.get(i).getPartnerBooks().size(); j++)
                    if (partners.get(i).getPartnerBooks().get(j).getIsLent() == false) {
                        ok2 = 1;
                        break;
                    }
            if (ok2 == 0)
                System.out.println("There are no available books! \n");
            else {
                System.out.println("The books in the library: ");
                for (int i = 0; i < partners.size(); i++)
                    for (int j = 0; j < partners.get(i).getPartnerBooks().size(); j++)
                        if (partners.get(i).getPartnerBooks().get(j).getIsLent() == false) {
                            partners.get(i).getPartnerBooks().get(j).print();
                            System.out.print("\n");
                        }
            }
        }
    }
}



