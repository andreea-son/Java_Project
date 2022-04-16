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
    private ArrayList<ArrayList<String>> listSectionBooks = new ArrayList<>(100);
    private static String[][] sectionField = new String[100][];
    private static String[][] sectionBooksField = new String[100][];
    private static Integer[] sectionId = new Integer[100];
    private static String[] sectionName = new String[100];
    private static String[] sectionBooksPath = new String[100];
    private static Integer[][] bookId = new Integer[100][];
    private static String[][] author = new String[100][];
    private static String[][] title = new String[100][];
    private static String[][] description = new String[100][];
    private static boolean[][] isLent = new boolean[100][];

    private static CSVSectionService csvSectionService = new CSVSectionService();

    private CSVSectionService() {}

    public static CSVSectionService getInstance() {
        return csvSectionService;
    }

    public void readSections() {
        for (int i = 0; i < 100; i++)
            sectionField[i] = new String[20];

        CSVPath = "../csv/sections.csv";
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
            if (!sectionField[i][2].equals("null"))
                sectionBooksPath[i] = sectionField[i][2];
            else {
                sectionBooksPath[i] = "";
            }
        for(int i = 0; i < listSection2.size(); i++)
            LibrarianService.getSections().add(new Sections(sectionId[i], sectionName[i]));
    }

    public void readSectionBooks() {
        for (int i = 0; i < 100; i++)
            sectionBooksField[i] = new String[20];

        for (int i = 0; i < 100; i++)
            listSectionBooks.add(new ArrayList<>());

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

        for (int i = 0; i < listSection2.size(); i++) {
            if (!sectionBooksPath[i].isEmpty()) {
                CSVPath = sectionBooksPath[i];
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(CSVPath));
                    while ((lineToRead = bufferedReader.readLine()) != null) {
                        listSectionBooks.get(i).add(lineToRead);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for (int j = 1; j < listSectionBooks.get(i).size(); j++)
                    sectionBooksField[j - 1] = listSectionBooks.get(i).get(j).split("\\s*,\\s*");

                for (int j = 1; j < listSectionBooks.get(i).size(); j++)
                    bookId[i][j - 1] = Integer.parseInt(sectionBooksField[j - 1][0]);

                for (int j = 1; j < listSectionBooks.get(i).size(); j++)
                    author[i][j - 1] = sectionBooksField[j - 1][1];

                for (int j = 1; j < listSectionBooks.get(i).size(); j++)
                    title[i][j - 1] = sectionBooksField[j - 1][2];

                for (int j = 1; j < listSectionBooks.get(i).size(); j++)
                    description[i][j - 1] = sectionBooksField[j - 1][3];

                for (int j = 1; j < listSectionBooks.get(i).size(); j++)
                    isLent[i][j - 1] = Boolean.parseBoolean(sectionBooksField[j - 1][4]);

                for (int j = 1; j < listSectionBooks.get(i).size(); j++)
                    LibrarianService.getSections().get(i).getSectionBooks().add(new Books(bookId[i][j - 1], author[i][j - 1], title[i][j - 1], description[i][j - 1], isLent[i][j - 1]));
            }
        }
        for (int j = 0; j < LibrarianService.getSections().size(); j++)
            LibrarianService.getSections().set(j, new Sections(sectionId[j], sectionName[j], LibrarianService.getSections().get(j).getSectionBooks()));

        for (int j = 0; j < LibrarianService.getSections().size(); j++)
            PartnerService.getSectionBooks().get(j).addAll(LibrarianService.getSections().get(j).getSectionBooks());
    }
}