import java.util.ArrayList;
public class Sections{
    private int sectionId;
    private String sectionName;
    private ArrayList<Books> sectionBooks = new ArrayList<>();
    public Sections(int sectionId, String sectionName, ArrayList<Books> sectionBooks){
        this.sectionName = sectionName;
        this.sectionBooks = sectionBooks;
        this.sectionId = sectionId;
    }
    public Sections(int sectionId, String sectionName){
        this.sectionName = sectionName;
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public ArrayList<Books> getSectionBooks() {
        return sectionBooks;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void print(){
        System.out.print("section ID: ");
        System.out.println(sectionId);
        System.out.print("    the name of the section: ");
        System.out.println(sectionName);
    }
}
