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
    private static String[][] invoiceField = new String[100][];
    private static Integer[] invoiceId = new Integer[100];
    private static String[] invoiceDate = new String[100];
    private static String[] cardOrCash = new String[100];
    private static Integer[] userId = new Integer[100];
    private static LibrarianService librarian = new LibrarianService();

    private CSVInvoiceService() {}

    private static CSVInvoiceService single_instance = null;

    public static CSVInvoiceService getInstance() {
        if (single_instance == null)
            single_instance = new CSVInvoiceService();

        return single_instance;
    }

    protected void readInvoices() {
        for (int i = 0; i < 100; i++)
            invoiceField[i] = new String[20];

        CSVPath = "Resources/csv/invoices.csv";

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
            userId[i] = Integer.parseInt(invoiceField[i][2]);

        for (int i = 0; i < listInvoices2.size(); i++)
            cardOrCash[i] = invoiceField[i][3];

        for (int i = 0; i < listInvoices2.size(); i++) {
            librarian.getInvoice().add(new Invoice(invoiceId[i], invoiceDate[i], userId[i], cardOrCash[i]));
        }
    }
}
