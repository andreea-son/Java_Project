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
    private ArrayList<ArrayList<String>> listPartnerBooks = new ArrayList<>(100);
    private static String[][] partnerField = new String[100][];
    private static String[][] partnerBooksField = new String[100][];
    private static Integer[] partnerId = new Integer[100];
    private static Integer[] numOfLogins = new Integer[100];
    private static String[] partnerName = new String[100];
    private static String[] partnerPassword = new String[100];
    private static String[] partnerEmail = new String[100];
    private static String[] partnerBooksPath = new String[100];
    private static Integer[][] bookId = new Integer[100][];
    private static String[][] author = new String[100][];
    private static String[][] title = new String[100][];
    private static String[][] description = new String[100][];
    private static boolean[][] isLent = new boolean[100][];

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
            if (!partnerField[i][5].equals("null"))
                partnerBooksPath[i] = partnerField[i][5];
            else {
                partnerBooksPath[i] = "";
            }
        for (int i = 0; i < listPartners2.size(); i++)
           LibrarianService.getPartners().add(new Partners(partnerId[i], numOfLogins[i], partnerName[i], partnerPassword[i], partnerEmail[i]));
    }

    public void readPartnerBooks() {
        for (int i = 0; i < 100; i++)
            partnerBooksField[i] = new String[20];

        for (int i = 0; i < 100; i++)
            listPartnerBooks.add(new ArrayList<>());

        for (int i = 0; i < 100; i++)
            bookId[i] = new Integer[20];

        for (int i = 0; i < 100; i++)
            author[i] = new String[20];

        for (int i = 0; i < 100; i++)
            title[i] = new String[20];

        for (int i = 0; i < 100; i++)
            description[i] = new String[20];

        for (int i = 0; i < 100; i++)
            isLent[i] = new boolean[20];

        for (int i = 0; i < listPartners2.size(); i++) {
            if (!partnerBooksPath[i].isEmpty()) {
                CSVPath = partnerBooksPath[i];
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(CSVPath));
                    while ((lineToRead = bufferedReader.readLine()) != null) {
                        listPartnerBooks.get(i).add(lineToRead);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for (int j = 1; j < listPartnerBooks.get(i).size(); j++)
                    partnerBooksField[j - 1] = listPartnerBooks.get(i).get(j).split("\\s*,\\s*");

                for (int j = 1; j < listPartnerBooks.get(i).size(); j++)
                    bookId[i][j - 1] = Integer.parseInt(partnerBooksField[j - 1][0]);

                for (int j = 1; j < listPartnerBooks.get(i).size(); j++)
                    author[i][j - 1] = partnerBooksField[j - 1][1];

                for (int j = 1; j < listPartnerBooks.get(i).size(); j++)
                    title[i][j - 1] = partnerBooksField[j - 1][2];

                for (int j = 1; j < listPartnerBooks.get(i).size(); j++)
                    description[i][j - 1] = partnerBooksField[j - 1][3];

                for (int j = 1; j < listPartnerBooks.get(i).size(); j++)
                    isLent[i][j - 1] = Boolean.parseBoolean(partnerBooksField[j - 1][4]);

                for (int j = 1; j < listPartnerBooks.get(i).size(); j++)
                    LibrarianService.getPartners().get(i).getPartnerBooks().add(new Books(bookId[i][j - 1], author[i][j - 1], title[i][j - 1], description[i][j - 1], isLent[i][j - 1]));
            }
        }
        for (int j = 0; j < LibrarianService.getPartners().size(); j++)
            LibrarianService.getPartners().set(j, new Partners(partnerId[j], numOfLogins[j], partnerName[j], partnerPassword[j], partnerEmail[j], LibrarianService.getPartners().get(j).getPartnerBooks()));

        for (int i = 0; i < LibrarianService.getPartners().size(); i++)
            PartnerService.getPartnerBooks().get(i).addAll(LibrarianService.getPartners().get(i).getPartnerBooks());
    }
}
