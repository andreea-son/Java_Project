import java.sql.SQLException;
import java.util.Scanner;
public class Main{
    private static Scanner myObj = new Scanner(System.in);
    private static int choose;
    private static String[] loginMenuOpt = {
            "Main menu: ",
            "1. Login as librarian",
            "2. Login as partner",
            "3. Login as user",
            "4. Exit"
    };
    private static String[] librarianMenuOpt = {
            "Librarian menu: ",
            "1. Add new section",
            "2. Add new partner",
            "3. Add new user",
            "4. Lend new book",
            "5. Return book",
            "6. View sections",
            "7. View all books from section",
            "8. View partners",
            "9. View books added by partner",
            "10. View users",
            "11. View books lent by user",
            "12. View books returned by user",
            "13. View all issued books",
            "14. View all available books",
            "15. View user discounts",
            "16. Remove book",
            "17. Remove section",
            "18. Remove partner",
            "19. Remove user",
            "20. Logout"
    };

    private static String[] partnerMenuOpt = {
            "Partner menu: ",
            "1. Add new book",
            "2. View the books you added",
            "3. Logout"
    };

    private static String[] userMenuOpt = {
            "User menu: ",
            "1. View all available books",
            "2. View the books you are currently lending",
            "3. View the books you returned",
            "4. View your discounts",
            "5. Logout"
    };

