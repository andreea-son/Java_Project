import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.io.*;
public class LibrarianService {
    private static ArrayList<Partners> partners = new ArrayList<>();
    private static ArrayList<Sections> sections = new ArrayList<>();
    private static ArrayList<Users> users = new ArrayList<>();
    private static ArrayList<String> sectionName = new ArrayList<>();
    private static LinkedList<Invoice> invoice = new LinkedList<>();
    private static ArrayList<ArrayList<LentBooks>> userLentBooks = new ArrayList<>(100);
    private static Books partnerBook = new Books();
    private static int partnerId;
    private static int sectionId;
    private static int userId;
    private static int invoiceId;
    private Console console = System.console();
    private Scanner myObj = new Scanner(System.in);

    public LibrarianService() {

    }

    public static ArrayList<Partners> getPartners() {
        return partners;
    }

    public static ArrayList<Sections> getSections() {
        return sections;
    }

    public static ArrayList<Users> getUsers() {
        return users;
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
            for (Sections section : sections)
                if (section.getSectionName().equals(sectionName.get(sectionId - 1))) {
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
            for (Partners partner : partners)
                if (partner.getPartnerName().equals(partnerName)) {
                    throw new AlreadyUsedNameException("This name has already been used for another partner! \n");
                }

        System.out.println("Enter the email of the new partner in the following format: ");
        System.out.println("Any uppercase/lowercase letter or digit or the special characters \"_\", \".\" and \"@\"");
        partnerEmail = myObj.nextLine();

        if (partners.size() >= 1)
            for (Partners partner : partners)
                if (partner.getPartnerEmail().equals(partnerEmail)) {
                    throw new AlreadyUsedEmailException("This email has already been used for another partner! \n");
                }

        if (!partnerEmail.matches("([a-z|A-Z|0-9|_|\\.]+)@([a-z|A-Z|0-9|_|\\.]+)[^\\.]\\.([a-z]+)")) {
            throw new IncorrectMailFormatException("This email doesn't have the required format! \n");
        }

        String[] password = partnerEmail.split("@");
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
            for (Users user : users)
                if (user.getUserName().equals(userName)) {
                    throw new AlreadyUsedNameException("This username has already been used! \n");
                }

        System.out.println("Enter the email of the new user in the following format: ");
        System.out.println("Any uppercase/lowercase letter or digit or the special characters \"_\", \".\" and \"@\"");
        userEmail = myObj.nextLine();

        if(users.size() >= 1)
            for (Users user : users)
                if (user.getUserEmail().equals(userEmail)) {
                    throw new AlreadyUsedEmailException("This email has already been used! \n");
                }

        if(!userEmail.matches("([a-z|A-Z|0-9|_|\\.]+)@([a-z|A-Z|0-9|_|\\.]+)[^\\.]\\.([a-z]+)")){
            throw new IncorrectMailFormatException("This email doesn't have the required format! \n");
        }

        String[] password = userEmail.split("@");
        userPassword = password[0];

        users.add(new Users(userId, userName, userPassword, userEmail));
    }

    public void lendNewBook() throws BookNotFoundException, BookAlreadyLentException, UserNotFoundException, IncorrectDateFormatException, MaxNumOfDaysException, WrongInputException{
        for(int i = 0; i < 100; i++)
            userLentBooks.add(new ArrayList<>());
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
        int ok3 = 0;

        for (Partners element : partners)
            if (element.getPartnerBooks().size() > 0) {
                ok1 = 1;
                break;
            }
        if (users.size() < 1)
            System.out.println("There are no added users that could lend!");
        else if (ok1 == 0)
            System.out.println("There are no books!");
        else {
            System.out.println("Enter the ID of the user that wants to lend the book: ");
            userId1 = myObj.nextInt();
            myObj.nextLine();

            for (Users user : users)
                if (userId1 == user.getUserId()) {
                    ok3 = 1;
                    break;
                }

            if (ok3 == 0)
                throw new UserNotFoundException("Could not find user with this ID! \n");

            System.out.println("Enter the ID of the book this user wants to lend: ");
            bookId = myObj.nextInt();
            myObj.nextLine();

            for (Partners item : partners)
                for (int j = 0; j < item.getPartnerBooks().size(); j++)
                    if (bookId == item.getPartnerBooks().get(j).getBookId()) {
                        ok2 = 1;
                        break;
                    }

            if(ok2 == 0)
                throw new BookNotFoundException("Could not find book with this ID! \n");

            for (Partners value : partners)
                for (int k = 0; k < value.getPartnerBooks().size(); k++)
                    if ((value.getPartnerBooks().get(k).getBookId() == bookId) && (value.getPartnerBooks().get(k).getIsLent())) {
                        partnerBook = value.getPartnerBooks().get(k);
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

            for (Partners partner : partners)
                for (int k = 0; k < partner.getPartnerBooks().size(); k++)
                    if (partner.getPartnerBooks().get(k).getBookId() == bookId) {
                        partnerBook = partner.getPartnerBooks().get(k);
                    }

            partnerBook.setIsLent(true);

            userName = users.get(userId1 - 1).getUserName();
            userPassword = users.get(userId1 - 1).getUserPassword();
            userEmail = users.get(userId1 - 1).getUserEmail();

            userLentBooks.get(userId1).add(0, new LentBooks(bookId, false, partnerBook.getAuthor(), partnerBook.getTitle(), partnerBook.getDescription(), issuedDate, daysFromIssue, true));

            for(int i = 0; i < userLentBooks.get(userId1).size(); i++)
                if(userLentBooks.get(userId1).get(i).getBookId() == bookId && userLentBooks.get(userId1).get(i).getIsLent())
                    userLentBook = userLentBooks.get(userId1).get(i);

            System.out.print("\n");
            System.out.println("The price you have to pay: " + userLentBook.getPrice());
            System.out.print("\n");

            System.out.println("Do you want to pay with card or cash? (card/cash): ");
            cardOrCash = myObj.nextLine();

            if(!cardOrCash.equalsIgnoreCase("card") && !cardOrCash.equalsIgnoreCase("cash"))
                throw new WrongInputException("You did not enter the right option! \n");

            users.set(userId1 - 1, new Users(userId1, userName, userPassword, userEmail, userLentBooks.get(userId1)));
            invoice.add(new Invoice(++invoiceId, issuedDate, userLentBook, users.get(userId1 - 1), cardOrCash));
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
        int ok5 = 0;

        LentBooks lentBook = new LentBooks();
        Books book = new Books();
        Users userThatLent;

        for (Users value : users)
            for (int k = 0; k < value.getUserLentBooks().size(); k++)
                if (value.getUserLentBooks().get(k).getIsLent()) {
                    ok1 = 1;
                    break;
                }
        if (users.size() < 1)
            System.out.println("There are no added users that could return!");
        else if (ok1 == 0)
            System.out.println("There are no lent books in the system!");
        else {

            System.out.println("Enter the ID of the user that wants to return the book: ");
            userId1 = myObj.nextInt();
            myObj.nextLine();

            for (Users user : users)
                if (userId1 == user.getUserId()) {
                    ok2 = 1;
                    break;
                }

            if(ok2 == 0)
                throw new UserNotFoundException("Could not find user with this ID! \n");

            for (int k = 0; k < users.get(userId1 - 1).getUserLentBooks().size(); k++)
                if(users.get(userId1 - 1).getUserLentBooks().get(k).getIsLent()) {
                    ok3 = 1;
                    break;
                }
            if(ok3 == 0)
                throw new NoLentBooksException("This user has no lent books at the moment! \n");

            userThatLent = users.get(userId1 - 1);

            System.out.println("Enter the ID of the book this user wants to return: ");
            bookId = myObj.nextInt();
            myObj.nextLine();

            for (Partners partner : partners)
                for (int j = 0; j < partner.getPartnerBooks().size(); j++)
                    if (bookId == partner.getPartnerBooks().get(j).getBookId()) {
                        ok4 = 1;
                        book = partner.getPartnerBooks().get(j);
                    }

            if (ok4 == 0)
                throw new BookNotFoundException("Could not find book with this ID! \n");

            for (int i = 0; i < userThatLent.getUserLentBooks().size(); i++)
                if (bookId == userThatLent.getUserLentBooks().get(i).getBookId() && userThatLent.getUserLentBooks().get(i).getIsLent()) {
                    ok5 = 1;
                    lentBook = userThatLent.getUserLentBooks().get(i);
                }

            if (ok5 == 0)
                throw new BookNotLentException("This book is not on the current lent book list of the user! You can't return it!\n");

            System.out.println("Enter current date (yyyy-MM-dd): ");
            actualReturnDate = myObj.nextLine();

            if (!actualReturnDate.matches("(((200)|(201))[0-9]|(202)[012])-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")) {
                throw new IncorrectDateFormatException("This date doesn't match the requested date format! \n");
            }

            book.setIsLent(false);
            lentBook.setIsLent(false);

            String[] date1 = lentBook.getIssuedDate().split("-0|-");
            int year1 = Integer.parseInt(date1[0]);
            int month1 = Integer.parseInt(date1[1]);
            int day1 = Integer.parseInt(date1[2]);

            String[] date2 = actualReturnDate.split("-0|-");
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

                if(!cardOrCash.equalsIgnoreCase("card") && !cardOrCash.equalsIgnoreCase("cash"))
                    throw new WrongInputException("You did not enter the right option! \n");

                invoice.add(new Invoice(++invoiceId, actualReturnDate, lentBook, userThatLent, cardOrCash));
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println("We generated an invoice for your purchase: ");
                invoice.get(invoiceId - 1).print();
                System.out.print("\n");
            }
        }
    }

    public void printPartners(){
        if(partners.size() > 0) {
            System.out.println("The partners added in the system: ");
            for (Partners partner : partners) {
                partner.print();
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
            int partnerId1;
            int ok = 0;
            System.out.println("Enter the ID of the partner: ");
            partnerId1 = myObj.nextInt();
            myObj.nextLine();
            for (Partners partner : partners)
                if (partnerId1 == partner.getPartnerId()) {
                    ok = 1;
                    break;
                }
            if(ok == 0)
                throw new PartnerNotFoundException("Could not find partner with this ID! \n");
            if (partners.get(partnerId1 - 1).getPartnerBooks().size() > 0) {
                System.out.println("The books added by the partner: ");
                for (int j = 0; j < partners.get(partnerId1 - 1).getPartnerBooks().size(); j++) {
                    partners.get(partnerId1 - 1).getPartnerBooks().get(j).print();
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
            for (Sections section : sections) {
                section.print();
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
            int sectionId1;
            int ok = 0;
            System.out.println("Enter the ID of the section: ");
            sectionId1 = myObj.nextInt();
            myObj.nextLine();
            for (Sections section : sections)
                if (sectionId1 == section.getSectionId()) {
                    ok = 1;
                    break;
                }
            if(ok == 0)
                throw new SectionNotFoundException("Could not find section with this ID! \n");
            if (sections.get(sectionId1 - 1).getSectionBooks().size() > 0) {
                System.out.println("This section's books: ");
                for (int j = 0; j < sections.get(sectionId1 - 1).getSectionBooks().size(); j++) {
                    sections.get(sectionId1 - 1).getSectionBooks().get(j).print();
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
            for (Users user : users) {
                user.print();
                System.out.print("\n");
            }
        }
        else {
            System.out.println("There are no users! \n");
        }
    }

    public void printUserLentBooks() throws UserNotFoundException{
        if(users.size() < 1)
            System.out.println("There are no users! \n");
        else {
            int userId1;
            int ok = 0;
            System.out.println("Enter the ID of the user: ");
            userId1 = myObj.nextInt();
            myObj.nextLine();
            for (Users user : users)
                if (userId1 == user.getUserId()) {
                    ok = 1;
                    break;
                }
            if(ok == 0)
                throw new UserNotFoundException("Could not find user with this ID! \n");
            if (users.get(userId1 - 1).getUserLentBooks().size() > 0) {
                System.out.println("The books lent by this user: ");
                for (int j = 0; j < users.get(userId1 - 1).getUserLentBooks().size(); j++) {
                    users.get(userId1 - 1).getUserLentBooks().get(j).print();
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
        for (Users user : users)
            if (user.getUserLentBooks().size() > 0) {
                ok1 = 1;
                break;
            }
        if(ok1 == 0)
            System.out.println("There are no issued books! \n");
        else {
            for (Users user : users)
                for (int j = 0; j < user.getUserLentBooks().size(); j++)
                    if (user.getUserLentBooks().get(j).getIsLent()) {
                        ok2 = 1;
                        break;
                    }
            if (ok2 == 0)
                System.out.println("There are no issued books! \n");
            else {
                System.out.println("The issued books: ");
                for (Users user : users)
                    for (int j = 0; j < user.getUserLentBooks().size(); j++)
                        if (user.getUserLentBooks().get(j).getIsLent()) {
                            user.getUserLentBooks().get(j).print();
                            System.out.println("The user that lent this book: ");
                            user.print();
                            System.out.print("\n");
                        }
            }
        }
    }

    public void printBooks(){
        int ok1 = 0;
        int ok2 = 0;
        for (Partners partner : partners)
            if (partner.getPartnerBooks().size() > 0) {
                ok1 = 1;
                break;
            }
        if (ok1 == 0)
            System.out.println("There are no available books! \n");
        else {
            for (Partners partner : partners)
                for (int j = 0; j < partner.getPartnerBooks().size(); j++)
                    if (!partner.getPartnerBooks().get(j).getIsLent()) {
                        ok2 = 1;
                        break;
                    }
            if (ok2 == 0)
                System.out.println("There are no available books! \n");
            else {
                System.out.println("The books in the library: ");
                for (Partners partner : partners)
                    for (int j = 0; j < partner.getPartnerBooks().size(); j++)
                        if (!partner.getPartnerBooks().get(j).getIsLent()) {
                            partner.getPartnerBooks().get(j).print();
                            System.out.print("\n");
                        }
            }
        }
    }

    public void deleteBook() throws BookNotFoundException, BookCurrentlyLentException{
        int bookId;
        int ok1 = 0;
        int ok2 = 0;
        int ok3 = 0;
        Books book = new Books();
        LentBooks lentBook = new LentBooks();
        for (Partners partner : partners)
            if (partner.getPartnerBooks().size() > 0) {
                ok1 = 1;
                break;
            }
        if (ok1 == 0)
            System.out.println("There are no books! \n");
        else {
            System.out.println("Enter the ID of the book you want to delete: ");
            bookId = myObj.nextInt();
            myObj.nextLine();
            for (Partners partner : partners)
                for (int j = 0; j < partner.getPartnerBooks().size(); j++)
                    if (bookId == partner.getPartnerBooks().get(j).getBookId()) {
                        book = partner.getPartnerBooks().get(j);
                        ok2 = 1;
                        break;
                    }
            if(ok2 == 0)
                throw new BookNotFoundException("Could not find book with this ID! \n");

            for (Users user : users)
                for (int j = 0; j < user.getUserLentBooks().size(); j++)
                    if (bookId == user.getUserLentBooks().get(j).getBookId() && user.getUserLentBooks().get(j).getIsLent()) {
                        ok3 = 1;
                        break;
                    }

            if(ok3 == 1)
                throw new BookCurrentlyLentException("This book is currently on the lent books' list. You can't remove it!\n");

            for (Users user : users)
                for (int j = 0; j < user.getUserLentBooks().size(); j++)
                    if (bookId == user.getUserLentBooks().get(j).getBookId() && !user.getUserLentBooks().get(j).getIsLent()) {
                        lentBook = user.getUserLentBooks().get(j);
                    }

            for (Partners partner : partners) partner.getPartnerBooks().remove(book);

            for (Sections section : sections) section.getSectionBooks().remove(book);

            lentBook.setIsDeleted(true);

        }
    }

    public void deleteSections() throws SectionNotFoundException, BookCurrentlyLentException{
        int sectionId1;
        int ok1 = 0;
        int ok2 = 0;
        if(sections.size() < 1)
            System.out.println("There are no sections!");
        else {
            System.out.println("Enter the ID of the section you want to delete: ");
            sectionId1 = myObj.nextInt();
            myObj.nextLine();
            for (Sections section : sections)
                if (sectionId1 == section.getSectionId()) {
                    ok1 = 1;
                    break;
                }
            if(ok1 == 0)
                throw new SectionNotFoundException("Could not find section with this ID! \n");

            for(int k = 0; k < sections.get(sectionId1 - 1).getSectionBooks().size(); k++)
                for (Users user : users)
                    for (int j = 0; j < user.getUserLentBooks().size(); j++)
                        if (sections.get(sectionId1 - 1).getSectionBooks().get(k).getBookId() == user.getUserLentBooks().get(j).getBookId() && user.getUserLentBooks().get(j).getIsLent()) {
                            ok2 = 1;
                            break;
                        }

            if(ok2 == 1)
                throw new BookCurrentlyLentException("This section contains a book that is currently lent. You can't remove this section!\n");

            if(sections.get(sectionId1 - 1).getSectionBooks().size() >= 1) {
                if (partners.size() >= 1)
                    for (Partners partner : partners)
                        partner.getPartnerBooks().removeAll(sections.get(sectionId1 - 1).getSectionBooks());
                sections.get(sectionId1 - 1).getSectionBooks().removeAll(sections.get(sectionId1 - 1).getSectionBooks());
            }

            for(int k = 0; k < sections.get(sectionId1 - 1).getSectionBooks().size(); k++)
                for (Users user : users)
                    for (int j = 0; j < user.getUserLentBooks().size(); j++)
                        if (sections.get(sectionId1 - 1).getSectionBooks().get(k).getBookId() == user.getUserLentBooks().get(j).getBookId() && !user.getUserLentBooks().get(j).getIsLent()) {
                            user.getUserLentBooks().get(j).setIsDeleted(true);
                        }

            sections.remove(sectionId1 - 1);
        }
    }

    public void deletePartners() throws PartnerNotFoundException, BookCurrentlyLentException{
        int partnerId1;
        int ok1 = 0;
        int ok2 = 0;
        if(partners.size() < 1){
            System.out.println("There are no partners! ");
        }
        else {
            System.out.println("Enter the ID of the partner you want to delete: ");
            partnerId1 = myObj.nextInt();
            myObj.nextLine();

            for (Partners partner : partners)
                if (partnerId1 == partner.getPartnerId()) {
                    ok1 = 1;
                    break;
                }
            if(ok1 == 0)
                throw new PartnerNotFoundException("Could not find partner with this ID! \n");

            for(int k = 0; k < partners.get(partnerId1 - 1).getPartnerBooks().size(); k++)
                for (Users user : users)
                    for (int j = 0; j < user.getUserLentBooks().size(); j++)
                        if (partners.get(partnerId1 - 1).getPartnerBooks().get(k).getBookId() == user.getUserLentBooks().get(j).getBookId() && user.getUserLentBooks().get(j).getIsLent()) {
                            ok2 = 1;
                            break;
                        }

            if(ok2 == 1)
                throw new BookCurrentlyLentException("This partner added a book that is currently lent. If you remove this partner, you will automatically remove the book!\n");

            for(int k = 0; k < partners.get(partnerId1 - 1).getPartnerBooks().size(); k++)
                for (Users user : users)
                    for (int j = 0; j < user.getUserLentBooks().size(); j++)
                        if (partners.get(partnerId1 - 1).getPartnerBooks().get(k).getBookId() == user.getUserLentBooks().get(j).getBookId() && !user.getUserLentBooks().get(j).getIsLent()) {
                            user.getUserLentBooks().get(j).setIsDeleted(true);
                        }

            if(partners.get(partnerId1 - 1).getPartnerBooks().size() >= 1){
                if(sections.size() >= 1)
                    for (Sections section : sections)
                        section.getSectionBooks().removeAll(partners.get(partnerId1 - 1).getPartnerBooks());
                partners.get(partnerId1 - 1).getPartnerBooks().removeAll(partners.get(partnerId1 - 1).getPartnerBooks());
            }

            partners.remove(partnerId1 - 1);
        }
    }
    public void deleteUsers() throws UserNotFoundException, BookCurrentlyLentException{
        int userId1;
        int ok1 = 0;
        int ok2 = 0;
        if(users.size() < 1){
            System.out.println("There are no users! ");
        }
        else {
            System.out.println("Enter the ID of the user you want to delete: ");
            userId1 = myObj.nextInt();
            myObj.nextLine();
            for (Users user : users)
                if (userId1 == user.getUserId()) {
                    ok1 = 1;
                    break;
                }
            if(ok1 == 0)
                throw new UserNotFoundException("Could not find user with this ID! \n");

            for(int j = 0; j < users.get(userId1 - 1).getUserLentBooks().size(); j++)
                if(users.get(userId1 - 1).getUserLentBooks().get(j).getIsLent()) {
                    ok2 = 1;
                    break;
                }

            if(ok2 == 1)
                throw new BookCurrentlyLentException("This user is currently lending a book! You can't remove this user!\n");

            for(int j = 0; j < users.get(userId1 - 1).getUserLentBooks().size(); j++)
                if(!users.get(userId1 - 1).getUserLentBooks().get(j).getIsLent()) {
                    users.get(userId1 - 1).getUserLentBooks().get(j).setIsDeleted(true);
                }

            if (users.get(userId1 - 1).getUserLentBooks().size() > 0)
                users.get(userId1 - 1).getUserLentBooks().removeAll(users.get(userId1 - 1).getUserLentBooks());

            users.remove(userId1 - 1);
        }
    }
}
