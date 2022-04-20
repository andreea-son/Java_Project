import java.util.Scanner;
public class Main{
    private static Scanner myObj = new Scanner(System.in);
    private static int choose;
    private static boolean ok = false;
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
    public static void clearConsole(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static void main(String[] args) {
        clearConsole();

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

        displayLoginMenu(loginMenuOpt);
        choose = myObj.nextInt();
        myObj.nextLine();
        while (true) {
            switch (choose) {
                case 1:
                {
                    LibrarianService librarianService = new LibrarianService();
                    clearConsole();
                    try {
                        librarianService.loginInformation();
                    } catch (UsernameNotFoundException ex) {
                        csvAuditService.closeFile();
                        System.err.print(ex);
                        System.exit(1);
                    } catch (IncorrectPasswordException ex1) {
                        csvAuditService.closeFile();
                        System.err.print(ex1);
                        System.exit(1);
                    }
                    csvAuditService.updateCSV();
                    clearConsole();
                    displayLibrarianMenu(librarianMenuOpt);
                    choose = myObj.nextInt();
                    myObj.nextLine();
                    while (choose != 20){
                        switch (choose) {
                            case 1:
                            {
                                clearConsole();
                                try {
                                    librarianService.addNewSection();
                                } catch (AlreadyUsedNameException ex) {
                                    csvAuditService.closeFile();
                                    System.err.print(ex);
                                    System.exit(1);
                                }
                                csvAuditService.updateCSV();
                                break;
                            }
                            case 2:
                            {
                                clearConsole();
                                try {
                                    librarianService.addNewPartner();
                                } catch (AlreadyUsedEmailException ex) {
                                    csvAuditService.closeFile();
                                    System.err.print(ex);
                                    System.exit(1);
                                } catch (IncorrectMailFormatException ex1) {
                                    csvAuditService.closeFile();
                                    System.err.print(ex1);
                                    System.exit(1);
                                }
                                csvAuditService.updateCSV();
                                break;
                            }
                            case 3:
                            {
                                clearConsole();
                                try {
                                    librarianService.addNewUser();
                                } catch (AlreadyUsedEmailException ex) {
                                    csvAuditService.closeFile();
                                    System.err.print(ex);
                                    System.exit(1);
                                } catch (IncorrectMailFormatException ex1) {
                                    csvAuditService.closeFile();
                                    System.err.print(ex1);
                                    System.exit(1);
                                }
                                csvAuditService.updateCSV();
                                break;
                            }
                            case 4:
                            {
                                clearConsole();
                                int ok1 = 0;
                                int ok2 = 0;

                                for (int i = 0; i < librarianService.getPartners().size(); i++)
                                    for (int j = 0; j < librarianService.getPartnerBooks().get(i).size(); j++)
                                        if (librarianService.getPartnerBooks().get(i).size() > 0)
                                            if (!librarianService.getPartnerBooks().get(i).get(j).getIsDeleted()){
                                                ok2 = 1;
                                                break;
                                            }

                                if(librarianService.getUsers().size() == 0) {
                                    System.out.println("There are no users! \n");
                                    choose = 20;
                                }
                                else{
                                    for (Users user : librarianService.getUsers())
                                        if (!user.getIsDeleted()) {
                                            ok1 = 1;
                                            break;
                                        }
                                    if (ok1 == 0) {
                                        System.out.println("There are no users! \n");
                                        choose = 20;
                                    }
                                    else if (ok2 == 0) {
                                        System.out.println("There are no books! \n");
                                        choose = 20;
                                    }
                                    else {
                                        try {
                                            librarianService.lendNewBook();
                                        } catch (BookNotFoundException ex) {
                                            csvAuditService.closeFile();
                                            System.err.print(ex);
                                            System.exit(1);
                                        } catch (BookAlreadyLentException ex1) {
                                            csvAuditService.closeFile();
                                            System.err.print(ex1);
                                            System.exit(1);
                                        } catch (UserNotFoundException ex2) {
                                            csvAuditService.closeFile();
                                            System.err.print(ex2);
                                            System.exit(1);
                                        } catch (IncorrectDateFormatException ex3) {
                                            csvAuditService.closeFile();
                                            System.err.print(ex3);
                                            System.exit(1);
                                        } catch (MaxNumOfDaysException ex4) {
                                            csvAuditService.closeFile();
                                            System.err.print(ex4);
                                            System.exit(1);
                                        } catch (WrongInputException ex5) {
                                            csvAuditService.closeFile();
                                            System.err.print(ex5);
                                            System.exit(1);
                                        } catch (InvalidCodeException ex6) {
                                            csvAuditService.closeFile();
                                            System.err.print(ex6);
                                            System.exit(1);
                                        } catch (DateNotValidException ex7) {
                                            csvAuditService.closeFile();
                                            System.err.print(ex7);
                                            System.exit(1);
                                        }
                                        csvAuditService.updateCSV();
                                    }
                                }
                                break;
                            }
                            case 5:
                            {
                                clearConsole();

                                int ok1 = 0;
                                int ok2 = 0;

                                for (int i = 0; i < librarianService.getUsers().size(); i++)
                                    for (int j = 0; j < librarianService.getUserLentBooks().get(i).size(); j++)
                                        if (librarianService.getUserLentBooks().get(i).get(j).getIsLent() && !librarianService.getUserLentBooks().get(i).get(j).getIsDeleted()) {
                                            ok2 = 1;
                                            break;
                                        }
                                if(librarianService.getUsers().size() == 0) {
                                    System.out.println("There are no users! \n");
                                    choose = 20;
                                }
                                else {
                                    for (Users user : librarianService.getUsers())
                                        if (!user.getIsDeleted()) {
                                            ok1 = 1;
                                            break;
                                        }
                                    if (ok1 == 0) {
                                        System.out.println("There are no users! \n");
                                        choose = 20;
                                    }
                                    else if (ok2 == 0) {
                                        System.out.println("There are no lent books! \n");
                                        choose = 20;
                                    }
                                    else {
                                        try {
                                            librarianService.returnBook();
                                        } catch (UserNotFoundException ex) {
                                            csvAuditService.closeFile();
                                            System.err.print(ex);
                                            System.exit(1);
                                        } catch (BookNotFoundException ex1) {
                                            csvAuditService.closeFile();
                                            System.err.print(ex1);
                                            System.exit(1);
                                        } catch (IncorrectDateFormatException ex2) {
                                            csvAuditService.closeFile();
                                            System.err.print(ex2);
                                            System.exit(1);
                                        } catch (BookNotLentException ex3) {
                                            csvAuditService.closeFile();
                                            System.err.print(ex3);
                                            System.exit(1);
                                        } catch (NoLentBooksException ex4) {
                                            csvAuditService.closeFile();
                                            System.err.print(ex4);
                                            System.exit(1);
                                        } catch (WrongInputException ex5) {
                                            csvAuditService.closeFile();
                                            System.err.print(ex5);
                                            System.exit(1);
                                        } catch (DateNotValidException ex6) {
                                            csvAuditService.closeFile();
                                            System.err.print(ex6);
                                            System.exit(1);
                                        }
                                        csvAuditService.updateCSV();
                                    }
                                }
                                break;
                            }
                            case 6:
                            {
                                clearConsole();
                                librarianService.printSections();
                                csvAuditService.updateCSV();
                                break;
                            }
                            case 7:
                            {
                                clearConsole();
                                try {
                                    librarianService.printSectionBooks();
                                } catch (SectionNotFoundException ex) {
                                    csvAuditService.closeFile();
                                    System.err.print(ex);
                                    System.exit(1);
                                }
                                csvAuditService.updateCSV();
                                break;
                            }
                            case 8:
                            {
                                clearConsole();
                                librarianService.printPartners();
                                csvAuditService.updateCSV();
                                break;
                            }
                            case 9:
                            {
                                clearConsole();
                                try {
                                    librarianService.printPartnerBooks();
                                } catch (PartnerNotFoundException ex) {
                                    csvAuditService.closeFile();
                                    System.err.print(ex);
                                    System.exit(1);
                                }
                                csvAuditService.updateCSV();
                                break;
                            }
                            case 10:
                            {
                                clearConsole();
                                librarianService.printUsers();
                                csvAuditService.updateCSV();
                                break;
                            }
                            case 11:
                            {
                                clearConsole();
                                try {
                                    librarianService.printUserLentBooks();
                                } catch (UserNotFoundException ex) {
                                    csvAuditService.closeFile();
                                    System.err.print(ex);
                                    System.exit(1);
                                }
                                csvAuditService.updateCSV();
                                break;
                            }
                            case 12:
                            {
                                clearConsole();
                                try {
                                    librarianService.printUserReturnedBooks();
                                } catch (UserNotFoundException ex) {
                                    csvAuditService.closeFile();
                                    System.err.print(ex);
                                    System.exit(1);
                                }
                                csvAuditService.updateCSV();
                                break;
                            }
                            case 13:
                            {
                                clearConsole();
                                librarianService.printIssuedBooks();
                                csvAuditService.updateCSV();
                                break;
                            }
                            case 14:
                            {
                                clearConsole();
                                librarianService.printBooks();
                                csvAuditService.updateCSV();
                                break;
                            }
                            case 15:
                            {
                                clearConsole();
                                try {
                                    librarianService.printUserDiscounts();
                                } catch (UserNotFoundException ex) {
                                    csvAuditService.closeFile();
                                    System.err.print(ex);
                                    System.exit(1);
                                }
                                csvAuditService.updateCSV();
                                break;
                            }
                            case 16:
                            {
                                clearConsole();

                                int ok1 = 0;
                                int ok2 = 0;

                                for (int i = 0; i < librarianService.getPartners().size(); i++)
                                    if (librarianService.getPartnerBooks().get(i).size() > 0) {
                                        ok1 = 1;
                                        break;
                                    }
                                if (ok1 == 0) {
                                    System.out.println("There are no books! \n");
                                    choose = 20;
                                }
                                else {
                                    for (int i = 0; i < librarianService.getPartners().size(); i++)
                                        for (int j = 0; j < librarianService.getPartnerBooks().get(i).size(); j++)
                                            if (!librarianService.getPartnerBooks().get(i).get(j).getIsLent() && !librarianService.getPartnerBooks().get(i).get(j).getIsDeleted()) {
                                                ok2 = 1;
                                                break;
                                            }
                                    if (ok2 == 0) {
                                        System.out.println("There are no books! \n");
                                        choose = 20;
                                    }
                                    else {
                                        try {
                                            librarianService.deleteBook();
                                        } catch (BookNotFoundException ex) {
                                            csvAuditService.closeFile();
                                            System.err.print(ex);
                                            System.exit(1);
                                        } catch (BookCurrentlyLentException ex1) {
                                            csvAuditService.closeFile();
                                            System.err.print(ex1);
                                            System.exit(1);
                                        }
                                        csvAuditService.updateCSV();
                                    }
                                }
                                break;
                            }
                            case 17:
                            {
                                clearConsole();

                                int ok1 = 0;

                                if(librarianService.getSections().size() < 1) {
                                    System.out.println("There are no sections! \n");
                                    choose = 20;
                                }
                                else {
                                    for (Sections section : librarianService.getSections())
                                        if (!section.getIsDeleted()) {
                                            ok1 = 1;
                                            break;
                                        }
                                    if (ok1 == 0) {
                                        System.out.println("There are no sections! \n");
                                        choose = 20;
                                    }
                                    else {
                                        try {
                                            librarianService.deleteSections();
                                        } catch (SectionNotFoundException ex) {
                                            csvAuditService.closeFile();
                                            System.err.print(ex);
                                            System.exit(1);
                                        } catch (BookCurrentlyLentException ex1) {
                                            csvAuditService.closeFile();
                                            System.err.print(ex1);
                                            System.exit(1);
                                        }
                                        csvAuditService.updateCSV();
                                    }
                                }
                                break;
                            }
                            case 18:
                            {
                                clearConsole();

                                int ok1 = 0;

                                if(librarianService.getPartners().size() < 1){
                                    System.out.println("There are no partners! \n");
                                }
                                else {
                                    for (Partners partner : librarianService.getPartners())
                                        if (!partner.getIsDeleted()) {
                                            ok1 = 1;
                                            break;
                                        }
                                    if (ok1 == 0)
                                        System.out.println("There are no partners! \n");
                                    else {
                                        try {
                                            librarianService.deletePartners();
                                        } catch (PartnerNotFoundException ex) {
                                            csvAuditService.closeFile();
                                            System.err.print(ex);
                                            System.exit(1);
                                        } catch (BookCurrentlyLentException ex1) {
                                            csvAuditService.closeFile();
                                            System.err.print(ex1);
                                            System.exit(1);
                                        }
                                        csvAuditService.updateCSV();
                                    }
                                }
                                break;
                            }
                            case 19:
                            {
                                clearConsole();

                                int ok1 = 0;

                                if(librarianService.getUsers().size() < 1){
                                    System.out.println("There are no users! \n");
                                }
                                else {
                                    for (Users user : librarianService.getUsers())
                                        if (!user.getIsDeleted()) {
                                            ok1 = 1;
                                            break;
                                        }
                                    if (ok1 == 0)
                                        System.out.println("There are no users! \n");
                                    else {
                                        try {
                                            librarianService.deleteUsers();
                                        } catch (UserNotFoundException ex) {
                                            csvAuditService.closeFile();
                                            System.err.print(ex);
                                            System.exit(1);
                                        } catch (BookCurrentlyLentException ex1) {
                                            csvAuditService.closeFile();
                                            System.err.print(ex1);
                                            System.exit(1);
                                        }
                                        csvAuditService.updateCSV();
                                    }
                                }
                                break;
                            }
                            default:
                            {
                                clearConsole();
                                System.out.println("Incorrect selection. Try again!");
                                ok = true;
                                break;
                            }
                        }
                        if(!ok) {
                            System.out.println("If you want to return to the previous menu type 0");
                            choose = myObj.nextInt();
                            myObj.nextLine();
                            if (choose == 0) {
                                clearConsole();
                                displayLibrarianMenu(librarianMenuOpt);
                                choose = myObj.nextInt();
                                myObj.nextLine();
                            }
                        }
                        else {
                            displayLibrarianMenu(librarianMenuOpt);
                            choose = myObj.nextInt();
                            myObj.nextLine();
                            ok = false;
                        }
                    }
                    break;
                }
                case 2: {
                    clearConsole();
                    PartnerService partnerService = new PartnerService();
                    if(LibrarianService.getPartners().size() == 0) {
                        System.out.println("There are no partners! ");
                        System.out.println("If you want to return to the previous menu type 0");
                        choose = myObj.nextInt();
                        myObj.nextLine();
                        if(choose == 0) {
                            choose = 3;
                            clearConsole();
                        }
                    }
                    else {
                        try {
                            partnerService.loginInformation();
                        } catch (EmailNotFoundException ex) {
                            csvAuditService.closeFile();
                            System.err.print(ex);
                            System.exit(1);
                        } catch (IncorrectPasswordException ex1) {
                            csvAuditService.closeFile();
                            System.err.print(ex1);
                            System.exit(1);
                        } catch (IncorrectPassFormatException ex2) {
                            csvAuditService.closeFile();
                            System.err.print(ex2);
                            System.exit(1);
                        }
                        csvAuditService.updateCSV();
                        clearConsole();
                        displayPartnerMenu(partnerMenuOpt);
                        choose = myObj.nextInt();
                        myObj.nextLine();
                        clearConsole();
                    }
                    while (choose != 3) {
                        switch (choose) {
                            case 1:
                            {
                                clearConsole();
                                try {
                                    partnerService.addNewBook();
                                } catch (SectionNotFoundException ex) {
                                    csvAuditService.closeFile();
                                    System.err.print(ex);
                                    System.exit(1);
                                } catch (BookAlreadyAddedException ex1) {
                                    csvAuditService.closeFile();
                                    System.err.print(ex1);
                                    System.exit(1);
                                }
                                csvAuditService.updateCSV();
                                break;
                            }
                            case 2:
                            {
                                clearConsole();
                                partnerService.printPartnerBooks();
                                csvAuditService.updateCSV();
                                break;
                            }
                            default:
                            {
                                clearConsole();
                                System.out.println("Incorrect selection. Try again!");
                                ok = true;
                                break;
                            }
                        }
                        if(!ok) {
                            System.out.println("If you want to return to the previous menu type 0");
                            choose = myObj.nextInt();
                            myObj.nextLine();
                            if (choose == 0) {
                                clearConsole();
                                displayPartnerMenu(partnerMenuOpt);
                                choose = myObj.nextInt();
                                myObj.nextLine();
                            }
                        }
                        else {
                            displayPartnerMenu(partnerMenuOpt);
                            choose = myObj.nextInt();
                            myObj.nextLine();
                            ok = false;
                        }
                    }
                    break;
                }
                case 3:
                {
                    clearConsole();
                    UserService userService = new UserService();
                    if(LibrarianService.getUsers().size() == 0) {
                        System.out.println("There are no users! ");
                        System.out.println("If you want to return to the previous menu type 0");
                        choose = myObj.nextInt();
                        myObj.nextLine();
                        if(choose == 0) {
                            choose = 3;
                            clearConsole();
                        }
                    }
                    else {
                        try {
                            userService.loginInformation();
                        } catch (EmailNotFoundException ex) {
                            csvAuditService.closeFile();
                            System.err.print(ex);
                            System.exit(1);
                        } catch (IncorrectPasswordException ex1) {
                            csvAuditService.closeFile();
                            System.err.print(ex1);
                            System.exit(1);
                        } catch (IncorrectPassFormatException ex2) {
                            csvAuditService.closeFile();
                            System.err.print(ex2);
                            System.exit(1);
                        }
                        csvAuditService.updateCSV();
                        clearConsole();
                        displayUserMenu(userMenuOpt);
                        choose = myObj.nextInt();
                        myObj.nextLine();
                        clearConsole();
                    }
                    while (choose != 5) {
                        switch (choose) {
                            case 1:
                            {
                                clearConsole();
                                userService.printAvailableBooks();
                                csvAuditService.updateCSV();
                                break;
                            }
                            case 2:
                            {
                                clearConsole();
                                userService.printLentBooks();
                                csvAuditService.updateCSV();
                                break;
                            }
                            case 3:
                            {
                                clearConsole();
                                userService.printReturnedBooks();
                                csvAuditService.updateCSV();
                                break;
                            }
                            case 4:
                            {
                                clearConsole();
                                userService.printDiscounts();
                                csvAuditService.updateCSV();
                                break;
                            }
                            default:
                            {
                                clearConsole();
                                System.out.println("Incorrect selection. Try again!");
                                ok = true;
                                break;
                            }
                        }
                        if(!ok) {
                            System.out.println("If you want to return to the previous menu type 0");
                            choose = myObj.nextInt();
                            myObj.nextLine();
                            if (choose == 0) {
                                clearConsole();
                                displayUserMenu(userMenuOpt);
                                choose = myObj.nextInt();
                                myObj.nextLine();
                            }
                        }
                        else {
                            displayUserMenu(userMenuOpt);
                            choose = myObj.nextInt();
                            myObj.nextLine();
                            ok = false;
                        }
                    }
                    break;
                }
                case 4:
                {
                    clearConsole();
                    csvAuditService.closeFile();
                    System.exit(0);
                    break;
                }
                default:
                {
                    clearConsole();
                    System.out.println("Incorrect selection. Try again!");
                    ok = true;
                    break;
                }
            }
            if(!ok) {
                clearConsole();
            }
            displayLoginMenu(loginMenuOpt);
            choose = myObj.nextInt();
            myObj.nextLine();
            ok = false;
        }
    }
}
