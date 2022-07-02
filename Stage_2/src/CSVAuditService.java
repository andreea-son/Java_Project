import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
public class CSVAuditService {

    private static FileWriter csvAuditFile;
    private static PrintWriter out;
    private static LibrarianService librarian = new LibrarianService();

    public CSVAuditService(){
        try {
            File file = new File("Resources/csv/audit.csv");

            csvAuditFile = new FileWriter(file);

            out = new PrintWriter(csvAuditFile);

            out.printf("%s,%s\n", "name_of_action", "timestamp");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateCSV(){
        out.printf("%s,%s\n",librarian.getActions1().get(LibrarianService.getActions1().size() - 1).getNameOfAction(), librarian.getActions1().get(LibrarianService.getActions1().size() - 1).getDateTime());
    }

    public void closeFile(){
        try {
            csvAuditFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
