import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class LibrarianService {
    private Scanner myObj = new Scanner(System.in);
    private static ArrayList<Partners> partners = new ArrayList<>();
    private static ArrayList<Sections> sections = new ArrayList<>();
    private static ArrayList<Users> users = new ArrayList<>();
    private static LinkedList<Invoice> invoice = new LinkedList<>();
    private static ArrayList<ArrayList<LentBooks>> userLentBooks = new ArrayList<>(100);
    private static ArrayList<ArrayList<Books>> sectionBooks = new ArrayList<>(100);
    private static ArrayList<ArrayList<Books>> partnerBooks = new ArrayList<>(100);
    private static ArrayList<ArrayList<_10PercentDiscount>> discounts = new ArrayList<>(100);
    private static MyDate lastDate = new MyDate();
    private static int sectionId;
    private static int partnerId;
    private static int userId;
    private static int invoiceId;

    public LibrarianService(){

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

    public static LinkedList<Invoice> getInvoice() {
        return invoice;
    }

    public static void setLastDate(MyDate lastDate) {
        LibrarianService.lastDate = lastDate;
    }

    public static void initialize(){
        for(int i = 0; i < 100; i++)
            userLentBooks.add(new ArrayList<>());

        for(int i = 0; i < 100; i++)
            partnerBooks.add(new ArrayList<>());

        for(int i = 0; i < 100; i++)
            sectionBooks.add(new ArrayList<>());

        for(int i = 0; i < 100; i++)
            discounts.add(new ArrayList<>());

    }

    public void loginInformation() throws UsernameNotFoundException, IncorrectPasswordException{
        String librarianEmail;
        String librarianPassword;

        lastDate.setDate(invoice.get(invoice.size() - 1).getInvoiceDate().getDate());

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

    public Connection getConnection(){
        try {
            String dbURL = "jdbc:oracle:thin:@localhost:1521:xe";
            String username = "system";
            String password = "oracle";
            return DriverManager.getConnection(dbURL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void readSections() throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM SECTION");
        while (result.next()) {
            int id = result.getInt(1);
            String sectionName = result.getString(2);
            sections.add(new Sections(id, sectionName, false));
        }
        statement.close();
        sectionId = sections.size();
    }

    public void readPartners() throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM PARTNER");
        while (result.next()) {
            int id = result.getInt(1);
            int numOfLogins = result.getInt(2);
            String partnerName = result.getString(3);
            String partnerPassword = result.getString(4);
            String partnerEmail = result.getString(5);
            partners.add(new Partners(id, numOfLogins, partnerName, partnerPassword, partnerEmail, false));
        }
        statement.close();
        partnerId = partners.size();
    }

    public void readUsers() throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM \"USER\"");
        while (result.next()) {
            int id = result.getInt(1);
            int numOfLogins = result.getInt(2);
            String userName = result.getString(3);
            String userPassword = result.getString(4);
            String userEmail = result.getString(5);
            users.add(new Users(id, numOfLogins, userName, userPassword, userEmail, false));
        }
        statement.close();
        userId = users.size();
    }

    public void readInvoices() throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM INVOICE");

        while (result.next()) {
            int id = result.getInt(1);
            String invoiceDate = result.getString(2);
            int userId1 = result.getInt(3);
            String cardOrCash = result.getString(4);
            boolean discount = Boolean.parseBoolean(result.getString(5));
            invoice.add(new Invoice(id, invoiceDate, userId1, cardOrCash, discount));
        }
        statement.close();
        invoiceId = invoice.size();
    }

    public void readDiscounts() throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM DISCOUNT");

        while (result.next()) {
            String code = result.getString(1);
            String expirationDate = result.getString(2);
            int userId1 = result.getInt(3);
            boolean isUsed = Boolean.parseBoolean(result.getString(4));
            discounts.get(userId1 - 1).add(new _10PercentDiscount(code, expirationDate, userId1, isUsed));
        }
        statement.close();
    }

    public void readBooks() throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM BOOK");

        while (result.next()) {
            int bookId = result.getInt(1);
            String author = result.getString(2);
            String title = result.getString(3);
            String description = result.getString(4);
            boolean isLent = Boolean.parseBoolean(result.getString(5));
            int sectionId1 = result.getInt(6);
            int partnerId1 = result.getInt(7);
            partnerBooks.get(partnerId1 - 1).add(new Books(bookId, author, title, description, isLent, false, sectionId1, partnerId1));
            sectionBooks.get(sectionId1 - 1).add(new Books(bookId, author, title, description, isLent, false, sectionId1, partnerId1));
        }
        statement.close();
    }

    public void readLentBooks() throws SQLException{
        Connection connection = getConnection();
        MyDate issuedDate = new MyDate();
        Statement statement2 = connection.createStatement();
        ResultSet result = statement2.executeQuery("SELECT * FROM LENT_BOOK");

        while (result.next()) {
            int bookId = result.getInt(1);
            String author = result.getString(2);
            String title = result.getString(3);
            String description = result.getString(4);
            issuedDate.setDate(result.getString(5));
            int daysFromIssue = result.getInt(6);
            boolean isLent = Boolean.parseBoolean(result.getString(8));
            int sectionId1 = result.getInt(9);
            int partnerId1 = result.getInt(10);
            int userId1 = result.getInt(11);
            userLentBooks.get(userId1 - 1).add(new LentBooks(bookId, false, author, title, description, issuedDate.getDate(), daysFromIssue, isLent, sectionId1, partnerId1, userId1));
            Statement statement1 = connection.createStatement();
            statement1.executeUpdate("UPDATE LENT_BOOK SET PRICE = " + 2 * daysFromIssue + ", EXCEEDED_PRICE = 0, EXCEEDED_DAYS = 0, RETURN_DATE = '" + issuedDate.addDays(daysFromIssue).getDate() + "' WHERE BOOK_ID = " + bookId + " AND ISSUED_DATE = '" + issuedDate.getDate() + "'");
            statement1.close();
        }
        statement2.close();
    }

    public void insertSection() throws SQLException, AlreadyUsedNameException {
        ++sectionId;

        Connection connection = getConnection();
        Statement statement2 = connection.createStatement();

        System.out.println("\nSection name: ");
        String sectionName = myObj.nextLine();

        if(sections.size() >= 1)
            for (Sections section : sections)
                if (section.getSectionName().equals(sectionName) && !section.getIsDeleted()) {
                    throw new AlreadyUsedNameException("This name has already been used for another section!");
                }

        statement2.executeUpdate("INSERT INTO SECTION VALUES(" + sectionId + ", '" + sectionName + "')");
        sections.add(new Sections(sectionId, sectionName, false));

        statement2.close();
    }

    public void insertPartner() throws SQLException, IncorrectMailFormatException, AlreadyUsedEmailException {
        ++partnerId;

        Connection connection = getConnection();
        Statement statement2 = connection.createStatement();

        System.out.println("\nPartner name: ");
        String partnerName = myObj.nextLine();

        System.out.println("Partner email, in the following format: ");
        System.out.println("Any uppercase/lowercase letter or digit or the special characters \"_\", \".\" and \"@\"");
        String partnerEmail = myObj.nextLine();

        if (!partnerEmail.matches("([a-z|A-Z|0-9|_|\\.]+)@([a-z|A-Z|0-9|_|\\.]+)[^\\.]\\.([a-z]+)")) {
            throw new IncorrectMailFormatException("This email doesn't have the required format!");
        }

        if (partners.size() >= 1)
            for (Partners partner : partners)
                if (partner.getPartnerEmail().equals(partnerEmail) && !partner.getIsDeleted()) {
                    throw new AlreadyUsedEmailException("This email has already been used for another partner!");
                }

        String[] password = partnerEmail.split("@");
        String partnerPassword = password[0];

        statement2.executeUpdate("INSERT INTO PARTNER VALUES(" + partnerId + ", " + 0 + ", '" + partnerName + "', '" + partnerPassword + "', '" + partnerEmail + "')");
        partners.add(new Partners(partnerId, 0, partnerName, partnerPassword, partnerEmail, false));

        statement2.close();
    }

    public void insertUser() throws SQLException, IncorrectMailFormatException, AlreadyUsedEmailException {
        ++userId;

        Connection connection = getConnection();
        Statement statement = connection.createStatement();

        System.out.println("\nUser name: ");
        String userName = myObj.nextLine();

        System.out.println("User email, in the following format: ");
        System.out.println("Any uppercase/lowercase letter or digit or the special characters \"_\", \".\" and \"@\"");
        String userEmail = myObj.nextLine();

        if (!userEmail.matches("([a-z|A-Z|0-9|_|\\.]+)@([a-z|A-Z|0-9|_|\\.]+)[^\\.]\\.([a-z]+)")) {
            throw new IncorrectMailFormatException("This email doesn't have the required format!");
        }

        if(users.size() >= 1)
            for (Users user : users)
                if (user.getUserEmail().equals(userEmail) && !user.getIsDeleted()) {
                    throw new AlreadyUsedEmailException("This email has already been used!");
                }

        String[] password = userEmail.split("@");
        String userPassword = password[0];

        statement.executeUpdate("INSERT INTO \"USER\" VALUES(" + userId + ", " + 0 + ", '" + userName + "', '" + userPassword + "', '" + userEmail + "')");
        users.add(new Users(userId, 0, userName, userPassword, userEmail, false));

        statement.close();
    }

    public void lendNewBook() throws BookNotFoundException, BookAlreadyLentException, UserNotFoundException, IncorrectDateFormatException, MaxNumOfDaysException, WrongInputException, InvalidCodeException, DateNotValidException, SQLException {
        int userId1;
        int bookId;
        int daysFromIssue;
        int ok1 = 0;
        int ok2 = 0;
        int ok3 = 0;
        int ok4 = 0;
        int ok5 = 0;
        String issuedDate;
        String cardOrCash;
        String yOrN;
        String discountCode;
        LentBooks userLentBook = new LentBooks();
        Books partnerBook = new Books();
        Books sectionBook = new Books();
        _10PercentDiscount discount = new _10PercentDiscount();
        MyDate tempDate = new MyDate();
        Connection connection = getConnection();

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
        else{
            for (Users user : users)
                if (!user.getIsDeleted()) {
                    ok2 = 1;
                    break;
                }
            if (ok2 == 0) {
                System.out.println("\nThere are no users that could lend!");
            }
            else if (ok1 == 0) {
                System.out.println("\nThere are no books!");
            }
            else {
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
                    throw new IncorrectDateFormatException("This date doesn't match the required date format!");
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

                userLentBooks.get(userId1 - 1).add(0, new LentBooks(bookId, false, partnerBook.getAuthor(), partnerBook.getTitle(), partnerBook.getDescription(), issuedDate, daysFromIssue, true, partnerBook.getSectionId(), partnerBook.getPartnerId(), userId1));

                for (int i = 0; i < userLentBooks.get(userId1 - 1).size(); i++)
                    if (userLentBooks.get(userId1 - 1).get(i).getBookId() == bookId && userLentBooks.get(userId1 - 1).get(i).getIsLent())
                        userLentBook = userLentBooks.get(userId1 - 1).get(i);

                System.out.print("\n");
                System.out.print("Price: ");
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
                    System.out.print("Price after discount: ");
                    System.out.printf("%.02f", discountPrice);
                    System.out.print(" ron");
                    System.out.println("\n");

                    discount.setIsUsed(true);

                    System.out.println("Card or cash? (card/cash): ");
                    cardOrCash = myObj.nextLine();

                    if (!cardOrCash.equalsIgnoreCase("card") && !cardOrCash.equalsIgnoreCase("cash"))
                        throw new WrongInputException("Invalid option!");

                    invoice.add(new Invoice(++invoiceId, issuedDate, userId1, cardOrCash, true));

                    Statement statement1 = connection.createStatement();
                    statement1.executeUpdate("UPDATE BOOK SET IS_LENT = 'TRUE' WHERE BOOK_ID = " + partnerBook.getBookId());
                    statement1.close();

                    Statement statement2 = connection.createStatement();
                    statement2.executeUpdate("INSERT INTO INVOICE VALUES(" + invoiceId + ", '" + issuedDate + "', " + userId1 + ", '" + cardOrCash + "', 'TRUE')");
                    statement2.close();

                    printInvoice();

                    userLentBook.setPrice(discountPrice);

                    Statement statement3 = connection.createStatement();
                    statement3.executeUpdate("INSERT INTO LENT_BOOK VALUES(" + bookId + ", '" + partnerBook.getAuthor() + "', '" + partnerBook.getTitle() + "', '" + partnerBook.getDescription() + "', '" + issuedDate + "', " + daysFromIssue + ", '" + userLentBook.getReturnDate().getDate() + "', 'TRUE', " + partnerBook.getSectionId() + ", " + partnerBook.getPartnerId() + ", " + userId1 + ", " + userLentBook.getPrice() + ", 0, 0 )");
                    statement3.close();

                    Statement statement4 = connection.createStatement();
                    statement4.executeUpdate("UPDATE DISCOUNT SET IS_USED = 'TRUE' WHERE CODE = '" + discountCode + "'");
                    statement4.close();

                    Statement statement5 = connection.createStatement();
                    statement5.executeUpdate("UPDATE LENT_BOOK SET PRICE = " + discountPrice + " WHERE BOOK_ID = " + userLentBook.getBookId() + " AND ISSUED_DATE = '" + userLentBook.getIssuedDate().getDate() + "'");
                    statement5.close();

                } else {
                    System.out.println("Card or cash? (card/cash): ");
                    cardOrCash = myObj.nextLine();

                    if (!cardOrCash.equalsIgnoreCase("card") && !cardOrCash.equalsIgnoreCase("cash"))
                        throw new WrongInputException("Invalid option!");

                    invoice.add(new Invoice(++invoiceId, issuedDate, userId1, cardOrCash, false));

                    Statement statement6 = connection.createStatement();
                    statement6.executeUpdate("UPDATE BOOK SET IS_LENT = 'TRUE' WHERE BOOK_ID = " + partnerBook.getBookId());
                    statement6.close();

                    Statement statement7 = connection.createStatement();
                    statement7.executeUpdate("INSERT INTO LENT_BOOK VALUES(" + bookId + ", '" + partnerBook.getAuthor() + "', '" + partnerBook.getTitle() + "', '" + partnerBook.getDescription() + "', '" + issuedDate + "', " + daysFromIssue + ", '" + userLentBook.getReturnDate().getDate() + "', 'TRUE', " + partnerBook.getSectionId() + ", " + partnerBook.getPartnerId() + ", " + userId1 + ", " + userLentBook.getPrice() + ", 0, 0 )");
                    statement7.close();

                    Statement statement8 = connection.createStatement();
                    statement8.executeUpdate("INSERT INTO INVOICE VALUES(" + invoiceId + ", '" + issuedDate + "', " + userId1 + ", '" + cardOrCash + "', 'FALSE')");
                    statement8.close();

                    printInvoice();
                }
            }
        }
    }

    static String getAlphaNumericString() {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

        StringBuilder stringBuilder = new StringBuilder(12);

        for (int i = 0; i < 12; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            stringBuilder.append(AlphaNumericString.charAt(index));
        }

        return stringBuilder.toString();
    }

    public void returnBook() throws UserNotFoundException, BookNotFoundException, IncorrectDateFormatException, BookNotLentException, NoLentBooksException, WrongInputException, DateNotValidException, SQLException {
        int userId1;
        int bookId;
        int ok1 = 0;
        int ok2 = 0;
        int ok3 = 0;
        int ok4 = 0;
        int ok5 = 0;
        int ok6 = 0;
        String tempDate;
        String cardOrCash;
        MyDate actualReturnDate = new MyDate();
        LentBooks lentBook = new LentBooks();
        Books sectionBook = new Books();
        Books partnerBook = new Books();
        Connection connection = getConnection();

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
                    throw new BookNotLentException("This book is currently not on the lent books' list of the user! You can't return it!");

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
                    System.out.println("This user exceeded the return date with " + lentBook.getExceededDays() + " days!");
                    System.out.print("They have to pay an additional price: ");
                    System.out.printf("%.02f", lentBook.getExceededPrice());
                    System.out.print(" ron");
                    System.out.println("\n");
                    System.out.println("Card or cash? (card/cash): ");
                    cardOrCash = myObj.nextLine();

                    if (!cardOrCash.equalsIgnoreCase("card") && !cardOrCash.equalsIgnoreCase("cash"))
                        throw new WrongInputException("Invalid option!");

                    Statement statement1 = connection.createStatement();
                    statement1.executeUpdate("UPDATE BOOK SET IS_LENT = 'FALSE' WHERE BOOK_ID = " + lentBook.getBookId());
                    statement1.close();

                    Statement statement2 = connection.createStatement();
                    statement2.executeUpdate("UPDATE LENT_BOOK SET IS_LENT = 'FALSE' WHERE BOOK_ID = " + lentBook.getBookId() + " AND ISSUED_DATE = '" + lentBook.getIssuedDate().getDate() + "' ");
                    statement2.close();

                    Statement statement3 = connection.createStatement();
                    statement3.executeUpdate("UPDATE LENT_BOOK SET EXCEEDED_DAYS = " + lentBook.getExceededDays() + ", EXCEEDED_PRICE = " + lentBook.getExceededPrice() + " WHERE BOOK_ID = " + lentBook.getBookId() + " AND ISSUED_DATE = '" + lentBook.getIssuedDate().getDate() + "'");
                    statement3.close();

                    invoice.add(new Invoice(++invoiceId, actualReturnDate.getDate(), userId1, cardOrCash, false));

                    Statement statement4 = connection.createStatement();
                    statement4.executeUpdate("INSERT INTO INVOICE VALUES(" + invoiceId + ", '" + actualReturnDate.getDate() + "', " + userId1 + ", '" + cardOrCash + "', 'FALSE')");
                    statement4.close();

                    printInvoice();

                } else if (lentBook.getExceededDays() < 0) {
                    Statement statement5 = connection.createStatement();
                    statement5.executeUpdate("UPDATE BOOK SET IS_LENT = 'FALSE' WHERE BOOK_ID = " + lentBook.getBookId());
                    statement5.close();

                    Statement statement6 = connection.createStatement();
                    statement6.executeUpdate("UPDATE LENT_BOOK SET IS_LENT = 'FALSE' WHERE BOOK_ID = " + lentBook.getBookId() + " AND ISSUED_DATE = '" + lentBook.getIssuedDate().getDate() + "' ");
                    statement6.close();

                    Statement statement7 = connection.createStatement();
                    statement7.executeUpdate("UPDATE LENT_BOOK SET EXCEEDED_DAYS = " + lentBook.getExceededDays() + ", EXCEEDED_PRICE = " + lentBook.getExceededPrice() + " WHERE BOOK_ID = " + lentBook.getBookId() + " AND ISSUED_DATE = '" + lentBook.getIssuedDate().getDate() + "'");
                    statement7.close();

                    lentBook.setReturnDate(actualReturnDate.getDate());

                    Statement statement8 = connection.createStatement();
                    statement8.executeUpdate("UPDATE LENT_BOOK SET RETURN_DATE = '" + lentBook.getReturnDate().getDate() + "' WHERE BOOK_ID = " + lentBook.getBookId() + " AND ISSUED_DATE = '" + lentBook.getIssuedDate().getDate() + "'");
                    statement8.close();

                    System.out.println("\nWhat a fast reader! We generated a 10% discount code for future purchases! ");
                    discounts.get(userId1 - 1).add(new _10PercentDiscount(getAlphaNumericString(), actualReturnDate.addMonths(2).getDate(), userId1, false));

                    Statement statement9 = connection.createStatement();
                    statement9.executeUpdate("INSERT INTO DISCOUNT VALUES('" + getAlphaNumericString() + "', '" + actualReturnDate.addMonths(2).getDate() + "', " + userId1 + ", 'FALSE')");
                    statement9.close();

                    int index = discounts.get(userId1 - 1).size() - 1;
                    System.out.println("Code: " + discounts.get(userId1 - 1).get(index).getCode());
                    System.out.print("Expiration date: ");
                    discounts.get(userId1 - 1).get(index).getExpirationDate().print();
                    System.out.print("\n");
                }
            }
        }
    }

    public void printInvoice() throws SQLException {
        LentBooks lentBook = new LentBooks();
        Users user = new Users();
        Connection connection = getConnection();
        String tempDate;

        Statement statement1 = connection.createStatement();
        ResultSet result1 = statement1.executeQuery("SELECT * FROM INVOICE WHERE INVOICE_ID = " + invoiceId);

        result1.next();
        String invoiceDate = result1.getString(2);
        int userId1 = result1.getInt(3);
        String cardOrCash = result1.getString(4);
        boolean discount = Boolean.parseBoolean(result1.getString(5));

        Statement statement2 = connection.createStatement();
        ResultSet result2 = statement1.executeQuery("SELECT * FROM LENT_BOOK WHERE USER_ID = " + userId1);

        while (result2.next()) {
            int bookId = result2.getInt(1);
            String author = result2.getString(2);
            String title = result2.getString(3);
            String description = result2.getString(4);
            MyDate issuedDate = new MyDate();
            issuedDate.setDate(result2.getString(5));
            int daysFromIssue = result2.getInt(6);
            MyDate returnDate = new MyDate();
            returnDate.setDate(result2.getString(7));
            boolean isLent = Boolean.parseBoolean(result2.getString(8));
            int sectionId1 = result2.getInt(9);
            int partnerId1 = result2.getInt(10);
            int exceededDays = result2.getInt(13);

            if(isLent && issuedDate.getDate().equals(invoiceDate)){
                lentBook = new LentBooks(bookId, false, author, title, description, issuedDate.getDate(), daysFromIssue, true, sectionId1, partnerId1, userId1);
            }
            else if(!isLent) {
                tempDate = returnDate.addDays(exceededDays).getDate();
                if (tempDate.equals(invoiceDate)) {
                    lentBook = new LentBooks(bookId, false, author, title, description, issuedDate.getDate(), daysFromIssue, false, sectionId1, partnerId1, userId1);
                }
            }
        }
        statement2.close();

        Statement statement3 = connection.createStatement();
        ResultSet result3 = statement1.executeQuery("SELECT * FROM \"USER\" WHERE USER_ID = " + userId1);

        while (result3.next()) {
            int id = result3.getInt(1);
            int numOfLogins = result3.getInt(2);
            String userName = result3.getString(3);
            String userPassword = result3.getString(4);
            String userEmail = result3.getString(5);

            user = new Users(id, numOfLogins, userName, userPassword, userEmail, false);
        }
        statement3.close();

        System.out.println("\nThe invoice generated for the purchase:\n");
        System.out.println("invoice ID: " + invoiceId);
        System.out.print("invoice date: " + invoiceDate);
        System.out.print("\n");
        System.out.println("lent book: ");
        System.out.println("\tbook ID: " + lentBook.getBookId());
        System.out.println("\tauthor: " + lentBook.getAuthor());
        System.out.println("\ttitle: " + lentBook.getTitle());
        System.out.println("\tdescription: " + lentBook.getDescription());
        System.out.print("\tissue date: ");
        lentBook.getIssuedDate().print();
        System.out.print("\n");

        if(lentBook.getExceededDays() > 0) {
            System.out.print("\treturn date: ");
            lentBook.getReturnDate().print();
            System.out.print(" + exceeded with " + lentBook.getExceededDays() + " days\n");
            System.out.print("\tadditional price: 3.00 x " + lentBook.getExceededDays() + " = ");
            System.out.printf("%.02f", lentBook.getExceededPrice());
            System.out.print(" ron\n");
            System.out.println("user that lent the book: ");
            System.out.println("\tuser ID: " + user.getUserId());
            System.out.println("\tname: " + user.getUserName());
            System.out.println("paid with: " + cardOrCash);
        }
        else {
            System.out.print("\treturn date: ");
            lentBook.getReturnDate().print();
            System.out.print("\n");
            if(discount) {
                float discountPrice;
                discountPrice = lentBook.getPrice() - lentBook.getPrice() / 10;
                System.out.print("\tprice: 2.00 x " + lentBook.getDaysFromIssue() + " = ");
                System.out.printf("%.02f", lentBook.getPrice());
                System.out.print(" ron\n");
                System.out.println("\tdiscount: 10%");
                System.out.print("\tprice after discount: ");
                System.out.printf("%.02f", discountPrice);
                System.out.print(" ron\n");
            }
            else {
                System.out.print("\tprice: 2.00 x " + lentBook.getDaysFromIssue() + " = ");
                System.out.printf("%.02f", lentBook.getPrice());
                System.out.print(" ron\n");
            }
            System.out.println("user that lent the book: ");
            System.out.println("\tuser ID: " + userId1);
            System.out.println("\tname: " + user.getUserName());
            System.out.println("paid with: " + cardOrCash);
            System.out.println("Note: For every day that exceeds the return day you have to pay an additional 3 ron");
        }
        statement1.close();
    }

    public void printUsers() throws SQLException {
        int countUser;
        Connection connection = getConnection();
        Statement statement1 = connection.createStatement();
        ResultSet result1 = statement1.executeQuery("SELECT COUNT(*) FROM \"USER\"");

        result1.next();
        countUser = result1.getInt(1);

        statement1.close();

        if(countUser == 0){
            System.out.println("\nThere are no users! ");
        }
        else {
            Statement statement2 = connection.createStatement();
            ResultSet result2 = statement2.executeQuery("SELECT * FROM \"USER\"");

            System.out.println("\nUsers:\n");

            while (result2.next() && countUser > 1) {
                int id = result2.getInt(1);
                String userName = result2.getString(3);
                String userEmail = result2.getString(5);
                System.out.print("user ID: ");
                System.out.println(id);
                System.out.print("\tname: ");
                System.out.println(userName);
                System.out.print("\temail: ");
                System.out.println(userEmail);
                System.out.print("\n");
                countUser--;
            }
            if(countUser == 1){
                int id = result2.getInt(1);
                String userName = result2.getString(3);
                String userEmail = result2.getString(5);
                System.out.print("user ID: ");
                System.out.println(id);
                System.out.print("\tname: ");
                System.out.println(userName);
                System.out.print("\temail: ");
                System.out.println(userEmail);
            }
            statement2.close();
        }
    }

    public void printPartners() throws SQLException {
        int countPartner;
        Connection connection = getConnection();
        Statement statement1 = connection.createStatement();
        ResultSet result1 = statement1.executeQuery("SELECT COUNT(*) FROM PARTNER");

        result1.next();
        countPartner = result1.getInt(1);

        statement1.close();

        if(countPartner == 0){
            System.out.println("\nThere are no partners! ");
        }
        else {
            Statement statement2 = connection.createStatement();
            ResultSet result2 = statement2.executeQuery("SELECT * FROM PARTNER");

            System.out.println("\nPartners:\n");

            while (result2.next() && countPartner > 1) {
                int id = result2.getInt(1);
                String partnerName = result2.getString(3);
                String partnerEmail = result2.getString(5);
                System.out.print("partner ID: ");
                System.out.println(id);
                System.out.print("\tname: ");
                System.out.println(partnerName);
                System.out.print("\temail: ");
                System.out.println(partnerEmail);
                System.out.print("\n");
                countPartner--;
            }
            if(countPartner == 1){
                int id = result2.getInt(1);
                String partnerName = result2.getString(3);
                String partnerEmail = result2.getString(5);
                System.out.print("partner ID: ");
                System.out.println(id);
                System.out.print("\tname: ");
                System.out.println(partnerName);
                System.out.print("\temail: ");
                System.out.println(partnerEmail);
            }
            statement2.close();
        }
    }

    public void printSections() throws SQLException {
        int countSection;
        Connection connection = getConnection();
        Statement statement1 = connection.createStatement();
        ResultSet result1 = statement1.executeQuery("SELECT COUNT(*) FROM SECTION");

        result1.next();
        countSection = result1.getInt(1);

        statement1.close();

        if(countSection == 0){
            System.out.println("\nThere are no sections! ");
        }
        else{
            Statement statement2 = connection.createStatement();
            ResultSet result2 = statement2.executeQuery("SELECT * FROM SECTION");

            System.out.println("\nSections:\n");

            while (result2.next() && countSection > 1) {
                int id = result2.getInt(1);
                String sectionName = result2.getString(2);
                System.out.print("section ID: ");
                System.out.println(id);
                System.out.print("\tname: ");
                System.out.println(sectionName);
                System.out.print("\n");
                countSection--;
            }
            if(countSection == 1){
                int id = result2.getInt(1);
                String sectionName = result2.getString(2);
                System.out.print("section ID: ");
                System.out.println(id);
                System.out.print("\tname: ");
                System.out.println(sectionName);
            }
            statement2.close();
        }
    }

    public void printSectionBooks() throws SectionNotFoundException, SQLException {
        int countSection;
        Connection connection = getConnection();
        Statement statement1 = connection.createStatement();
        ResultSet result1 = statement1.executeQuery("SELECT COUNT(*) FROM SECTION");

        result1.next();
        countSection = result1.getInt(1);

        statement1.close();

        if(countSection == 0){
            System.out.println("\nThere are no sections! ");
        }
        else{
            int sectionId1;
            int countSectionBooks;
            int ok = 0;
            System.out.println("\nSection ID: ");
            sectionId1 = myObj.nextInt();
            myObj.nextLine();

            for (Sections section : sections)
                if (sectionId1 == section.getSectionId() && !section.getIsDeleted()) {
                    ok = 1;
                    break;
                }
            if (ok == 0)
                throw new SectionNotFoundException("Could not find section with this ID!");
            Statement statement2 = connection.createStatement();
            ResultSet result2 = statement2.executeQuery("SELECT COUNT(*) FROM BOOK WHERE SECTION_ID = " + sectionId1);

            result2.next();
            countSectionBooks = result2.getInt(1);

            statement2.close();
            if(countSectionBooks == 0)
                System.out.println("There are no books in this section!");
            else{
                Statement statement3 = connection.createStatement();
                ResultSet result3 = statement3.executeQuery("SELECT * FROM BOOK WHERE SECTION_ID = " + sectionId1);

                System.out.println("This section's books:\n");

                while (result3.next() && countSectionBooks > 1) {
                    int bookId = result3.getInt(1);
                    String author = result3.getString(2);
                    String title = result3.getString(3);
                    String description = result3.getString(4);
                    System.out.println("book id: " + bookId);
                    System.out.println("\tauthor: " + author);
                    System.out.println("\ttitle: " + title);
                    System.out.println("\tdescription: " + description);
                    System.out.print("\n");
                    countSectionBooks--;
                }
                if(countSectionBooks == 1){
                    int bookId = result3.getInt(1);
                    String author = result3.getString(2);
                    String title = result3.getString(3);
                    String description = result3.getString(4);
                    System.out.println("book id: " + bookId);
                    System.out.println("\tauthor: " + author);
                    System.out.println("\ttitle: " + title);
                    System.out.println("\tdescription: " + description);
                }
                statement3.close();
            }
        }
    }

    public void printPartnerBooks() throws PartnerNotFoundException, SQLException {
        int countPartner;
        Connection connection = getConnection();
        Statement statement1 = connection.createStatement();
        ResultSet result1 = statement1.executeQuery("SELECT COUNT(*) FROM PARTNER");

        result1.next();
        countPartner = result1.getInt(1);

        statement1.close();
        if(countPartner == 0){
            System.out.println("\nThere are no partners! ");
        }
        else {
            int partnerId1;
            int countPartnerBooks;
            int ok = 0;
            System.out.println("\nPartner ID: ");
            partnerId1 = myObj.nextInt();
            myObj.nextLine();

            for (Partners partner : partners)
                if (partnerId1 == partner.getPartnerId() && !partner.getIsDeleted()) {
                    ok = 1;
                    break;
                }
            if (ok == 0)
                throw new PartnerNotFoundException("Could not find partner with this ID!");
            Statement statement2 = connection.createStatement();
            ResultSet result2 = statement2.executeQuery("SELECT COUNT(*) FROM BOOK WHERE PARTNER_ID = " + partnerId1);

            result2.next();
            countPartnerBooks = result2.getInt(1);

            statement2.close();

            if (countPartnerBooks == 0)
                System.out.println("There are no books added by this partner!");
            else {
                Statement statement3 = connection.createStatement();
                ResultSet result3 = statement3.executeQuery("SELECT * FROM BOOK WHERE PARTNER_ID = " + partnerId1);

                System.out.println("This partner's books:\n");

                while (result3.next() && countPartnerBooks > 1) {
                    int bookId = result3.getInt(1);
                    String author = result3.getString(2);
                    String title = result3.getString(3);
                    String description = result3.getString(4);
                    System.out.println("book id: " + bookId);
                    System.out.println("\tauthor: " + author);
                    System.out.println("\ttitle: " + title);
                    System.out.println("\tdescription: " + description);
                    System.out.print("\n");
                    countPartnerBooks--;
                }
                if(countPartnerBooks == 1){
                    int bookId = result3.getInt(1);
                    String author = result3.getString(2);
                    String title = result3.getString(3);
                    String description = result3.getString(4);
                    System.out.println("book id: " + bookId);
                    System.out.println("\tauthor: " + author);
                    System.out.println("\ttitle: " + title);
                    System.out.println("\tdescription: " + description);
                }
                statement3.close();
            }
        }
    }

    public void printUserLentBooks() throws UserNotFoundException, SQLException {
        int countUser;
        Connection connection = getConnection();
        Statement statement1 = connection.createStatement();
        ResultSet result1 = statement1.executeQuery("SELECT COUNT(*) FROM \"USER\"");

        result1.next();
        countUser = result1.getInt(1);

        statement1.close();

        if(countUser == 0){
            System.out.println("\nThere are no users! ");
        }
        else {
            int userId1;
            int countLentBooks;
            int ok = 0;
            System.out.println("\nUser ID: ");
            userId1 = myObj.nextInt();
            myObj.nextLine();

            for (Users user : users)
                if (userId1 == user.getUserId() && !user.getIsDeleted()) {
                    ok = 1;
                    break;
                }
            if (ok == 0)
                throw new UserNotFoundException("Could not find user with this ID!");
            Statement statement2 = connection.createStatement();
            ResultSet result2 = statement2.executeQuery("SELECT COUNT(*) FROM LENT_BOOK WHERE USER_ID = " + userId1 + " AND UPPER(IS_LENT) = 'TRUE'");

            result2.next();
            countLentBooks = result2.getInt(1);

            statement2.close();
            if (countLentBooks == 0)
                System.out.println("There are no books lent by this user!");
            else {
                Statement statement3 = connection.createStatement();
                ResultSet result3 = statement3.executeQuery("SELECT * FROM LENT_BOOK WHERE USER_ID = " + userId1 + " AND UPPER(IS_LENT) = 'TRUE'");

                System.out.println("This user's lent books:\n");

                while (result3.next() && countLentBooks > 1) {
                    int bookId = result3.getInt(1);
                    String author = result3.getString(2);
                    String title = result3.getString(3);
                    String description = result3.getString(4);
                    String issuedDate = result3.getString(5);
                    String returnDate = result3.getString(7);
                    float price = result3.getFloat(12);
                    System.out.println("book id: " + bookId);
                    System.out.println("\tauthor: " + author);
                    System.out.println("\ttitle: " + title);
                    System.out.println("\tdescription: " + description);
                    System.out.println("\tissued date: " + issuedDate);
                    System.out.println("\treturn date: " + returnDate);
                    System.out.print("\tprice: ");
                    System.out.printf("%.02f", price);
                    System.out.print(" ron\n");
                    System.out.print("\n");
                    countLentBooks--;
                }
                if(countLentBooks == 1){
                    int bookId = result3.getInt(1);
                    String author = result3.getString(2);
                    String title = result3.getString(3);
                    String description = result3.getString(4);
                    String issuedDate = result3.getString(5);
                    String returnDate = result3.getString(7);
                    float price = result3.getFloat(12);
                    System.out.println("book id: " + bookId);
                    System.out.println("\tauthor: " + author);
                    System.out.println("\ttitle: " + title);
                    System.out.println("\tdescription: " + description);
                    System.out.println("\tissued date: " + issuedDate);
                    System.out.println("\treturn date: " + returnDate);
                    System.out.print("\tprice: ");
                    System.out.printf("%.02f", price);
                    System.out.print(" ron\n");
                }
                statement3.close();
            }
        }
    }

    public void printUserReturnedBooks() throws UserNotFoundException, SQLException{
        int countUser;
        Connection connection = getConnection();
        Statement statement1 = connection.createStatement();
        ResultSet result1 = statement1.executeQuery("SELECT COUNT(*) FROM \"USER\"");

        result1.next();
        countUser = result1.getInt(1);

        statement1.close();

        if(countUser == 0){
            System.out.println("\nThere are no users! ");
        }
        else {
            int userId1;
            int countReturnedBooks;
            int ok = 0;
            System.out.println("\nUser ID: ");
            userId1 = myObj.nextInt();
            myObj.nextLine();

            for (Users user : users)
                if (userId1 == user.getUserId() && !user.getIsDeleted()) {
                    ok = 1;
                    break;
                }
            if (ok == 0)
                throw new UserNotFoundException("Could not find user with this ID!");
            Statement statement2 = connection.createStatement();
            ResultSet result2 = statement2.executeQuery("SELECT COUNT(*) FROM LENT_BOOK WHERE USER_ID = " + userId1 + " AND UPPER(IS_LENT) = 'FALSE'");

            result2.next();
            countReturnedBooks = result2.getInt(1);

            statement2.close();
            if (countReturnedBooks == 0)
                System.out.println("There are no books returned by this user!");
            else {
                Statement statement3 = connection.createStatement();
                ResultSet result3 = statement3.executeQuery("SELECT * FROM LENT_BOOK WHERE USER_ID = " + userId1 + " AND UPPER(IS_LENT) = 'FALSE'");

                System.out.println("This user's returned books:\n");

                while (result3.next() && countReturnedBooks > 1) {
                    int bookId = result3.getInt(1);
                    String author = result3.getString(2);
                    String title = result3.getString(3);
                    String description = result3.getString(4);
                    String issuedDate = result3.getString(5);
                    String returnDate = result3.getString(7);
                    float price = result3.getFloat(12);
                    int exceededDays = result3.getInt(13);
                    float exceededPrice = result3.getFloat(14);
                    System.out.println("book id: " + bookId);
                    System.out.println("\tauthor: " + author);
                    System.out.println("\ttitle: " + title);
                    System.out.println("\tdescription: " + description);
                    System.out.print("\tissue date: " + issuedDate);
                    System.out.print("\n");
                    if (exceededDays > 0) {
                        System.out.print("\treturn date: " + returnDate);
                        System.out.print(" + exceeded with " + exceededDays + " days\n");
                        System.out.print("\tinitial price: ");
                        System.out.printf("%.02f", price);
                        System.out.print(" ron\n");
                        System.out.print("\tprice added after exceeding the return date: ");
                        System.out.printf("%.02f", exceededPrice);
                        System.out.print(" ron\n");
                    } else {
                        System.out.println("\treturn date: " + returnDate);
                        System.out.print("\tprice: ");
                        System.out.printf("%.02f", price);
                        System.out.print(" ron\n");
                    }
                    System.out.print("\n");
                    countReturnedBooks--;
                }
                if(countReturnedBooks == 1){
                    int bookId = result3.getInt(1);
                    String author = result3.getString(2);
                    String title = result3.getString(3);
                    String description = result3.getString(4);
                    String issuedDate = result3.getString(5);
                    String returnDate = result3.getString(7);
                    float price = result3.getFloat(12);
                    int exceededDays = result3.getInt(13);
                    float exceededPrice = result3.getFloat(14);
                    System.out.println("book id: " + bookId);
                    System.out.println("\tauthor: " + author);
                    System.out.println("\ttitle: " + title);
                    System.out.println("\tdescription: " + description);
                    System.out.print("\tissue date: " + issuedDate);
                    System.out.print("\n");
                    if (exceededDays > 0) {
                        System.out.print("\treturn date: " + returnDate);
                        System.out.print(" + exceeded with " + exceededDays + " days\n");
                        System.out.print("\tinitial price: ");
                        System.out.printf("%.02f", price);
                        System.out.print(" ron\n");
                        System.out.print("\tprice added after exceeding the return date: ");
                        System.out.printf("%.02f", exceededPrice);
                        System.out.print(" ron\n");
                    } else {
                        System.out.println("\treturn date: " + returnDate);
                        System.out.print("\tprice: ");
                        System.out.printf("%.02f", price);
                        System.out.print(" ron\n");
                    }
                }
                statement3.close();
            }
        }
    }

    public void printIssuedBooks() throws SQLException {
        int countLentBook;
        Connection connection = getConnection();
        Statement statement1 = connection.createStatement();
        ResultSet result1 = statement1.executeQuery("SELECT COUNT(*) FROM LENT_BOOK WHERE UPPER(IS_LENT) = 'TRUE'");

        result1.next();
        countLentBook = result1.getInt(1);

        statement1.close();

        if(countLentBook == 0){
            System.out.println("\nThere are no issued books! ");
        }
        else {
            Statement statement2 = connection.createStatement();
            ResultSet result3 = statement2.executeQuery("SELECT * FROM LENT_BOOK WHERE UPPER(IS_LENT) = 'TRUE'");

            System.out.println("\nIssued books:\n");

            while (result3.next() && countLentBook > 1) {
                int bookId = result3.getInt(1);
                String author = result3.getString(2);
                String title = result3.getString(3);
                String description = result3.getString(4);
                String issuedDate = result3.getString(5);
                String returnDate = result3.getString(7);
                float price = result3.getFloat(12);
                System.out.println("book id: " + bookId);
                System.out.println("\tauthor: " + author);
                System.out.println("\ttitle: " + title);
                System.out.println("\tdescription: " + description);
                System.out.println("\tissued date: " + issuedDate);
                System.out.println("\treturn date: " + returnDate);
                System.out.print("\tprice: ");
                System.out.printf("%.02f", price);
                System.out.print(" ron\n");
                System.out.print("\n");
                countLentBook--;
            }
            if(countLentBook == 1){
                int bookId = result3.getInt(1);
                String author = result3.getString(2);
                String title = result3.getString(3);
                String description = result3.getString(4);
                String issuedDate = result3.getString(5);
                String returnDate = result3.getString(7);
                float price = result3.getFloat(12);
                System.out.println("book id: " + bookId);
                System.out.println("\tauthor: " + author);
                System.out.println("\ttitle: " + title);
                System.out.println("\tdescription: " + description);
                System.out.println("\tissued date: " + issuedDate);
                System.out.println("\treturn date: " + returnDate);
                System.out.print("\tprice: ");
                System.out.printf("%.02f", price);
                System.out.print(" ron\n");
            }
            statement2.close();
        }
    }

    public void printBooks() throws SQLException {
        int countBook;
        Connection connection = getConnection();
        Statement statement1 = connection.createStatement();
        ResultSet result1 = statement1.executeQuery("SELECT COUNT(*) FROM BOOK WHERE UPPER(IS_LENT) = 'FALSE'");

        result1.next();
        countBook = result1.getInt(1);

        statement1.close();

        if(countBook == 0){
            System.out.println("\nThere are no available books! ");
        }
        else {
            Statement statement2 = connection.createStatement();
            ResultSet result3 = statement2.executeQuery("SELECT * FROM BOOK WHERE UPPER(IS_LENT) = 'FALSE'");

            System.out.println("\nAvailable books:\n");

            while (result3.next() && countBook > 1) {
                int bookId = result3.getInt(1);
                String author = result3.getString(2);
                String title = result3.getString(3);
                String description = result3.getString(4);
                System.out.println("book id: " + bookId);
                System.out.println("\tauthor: " + author);
                System.out.println("\ttitle: " + title);
                System.out.println("\tdescription: " + description);
                System.out.print("\n");
                countBook--;
            }
            if(countBook == 1){
                int bookId = result3.getInt(1);
                String author = result3.getString(2);
                String title = result3.getString(3);
                String description = result3.getString(4);
                System.out.println("book id: " + bookId);
                System.out.println("\tauthor: " + author);
                System.out.println("\ttitle: " + title);
                System.out.println("\tdescription: " + description);
            }
            statement2.close();
        }
    }

    public void printUserDiscounts() throws UserNotFoundException, SQLException {
        int countUser;
        int countDiscount = 0;
        int userId1;
        int ok = 0;
        Connection connection = getConnection();
        Statement statement1 = connection.createStatement();
        ResultSet result1 = statement1.executeQuery("SELECT COUNT(*) FROM \"USER\"");

        result1.next();
        countUser = result1.getInt(1);

        statement1.close();

        if(countUser == 0){
            System.out.println("\nThere are no users! ");
        }
        else {
            System.out.println("\nUser ID: ");
            userId1 = myObj.nextInt();
            myObj.nextLine();
            for (Users user : users)
                if (userId1 == user.getUserId() && !user.getIsDeleted()) {
                    ok = 1;
                    break;
                }
            if (ok == 0)
                throw new UserNotFoundException("Could not find user with this ID!");

            Statement statement2 = connection.createStatement();
            ResultSet result2 = statement2.executeQuery("SELECT * FROM DISCOUNT WHERE USER_ID = " + userId1 + " AND UPPER(IS_USED) = 'FALSE'");

            while (result2.next()) {
                String code = result2.getString(1);
                MyDate expirationDate = new MyDate();
                expirationDate.setDate(result2.getString(2));
                if (lastDate.isBiggerThan(expirationDate)) {
                    Statement statement3 = connection.createStatement();
                    statement3.executeUpdate("UPDATE DISCOUNT SET IS_USED = 'TRUE' WHERE EXP_DATE = '" + expirationDate.getDate() + "'");
                    statement3.close();
                    for (int i = 0; i < discounts.get(userId1 - 1).size(); i++)
                        if(discounts.get(userId1 - 1).get(i).getCode().equals(code))
                            discounts.get(userId1 - 1).get(i).setIsUsed(true);
                } else
                    ++countDiscount;
            }
            statement2.close();

            if (countDiscount == 0)
                System.out.println("This user hasn't earned any discounts yet!");
            else {
                System.out.println("This user's discounts: ");
                Statement statement3 = connection.createStatement();
                ResultSet result3 = statement3.executeQuery("SELECT * FROM DISCOUNT WHERE USER_ID = " + userId1 + " AND UPPER(IS_USED) = 'FALSE'");
                while (result3.next()) {
                    String code = result3.getString(1);
                    MyDate expirationDate = new MyDate();
                    expirationDate.setDate(result3.getString(2));
                    System.out.print("\nthe code: " + code + ", the expiration date: ");
                    expirationDate.print();
                }
                System.out.print("\n");
                statement3.close();
            }
        }
    }

    public void deleteBook() throws BookNotFoundException, BookCurrentlyLentException, SQLException {
        int bookId;
        int ok1 = 0;
        int ok2 = 0;
        int ok3 = 0;
        int ok4 = 0;
        Connection connection = getConnection();

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
                System.out.println("\nBook ID: ");
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

                Statement statement = connection.createStatement();
                statement.executeUpdate("DELETE FROM BOOK WHERE BOOK_ID = " + bookId);
                statement.close();

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

    public void deleteSections() throws SectionNotFoundException, BookCurrentlyLentException, SQLException {
        int sectionId1;
        int ok1 = 0;
        int ok2 = 0;
        int ok3 = 0;
        Connection connection = getConnection();

        if(sections.size() < 1) {
            System.out.println("\nThere are no sections!");
        }
        else {
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

                Statement statement = connection.createStatement();
                statement.executeUpdate("DELETE FROM SECTION WHERE SECTION_ID = " + sectionId1);
                statement.close();

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

    public void deletePartners() throws PartnerNotFoundException, BookCurrentlyLentException, SQLException {
        int partnerId1;
        int ok1 = 0;
        int ok2 = 0;
        int ok3 = 0;
        Connection connection = getConnection();

        if(partners.size() < 1){
            System.out.println("\nThere are no partners!");
        }

        else {
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
                    throw new BookCurrentlyLentException("This partner added a book that is currently lent. If you remove this partner, you will automatically remove the book!");

                Statement statement = connection.createStatement();
                statement.executeUpdate("DELETE FROM SECTION WHERE SECTION_ID = " + partnerId1);
                statement.close();

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

    public void deleteUsers() throws UserNotFoundException, BookCurrentlyLentException, SQLException {
        int userId1;
        int ok1 = 0;
        int ok2 = 0;
        int ok3 = 0;
        Connection connection = getConnection();

        if(users.size() < 1){
            System.out.println("\nThere are no users!");
        }
        else {
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

                Statement statement = connection.createStatement();
                statement.executeUpdate("DELETE FROM \"USER\" WHERE USER_ID = " + userId1);
                statement.close();

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
