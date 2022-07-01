import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class CSVLentBookService {
    private String CSVPath;
    private String lineToRead;
    private ArrayList<String> listLentBooks1 = new ArrayList<>();
    private ArrayList<String> listLentBooks2 = new ArrayList<>();
    private static String[][] lentBookField = new String[100][];
    private static Integer[] bookId = new Integer[100];
    private static boolean[] isDeleted = new boolean[100];
    private static String[] author = new String[100];
    private static String[] title = new String[100];
    private static String[] description = new String[100];
    private static String[] issuedDate = new String[100];
    private static Integer[] daysFromIssue = new Integer[100];
    private static boolean[] isLent = new boolean[100];
    private static Integer[] sectionId = new Integer[100];
    private static Integer[] partnerId = new Integer[100];
    private static Integer[] userId = new Integer[100];
    private static LibrarianService librarian = new LibrarianService();

    private CSVLentBookService() {}

    private static CSVLentBookService single_instance = null;

    public static CSVLentBookService getInstance() {
        if (single_instance == null)
            single_instance = new CSVLentBookService();

        return single_instance;
    }

    protected void readLentBooks() {
        for (int i = 0; i < 100; i++)
            lentBookField[i] = new String[20];

        CSVPath = "Resources/csv/lentBooks.csv";

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(CSVPath));
            while ((lineToRead = bufferedReader.readLine()) != null) {
                listLentBooks1.add(lineToRead);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 1; i < listLentBooks1.size(); i++)
            listLentBooks2.add(listLentBooks1.get(i));

        Collections.sort(listLentBooks2);

        for (int i = 0; i < listLentBooks2.size(); i++)
            lentBookField[i] = listLentBooks2.get(i).split("\\s*,\\s*");

        for (int i = 0; i < listLentBooks2.size(); i++)
            bookId[i] = Integer.parseInt(lentBookField[i][0]);

        for (int i = 0; i < listLentBooks2.size(); i++)
            isDeleted[i] = Boolean.parseBoolean(lentBookField[i][1]);

        for (int i = 0; i < listLentBooks2.size(); i++)
            author[i] = lentBookField[i][2];

        for (int i = 0; i < listLentBooks2.size(); i++)
            title[i] = lentBookField[i][3];

        for (int i = 0; i < listLentBooks2.size(); i++)
            description[i] = lentBookField[i][4];

        for (int i = 0; i < listLentBooks2.size(); i++)
            issuedDate[i] = lentBookField[i][5];

        for (int i = 0; i < listLentBooks2.size(); i++)
            daysFromIssue[i] = Integer.parseInt(lentBookField[i][6]);

        for (int i = 0; i < listLentBooks2.size(); i++)
            isLent[i] = Boolean.parseBoolean(lentBookField[i][7]);

        for (int i = 0; i < listLentBooks2.size(); i++)
            sectionId[i] = Integer.parseInt(lentBookField[i][8]);

        for (int i = 0; i < listLentBooks2.size(); i++)
            partnerId[i] = Integer.parseInt(lentBookField[i][9]);

        for (int i = 0; i < listLentBooks2.size(); i++)
            userId[i] = Integer.parseInt(lentBookField[i][10]);

        for (int i = 0; i < listLentBooks2.size(); i++) {
            librarian.getUserLentBooks().get(userId[i] - 1).add(new LentBooks(bookId[i], isDeleted[i], author[i], title[i], description[i], issuedDate[i], daysFromIssue[i], isLent[i], sectionId[i], partnerId[i], userId[i]));
        }
    }
}
