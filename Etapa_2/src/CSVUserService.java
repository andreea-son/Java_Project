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
    private static String[][] userField = new String[100][];
    private static Integer[] userId = new Integer[100];
    private static Integer[] numOfLogins = new Integer[100];
    private static String[] userName = new String[100];
    private static String[] userPassword = new String[100];
    private static String[] userEmail = new String[100];
    private static boolean[] isDeleted = new boolean[100];

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
            isDeleted[i] = Boolean.parseBoolean(userField[i][4]);

        for (int i = 0; i < listUsers2.size(); i++)
            LibrarianService.getUsers().add(new Users(userId[i], numOfLogins[i], userName[i], userPassword[i], userEmail[i], isDeleted[i]));
    }
}
