import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CSVSectionService {
    private String CSVPath;
    private String lineToRead;
    private ArrayList<String> listSection1 = new ArrayList<>();
    private ArrayList<String> listSection2 = new ArrayList<>();
    private static String[][] sectionField = new String[100][];
    private static Integer[] sectionId = new Integer[100];
    private static String[] sectionName = new String[100];
    private static LibrarianService librarian = new LibrarianService();
    private static boolean[] isDeleted = new boolean[100];

    private CSVSectionService() {}

    private static CSVSectionService single_instance = null;

    public static CSVSectionService getInstance() {
        if (single_instance == null)
            single_instance = new CSVSectionService();

        return single_instance;
    }

    public void readSections() {
        for (int i = 0; i < 100; i++)
            sectionField[i] = new String[20];

        CSVPath = "Resources/csv/sections.csv";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(CSVPath));
            while ((lineToRead = bufferedReader.readLine()) != null) {
                listSection1.add(lineToRead);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 1; i < listSection1.size(); i++)
            listSection2.add(listSection1.get(i));

        Collections.sort(listSection2);

        for (int i = 0; i < listSection2.size(); i++)
            sectionField[i] = listSection2.get(i).split("\\s*,\\s*");

        for (int i = 0; i < listSection2.size(); i++)
            sectionId[i] = Integer.parseInt(sectionField[i][0]);

        for (int i = 0; i < listSection2.size(); i++)
            sectionName[i] = sectionField[i][1];

        for (int i = 0; i < listSection2.size(); i++)
            isDeleted[i] = Boolean.parseBoolean(sectionField[i][2]);

        for(int i = 0; i < listSection2.size(); i++)
            librarian.getSections().add(new Sections(sectionId[i], sectionName[i], isDeleted[i]));
    }
}