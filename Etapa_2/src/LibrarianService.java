import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.io.*;

public class LibrarianService {
    private static ArrayList<Partners> partners = new ArrayList<>();
    private static ArrayList<Sections> sections = new ArrayList<>();
    private static ArrayList<Users> users = new ArrayList<>();
    private static LinkedList<Invoice> invoice = new LinkedList<>();
    private static ArrayList<ArrayList<LentBooks>> userLentBooks = new ArrayList<>(100);
    private static MyDate lastDate = new MyDate();
    private static ArrayList<MyAction> actions= new ArrayList<>();
    private static int counter;
    private int partnerId = partners.size();
    private int sectionId = sections.size();
    private int userId = users.size();
    private int invoiceId = invoice.size();
    private Console console = System.console();
    private Scanner myObj = new Scanner(System.in);
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");


    public LibrarianService() {
        for(int i = 0; i < 100; i++)
            actions.add(new MyAction());
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

    public static LinkedList<Invoice> getInvoice() {
        return invoice;
    }

    public static ArrayList<ArrayList<LentBooks>> getUserLentBooks() {
        for(int i = 0; i < 100; i++)
            userLentBooks.add(new ArrayList<>());
        return userLentBooks;
    }

    public static MyDate getLastDate() {
        return lastDate;
    }

    public static ArrayList<MyAction> getActions1() {
        return actions;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        LibrarianService.counter = counter;
    }

    public void loginInformation() throws UsernameNotFoundException, IncorrectPasswordException{
        String librarianEmail;
        actions.set(counter++, new MyAction("librarian: login", dtf.format(LocalDateTime.now())));

        lastDate.setDate(invoice.getLast().getBook().getIssuedDate().getDate());

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
        actions.set(counter++, new MyAction("librarian: add new section", dtf.format(LocalDateTime.now())));

        String sectionName;

        System.out.println("Enter the name of the new section: ");
        sectionName = myObj.nextLine();

        if(sections.size() >= 1)
            for (Sections section : sections)
                if (section.getSectionName().equals(sectionName)) {
                    throw new AlreadyUsedNameException("This name has already been used for another section! \n");
                }

        sections.add(new Sections(sectionId, sectionName));
    }

    public void addNewPartner() throws AlreadyUsedNameException, AlreadyUsedEmailException, IncorrectMailFormatException{
        ++partnerId;
        actions.set(counter++, new MyAction("librarian: add new partner", dtf.format(LocalDateTime.now())));

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

    public void addNewUser() throws AlreadyUsedNameException, AlreadyUsedEmailException, IncorrectMailFormatException{
        ++userId;
        actions.set(counter++, new MyAction("librarian: add new user", dtf.format(LocalDateTime.now())));

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

        users.add(new Users(userId, 0, userName, userPassword, userEmail));
    }

    public void clearConsole(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void lendNewBook() throws BookNotFoundException, BookAlreadyLentException, UserNotFoundException, IncorrectDateFormatException, MaxNumOfDaysException, WrongInputException, InvalidCodeException, DateNotValidException{
        actions.set(counter++, new MyAction("librarian: lend new book", dtf.format(LocalDateTime.now())));

        for(int i = 0; i < 100; i++)
            userLentBooks.add(new ArrayList<>());

        String userName;
        String userPassword;
        String userEmail;
        String issuedDate;
        String cardOrCash;
        String yOrN;
        String discountCode;
        LentBooks userLentBook = new LentBooks();
        Books partnerBook = new Books();
        Books sectionBook = new Books();
        _10PercentDiscount discount = new _10PercentDiscount();
        MyDate tempDate = new MyDate();

        int numOfLogins;
        int userId1;
        int bookId;
        int daysFromIssue;
        int ok1 = 0;
        int ok2 = 0;
        int ok3 = 0;
        int ok4 = 0;

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

            if (ok2 == 0)
                throw new BookNotFoundException("Could not find book with this ID! \n");

            for (Partners value : partners)
                for (int k = 0; k < value.getPartnerBooks().size(); k++)
                    if ((value.getPartnerBooks().get(k).getBookId() == bookId) && (value.getPartnerBooks().get(k).getIsLent())) {
                        throw new BookAlreadyLentException("This book is not available for lending right now! \n");
                    }

            System.out.println("Enter issue date (yyyy-MM-dd): ");
            issuedDate = myObj.nextLine();

            if (!issuedDate.matches("(((200)|(201))[0-9]|(202)[012])-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")) {
                throw new IncorrectDateFormatException("This date doesn't match the requested date format! \n");
            }

            tempDate.setDate(issuedDate);

            if(lastDate.isBiggerThan(tempDate))
                throw new DateNotValidException("This date is not valid! Please enter a date that is equal to or grater than the last date that you have entered! \n");

            lastDate.setDate(issuedDate);

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

            for (Sections section : sections)
                for (int k = 0; k < section.getSectionBooks().size(); k++)
                    if (section.getSectionBooks().get(k).getBookId() == bookId) {
                        sectionBook = section.getSectionBooks().get(k);
                    }

            partnerBook.setIsLent(true);
            sectionBook.setIsLent(true);

            userName = users.get(userId1 - 1).getUserName();
            userPassword = users.get(userId1 - 1).getUserPassword();
            userEmail = users.get(userId1 - 1).getUserEmail();

            userLentBooks.get(userId1 - 1).add(0, new LentBooks(bookId, false, partnerBook.getAuthor(), partnerBook.getTitle(), partnerBook.getDescription(), issuedDate, daysFromIssue, true));

            for(int i = 0; i < userLentBooks.get(userId1 - 1).size(); i++)
                if(userLentBooks.get(userId1 - 1).get(i).getBookId() == bookId && userLentBooks.get(userId1 - 1).get(i).getIsLent())
                    userLentBook = userLentBooks.get(userId1 - 1).get(i);

            System.out.print("\n");
            System.out.print("The price: ");
            System.out.printf("%.02f", userLentBook.getPrice());
            System.out.print(" ron");
            System.out.println("\n");

            System.out.println("Do you have a discount code? (y/n)");
            yOrN = myObj.nextLine();

            if (!yOrN.equals("y") && !yOrN.equals("Y") && !yOrN.equals("n") && !yOrN.equals("N"))
                throw new WrongInputException("You entered an invalid option! \n");

            if (yOrN.equals("y") || yOrN.equals("Y")) {
                System.out.println("Please enter the code: ");
                discountCode = myObj.nextLine();
                for (int i = 0; i < Users.getDiscounts().size(); i++)
                    if (discountCode.equals(Users.getDiscounts().get(i).getCode()) && !Users.getDiscounts().get(i).getIsUsed()) {
                        ok4 = 1;
                        discount = Users.getDiscounts().get(i);
                        break;
                    }

                if (ok4 == 0)
                    throw new InvalidCodeException("This code doesn't exist on this user's discounts' list or it has already been used! \n");

                if (discount.getExpirationDate().isBiggerThan(tempDate)) {
                    throw new InvalidCodeException("This code has already expired! \n");
                }
                float discountPrice = userLentBook.getPrice() - userLentBook.getPrice() / 10;

                System.out.print("\n");
                System.out.print("The price after discount: ");
                System.out.printf("%.02f", discountPrice);
                System.out.print(" ron");
                System.out.println("\n");

                discount.setIsUsed(true);

                System.out.println("Paying with card or cash? (card/cash): ");
                cardOrCash = myObj.nextLine();

                if (!cardOrCash.equalsIgnoreCase("card") && !cardOrCash.equalsIgnoreCase("cash"))
                    throw new WrongInputException("You did not enter the right option! \n");

                numOfLogins = users.get(userId1 - 1).getNumOfLogins();
                users.set(userId1 - 1, new Users(userId1, numOfLogins, userName, userPassword, userEmail, userLentBooks.get(userId1 - 1)));
                invoice.add(new Invoice(++invoiceId, issuedDate, userLentBook, users.get(userId1 - 1), cardOrCash, true));
                clearConsole();
                System.out.println("We generated an invoice for the purchase: ");
                invoice.get(invoiceId - 1).print();
                System.out.print("\n");
                userLentBook.setPrice(discountPrice);
            } else {
                System.out.println("Paying with card or cash? (card/cash): ");
                cardOrCash = myObj.nextLine();

                if (!cardOrCash.equalsIgnoreCase("card") && !cardOrCash.equalsIgnoreCase("cash"))
                    throw new WrongInputException("You did not enter the right option! \n");

                numOfLogins = users.get(userId1 - 1).getNumOfLogins();
                users.set(userId1 - 1, new Users(userId1, numOfLogins, userName, userPassword, userEmail, userLentBooks.get(userId1 - 1)));
                invoice.add(new Invoice(++invoiceId, issuedDate, userLentBook, users.get(userId1 - 1), cardOrCash, false));
                clearConsole();
                System.out.println("We generated an invoice for the purchase: ");
                invoice.get(invoiceId - 1).print();
                System.out.print("\n");
            }
        }
    }

    static String getAlphaNumericString(int n)
    {

        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

        StringBuilder stringBuilder = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            stringBuilder.append(AlphaNumericString.charAt(index));
        }

        return stringBuilder.toString();
    }

    public void returnBook() throws UserNotFoundException, BookNotFoundException, IncorrectDateFormatException, BookNotLentException, NoLentBooksException, WrongInputException, DateNotValidException{
        actions.set(counter++, new MyAction("librarian: return book", dtf.format(LocalDateTime.now())));

        MyDate actualReturnDate = new MyDate();
        String tempDate;
        String cardOrCash;
        int userId1;
        int bookId;
        int ok1 = 0;
        int ok2 = 0;
        int ok3 = 0;
        int ok4 = 0;
        int ok5 = 0;
        long aux;

        LentBooks lentBook = new LentBooks();
        Books sectionBook = new Books();
        Books partnerBook = new Books();
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
                        partnerBook = partner.getPartnerBooks().get(j);
                        break;
                    }

            for (Sections section : sections)
                for (int j = 0; j < section.getSectionBooks().size(); j++)
                    if (bookId == section.getSectionBooks().get(j).getBookId()) {
                        sectionBook = section.getSectionBooks().get(j);
                        break;
                    }

            if (ok4 == 0)
                throw new BookNotFoundException("Could not find book with this ID! \n");

            for (int i = 0; i < userThatLent.getUserLentBooks().size(); i++)
                if (bookId == userThatLent.getUserLentBooks().get(i).getBookId() && userThatLent.getUserLentBooks().get(i).getIsLent()){
                    ok5 = 1;
                    lentBook = userThatLent.getUserLentBooks().get(i);
                    break;
                }

            if (ok5 == 0)
                throw new BookNotLentException("This book is not on the current lent book list of the user! You can't return it!\n");

            System.out.println("Enter current date (yyyy-MM-dd): ");
            tempDate = myObj.nextLine();
            actualReturnDate.setDate(tempDate);

            if (!actualReturnDate.getDate().matches("(((200)|(201))[0-9]|(202)[012])-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")) {
                throw new IncorrectDateFormatException("This date doesn't match the requested date format! \n");
            }

            if(lastDate.isBiggerThan(actualReturnDate))
                throw new DateNotValidException("This date is not valid! Please enter a date that is equal to or grater than the last date that you have entered! \n");
            lastDate.setDate(tempDate);

            aux = lentBook.getIssuedDate().differenceInDays(actualReturnDate);

            if(aux < 0)
                throw new DateNotValidException("The date you entered is not valid! Please enter a date equal to or grater than the issue date! \n");

            partnerBook.setIsLent(false);
            sectionBook.setIsLent(false);
            lentBook.setIsLent(false);

            lentBook.setExceededDays1(lentBook.getReturnDate().differenceInDays(actualReturnDate));
            lentBook.setExceededPrice1(3 * lentBook.getExceededDays());

            if(lentBook.getExceededDays() > 0) {
                System.out.print("\n");
                System.out.println("The user exceeded the return date with " + lentBook.getExceededDays() + " days!");
                System.out.print("They have to pay an additional price: ");
                System.out.printf("%.02f", lentBook.getExceededPrice());
                System.out.print(" ron");
                System.out.println("\n");
                System.out.println("Paying with card or cash? (card/cash): ");
                cardOrCash = myObj.nextLine();

                if(!cardOrCash.equalsIgnoreCase("card") && !cardOrCash.equalsIgnoreCase("cash"))
                    throw new WrongInputException("You did not enter the right option! \n");

                invoice.add(new Invoice(++invoiceId, actualReturnDate.getDate(), lentBook, userThatLent, cardOrCash, false));
                clearConsole();
                System.out.println("We generated an invoice for the purchase: ");
                invoice.get(invoiceId - 1).print();
                System.out.print("\n");
            }
            else if(lentBook.getExceededDays() < 0){
                lentBook.setReturnDate(actualReturnDate.getDate());
                System.out.println("\nWhat a fast reader! We generated a 10% discount code for future purchases! ");
                Users.getDiscounts().add(new _10PercentDiscount(getAlphaNumericString(12), actualReturnDate.addMonths(2).getDate(), false));
                int currentEl = Users.getDiscounts().size() - 1;
                System.out.println("The code: " + Users.getDiscounts().get(currentEl).getCode());
                System.out.print("The expiration date: ");
                Users.getDiscounts().get(currentEl).getExpirationDate().print();
                System.out.print(" (2 months from now)\n\n");
            }
        }
    }

    public void printPartners(){
        actions.set(counter++, new MyAction("librarian: print partners", dtf.format(LocalDateTime.now())));

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
        actions.set(counter++,new MyAction("print partner books", dtf.format(LocalDateTime.now())));

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
        actions.set(counter++, new MyAction("librarian: print sections", dtf.format(LocalDateTime.now())));

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
        actions.set(counter++,new MyAction("librarian: print section books", dtf.format(LocalDateTime.now())));

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
        actions.set(counter++, new MyAction("librarian: print users", dtf.format(LocalDateTime.now())));

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
        actions.set(counter++, new MyAction("print user lent books", dtf.format(LocalDateTime.now())));

        if(users.size() < 1)
            System.out.println("There are no users! \n");
        else {
            int userId1;
            int ok1 = 0;
            int ok2 = 0;
            System.out.println("Enter the ID of the user: ");
            userId1 = myObj.nextInt();
            myObj.nextLine();
            for (Users user : users)
                if (userId1 == user.getUserId()) {
                    ok1 = 1;
                    break;
                }
            if(ok1 == 0)
                throw new UserNotFoundException("Could not find user with this ID! \n");
            if (users.get(userId1 - 1).getUserLentBooks().size() > 0) {
                for (int j = 0; j < users.get(userId1 - 1).getUserLentBooks().size(); j++)
                    if (users.get(userId1 - 1).getUserLentBooks().get(j).getIsLent()) {
                        ok2 = 1;
                        break;
                    }
                if(ok2 == 0)
                    System.out.println("There are currently no books lent by this user! \n");
                else {
                    System.out.println("Currently lent books: ");
                    for (int j = 0; j < users.get(userId1 - 1).getUserLentBooks().size(); j++)
                        if (users.get(userId1 - 1).getUserLentBooks().get(j).getIsLent()) {
                            users.get(userId1 - 1).getUserLentBooks().get(j).print();
                            System.out.print("\n");
                        }
                }
            }
            else {
                System.out.println("There are currently no books lent by this user! \n");
            }
        }
    }

    public void printUserReturnedBooks() throws UserNotFoundException{
        actions.set(counter++, new MyAction("librarian: print user returned books", dtf.format(LocalDateTime.now())));

        if(users.size() < 1)
            System.out.println("There are no users! \n");
        else {
            int userId1;
            int ok1 = 0;
            int ok2 = 0;
            System.out.println("Enter the ID of the user: ");
            userId1 = myObj.nextInt();
            myObj.nextLine();
            for (Users user : users)
                if (userId1 == user.getUserId()) {
                    ok1 = 1;
                    break;
                }
            if(ok1 == 0)
                throw new UserNotFoundException("Could not find user with this ID! \n");
            if (users.get(userId1 - 1).getUserLentBooks().size() > 0) {
                for (int j = 0; j < users.get(userId1 - 1).getUserLentBooks().size(); j++)
                    if (!users.get(userId1 - 1).getUserLentBooks().get(j).getIsLent()) {
                        ok2 = 1;
                        break;
                    }
                if(ok2 == 0)
                    System.out.println("There are no books returned by this user! \n");
                else {
                    System.out.println("Returned books: ");
                    for (int j = 0; j < users.get(userId1 - 1).getUserLentBooks().size(); j++)
                        if (!users.get(userId1 - 1).getUserLentBooks().get(j).getIsLent()) {
                            users.get(userId1 - 1).getUserLentBooks().get(j).print();
                            System.out.print("\n");
                        }
                }
            }
            else {
                System.out.println("There are no books returned by this user! \n");
            }
        }
    }

    public void printIssuedBooks(){
        actions.set(counter++, new MyAction("librarian: print all issued books", dtf.format(LocalDateTime.now())));

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
        actions.set(counter++, new MyAction("librarian: print all available books", dtf.format(LocalDateTime.now())));

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

    public void printUserDiscounts() throws UserNotFoundException{
        actions.set(counter++, new MyAction("librarian: print user discounts", dtf.format(LocalDateTime.now())));

        if(users.size() < 1)
            System.out.println("There are no users! \n");
        else {
            int userId1;
            int ok1 = 0;
            int ok2 = 0;
            System.out.println("Enter the ID of the user: ");
            userId1 = myObj.nextInt();
            myObj.nextLine();
            for (Users user : users)
                if (userId1 == user.getUserId()) {
                    ok1 = 1;
                    break;
                }
            if(ok1 == 0)
                throw new UserNotFoundException("Could not find user with this ID! \n");
            if (users.get(userId1 - 1).getDiscounts().size() > 0) {
                for (int j = 0; j < users.get(userId1 - 1).getDiscounts().size(); j++)
                    if (!users.get(userId1 - 1).getDiscounts().get(j).getIsUsed()) {
                        if(lastDate.isBiggerThan(users.get(userId1 - 1).getDiscounts().get(j).getExpirationDate()))
                            users.get(userId1 - 1).getDiscounts().get(j).setIsUsed(true);
                        else {
                            ok2 = 1;
                        }
                    }
                if(ok2 == 0)
                    System.out.println("This user hasn't earned any discounts yet! \n");
                else {
                    System.out.println("Current discounts: ");
                    for (int j = 0; j < users.get(userId1 - 1).getDiscounts().size(); j++)
                        if (!users.get(userId1 - 1).getDiscounts().get(j).getIsUsed()) {
                            users.get(userId1 - 1).getDiscounts().get(j).print();
                            System.out.print("\n");
                        }
                }
            }
            else {
                System.out.println("This user hasn't earned any discounts yet! \n");
            }
        }
    }

    public void deleteBook() throws BookNotFoundException, BookCurrentlyLentException{
        actions.set(counter++, new MyAction("librarian: delete book", dtf.format(LocalDateTime.now())));

        int bookId;
        int ok1 = 0;
        int ok2 = 0;
        int ok3 = 0;
        Books book = new Books();

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
                    if (bookId == user.getUserLentBooks().get(j).getBookId()) {
                        user.getUserLentBooks().get(j).setIsDeleted(true);
                    }

            for (Partners partner : partners)
                partner.getPartnerBooks().remove(book);

            for (Sections section : sections)
                section.getSectionBooks().remove(book);

        }
    }

    public void deleteSections() throws SectionNotFoundException, BookCurrentlyLentException{
        actions.set(counter++, new MyAction("librarian: delete section", dtf.format(LocalDateTime.now())));

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
                for (int k = 0; k < sections.get(sectionId1 - 1).getSectionBooks().size(); k++)
                    for (Users user : users)
                        for (int j = 0; j < user.getUserLentBooks().size(); j++)
                            if (sections.get(sectionId1 - 1).getSectionBooks().get(k).getBookId() == user.getUserLentBooks().get(j).getBookId()) {
                                user.getUserLentBooks().get(j).setIsDeleted(true);
                            }
            }

            if(sections.get(sectionId1 - 1).getSectionBooks().size() >= 1) {
                if (partners.size() >= 1)
                    for (Partners partner : partners)
                        for(int i = 0; i < sections.get(sectionId1 - 1).getSectionBooks().size(); i++)
                            partner.getPartnerBooks().remove(sections.get(sectionId1 - 1).getSectionBooks().get(i));
                sections.get(sectionId1 - 1).getSectionBooks().removeAll(sections.get(sectionId1 - 1).getSectionBooks());
            }

            sections.remove(sectionId1 - 1);
        }
    }

    public void deletePartners() throws PartnerNotFoundException, BookCurrentlyLentException{
        actions.set(counter++, new MyAction("librarian: delete partner", dtf.format(LocalDateTime.now())));

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
                        if (partners.get(partnerId1 - 1).getPartnerBooks().get(k).getBookId() == user.getUserLentBooks().get(j).getBookId()) {
                            user.getUserLentBooks().get(j).setIsDeleted(true);
                        }

            if(partners.get(partnerId1 - 1).getPartnerBooks().size() >= 1){
                if(sections.size() >= 1)
                    for (Sections section : sections)
                        for(int i = 0; i < partners.get(partnerId1 - 1).getPartnerBooks().size(); i++)
                            section.getSectionBooks().remove(partners.get(partnerId1 - 1).getPartnerBooks().get(i));
                partners.get(partnerId1 - 1).getPartnerBooks().removeAll(partners.get(partnerId1 - 1).getPartnerBooks());
            }

            partners.remove(partnerId1 - 1);
        }
    }
    public void deleteUsers() throws UserNotFoundException, BookCurrentlyLentException{
        actions.set(counter++, new MyAction("librarian: delete user", dtf.format(LocalDateTime.now())));

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

            if (users.get(userId1 - 1).getUserLentBooks().size() > 0)
                users.get(userId1 - 1).getUserLentBooks().removeAll(users.get(userId1 - 1).getUserLentBooks());

            users.remove(userId1 - 1);
        }
    }
}