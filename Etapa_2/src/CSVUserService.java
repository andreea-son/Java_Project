import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class CSVUserService {
    private String CSVPath;
    private String lineToRead;
    private ArrayList<String> listUsers1 = new ArrayList<>();
    private ArrayList<String> listUsers2 = new ArrayList<>();
    private ArrayList<ArrayList<String>> listUserBooks = new ArrayList<>(100);
    private static String[][] userField = new String[100][];
    private static String[][] userBooksField = new String[100][];
    private static Integer[] userId = new Integer[100];
    private static Integer[] numOfLogins = new Integer[100];
    private static String[] userName = new String[100];
    private static String[] userPassword = new String[100];
    private static String[] userEmail = new String[100];
    private static String[] userBooksPath = new String[100];
    private static Integer[][] bookId = new Integer[100][];
    private static boolean[][] isDeleted = new boolean[100][];
    private static String[][] author = new String[100][];
    private static String[][] title = new String[100][];
    private static String[][] description = new String[100][];
    private static String[][] issuedDate = new String[100][];
    private static Integer[][] daysFromIssue = new Integer[100][];
    private static boolean[][] isLent = new boolean[100][];

    private static CSVUserService csvUserService = new CSVUserService();

    private CSVUserService(){}

    public static CSVUserService getInstance() {
        return csvUserService;
    }

    public void readUsers() {
        for (int i = 0; i < 100; i++)
            userField[i] = new String[20];

        CSVPath = "../csv/users.csv";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(CSVPath));
            while ((lineToRead = bufferedReader.readLine()) != null) {
                listUsers1.add(lineToRead);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 1; i < listUsers1.size(); i++)
            listUsers2.add(listUsers1.get(i));

        Collections.sort(listUsers2);

        for (int i = 0; i < listUsers2.size(); i++)
            userField[i] = listUsers2.get(i).split("\\s*,\\s*");

        for (int i = 0; i < listUsers2.size(); i++)
            userId[i] = Integer.parseInt(userField[i][0]);

        for (int i = 0; i < listUsers2.size(); i++)
            numOfLogins[i] = Integer.parseInt(userField[i][1]);

        for (int i = 0; i < listUsers2.size(); i++)
            userName[i] = userField[i][2];

        for (int i = 0; i < listUsers2.size(); i++)
            userPassword[i] = userField[i][3];

        for (int i = 0; i < listUsers2.size(); i++)
            userEmail[i] = userField[i][4];

        for (int i = 0; i < listUsers2.size(); i++)
            if (!userField[i][5].equals("null"))
                userBooksPath[i] = userField[i][5];
            else {
                userBooksPath[i] = "";
            }
        for (int i = 0; i < listUsers2.size(); i++)
            LibrarianService.getUsers().add(new Users(userId[i], numOfLogins[i], userName[i], userPassword[i], userEmail[i]));
    }

    public void readUserBooks() {
        for (int i = 0; i < 100; i++)
            userBooksField[i] = new String[20];

        for (int i = 0; i < 100; i++)
            listUserBooks.add(new ArrayList<>());

        for (int i = 0; i < 100; i++)
            bookId[i] = new Integer[20];

        for (int i = 0; i < 100; i++)
            isDeleted[i] = new boolean[20];

        for (int i = 0; i < 100; i++)
            author[i] = new String[20];

        for (int i = 0; i < 100; i++)
            title[i] = new String[20];

        for (int i = 0; i < 100; i++)
            description[i] = new String[20];

        for (int i = 0; i < 100; i++)
            issuedDate[i] = new String[20];

        for (int i = 0; i < 100; i++)
            daysFromIssue[i] = new Integer[20];

        for (int i = 0; i < 100; i++)
            isLent[i] = new boolean[20];

        for (int i = 0; i < listUsers2.size(); i++) {
            if (!userBooksPath[i].isEmpty()) {
                CSVPath = userBooksPath[i];
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(CSVPath));
                    while ((lineToRead = bufferedReader.readLine()) != null) {
                        listUserBooks.get(i).add(lineToRead);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for (int j = 1; j < listUserBooks.get(i).size(); j++)
                    userBooksField[j - 1] = listUserBooks.get(i).get(j).split("\\s*,\\s*");

                for (int j = 1; j < listUserBooks.get(i).size(); j++)
                    bookId[i][j - 1] = Integer.parseInt(userBooksField[j - 1][0]);

                for (int j = 1; j < listUserBooks.get(i).size(); j++)
                    isDeleted[i][j - 1] = Boolean.parseBoolean(userBooksField[j - 1][1]);

                for (int j = 1; j < listUserBooks.get(i).size(); j++)
                    author[i][j - 1] = userBooksField[j - 1][2];

                for (int j = 1; j < listUserBooks.get(i).size(); j++)
                    title[i][j - 1] = userBooksField[j - 1][3];

                for (int j = 1; j < listUserBooks.get(i).size(); j++)
                    description[i][j - 1] = userBooksField[j - 1][4];

                for (int j = 1; j < listUserBooks.get(i).size(); j++)
                    issuedDate[i][j - 1] = userBooksField[j - 1][5];

                for (int j = 1; j < listUserBooks.get(i).size(); j++)
                    daysFromIssue[i][j - 1] = Integer.parseInt(userBooksField[j - 1][6]);

                for (int j = 1; j < listUserBooks.get(i).size(); j++)
                    isLent[i][j - 1] = Boolean.parseBoolean(userBooksField[j - 1][7]);

                for (int j = 1; j < listUserBooks.get(i).size(); j++)
                    LibrarianService.getUsers().get(i).getUserLentBooks().add(new LentBooks(bookId[i][j - 1], isDeleted[i][j - 1], author[i][j - 1], title[i][j - 1], description[i][j - 1], issuedDate[i][j - 1], daysFromIssue[i][j - 1], isLent[i][j - 1]));

            }
        }

        for (int j = 0; j < LibrarianService.getUsers().size(); j++)
            LibrarianService.getUserLentBooks().get(j).addAll(LibrarianService.getUsers().get(j).getUserLentBooks());

        for (int j = 0; j < LibrarianService.getUsers().size(); j++)
            LibrarianService.getUsers().set(j, new Users(userId[j], numOfLogins[j], userName[j], userPassword[j], userEmail[j], LibrarianService.getUsers().get(j).getUserLentBooks()));
    }
}
