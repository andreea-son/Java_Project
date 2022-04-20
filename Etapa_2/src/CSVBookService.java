import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class CSVBookService {
    private String CSVPath;
    private String lineToRead;
    private ArrayList<String> listBooks1 = new ArrayList<>();
    private ArrayList<String> listBooks2 = new ArrayList<>();
    private static String[][] bookField = new String[100][];
    private static Integer[] bookId = new Integer[100];
    private static String[] author = new String[100];
    private static String[] title = new String[100];
    private static String[] description = new String[100];
    private static boolean[] isLent = new boolean[100];
    private static boolean[] isDeleted = new boolean[100];
    private static Integer[] sectionId = new Integer[100];
    private static Integer[] partnerId = new Integer[100];
    private static LibrarianService librarian = new LibrarianService();

    private static CSVBookService csvBookService = new CSVBookService();

    private CSVBookService() {}

    public static CSVBookService getInstance() {
        return csvBookService;
    }

    protected void readBooks() {
        for (int i = 0; i < 100; i++)
            bookField[i] = new String[20];

        CSVPath = "../csv/books.csv";

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(CSVPath));
            while ((lineToRead = bufferedReader.readLine()) != null) {
                listBooks1.add(lineToRead);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 1; i < listBooks1.size(); i++)
            listBooks2.add(listBooks1.get(i));

        Collections.sort(listBooks2);

        for (int i = 0; i < listBooks2.size(); i++)
            bookField[i] = listBooks2.get(i).split("\\s*,\\s*");

        for (int i = 0; i < listBooks2.size(); i++)
            bookId[i] = Integer.parseInt(bookField[i][0]);

        for (int i = 0; i < listBooks2.size(); i++)
            author[i] = bookField[i][1];

        for (int i = 0; i < listBooks2.size(); i++)
            title[i] = bookField[i][2];

        for (int i = 0; i < listBooks2.size(); i++)
            description[i] = bookField[i][3];

        for (int i = 0; i < listBooks2.size(); i++)
            isLent[i] = Boolean.parseBoolean(bookField[i][4]);

        for (int i = 0; i < listBooks2.size(); i++)
            isDeleted[i] = Boolean.parseBoolean(bookField[i][5]);

        for (int i = 0; i < listBooks2.size(); i++)
            sectionId[i] = Integer.parseInt(bookField[i][6]);

        for (int i = 0; i < listBooks2.size(); i++)
            partnerId[i] = Integer.parseInt(bookField[i][7]);

        for (int i = 0; i < listBooks2.size(); i++) {
            librarian.getPartnerBooks().get(partnerId[i] - 1).add(new Books(bookId[i], author[i], title[i], description[i], isLent[i], isDeleted[i], sectionId[i], partnerId[i]));
        }
        for (int i = 0; i < listBooks2.size(); i++) {
            librarian.getSectionBooks().get(sectionId[i] - 1).add(new Books(bookId[i], author[i], title[i], description[i], isLent[i], isDeleted[i], sectionId[i], partnerId[i]));
        }
    }
}
