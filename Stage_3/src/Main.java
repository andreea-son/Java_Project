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
        }
        try {
            librarianService.readUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            librarianService.readPartners();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            librarianService.readDiscounts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            librarianService.readSections();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            librarianService.readBooks();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            librarianService.readLentBooks();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        while (true) {
            System.out.print("\n");
            displayLoginMenu(loginMenuOpt);
            choose = myObj.nextInt();
            myObj.nextLine();
            switch (choose) {
                case 1:
                {
                    boolean running = true;
                    while(running) {
                        try {
                            librarianService.loginInformation();
                            running = false;
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    while (choose != 20){
                        System.out.print("\n");
                        displayLibrarianMenu(librarianMenuOpt);
                        choose = myObj.nextInt();
                        myObj.nextLine();
                        switch (choose) {
                            case 1:
                            {
                                running = true;
                                while(running) {
                                    try {
                                        librarianService.insertSection();
                                        running = false;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                break;
                            }
                            case 2:
                            {
                                running = true;
                                while(running) {
                                    try {
                                        librarianService.insertPartner();
                                        running = false;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                break;
                            }
                            case 3:
                            {
                                running = true;
                                while(running) {
                                    try {
                                        librarianService.insertUser();
                                        running = false;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                break;
                            }
                            case 4:
                            {
                                running = true;
                                while(running) {
                                    try {
                                        librarianService.lendNewBook();
                                        running = false;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                break;
                            }
                            case 5:
                            {
                                running = true;
                                while(running) {
                                    try {
                                        librarianService.returnBook();
                                        running = false;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                break;
                            }
                            case 6:
                            {
                                running = true;
                                while(running) {
                                    try {
                                        librarianService.printSections();
                                        running = false;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                break;
                            }
                            case 7:
                            {
                                running = true;
                                while(running) {
                                    try {
                                        librarianService.printSectionBooks();
                                        running = false;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                break;
                            }
                            case 8:
                            {
                                running = true;
                                while(running) {
                                    try {
                                        librarianService.printPartners();
                                        running = false;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                break;
                            }
                            case 9:
                            {
                                running = true;
                                while(running) {
                                    try {
                                        librarianService.printPartnerBooks();
                                        running = false;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                break;
                            }
                            case 10:
                            {
                                running = true;
                                while(running) {
                                    try {
                                        librarianService.printUsers();
                                        running = false;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                break;
                            }
                            case 11:
                            {
                                running = true;
                                while(running) {
                                    try {
                                        librarianService.printUserLentBooks();
                                        running = false;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                break;
                            }
                            case 12:
                            {
                                running = true;
                                while(running) {
                                    try {
                                        librarianService.printUserReturnedBooks();
                                        running = false;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                break;
                            }
                            case 13:
                            {
                                running = true;
                                while(running) {
                                    try {
                                        librarianService.printIssuedBooks();
                                        running = false;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                break;
                            }
                            case 14:
                            {
                                running = true;
                                while(running) {
                                    try {
                                        librarianService.printBooks();
                                        running = false;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                break;
                            }
                            case 15:
                            {
                                running = true;
                                while(running) {
                                    try {
                                        librarianService.printUserDiscounts();
                                        running = false;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                break;
                            }
                            case 16:
                            {
                                running = true;
                                while(running) {
                                    try {
                                        librarianService.deleteBook();
                                        running = false;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                break;
                            }
                            case 17:
                            {
                                running = true;
                                while(running) {
                                    try {
                                        librarianService.deleteSections();
                                        running = false;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                break;
                            }
                            case 18:
                            {
                                running = true;
                                while(running) {
                                    try {
                                        librarianService.deletePartners();
                                        running = false;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                break;
                            }
                            case 19:
                            {
                                running = true;
                                while(running) {
                                    try {
                                        librarianService.deleteUsers();
                                        running = false;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
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
                        boolean running = true;
                        while(running) {
                            try {
                                partnerService.loginInformation();
                                running = false;
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
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
                                boolean running = true;
                                while(running) {
                                    try {
                                        partnerService.addNewBook();
                                        running = false;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                break;
                            }
                            case 2:
                            {
                                boolean running = true;
                                while(running) {
                                    try {
                                        partnerService.printPartnerBooks();
                                        running = false;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
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
                        boolean running = true;
                        while(running) {
                            try {
                                userService.loginInformation();
                                running = false;
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
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
                                boolean running = true;
                                while(running) {
                                    try {
                                        userService.printAvailableBooks();
                                        running = false;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                break;
                            }
                            case 2:
                            {
                                boolean running = true;
                                while(running) {
                                    try {
                                        userService.printLentBooks();
                                        running = false;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                break;
                            }
                            case 3:
                            {
                                boolean running = true;
                                while(running) {
                                    try {
                                        userService.printReturnedBooks();
                                        running = false;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                break;
                            }
                            case 4:
                            {
                                boolean running = true;
                                while(running) {
                                    try {
                                        userService.printDiscounts();
                                        running = false;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
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
