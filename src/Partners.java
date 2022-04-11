import java.util.ArrayList;
public class Partners extends TypeOfUser{
    private ArrayList<Books> partnerBooks = new ArrayList<>();
    private int numOfLogins;

    public Partners(int partnerId, int numOfLogins, String partnerName, String partnerPassword, String partnerEmail, ArrayList<Books> partnerBooks){
        super(partnerId, partnerName, partnerPassword, partnerEmail);
        this.partnerBooks = partnerBooks;
        this.numOfLogins = numOfLogins;
    }

    public Partners(int partnerId, int numOfLogins, String partnerName, String partnerPassword, String partnerEmail){
        super(partnerId, partnerName, partnerPassword, partnerEmail);
        this.numOfLogins = numOfLogins;
    }

    public ArrayList<Books> getPartnerBooks() {
        return partnerBooks;
    }

    public String getPartnerName() {
        return getName();
    }

    public String getPartnerPassword() {
        return getPassword();
    }

    public String getPartnerEmail() {
        return getEmail();
    }

    public int getPartnerId() {
        return getId();
    }

    public int getNumOfLogins() {
        return numOfLogins;
    }

    public void setNumOfLogins(int numOfLogins) {
        this.numOfLogins = numOfLogins;
    }

    public void setPartnerPassword(String partnerPassword) {
        setPassword(partnerPassword);
    }

    public void print(){
        System.out.print("partner ID: ");
        System.out.println(getId());
        System.out.print("    partner's name: ");
        System.out.println(getName());
        System.out.print("    partner's email: ");
        System.out.println(getEmail());
    }

}
