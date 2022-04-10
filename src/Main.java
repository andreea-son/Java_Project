import java.util.Scanner;
public class Main{
    private static Scanner myObj = new Scanner(System.in);
    private static int choose;
    public static String[] loginMenuOpt = {
        "Main menu: ",
        "1. Login as librarian",
        "2. Login as partner",
        "3. Login as user",
        "4. Exit"
    };
    public static String[] librarianMenuOpt = {
        "Librarian menu: ",
        "1. Add new section",
        "2. Add new partner",
        "3. Add new user",
        "4. Lend new book",
        "5. Return book",
        "6. Print sections",
        "7. Print books from section",
        "8. Print partners",
        "9. Print books added by partner",
        "10. Print users",
        "11. Print books lent by user",
        "12. Print all issued books",
        "13. Print all available books",
        "14. Logout"
    };

    public static String[] partnerMenuOpt = {
        "Partner menu: ",
        "1. Add new book",
        "2. View the books you added",
        "3. Logout"
    };
    public static void displayLoginMenu(String[] options){
        for (String option : options){
            System.out.println(option);
        }
        System.out.print("Choose your option an option between 1 and 4: ");
    }
    public static void displayPartnerMenu(String[] options){
        for (String option : options){
            System.out.println(option);
        }
        System.out.print("Choose your option an option between 1 and 3: ");
    }
    public static void displayLibrarianMenu(String[] options){
        for (String option : options){
            System.out.println(option);
        }
        System.out.print("Choose your option an option between 1 and 14: ");
    }
    public static void clearConsole(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static void main(String[] args) {
        clearConsole();
        displayLoginMenu(loginMenuOpt);
        choose = myObj.nextInt();
        myObj.nextLine();
        while (true) {
            switch (choose) {
                case 1:
                {
                    clearConsole();
                    LibrarianMenu librarianMenu = new LibrarianMenu();
                    try {
                        librarianMenu.loginInformation();
                    } catch (UsernameNotFoundException ex) {
                        System.err.print(ex);
                        System.exit(1);
                    } catch (IncorrectPasswordException ex1) {
                        System.err.print(ex1);
                        System.exit(1);
                    }
                    clearConsole();
                    displayLibrarianMenu(librarianMenuOpt);
                    choose = myObj.nextInt();
                    myObj.nextLine();
                    while (choose != 14){
                        switch (choose) {
                            case 1:
                            {
                                clearConsole();
                                try {
                                    librarianMenu.addNewSection();
                                } catch (AlreadyUsedNameException ex) {
                                    System.err.print(ex);
                                    System.exit(1);
                                }
                                break;
                            }
                            case 2:
                            {
                                clearConsole();
                                try {
                                    librarianMenu.addNewPartner();
                                } catch (AlreadyUsedNameException ex) {
                                    System.err.print(ex);
                                    System.exit(1);
                                } catch (AlreadyUsedEmailException ex1) {
                                    System.err.print(ex1);
                                    System.exit(1);
                                } catch (IncorrectMailFormatException ex2) {
                                    System.err.print(ex2);
                                    System.exit(1);
                                } catch (IncorrectPassFormatException ex3) {
                                    System.err.print(ex3);
                                    System.exit(1);
                                }
                                break;
                            }
                            case 3:
                            {
                                clearConsole();
                                try {
                                    librarianMenu.addNewUser();
                                } catch (BookNotFoundException ex) {
                                    System.err.print(ex);
                                    System.exit(1);
                                } catch (AlreadyUsedNameException ex1) {
                                    System.err.print(ex1);
                                    System.exit(1);
                                } catch (AlreadyUsedEmailException ex2) {
                                    System.err.print(ex2);
                                    System.exit(1);
                                } catch (IncorrectPasswordException ex3) {
                                    System.err.print(ex3);
                                    System.exit(1);
                                } catch (IncorrectMailFormatException ex4) {
                                    System.err.print(ex4);
                                    System.exit(1);
                                } catch (IncorrectPassFormatException ex5) {
                                    System.err.print(ex5);
                                    System.exit(1);
                                }
                                break;
                            }
                            case 4:
                            {
                                clearConsole();
                                try {
                                    librarianMenu.lendNewBook();
                                } catch (BookNotFoundException ex) {
                                    System.err.print(ex);
                                    System.exit(1);
                                } catch (BookAlreadyLentException ex1) {
                                    System.err.print(ex1);
                                    System.exit(1);
                                } catch (UserNotFoundException ex2) {
                                    System.err.print(ex2);
                                    System.exit(1);
                                } catch (IncorrectDateFormatException ex3) {
                                    System.err.print(ex3);
                                    System.exit(1);
                                } catch (MaxNumOfDaysException ex4) {
                                    System.err.print(ex4);
                                    System.exit(1);
                                } catch (WrongInputException ex5) {
                                    System.err.print(ex5);
                                    System.exit(1);
                                }
                                break;
                            }
                            case 5:
                            {
                                clearConsole();
                                try {
                                    librarianMenu.returnBook();
                                } catch (UserNotFoundException ex) {
                                    System.err.print(ex);
                                    System.exit(1);
                                } catch (BookNotFoundException ex1) {
                                    System.err.print(ex1);
                                    System.exit(1);
                                } catch (IncorrectDateFormatException ex2) {
                                    System.err.print(ex2);
                                    System.exit(1);
                                } catch (BookNotLentException ex3) {
                                    System.err.print(ex3);
                                    System.exit(1);
                                } catch (NoLentBooksException ex4) {
                                    System.err.print(ex4);
                                    System.exit(1);
                                } catch (WrongInputException ex5) {
                                    System.err.print(ex5);
                                    System.exit(1);
                                }
                                break;
                            }
                            case 6:
                            {
                                clearConsole();
                                librarianMenu.printSections();
                                break;
                            }
                            case 7:
                            {
                                clearConsole();
                                try {
                                    librarianMenu.printSectionBooks();
                                } catch (SectionNotFoundException ex) {
                                    System.err.print(ex);
                                    System.exit(1);
                                }
                                break;
                            }
                            case 8:
                            {
                                clearConsole();
                                librarianMenu.printPartners();
                                break;
                            }
                            case 9:
                            {
                                clearConsole();
                                try {
                                    librarianMenu.printPartnerBooks();
                                } catch (PartnerNotFoundException ex) {
                                    System.err.print(ex);
                                    System.exit(1);
                                }
                                break;
                            }
                            case 10:
                            {
                                clearConsole();
                                librarianMenu.printUsers();
                                break;
                            }
                            case 11:
                            {
                                clearConsole();
                                try {
                                    librarianMenu.printUserLentBooks();
                                } catch (UserNotFoundException ex) {
                                    System.err.print(ex);
                                    System.exit(1);
                                }
                                break;
                            }
                            case 12:
                            {
                                clearConsole();
                                librarianMenu.printIssuedBooks();
                                break;
                            }
                            case 13:
                            {
                                clearConsole();
                                librarianMenu.printBooks();
                                break;
                            }
                            default:
                            {
                                clearConsole();
                                System.out.println("Incorrect selection. Try again!");
                                break;
                            }
                        }
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
                    break;
                }
                case 2: {
                    clearConsole();
                    PartnerMenu partnerMenu = new PartnerMenu();
                    try {
                        partnerMenu.loginInformation();
                    } catch (EmailNotFoundException ex) {
                        System.err.print(ex);
                        System.exit(1);
                    } catch (IncorrectPasswordException ex1) {
                        System.err.print(ex1);
                        System.exit(1);
                    } catch (IncorrectPassFormatException ex2) {
                        System.err.print(ex2);
                        System.exit(1);
                    }
                    clearConsole();
                    displayPartnerMenu(partnerMenuOpt);
                    choose = myObj.nextInt();
                    myObj.nextLine();
                    clearConsole();
                    while (choose != 3) {
                        switch (choose) {
                            case 1:
                            {
                                clearConsole();
                                try {
                                    partnerMenu.addNewBook();
                                } catch (SectionNotFoundException ex) {
                                    System.err.print(ex);
                                    System.exit(1);
                                } catch (WrongInputException ex1) {
                                    System.err.print(ex1);
                                    System.exit(1);
                                } catch (BookAlreadyAddedException ex2) {
                                    System.err.print(ex2);
                                    System.exit(1);
                                }
                                break;
                            }
                            case 2:
                            {
                                clearConsole();
                                partnerMenu.printPartnerBooks();
                                break;
                            }
                            default:
                            {
                                System.out.println("Incorrect selection. Try again!");
                                break;
                            }
                        }
                        System.out.println("If you want to return to the previous menu type 0");
                        choose = myObj.nextInt();
                        myObj.nextLine();
                        if(choose == 0) {
                            clearConsole();
                            displayPartnerMenu(partnerMenuOpt);
                            choose = myObj.nextInt();
                            myObj.nextLine();
                        }
                    }
                    break;
                }
                case 3:
                {
                    break;
                }
                case 4:
                {
                    clearConsole();
                    System.exit(0);
                    break;
                }
                default:
                {
                    clearConsole();
                    System.out.println("Incorrect selection. Try again!!");
                    break;
                }
            }
            clearConsole();
            displayLoginMenu(loginMenuOpt);
            choose = myObj.nextInt();
            myObj.nextLine();
        }
    }
}
