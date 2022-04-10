import java.util.ArrayList;
public class Sections extends Books{
    private int sectionId;
    private String sectionName;
    private ArrayList<Books> sectionBooks = new ArrayList<Books>();
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

    public void print(){
        System.out.print("section id: ");
        System.out.println(sectionId);
        System.out.print("    the name of the section: ");
        System.out.println(sectionName);
    }
}
