import java.util.ArrayList;
public class Partners {
    private String partnerName;
    private String partnerPassword;
    private String partnerEmail;
    private int partnerId;
    private ArrayList<Books> partnerBooks = new ArrayList<Books>();
    private int numOfLogins;

    public Partners(int partnerId, int numOfLogins, String partnerName, String partnerPassword, String partnerEmail, ArrayList<Books> partnerBooks){
        this.partnerId = partnerId;
        this.partnerEmail = partnerEmail;
        this.partnerBooks = partnerBooks;
        this.partnerName = partnerName;
        this.partnerPassword = partnerPassword;
        this.numOfLogins = numOfLogins;
    }

    public Partners(int partnerId, int numOfLogins, String partnerName, String partnerPassword, String partnerEmail){
        this.partnerId = partnerId;
        this.partnerEmail = partnerEmail;
        this.partnerName = partnerName;
        this.partnerPassword = partnerPassword;
    }

    public Partners(){
    }

    public ArrayList<Books> getPartnerBooks() {
        return partnerBooks;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public String getPartnerPassword() {
        return partnerPassword;
    }

    public String getPartnerEmail() {
        return partnerEmail;
    }

    public int getPartnerId() {
        return partnerId;
    }

    public int getNumOfLogins() {
        return numOfLogins;
    }

    public void setNumOfLogins(int numOfLogins) {
        this.numOfLogins = numOfLogins;
    }

    public void setPartnerPassword(String partnerPassword) {
        this.partnerPassword = partnerPassword;
    }

    public void print(){
        System.out.print("partner id: ");
        System.out.println(partnerId);
        System.out.print("    partner's name: ");
        System.out.println(partnerName);
        System.out.print("    partner's email: ");
        System.out.println(partnerEmail);
    }

}
