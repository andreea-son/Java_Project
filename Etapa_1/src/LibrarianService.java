import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class LibrarianService {
    private static ArrayList<Partners> partners = new ArrayList<>();
    private static ArrayList<Sections> sections = new ArrayList<>();
    private static ArrayList<Users> users = new ArrayList<>();
    private static LinkedList<Invoice> invoice = new LinkedList<>();
    private static ArrayList<ArrayList<LentBooks>> userLentBooks = new ArrayList<>(100);
    private static ArrayList<ArrayList<Books>> sectionBooks = new ArrayList<>(100);
    private static ArrayList<ArrayList<Books>> partnerBooks = new ArrayList<>(100);
    private static ArrayList<ArrayList<_10PercentDiscount>> discounts = new ArrayList<>(100);
    private static MyDate lastDate = new MyDate();
    private int partnerId = partners.size();
    private int sectionId = sections.size();
    private int userId = users.size();
    private int invoiceId = invoice.size();
    private Scanner myObj = new Scanner(System.in);

    public LibrarianService() {

    }

    public static void initialize() {
        for (int i = 0; i < 100; i++)
            userLentBooks.add(new ArrayList<>());

        for (int i = 0; i < 100; i++)
            partnerBooks.add(new ArrayList<>());

        for (int i = 0; i < 100; i++)
            sectionBooks.add(new ArrayList<>());

        for (int i = 0; i < 100; i++)
            discounts.add(new ArrayList<>());

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

    public static ArrayList<ArrayList<LentBooks>> getUserLentBooks() {
        return userLentBooks;
    }

    public static ArrayList<ArrayList<Books>> getSectionBooks() {
        return sectionBooks;
    }

    public static ArrayList<ArrayList<Books>> getPartnerBooks() {
        return partnerBooks;
    }

    public static ArrayList<ArrayList<_10PercentDiscount>> getDiscounts() {
        return discounts;
    }

    public static MyDate getLastDate() {
        return lastDate;
    }

    public void loginInformation() throws UsernameNotFoundException, IncorrectPasswordException {
        String librarianEmail;
        String librarianPassword;

        lastDate.setDate("2000-01-01");

        System.out.println("\nUsername: ");
        librarianEmail = myObj.nextLine();
        if (!librarianEmail.equals("admin")) {
            throw new UsernameNotFoundException("Incorrect username!");
        }

        System.out.println("Password: ");

        librarianPassword = myObj.nextLine();

        if (!librarianPassword.equals("admin")) {
            throw new IncorrectPasswordException("Incorrect password!");
        }
    }

    public void addNewSection() throws AlreadyUsedNameException {
        ++sectionId;

        String sectionName;

        System.out.println("\nSection Name: ");
        sectionName = myObj.nextLine();

        if (sections.size() >= 1)
            for (Sections section : sections)
                if (section.getSectionName().equals(sectionName) && !section.getIsDeleted()) {
                    throw new AlreadyUsedNameException("This name has already been used for another section!");
                }

        sections.add(new Sections(sectionId, sectionName, false));
        System.out.println("Section added successfully!");
    }

    public void addNewPartner() throws AlreadyUsedEmailException, IncorrectMailFormatException {
        ++partnerId;

        String partnerName;
        String partnerPassword;
        String partnerEmail;

        System.out.println("\nPartner name: ");
        partnerName = myObj.nextLine();

        System.out.println("Partner email, in the following format: ");
        System.out.println("Any uppercase/lowercase letter or digit or the special characters \"_\", \".\" and \"@\"");
        partnerEmail = myObj.nextLine();

        if (partners.size() >= 1)
            for (Partners partner : partners)
                if (partner.getPartnerEmail().equals(partnerEmail) && !partner.getIsDeleted()) {
                    throw new AlreadyUsedEmailException("This email has already been used for another partner!");
                }

        if (!partnerEmail.matches("([a-z|A-Z|0-9|_|\\.]+)@([a-z|A-Z|0-9|_|\\.]+)[^\\.]\\.([a-z]+)")) {
            throw new IncorrectMailFormatException("This email doesn't have the required format!");
        }

        String[] password = partnerEmail.split("@");
        partnerPassword = password[0];

        partners.add(new Partners(partnerId, 0, partnerName, partnerPassword, partnerEmail, false));
        System.out.println("Partner added successfully!");
    }

    public void addNewUser() throws AlreadyUsedEmailException, IncorrectMailFormatException {
        ++userId;

        String userName;
        String userEmail;
        String userPassword;

        System.out.println("\nUser name: ");
        userName = myObj.nextLine();

        System.out.println("User email, in the following format: ");
        System.out.println("Any uppercase/lowercase letter or digit or the special characters \"_\", \".\" and \"@\"");
        userEmail = myObj.nextLine();

        if (users.size() >= 1)
            for (Users user : users)
                if (user.getUserEmail().equals(userEmail) && !user.getIsDeleted()) {
                    throw new AlreadyUsedEmailException("This email has already been used!");
                }

        if (!userEmail.matches("([a-z|A-Z|0-9|_|\\.]+)@([a-z|A-Z|0-9|_|\\.]+)[^\\.]\\.([a-z]+)")) {
            throw new IncorrectMailFormatException("This email doesn't have the required format!");
        }

        String[] password = userEmail.split("@");
        userPassword = password[0];

        users.add(new Users(userId, 0, userName, userPassword, userEmail, false));
        System.out.println("User added successfully!");
    }

    public void lendNewBook() throws BookNotFoundException, BookAlreadyLentException, UserNotFoundException, IncorrectDateFormatException, MaxNumOfDaysException, WrongInputException, InvalidCodeException, DateNotValidException {
        int numOfLogins;
        int userId1;
        int bookId;
        int daysFromIssue;
        int ok1 = 0;
        int ok2 = 0;
        int ok3 = 0;
        int ok4 = 0;
        int ok5 = 0;
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

        for (int i = 0; i < partners.size(); i++)
            for (int j = 0; j < partnerBooks.get(i).size(); j++)
                if (partnerBooks.get(i).size() > 0)
                    if (!partnerBooks.get(i).get(j).getIsDeleted()){
                        ok1 = 1;
                        break;
                    }

        if(users.size() == 0) {
            System.out.println("\nThere are no users that could lend!");
        }
        else {
            for (Users user : users)
                if (!user.getIsDeleted()) {
                    ok2 = 1;
                    break;
                }
            if (ok2 == 0) {
                System.out.println("\nThere are no users that could lend!");
            } else if (ok1 == 0) {
                System.out.println("\nThere are no books!");
            } else {
                System.out.println("\nUser ID: ");
                userId1 = myObj.nextInt();
                myObj.nextLine();

                for (Users user : users)
                    if (userId1 == user.getUserId() && !user.getIsDeleted()) {
                        ok3 = 1;
                        break;
                    }

                if (ok3 == 0)
                    throw new UserNotFoundException("Could not find user with this ID!");

                System.out.println("Book ID: ");
                bookId = myObj.nextInt();
                myObj.nextLine();

                for (int i = 0; i < partners.size(); i++)
                    for (int j = 0; j < partnerBooks.get(i).size(); j++)
                        if (bookId == partnerBooks.get(i).get(j).getBookId() && !partnerBooks.get(i).get(j).getIsDeleted()) {
                            ok4 = 1;
                            break;
                        }

                if (ok4 == 0)
                    throw new BookNotFoundException("Could not find book with this ID!");

                for (int i = 0; i < partners.size(); i++)
                    for (int j = 0; j < partnerBooks.get(i).size(); j++)
                        if ((partnerBooks.get(i).get(j).getBookId() == bookId) && (partnerBooks.get(i).get(j).getIsLent())) {
                            throw new BookAlreadyLentException("This book is not available for lending right now!");
                        }

                System.out.println("Issue date (yyyy-MM-dd): ");
                issuedDate = myObj.nextLine();

                if (!issuedDate.matches("(((200)|(201))[0-9]|(202)[012])-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")) {
                    throw new IncorrectDateFormatException("This date doesn't match the requested date format!");
                }

                tempDate.setDate(issuedDate);

                if (lastDate.isBiggerThan(tempDate))
                    throw new DateNotValidException("Please enter a date equal to or grater than " + lastDate.getDate());

                lastDate.setDate(issuedDate);

                System.out.println("Number of days to keep the book (< 60): ");
                daysFromIssue = myObj.nextInt();
                myObj.nextLine();

                if (daysFromIssue > 60) {
                    throw new MaxNumOfDaysException("Too many days!");
                }

                for (int i = 0; i < partners.size(); i++)
                    for (int j = 0; j < partnerBooks.get(i).size(); j++)
                        if (partnerBooks.get(i).get(j).getBookId() == bookId) {
                            partnerBook = partnerBooks.get(i).get(j);
                        }

                for (int i = 0; i < sections.size(); i++)
                    for (int j = 0; j < sectionBooks.get(i).size(); j++)
                        if (sectionBooks.get(i).get(j).getBookId() == bookId) {
                            sectionBook = sectionBooks.get(i).get(j);
                        }

                partnerBook.setIsLent(true);
                sectionBook.setIsLent(true);

                userName = users.get(userId1 - 1).getUserName();
                userPassword = users.get(userId1 - 1).getUserPassword();
                userEmail = users.get(userId1 - 1).getUserEmail();

                userLentBooks.get(userId1 - 1).add(0, new LentBooks(bookId, false, partnerBook.getAuthor(), partnerBook.getTitle(), partnerBook.getDescription(), issuedDate, daysFromIssue, true, partnerBook.getSectionId(), partnerBook.getPartnerId(), userId1));

                for (int i = 0; i < userLentBooks.get(userId1 - 1).size(); i++)
                    if (userLentBooks.get(userId1 - 1).get(i).getBookId() == bookId && userLentBooks.get(userId1 - 1).get(i).getIsLent())
                        userLentBook = userLentBooks.get(userId1 - 1).get(i);

                System.out.print("\n");
                System.out.print("The price: ");
                System.out.printf("%.02f", userLentBook.getPrice());
                System.out.print(" ron");
                System.out.println("\n");

                System.out.println("Discount code? (y/n)");
                yOrN = myObj.nextLine();

                if (!yOrN.equals("y") && !yOrN.equals("Y") && !yOrN.equals("n") && !yOrN.equals("N"))
                    throw new WrongInputException("Invalid option!");

                if (yOrN.equals("y") || yOrN.equals("Y")) {
                    System.out.println("The code: ");
                    discountCode = myObj.nextLine();
                    for (int i = 0; i < discounts.get(userId1 - 1).size(); i++)
                        if (discountCode.equals(discounts.get(userId1 - 1).get(i).getCode()) && !discounts.get(userId1 - 1).get(i).getIsUsed()) {
                            ok5 = 1;
                            discount = discounts.get(userId1 - 1).get(i);
                            break;
                        }

                    if (ok5 == 0)
                        throw new InvalidCodeException("This code doesn't exist on this user's discounts' list or it has already been used!");

                    if (tempDate.isBiggerThan(discount.getExpirationDate())) {
                        throw new InvalidCodeException("This code has already expired!");
                    }
                    float discountPrice = userLentBook.getPrice() - userLentBook.getPrice() / 10;

                    System.out.print("\n");
                    System.out.print("The price after discount: ");
                    System.out.printf("%.02f", discountPrice);
                    System.out.print(" ron");
                    System.out.println("\n");

                    discount.setIsUsed(true);

                    System.out.println("Card or cash? (card/cash): ");
                    cardOrCash = myObj.nextLine();

                    if (!cardOrCash.equalsIgnoreCase("card") && !cardOrCash.equalsIgnoreCase("cash"))
                        throw new WrongInputException("Invalid option!");

                    numOfLogins = users.get(userId1 - 1).getNumOfLogins();
                    users.set(userId1 - 1, new Users(userId1, numOfLogins, userName, userPassword, userEmail, false));
                    invoice.add(new Invoice(++invoiceId, issuedDate, userId1, cardOrCash, true));
                    System.out.println("\nThe invoice generated for the purchase:\n");
                    invoice.get(invoiceId - 1).print();
                    userLentBook.setPrice(discountPrice);
                } else {
                    System.out.println("Card or cash? (card/cash): ");
                    cardOrCash = myObj.nextLine();

                    if (!cardOrCash.equalsIgnoreCase("card") && !cardOrCash.equalsIgnoreCase("cash"))
                        throw new WrongInputException("Invalid option!");

                    numOfLogins = users.get(userId1 - 1).getNumOfLogins();
                    users.set(userId1 - 1, new Users(userId1, numOfLogins, userName, userPassword, userEmail, false));
                    invoice.add(new Invoice(++invoiceId, issuedDate, userId1, cardOrCash, false));
                    System.out.println("\nThe invoice generated for the purchase:\n ");
                    invoice.get(invoiceId - 1).print();
                }
            }
        }
    }

    static String getAlphaNumericString(int n) {

        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

        StringBuilder stringBuilder = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            stringBuilder.append(AlphaNumericString.charAt(index));
        }

        return stringBuilder.toString();
    }

    public void returnBook() throws UserNotFoundException, BookNotFoundException, IncorrectDateFormatException, BookNotLentException, NoLentBooksException, WrongInputException, DateNotValidException {
        int userId1;
        int bookId;
        int ok1 = 0;
        int ok2 = 0;
        int ok3 = 0;
        int ok4 = 0;
        int ok5 = 0;
        int ok6 = 0;
        MyDate actualReturnDate = new MyDate();
        LentBooks lentBook = new LentBooks();
        Books sectionBook = new Books();
        Books partnerBook = new Books();
        String tempDate;
        String cardOrCash;

        for (int i = 0; i < users.size(); i++)
            for (int j = 0; j < userLentBooks.get(i).size(); j++)
                if (userLentBooks.get(i).get(j).getIsLent() && !userLentBooks.get(i).get(j).getIsDeleted()) {
                    ok1 = 1;
                    break;
                }
        if(users.size() == 0) {
            System.out.println("\nThere are no users that could return books!");
        }
        else {
            for (Users user : users)
                if (!user.getIsDeleted()) {
                    ok2 = 1;
                    break;
                }
            if (ok2 == 0) {
                System.out.println("\nThere are no users that could return books!");
            } else if (ok1 == 0) {
                System.out.println("\nThere are no lent books!");
            } else {
                System.out.println("\nUser ID: ");
                userId1 = myObj.nextInt();
                myObj.nextLine();

                for (Users user : users)
                    if (userId1 == user.getUserId() && !user.getIsDeleted()) {
                        ok3 = 1;
                        break;
                    }

                if (ok3 == 0)
                    throw new UserNotFoundException("Could not find user with this ID!");

                for (int i = 0; i < userLentBooks.get(userId1 - 1).size(); i++)
                    if (userLentBooks.get(userId1 - 1).get(i).getIsLent()) {
                        ok4 = 1;
                        break;
                    }
                if (ok4 == 0)
                    throw new NoLentBooksException("This user has no lent books at the moment!");

                System.out.println("Book ID: ");
                bookId = myObj.nextInt();
                myObj.nextLine();

                for (int i = 0; i < partners.size(); i++)
                    for (int j = 0; j < partnerBooks.get(i).size(); j++) {
                        if (bookId == partnerBooks.get(i).get(j).getBookId() && !partnerBooks.get(i).get(j).getIsDeleted()) {
                            ok5 = 1;
                            partnerBook = partnerBooks.get(i).get(j);
                            break;
                        }
                    }

                if (ok5 == 0)
                    throw new BookNotFoundException("Could not find book with this ID!");

                for (int i = 0; i < sections.size(); i++)
                    for (int j = 0; j < sectionBooks.get(i).size(); j++)
                        if (bookId == sectionBooks.get(i).get(j).getBookId() && !sectionBooks.get(i).get(j).getIsDeleted()) {
                            sectionBook = sectionBooks.get(i).get(j);
                            break;
                        }

                for (int i = 0; i < userLentBooks.get(userId1 - 1).size(); i++)
                    if (bookId == userLentBooks.get(userId1 - 1).get(i).getBookId() && userLentBooks.get(userId1 - 1).get(i).getIsLent()) {
                        ok6 = 1;
                        lentBook = userLentBooks.get(userId1 - 1).get(i);
                        break;
                    }

                if (ok6 == 0)
                    throw new BookNotLentException("This book is not on the current lent book list of the user! You can't return it!");

                System.out.println("Current date (yyyy-MM-dd): ");
                tempDate = myObj.nextLine();
                actualReturnDate.setDate(tempDate);

                if (!actualReturnDate.getDate().matches("(((200)|(201))[0-9]|(202)[012])-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")) {
                    throw new IncorrectDateFormatException("This date doesn't match the requested date format!");
                }

                if (lastDate.isBiggerThan(actualReturnDate))
                    throw new DateNotValidException("Please enter a date equal to or grater than " + lastDate.getDate());
                lastDate.setDate(tempDate);

                partnerBook.setIsLent(false);
                sectionBook.setIsLent(false);
                lentBook.setIsLent(false);

                lentBook.setExceededDays1(lentBook.getReturnDate().differenceInDays(actualReturnDate));
                lentBook.setExceededPrice1(3 * lentBook.getExceededDays());

                if (lentBook.getExceededDays() > 0) {
                    System.out.print("\n");
                    System.out.println("The user exceeded the return date with " + lentBook.getExceededDays() + " days!");
                    System.out.print("They have to pay an additional price: ");
                    System.out.printf("%.02f", lentBook.getExceededPrice());
                    System.out.print(" ron");
                    System.out.println("\n");
                    System.out.println("Card or cash? (card/cash): ");
                    cardOrCash = myObj.nextLine();

                    if (!cardOrCash.equalsIgnoreCase("card") && !cardOrCash.equalsIgnoreCase("cash"))
                        throw new WrongInputException("Invalid option!");

                    invoice.add(new Invoice(++invoiceId, actualReturnDate.getDate(), userId1, cardOrCash, false));
                    System.out.println("\nThe invoice generated for the purchase:\n ");
                    invoice.get(invoiceId - 1).print();
                } else if (lentBook.getExceededDays() < 0) {
                    lentBook.setReturnDate(actualReturnDate.getDate());
                    System.out.println("\nWhat a fast reader! We generated a 10% discount code for future purchases! ");
                    discounts.get(userId1 - 1).add(new _10PercentDiscount(getAlphaNumericString(12), actualReturnDate.addMonths(2).getDate(), userId1, false));
                    int index = discounts.get(userId1 - 1).size() - 1;
                    System.out.print("\n");
                    System.out.print("Code: " + discounts.get(userId1 - 1).get(index).getCode() + ", expiration date: ");
                    discounts.get(userId1 - 1).get(index).getExpirationDate().print();
                    System.out.print("\n");
                }
            }
        }
    }

    public void printPartners() {
        int ok = 0;

        if (partners.size() > 0) {
            for (Partners partner : partners)
                if (!partner.getIsDeleted()) {
                    ok = 1;
                    break;
                }
            if (ok == 0)
                System.out.println("\nThere are no partners!");
            else {
                System.out.println("\nPartners:\n");
                for (int i = 0; i < partners.size() - 1; i++)
                    if (!partners.get(i).getIsDeleted()) {
                        partners.get(i).print();
                        System.out.print("\n");
                    }
                partners.get(partners.size() - 1).print();
            }
        } else {
            System.out.println("\nThere are no partners!");
        }
    }

    public void printPartnerBooks() throws PartnerNotFoundException {
        int partnerId1;
        int ok1 = 0;
        int ok2 = 0;
        int ok3 = 0;

        if (partners.size() > 0) {
            for (Partners partner : partners)
                if (!partner.getIsDeleted()) {
                    ok1 = 1;
                    break;
                }
            if (ok1 == 0)
                System.out.println("\nThere are no partners!");
            else {

                System.out.println("\nPartner ID:");
                partnerId1 = myObj.nextInt();
                myObj.nextLine();
                for (Partners partner : partners)
                    if (partnerId1 == partner.getPartnerId() && !partner.getIsDeleted()) {
                        ok2 = 1;
                        break;
                    }
                if (ok2 == 0)
                    throw new PartnerNotFoundException("Could not find partner with this ID!");
                if (partnerBooks.get(partnerId1 - 1).size() > 0) {
                    for (int j = 0; j < partnerBooks.get(partnerId1 - 1).size(); j++)
                        if (!partnerBooks.get(partnerId1 - 1).get(j).getIsDeleted()) {
                            ok3 = 1;
                            break;
                        }
                    if (ok3 == 0)
                        System.out.println("There are no books added by this partner!");
                    else {
                        System.out.println("This partner's books:\n");
                        for (int j = 0; j < partnerBooks.get(partnerId1 - 1).size() - 1; j++)
                            if (!partnerBooks.get(partnerId1 - 1).get(j).getIsDeleted()) {
                                partnerBooks.get(partnerId1 - 1).get(j).print();
                                System.out.print("\n");
                            }
                        partnerBooks.get(partnerId1 - 1).get(partnerBooks.get(partnerId1 - 1).size() - 1).print();
                    }
                } else {
                    System.out.println("There are no books added by this partner!");
                }
            }
        } else {
            System.out.println("\nThere are no partners!");
        }
    }

    public void printSections() {
        int ok = 0;

        if (sections.size() > 0) {
            for (Sections section : sections)
                if (!section.getIsDeleted()) {
                    ok = 1;
                    break;
                }
            if (ok == 0)
                System.out.println("\nThere are no sections!");
            else {
                System.out.println("\nSections:\n");
                for (int i = 0; i < sections.size() - 1; i++)
                    if (!sections.get(i).getIsDeleted()) {
                        sections.get(i).print();
                        System.out.print("\n");
                    }
                sections.get(sections.size() - 1).print();
            }
        } else {
            System.out.println("\nThere are no sections!");
        }
    }

    public void printSectionBooks() throws SectionNotFoundException {
        int sectionId1;
        int ok1 = 0;
        int ok2 = 0;
        int ok3 = 0;

        if (sections.size() > 0) {
            for (Sections section : sections)
                if (!section.getIsDeleted()) {
                    ok1 = 1;
                    break;
                }
            if (ok1 == 0)
                System.out.println("\nThere are no sections!");
            else {

                System.out.println("\nSection ID:");
                sectionId1 = myObj.nextInt();
                myObj.nextLine();
                for (Sections section : sections)
                    if (sectionId1 == section.getSectionId() && !section.getIsDeleted()) {
                        ok2 = 1;
                        break;
                    }
                if (ok2 == 0)
                    throw new SectionNotFoundException("Could not find section with this ID!");
                if (sectionBooks.get(sectionId1 - 1).size() > 0) {
                    for (int j = 0; j < sectionBooks.get(sectionId1 - 1).size(); j++)
                        if (!sectionBooks.get(sectionId1 - 1).get(j).getIsDeleted()) {
                            ok3 = 1;
                            break;
                        }
                    if (ok3 == 0)
                        System.out.println("There are no books in this section!");
                    else {
                        System.out.println("This section's books:\n");
                        for (int j = 0; j < sectionBooks.get(sectionId1 - 1).size() - 1; j++)
                            if (!sectionBooks.get(sectionId1 - 1).get(j).getIsDeleted()) {
                                sectionBooks.get(sectionId1 - 1).get(j).print();
                                System.out.print("\n");
                            }
                        sectionBooks.get(sectionId1 - 1).get(sectionBooks.get(sectionId1 - 1).size() - 1).print();
                    }
                } else {
                    System.out.println("There are no books in this section!");
                }
            }
        } else {
            System.out.println("\nThere are no sections!");
        }
    }

    public void printUsers() {
        int ok = 0;

        if (users.size() > 0) {
            for (Users user : users)
                if (!user.getIsDeleted()) {
                    ok = 1;
                    break;
                }
            if (ok == 0)
                System.out.println("\nThere are no users!");
            else {
                System.out.println("\nUsers:\n");
                for (int i = 0; i < users.size() - 1; i++)
                    if (!users.get(i).getIsDeleted()) {
                        users.get(i).print();
                        System.out.print("\n");
                    }
                users.get(users.size() - 1).print();
            }
        } else {
            System.out.println("\nThere are no users!");
        }
    }

    public void printUserLentBooks() throws UserNotFoundException {
        int userId1;
        int ok1 = 0;
        int ok2 = 0;
        int ok3 = 0;

        if (users.size() > 0) {
            for (Users user : users)
                if (!user.getIsDeleted()) {
                    ok1 = 1;
                    break;
                }
            if (ok1 == 0)
                System.out.println("\nThere are no users!");
            else {

                System.out.println("\nUser ID:");
                userId1 = myObj.nextInt();
                myObj.nextLine();
                for (Users user : users)
                    if (userId1 == user.getUserId() && !user.getIsDeleted()) {
                        ok2 = 1;
                        break;
                    }
                if (ok2 == 0)
                    throw new UserNotFoundException("Could not find user with this ID!");
                if (userLentBooks.get(userId1 - 1).size() > 0) {
                    for (int j = 0; j < userLentBooks.get(userId1 - 1).size(); j++)
                        if (userLentBooks.get(userId1 - 1).get(j).getIsLent()) {
                            ok3 = 1;
                            break;
                        }
                    if (ok3 == 0)
                        System.out.println("There are currently no books lent by this user!");
                    else {
                        System.out.println("This user's lent books:\n");
                        for (int j = 0; j < userLentBooks.get(userId1 - 1).size() - 1; j++)
                            if (userLentBooks.get(userId1 - 1).get(j).getIsLent()) {
                                userLentBooks.get(userId1 - 1).get(j).print();
                                System.out.print("\n");
                            }
                        userLentBooks.get(userId1 - 1).get(userLentBooks.get(userId1 - 1).size() - 1).print();
                    }
                } else {
                    System.out.println("There are currently no books lent by this user!");
                }
            }
        } else {
            System.out.println("\nThere are no users!");
        }
    }

    public void printUserReturnedBooks() throws UserNotFoundException {
        int userId1;
        int ok1 = 0;
        int ok2 = 0;
        int ok3 = 0;

        if (users.size() > 0) {
            for (Users user : users)
                if (!user.getIsDeleted()) {
                    ok1 = 1;
                    break;
                }
            if (ok1 == 0)
                System.out.println("\nThere are no users!");
            else {

                System.out.println("\nUser ID: ");
                userId1 = myObj.nextInt();
                myObj.nextLine();
                for (Users user : users)
                    if (userId1 == user.getUserId() && !user.getIsDeleted()) {
                        ok2 = 1;
                        break;
                    }
                if (ok2 == 0)
                    throw new UserNotFoundException("Could not find user with this ID!");
                if (userLentBooks.get(userId1 - 1).size() > 0) {
                    for (int j = 0; j < userLentBooks.get(userId1 - 1).size(); j++)
                        if (!userLentBooks.get(userId1 - 1).get(j).getIsLent()) {
                            ok3 = 1;
                            break;
                        }
                    if (ok3 == 0)
                        System.out.println("There are no books returned by this user!");
                    else {
                        System.out.println("This user's returned books:\n");
                        for (int j = 0; j < userLentBooks.get(userId1 - 1).size() - 1; j++)
                            if (!userLentBooks.get(userId1 - 1).get(j).getIsLent()) {
                                userLentBooks.get(userId1 - 1).get(j).print();
                                System.out.print("\n");
                            }
                        userLentBooks.get(userId1 - 1).get(userLentBooks.get(userId1 - 1).size() - 1).print();
                    }
                } else {
                    System.out.println("There are no books returned by this user!");
                }
            }
        } else {
            System.out.println("\nThere are no users!");
        }
    }

    public void printIssuedBooks() {
        int ok1 = 0;
        int ok2 = 0;

        for (int i = 0; i < users.size(); i++)
            if (userLentBooks.get(i).size() > 0) {
                ok1 = 1;
                break;
            }
        if (ok1 == 0)
            System.out.println("\nThere are no issued books!");
        else {
            for (int i = 0; i < users.size(); i++)
                for (int j = 0; j < userLentBooks.get(i).size(); j++)
                    if (userLentBooks.get(i).get(j).getIsLent()) {
                        ok2 = 1;
                        break;
                    }
            if (ok2 == 0)
                System.out.println("\nThere are no issued books!");
            else {
                System.out.println("\nIssued books:\n");
                for (int i = 0; i < users.size(); i++) {
                    if(userLentBooks.get(i).size() >= 1) {
                        for (int j = 0; j < userLentBooks.get(i).size(); j++)
                            if (userLentBooks.get(i).get(j).getIsLent()) {
                                userLentBooks.get(i).get(j).print();
                                System.out.println("The user that lent this book: ");
                                users.get(i).print();
                                System.out.print("\n");
                            }
                    }
                }
            }
        }
    }

    public void printBooks() {
        int ok1 = 0;
        int ok2 = 0;

        for (int i = 0; i < partners.size(); i++)
            if (partnerBooks.get(i).size() > 0) {
                ok1 = 1;
                break;
            }
        if (ok1 == 0)
            System.out.println("\nThere are no books!");
        else {
            for (int i = 0; i < partners.size(); i++)
                for (int j = 0; j < partnerBooks.get(i).size(); j++)
                    if (!partnerBooks.get(i).get(j).getIsLent() && !partnerBooks.get(i).get(j).getIsDeleted()) {
                        ok2 = 1;
                        break;
                    }
            if (ok2 == 0)
                System.out.println("\nThere are no available books!");
            else {
                System.out.println("\nAvailable books:\n");
                for (int i = 0; i < partners.size(); i++) {
                    for (int j = 0; j < partnerBooks.get(i).size(); j++)
                        if (!partnerBooks.get(i).get(j).getIsLent() && !partnerBooks.get(i).get(j).getIsDeleted()) {
                            partnerBooks.get(i).get(j).print();
                            System.out.print("\n");
                        }
                }
            }
        }
    }

    public void printUserDiscounts() throws UserNotFoundException {
        int ok1 = 0;
        int ok2 = 0;
        int ok3 = 0;
        int userId1;

        if (users.size() > 0) {
            for (Users user : users)
                if (!user.getIsDeleted()) {
                    ok1 = 1;
                    break;
                }
            if (ok1 == 0)
                System.out.println("\nThere are no users!");
            else {
                System.out.println("\nUser ID: ");
                userId1 = myObj.nextInt();
                myObj.nextLine();
                for (Users user : users)
                    if (userId1 == user.getUserId() && !user.getIsDeleted()) {
                        ok2 = 1;
                        break;
                    }
                if (ok2 == 0)
                    throw new UserNotFoundException("Could not find user with this ID!");
                if (discounts.get(userId1 - 1).size() > 0) {
                    for (int j = 0; j < discounts.get(userId1 - 1).size(); j++)
                        if (!discounts.get(userId1 - 1).get(j).getIsUsed()) {
                            if (lastDate.isBiggerThan(discounts.get(userId1 - 1).get(j).getExpirationDate()))
                                discounts.get(userId1 - 1).get(j).setIsUsed(true);
                            else {
                                ok3 = 1;
                            }
                        }
                    if (ok3 == 0)
                        System.out.println("This user hasn't earned any discounts yet!");
                    else {
                        System.out.println("This user's discounts: ");
                        for (int j = 0; j < discounts.get(userId1 - 1).size() - 1; j++)
                            if (!discounts.get(userId1 - 1).get(j).getIsUsed()) {
                                discounts.get(userId1 - 1).get(j).print();
                                System.out.print("\n");
                            }
                        discounts.get(userId1 - 1).get(discounts.get(userId1 - 1).size() - 1).print();
                    }
                } else {
                    System.out.println("This user hasn't earned any discounts yet!");
                }
            }
        } else {
            System.out.println("\nThere are no users!");
        }
    }

    public void deleteBook() throws BookNotFoundException, BookCurrentlyLentException {
        int bookId;
        int ok1 = 0;
        int ok2 = 0;
        int ok3 = 0;
        int ok4 = 0;

        for (int i = 0; i < partners.size(); i++)
            if (partnerBooks.get(i).size() > 0) {
                ok1 = 1;
                break;
            }
        if (ok1 == 0) {
            System.out.println("\nThere are no books!");
        }
        else {
            for (int i = 0; i < partners.size(); i++)
                for (int j = 0; j < partnerBooks.get(i).size(); j++)
                    if (!partnerBooks.get(i).get(j).getIsLent() && !partnerBooks.get(i).get(j).getIsDeleted()) {
                        ok2 = 1;
                        break;
                    }
            if (ok2 == 0) {
                System.out.println("\nThere are no books!");
            } else {
                System.out.println("\nBook ID:");
                bookId = myObj.nextInt();
                myObj.nextLine();
                for (int i = 0; i < partners.size(); i++)
                    for (int j = 0; j < partnerBooks.get(i).size(); j++)
                        if (bookId == partnerBooks.get(i).get(j).getBookId() && !partnerBooks.get(i).get(j).getIsDeleted()) {
                            ok3 = 1;
                            break;
                        }
                if (ok3 == 0)
                    throw new BookNotFoundException("Could not find book with this ID!");

                for (int i = 0; i < users.size(); i++)
                    for (int j = 0; j < userLentBooks.get(i).size(); j++)
                        if (bookId == userLentBooks.get(i).get(j).getBookId() && userLentBooks.get(i).get(j).getIsLent()) {
                            ok4 = 1;
                            break;
                        }

                if (ok4 == 1)
                    throw new BookCurrentlyLentException("This book is currently on the lent books' list. You can't remove it!");

                for (int i = 0; i < users.size(); i++)
                    for (int j = 0; j < userLentBooks.get(i).size(); j++)
                        if (bookId == userLentBooks.get(i).get(j).getBookId()) {
                            userLentBooks.get(i).get(j).setIsDeleted(true);
                        }

                for (int i = 0; i < partners.size(); i++)
                    for (int j = 0; j < partnerBooks.get(i).size(); j++)
                        if (bookId == partnerBooks.get(i).get(j).getBookId()) {
                            partnerBooks.get(i).get(j).setIsDeleted(true);
                        }

                for (int i = 0; i < sections.size(); i++)
                    for (int j = 0; j < sectionBooks.get(i).size(); j++)
                        if (bookId == sectionBooks.get(i).get(j).getBookId()) {
                            sectionBooks.get(i).get(j).setIsDeleted(true);
                        }
                System.out.println("Book deleted successfully!");
            }
        }
    }

    public void deleteSections() throws SectionNotFoundException, BookCurrentlyLentException {
        int sectionId1;
        int ok1 = 0;
        int ok2 = 0;
        int ok3 = 0;

        if (sections.size() < 1) {
            System.out.println("\nThere are no sections!");
        } else {
            for (Sections section : sections)
                if (!section.getIsDeleted()) {
                    ok1 = 1;
                    break;
                }
            if (ok1 == 0) {
                System.out.println("\nThere are no sections!");
            } else {
                System.out.println("\nSection ID: ");
                sectionId1 = myObj.nextInt();
                myObj.nextLine();
                for (Sections section : sections)
                    if (sectionId1 == section.getSectionId() && !section.getIsDeleted()) {
                        ok2 = 1;
                        break;
                    }
                if (ok2 == 0)
                    throw new SectionNotFoundException("Could not find section with this ID!");

                for (int i = 0; i < sectionBooks.get(sectionId1 - 1).size(); i++)
                    for (int j = 0; j < users.size(); j++)
                        for (int k = 0; k < userLentBooks.get(j).size(); k++)
                            if (sectionBooks.get(sectionId1 - 1).get(i).getBookId() == userLentBooks.get(j).get(k).getBookId() && userLentBooks.get(j).get(k).getIsLent()) {
                                ok3 = 1;
                                break;
                            }

                if (ok3 == 1)
                    throw new BookCurrentlyLentException("This section contains a book that is currently lent. You can't remove this section!");

                if (sectionBooks.get(sectionId1 - 1).size() >= 1) {
                    for (int i = 0; i < sectionBooks.get(sectionId1 - 1).size(); i++)
                        for (int j = 0; j < users.size(); j++)
                            for (int k = 0; k < userLentBooks.get(j).size(); k++)
                                if (sectionBooks.get(sectionId1 - 1).get(i).getBookId() == userLentBooks.get(j).get(k).getBookId()) {
                                    userLentBooks.get(j).get(k).setIsDeleted(true);
                                }
                }

                if (sectionBooks.get(sectionId1 - 1).size() >= 1) {
                    for (int i = 0; i < sectionBooks.get(sectionId1 - 1).size(); i++)
                        for (int j = 0; j < partners.size(); j++)
                            for (int k = 0; k < partnerBooks.get(j).size(); k++)
                                if (sectionBooks.get(sectionId1 - 1).get(i).getBookId() == partnerBooks.get(j).get(k).getBookId()) {
                                    partnerBooks.get(j).get(k).setIsDeleted(true);
                                }
                }

                for (int i = 0; i < sectionBooks.get(sectionId1 - 1).size(); i++)
                    sectionBooks.get(sectionId1 - 1).get(i).setIsDeleted(true);

                sections.get(sectionId1 - 1).setIsDeleted(true);
                System.out.println("Section deleted successfully!");
            }
        }
    }

    public void deletePartners() throws PartnerNotFoundException, BookCurrentlyLentException {
        int partnerId1;
        int ok1 = 0;
        int ok2 = 0;
        int ok3 = 0;

        if (partners.size() < 1) {
            System.out.println("\nThere are no partners!");
        } else {
            for (Partners partner : partners)
                if (!partner.getIsDeleted()) {
                    ok1 = 1;
                    break;
                }
            if (ok1 == 0)
                System.out.println("\nThere are no partners!");
            else {
                System.out.println("\nPartner ID: ");
                partnerId1 = myObj.nextInt();
                myObj.nextLine();

                for (Partners partner : partners)
                    if (partnerId1 == partner.getPartnerId() && !partner.getIsDeleted()) {
                        ok2 = 1;
                        break;
                    }
                if (ok2 == 0)
                    throw new PartnerNotFoundException("Could not find partner with this ID!");

                for (int i = 0; i < partnerBooks.get(partnerId1 - 1).size(); i++)
                    for (int j = 0; j < users.size(); j++)
                        for (int k = 0; k < userLentBooks.get(j).size(); k++)
                            if (partnerBooks.get(partnerId1 - 1).get(i).getBookId() == userLentBooks.get(j).get(k).getBookId() && userLentBooks.get(j).get(k).getIsLent()) {
                                ok3 = 1;
                                break;
                            }

                if (ok3 == 1)
                    throw new BookCurrentlyLentException("This partner added a book that is currently lent. If you remove this partner, you will remove the book!");

                for (int i = 0; i < partnerBooks.get(partnerId1 - 1).size(); i++)
                    for (int j = 0; j < users.size(); j++)
                        for (int k = 0; k < userLentBooks.get(j).size(); k++)
                            if (partnerBooks.get(partnerId1 - 1).get(i).getBookId() == userLentBooks.get(j).get(k).getBookId()) {
                                userLentBooks.get(j).get(k).setIsDeleted(true);
                            }

                for (int i = 0; i < partnerBooks.get(partnerId1 - 1).size(); i++)
                    for (int j = 0; j < sections.size(); j++)
                        for (int k = 0; k < sectionBooks.get(j).size(); k++)
                            if (partnerBooks.get(partnerId1 - 1).get(i).getBookId() == sectionBooks.get(j).get(k).getBookId()) {
                                sectionBooks.get(j).get(k).setIsDeleted(true);
                            }

                for (int i = 0; i < partnerBooks.get(partnerId1 - 1).size(); i++)
                    partnerBooks.get(partnerId1 - 1).get(i).setIsDeleted(true);

                partners.get(partnerId1 - 1).setIsDeleted(true);
                System.out.println("Partner deleted successfully!");
            }
        }
    }

    public void deleteUsers() throws UserNotFoundException, BookCurrentlyLentException {
        int userId1;
        int ok1 = 0;
        int ok2 = 0;
        int ok3 = 0;

        if (users.size() < 1) {
            System.out.println("\nThere are no users!");
        } else {
            for (Users user : users)
                if (!user.getIsDeleted()) {
                    ok1 = 1;
                    break;
                }
            if (ok1 == 0)
                System.out.println("\nThere are no users!");
            else {
                System.out.println("\nUser ID: ");
                userId1 = myObj.nextInt();
                myObj.nextLine();
                for (Users user : users)
                    if (userId1 == user.getUserId() && !user.getIsDeleted()) {
                        ok2 = 1;
                        break;
                    }
                if (ok2 == 0)
                    throw new UserNotFoundException("Could not find user with this ID!");

                for (int j = 0; j < userLentBooks.get(userId1 - 1).size(); j++)
                    if (userLentBooks.get(userId1 - 1).get(j).getIsLent()) {
                        ok3 = 1;
                        break;
                    }

                if (ok3 == 1)
                    throw new BookCurrentlyLentException("This user is currently lending a book! You can't remove this user!");

                for (int i = 0; i < userLentBooks.get(userId1 - 1).size(); i++)
                    userLentBooks.get(userId1 - 1).get(i).setIsDeleted(true);

                for (int i = 0; i < discounts.get(userId1 - 1).size(); i++)
                    discounts.get(userId1 - 1).get(i).setIsUsed(true);

                users.get(userId1 - 1).setIsDeleted(true);
                System.out.println("User deleted successfully!");
            }
        }
    }
}
