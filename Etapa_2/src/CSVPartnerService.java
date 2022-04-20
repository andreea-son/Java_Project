import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CSVPartnerService {
    private String CSVPath;
    private String lineToRead;
    private ArrayList<String> listPartners1 = new ArrayList<>();
    private ArrayList<String> listPartners2 = new ArrayList<>();
    private static String[][] partnerField = new String[100][];
    private static Integer[] partnerId = new Integer[100];
    private static Integer[] numOfLogins = new Integer[100];
    private static String[] partnerName = new String[100];
    private static String[] partnerPassword = new String[100];
    private static String[] partnerEmail = new String[100];
    private static boolean[] isDeleted = new boolean[100];
    private static LibrarianService librarian = new LibrarianService();

    private static CSVPartnerService csvPartnerService = new CSVPartnerService();

    private CSVPartnerService() {}

    public static CSVPartnerService getInstance() {
        return csvPartnerService;
    }

    public void readPartners() {
        for (int i = 0; i < 100; i++)
            partnerField[i] = new String[20];

        CSVPath = "../csv/partners.csv";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(CSVPath));
            while ((lineToRead = bufferedReader.readLine()) != null) {
                listPartners1.add(lineToRead);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 1; i < listPartners1.size(); i++)
            listPartners2.add(listPartners1.get(i));

        Collections.sort(listPartners2);

        for (int i = 0; i < listPartners2.size(); i++)
            partnerField[i] = listPartners2.get(i).split("\\s*,\\s*");

        for (int i = 0; i < listPartners2.size(); i++)
            partnerId[i] = Integer.parseInt(partnerField[i][0]);

        for (int i = 0; i < listPartners2.size(); i++)
            numOfLogins[i] = Integer.parseInt(partnerField[i][1]);

        for (int i = 0; i < listPartners2.size(); i++)
            partnerName[i] = partnerField[i][2];

        for (int i = 0; i < listPartners2.size(); i++)
            partnerPassword[i] = partnerField[i][3];

        for (int i = 0; i < listPartners2.size(); i++)
            partnerEmail[i] = partnerField[i][4];

        for (int i = 0; i < listPartners2.size(); i++)
            isDeleted[i] = Boolean.parseBoolean(partnerField[i][5]);

        for (int i = 0; i < listPartners2.size(); i++)
           librarian.getPartners().add(new Partners(partnerId[i], numOfLogins[i], partnerName[i], partnerPassword[i], partnerEmail[i], isDeleted[i]));
    }
}