    public static void displayLoginMenu(String[] options){
        for (String option : options){
            System.out.println(option);
        }
        System.out.print("Choose an option between 1 and 4: ");
    }
    public static void displayPartnerMenu(String[] options){
        for (String option : options){
            System.out.println(option);
        }
        System.out.print("Choose an option between 1 and 3: ");
    }
    public static void displayLibrarianMenu(String[] options){
        for (String option : options){
            System.out.println(option);
        }
        System.out.print("Choose an option between 1 and 20: ");
    }
    public static void displayUserMenu(String[] options){
        for (String option : options){
            System.out.println(option);
        }
        System.out.print("Choose an option between 1 and 5: ");
    }
    public static void main(String[] args) {
        LibrarianService.initialize();
        LibrarianService librarianService = new LibrarianService();
        try {
            librarianService.readInvoices();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        try {
            librarianService.readUsers();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        try {
            librarianService.readPartners();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        try {
            librarianService.readDiscounts();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        try {
            librarianService.readSections();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        try {
            librarianService.readBooks();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        try {
            librarianService.readLentBooks();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        while (true) {
            System.out.print("\n");
            displayLoginMenu(loginMenuOpt);
            choose = myObj.nextInt();
            myObj.nextLine();
            switch (choose) {
                case 1:
                {
                    try {
                        librarianService.loginInformation();
                    } catch (UsernameNotFoundException | IncorrectPasswordException e) {
                        e.printStackTrace();
                        System.exit(1);
                    }
                    while (choose != 20){
                        System.out.print("\n");
                        displayLibrarianMenu(librarianMenuOpt);
                        choose = myObj.nextInt();
                        myObj.nextLine();
                        switch (choose) {
                            case 1:
                            {
                                try {
                                    librarianService.insertSection();
                                } catch (SQLException | AlreadyUsedNameException e) {
                                    e.printStackTrace();
                                    System.exit(1);
                                }
                                break;
                            }
                            case 2:
                            {
                                try {
                                    librarianService.insertPartner();
                                } catch (SQLException | IncorrectMailFormatException | AlreadyUsedEmailException e) {
                                    e.printStackTrace();
                                    System.exit(1);
                                }
                                break;
                            }
                            case 3:
                            {
                                try {
                                    librarianService.insertUser();
                                } catch (SQLException | IncorrectMailFormatException | AlreadyUsedEmailException e) {
                                    e.printStackTrace();
                                    System.exit(1);
                                }
                                break;
                            }
                            case 4:
                            {
                                try {
                                    librarianService.lendNewBook();
                                } catch (BookNotFoundException | BookAlreadyLentException | UserNotFoundException | IncorrectDateFormatException | MaxNumOfDaysException | WrongInputException | InvalidCodeException | DateNotValidException | SQLException e) {
                                    e.printStackTrace();
                                    System.exit(1);
                                }
                                break;
                            }
                            case 5:
                            {
                                try {
                                    librarianService.returnBook();
                                } catch (UserNotFoundException | BookNotFoundException | IncorrectDateFormatException | BookNotLentException | NoLentBooksException | WrongInputException | DateNotValidException | SQLException e) {
                                    e.printStackTrace();
                                    System.exit(1);
                                }
                                break;
                            }
                            case 6:
                            {
                                try {
                                    librarianService.printSections();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                    System.exit(1);
                                }
                                break;
                            }
                            case 7:
                            {
                                try {
                                    librarianService.printSectionBooks();
                                } catch (SectionNotFoundException | SQLException e) {
                                    e.printStackTrace();
                                    System.exit(1);
                                }

                                break;
                            }
                            case 8:
                            {
                                try {
                                    librarianService.printPartners();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                    System.exit(1);
                                }
                                break;
                            }
                            case 9:
                            {
                                try {
                                    librarianService.printPartnerBooks();
                                } catch (PartnerNotFoundException | SQLException e) {
                                    e.printStackTrace();
                                    System.exit(1);
                                }
                                break;
                            }
                            case 10:
                            {
                                try {
                                    librarianService.printUsers();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                    System.exit(1);
                                }
                                break;
                            }
                            case 11:
                            {
                                try {
                                    librarianService.printUserLentBooks();
                                } catch (UserNotFoundException | SQLException e) {
                                    e.printStackTrace();
                                    System.exit(1);
                                }
                                break;
                            }
                            case 12:
                            {
                                try {
                                    librarianService.printUserReturnedBooks();
                                } catch (UserNotFoundException | SQLException e) {
                                    e.printStackTrace();
                                    System.exit(1);
                                }
                                break;
                            }
                            case 13:
                            {
                                try {
                                    librarianService.printIssuedBooks();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                    System.exit(1);
                                }
                                break;
                            }
                            case 14:
                            {
                                try {
                                    librarianService.printBooks();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                    System.exit(1);
                                }
                                break;
                            }
                            case 15:
                            {
                                try {
                                    librarianService.printUserDiscounts();
                                } catch (UserNotFoundException | SQLException e) {
                                    e.printStackTrace();
                                    System.exit(1);
                                }
                                break;
                            }
                            case 16:
                            {

                                try {
                                    librarianService.deleteBook();
                                } catch (BookNotFoundException | BookCurrentlyLentException | SQLException e) {
                                    e.printStackTrace();
                                    System.exit(1);
                                }
                                break;
                            }
                            case 17:
                            {
                                try {
                                    librarianService.deleteSections();
                                } catch (SectionNotFoundException | BookCurrentlyLentException | SQLException e) {
                                    e.printStackTrace();
                                    System.exit(1);
                                }
                                break;
                            }
                            case 18:
                            {
                                try {
                                    librarianService.deletePartners();
                                } catch (PartnerNotFoundException | BookCurrentlyLentException | SQLException e) {
                                    e.printStackTrace();
                                    System.exit(1);
                                }
                                break;
                            }
                            case 19:
                            {
                                try {
                                    librarianService.deleteUsers();
                                } catch (UserNotFoundException | BookCurrentlyLentException | SQLException e) {
                                    e.printStackTrace();
                                    System.exit(1);
                                }
                                break;
                            }
                            case 20:
                            {
                                break;
                            }
                            default:
                            {
                                System.out.println("Incorrect selection. Try again!");
                                break;
                            }
                        }
                    }
                    break;
                }
                case 2: {
                    PartnerService partnerService = new PartnerService();
                    if(LibrarianService.getPartners().size() == 0) {
                        System.out.println("There are no partners! ");
                        choose = 3;
                    }
                    else {
                        try {
                            partnerService.loginInformation();
                        } catch (EmailNotFoundException | IncorrectPasswordException | IncorrectPassFormatException | SQLException e) {
                            e.printStackTrace();
                            System.exit(1);
                        }

                    }
                    while (choose != 3) {
                        System.out.print("\n");
                        displayPartnerMenu(partnerMenuOpt);
                        choose = myObj.nextInt();
                        myObj.nextLine();
                        switch (choose) {
                            case 1:
                            {
                                try {
                                    partnerService.addNewBook();
                                } catch (SectionNotFoundException | SQLException | BookAlreadyAddedException e) {
                                    e.printStackTrace();
                                    System.exit(1);
                                }
                                break;
                            }
                            case 2:
                            {
                                try {
                                    partnerService.printPartnerBooks();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                    System.exit(1);
                                }
                                break;
                            }
                            case 3:
                            {
                                break;
                            }
                            default:
                            {
                                System.out.println("Incorrect selection. Try again!");
                                break;
                            }
                        }
                    }
                    break;
                }
                case 3:
                {
                    UserService userService = new UserService();
                    if(LibrarianService.getUsers().size() == 0) {
                        System.out.println("There are no users! ");
                        choose = 5;
                    }
                    else {
                        try {
                            userService.loginInformation();
                        } catch (EmailNotFoundException | IncorrectPasswordException | IncorrectPassFormatException | SQLException e) {
                            e.printStackTrace();
                            System.exit(1);
                        }
                    }
                    while (choose != 5) {
                        System.out.print("\n");
                        displayUserMenu(userMenuOpt);
                        choose = myObj.nextInt();
                        myObj.nextLine();
                        switch (choose) {
                            case 1:
                            {
                                try {
                                    userService.printAvailableBooks();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                    System.exit(1);
                                }
                                break;
                            }
                            case 2:
                            {
                                try {
                                    userService.printLentBooks();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                    System.exit(1);
                                }
                                break;
                            }
                            case 3:
                            {
                                try {
                                    userService.printReturnedBooks();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                    System.exit(1);
                                }
                                break;
                            }
                            case 4:
                            {
                                try {
                                    userService.printDiscounts();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                    System.exit(1);
                                }
                                break;
                            }
                            case 5:
                            {
                                break;
                            }
                            default:
                            {
                                System.out.println("Incorrect selection. Try again!");
                                break;
                            }
                        }
                    }
                    break;
                }
                case 4:
                {
                    System.exit(0);
                    break;
                }
                default:
                {
                    System.out.println("Incorrect selection. Try again!");
                    break;
                }
            }
        }
    }
}
