import java.util.ArrayList;
public class Users {
    private String userName;
    private String userEmail;
    private String userPassword;
    private int userId;
    private ArrayList<LendedBooks> userLendedBooks = new ArrayList<LendedBooks>();
    public Users(int userId, String userName, String userPassword, String userEmail, ArrayList<LendedBooks> userLendedBooks){
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userLendedBooks = userLendedBooks;
    }
    public Users(int userId, String userName, String userPassword, String userEmail){
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
    }

    public Users(){
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public ArrayList<LendedBooks> getUserLendedBooks() {
        return userLendedBooks;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void print(){
        System.out.print("user id: ");
        System.out.println(userId);
        System.out.print("    the name of the user: ");
        System.out.println(userName);
        System.out.print("    the email of the user: ");
        System.out.println(userEmail);
    }

}
