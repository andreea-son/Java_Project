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

        CSVPartnerService csvPartnerService = CSVPartnerService.getInstance();
        csvPartnerService.readPartners();

        CSVSectionService csvSectionService = CSVSectionService.getInstance();
        csvSectionService.readSections();

        CSVUserService csvUserService = CSVUserService.getInstance();
        csvUserService.readUsers();

        CSVBookService csvBookService = CSVBookService.getInstance();
        csvBookService.readBooks();

        CSVLentBookService csvLentBookService = CSVLentBookService.getInstance();
        csvLentBookService.readLentBooks();

        CSVInvoiceService csvInvoiceService = CSVInvoiceService.getInstance();
        csvInvoiceService.readInvoices();

        CSVAuditService csvAuditService = new CSVAuditService();

        while (true) {
            System.out.print("\n");
            displayLoginMenu(loginMenuOpt);
            choose = myObj.nextInt();
            myObj.nextLine();
            switch (choose) {
                case 1:
                {
                    LibrarianService librarianService = new LibrarianService();
                    boolean running = true;
                    while(running) {
                        try {
                            librarianService.loginInformation();
                            running = false;
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    csvAuditService.updateCSV();
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
                                        librarianService.addNewSection();
                                        running = false;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                csvAuditService.updateCSV();
                                break;
                            }
                            case 2:
                            {
                                running = true;
                                while(running) {
                                    try {
                                        librarianService.addNewPartner();
                                        running = false;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                csvAuditService.updateCSV();
                                break;
                            }
                            case 3:
                            {
                                running = true;
                                while(running) {
                                    try {
                                        librarianService.addNewUser();
                                        running = false;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                csvAuditService.updateCSV();
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
                                csvAuditService.updateCSV();
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
                                csvAuditService.updateCSV();
                                break;
                            }
                            case 6:
                            {
                                librarianService.printSections();
                                csvAuditService.updateCSV();
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
                                csvAuditService.updateCSV();
                                break;
                            }
                            case 8:
                            {
                                librarianService.printPartners();
                                csvAuditService.updateCSV();
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
                                csvAuditService.updateCSV();
                                break;
                            }
                            case 10:
                            {
                                librarianService.printUsers();
                                csvAuditService.updateCSV();
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
                                csvAuditService.updateCSV();
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
                                csvAuditService.updateCSV();
                                break;
                            }
                            case 13:
                            {
                                librarianService.printIssuedBooks();
                                csvAuditService.updateCSV();
                                break;
                            }
                            case 14:
                            {
                                librarianService.printBooks();
                                csvAuditService.updateCSV();
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
                                csvAuditService.updateCSV();
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
                                csvAuditService.updateCSV();
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
                                csvAuditService.updateCSV();
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
                                csvAuditService.updateCSV();
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
                                csvAuditService.updateCSV();
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
                        System.out.println("There are no partners! \n");
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
                        csvAuditService.updateCSV();
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
                                csvAuditService.updateCSV();
                                break;
                            }
                            case 2:
                            {
                                partnerService.printPartnerBooks();
                                csvAuditService.updateCSV();
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
                        System.out.println("There are no users! \n");
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
                        csvAuditService.updateCSV();
                    }
                    while (choose != 5) {
                        System.out.print("\n");
                        displayUserMenu(userMenuOpt);
                        choose = myObj.nextInt();
                        myObj.nextLine();
                        switch (choose) {
                            case 1:
                            {
                                userService.printAvailableBooks();
                                csvAuditService.updateCSV();
                                break;
                            }
                            case 2:
                            {
                                userService.printLentBooks();
                                csvAuditService.updateCSV();
                                break;
                            }
                            case 3:
                            {
                                userService.printReturnedBooks();
                                csvAuditService.updateCSV();
                                break;
                            }
                            case 4:
                            {
                                userService.printDiscounts();
                                csvAuditService.updateCSV();
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
                    csvAuditService.closeFile();
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
