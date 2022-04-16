import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CSVInvoiceService {
    private String CSVPath;
    private String lineToRead;
    private ArrayList<String> listInvoices1 = new ArrayList<>();
    private ArrayList<String> listInvoices2 = new ArrayList<>();
    private ArrayList<ArrayList<String>> listInvoiceBook = new ArrayList<>(100);
    private ArrayList<ArrayList<String>> listInvoiceUser = new ArrayList<>(100);
    private static String[][] invoiceField = new String[100][];
    private static String[][] invoiceBookField = new String[100][];
    private static String[][] invoiceUserField = new String[100][];
    private static Integer[] invoiceId = new Integer[100];
    private static String[] invoiceDate = new String[100];
    private static String[] cardOrCash = new String[100];
    private static String[] invoiceBookPath = new String[100];
    private static String[] invoiceUserPath = new String[100];
    private static Integer[] bookId = new Integer[100];
    private static boolean[] isDeleted = new boolean[100];
    private static String[] author = new String[100];
    private static String[] title = new String[100];
    private static String[] description = new String[100];
    private static String[] issuedDate = new String[100];
    private static Integer[] daysFromIssue = new Integer[100];
    private static boolean[] isLent = new boolean[100];
    private static Integer[] userId = new Integer[100];
    private static Integer[] numOfLogins = new Integer[100];
    private static String[] userName = new String[100];
    private static String[] userPassword = new String[100];
    private static String[] userEmail = new String[100];
    private static ArrayList<Users> userThatLent = new ArrayList<>();
    private static ArrayList<LentBooks> book = new ArrayList<>();

    private static CSVInvoiceService csvInvoiceService = new CSVInvoiceService();

    private CSVInvoiceService() {}

    public static CSVInvoiceService getInstance() {
        return csvInvoiceService;
    }

    protected void readInvoices() {
        for (int i = 0; i < 100; i++)
            invoiceField[i] = new String[20];

        CSVPath = "../csv/invoices.csv";

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(CSVPath));
            while ((lineToRead = bufferedReader.readLine()) != null) {
                listInvoices1.add(lineToRead);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 1; i < listInvoices1.size(); i++)
            listInvoices2.add(listInvoices1.get(i));

        Collections.sort(listInvoices2);

        for (int i = 0; i < listInvoices2.size(); i++)
            invoiceField[i] = listInvoices2.get(i).split("\\s*,\\s*");

        for (int i = 0; i < listInvoices2.size(); i++)
            invoiceId[i] = Integer.parseInt(invoiceField[i][0]);

        for (int i = 0; i < listInvoices2.size(); i++)
            invoiceDate[i] = invoiceField[i][1];

        for (int i = 0; i < listInvoices2.size(); i++)
            invoiceBookPath[i] = invoiceField[i][2];

        for (int i = 0; i < listInvoices2.size(); i++)
            invoiceUserPath[i] = invoiceField[i][3];

        for (int i = 0; i < listInvoices2.size(); i++)
            cardOrCash[i] = invoiceField[i][4];

    }

    protected void readInvoiceBook(){
        for (int i = 0; i < 100; i++)
            invoiceBookField[i] = new String[20];

        for (int i = 0; i < 100; i++)
            listInvoiceBook.add(new ArrayList<>());

        for (int i = 0; i < listInvoices2.size(); i++) {
            CSVPath = invoiceBookPath[i];
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(CSVPath));
                while ((lineToRead = bufferedReader.readLine()) != null) {
                    listInvoiceBook.get(i).add(lineToRead);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            invoiceBookField[i] = listInvoiceBook.get(i).get(1).split("\\s*,\\s*");

            bookId[i] = Integer.parseInt(invoiceBookField[i][0]);

            isDeleted[i] = Boolean.parseBoolean(invoiceBookField[i][1]);

            author[i] = invoiceBookField[i][2];

            title[i] = invoiceBookField[i][3];

            description[i] = invoiceBookField[i][4];

            issuedDate[i] = invoiceBookField[i][5];

            daysFromIssue[i] = Integer.parseInt(invoiceBookField[i][6]);

            isLent[i] = Boolean.parseBoolean(invoiceBookField[i][7]);
        }
    }

    protected void readInvoiceUser() {
        for (int i = 0; i < 100; i++)
            invoiceUserField[i] = new String[20];

        for (int i = 0; i < 100; i++)
            listInvoiceUser.add(new ArrayList<>());

        for (int i = 0; i < listInvoices2.size(); i++) {
            CSVPath = invoiceUserPath[i];
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(CSVPath));
                while ((lineToRead = bufferedReader.readLine()) != null) {
                    listInvoiceUser.get(i).add(lineToRead);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            invoiceUserField[i] = listInvoiceUser.get(i).get(1).split(",");

            userId[i] = Integer.parseInt(invoiceUserField[i][0]);

            numOfLogins[i] = Integer.parseInt(invoiceUserField[i][1]);

            userName[i] = invoiceUserField[i][2];

            userPassword[i] = invoiceUserField[i][3];

            userEmail[i] = invoiceUserField[i][4];
        }

        for (int i = 0; i < listInvoices2.size(); i++) {
            for (int j = 0; j < LibrarianService.getUsers().size(); j++)
                if (userId[i] == LibrarianService.getUsers().get(j).getUserId())
                    userThatLent.add(LibrarianService.getUsers().get(j));
        }

        for (int i = 0; i < listInvoices2.size(); i++){
            for (int j = 0; j < LibrarianService.getUsers().size(); j++)
                for (int k = 0; k < LibrarianService.getUsers().get(j).getUserLentBooks().size(); k++)
                    if (bookId[i] == LibrarianService.getUsers().get(j).getUserLentBooks().get(k).getBookId()) {
                        book.add(LibrarianService.getUsers().get(j).getUserLentBooks().get(k));
                    }
        }

        for (int i = 0; i < listInvoices2.size(); i++) {
            LibrarianService.getInvoice().add(new Invoice(invoiceId[i], invoiceDate[i], book.get(i), userThatLent.get(i), cardOrCash[i]));
        }
    }
}
