import java.util.ArrayList;
public class Users extends TypeOfUser{
    private ArrayList<LentBooks> userLentBooks = new ArrayList<>();
    private int numOfLogins;

    public Users(int userId, String userName, String userPassword, String userEmail, ArrayList<LentBooks> userLentBooks){
        super(userId, userName, userPassword, userEmail);
        this.userLentBooks = userLentBooks;
    }

    public Users(int userId, String userName, String userPassword, String userEmail){
        super(userId, userName, userPassword, userEmail);
    }

    public Users(){
    }

    public String getUserName() {
        return getName();
    }

    public String getUserPassword() {
        return getPassword();
    }

    public int getUserId() {
        return getId();
    }

    public String getUserEmail() {
        return getEmail();
    }

    public ArrayList<LentBooks> getUserLentBooks() {
        return userLentBooks;
    }

    public int getNumOfLogins() {
        return numOfLogins;
    }

    public void setNumOfLogins(int numOfLogins) {
        this.numOfLogins = numOfLogins;
    }

    public void setUserPassword(String userPassword){
        setPassword(userPassword);
    }

    public void print(){
        System.out.print("user ID: ");
        System.out.println(getId());
        System.out.print("    the name of the user: ");
        System.out.println(getName());
        System.out.print("    the email of the user: ");
        System.out.println(getEmail());
    }

}
