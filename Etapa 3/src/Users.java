public class Users extends TypeOfUser{
    private int numOfLogins;

    public Users(int userId, int numOfLogins, String userName, String userPassword, String userEmail, boolean isDeleted){
        super(userId, userName, userPassword, userEmail, isDeleted);
        this.numOfLogins = numOfLogins;
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

    public int getNumOfLogins() {
        return numOfLogins;
    }

    public void setNumOfLogins(int numOfLogins) {
        this.numOfLogins = numOfLogins;
    }

    public void setUserPassword(String userPassword){
        setPassword(userPassword);
    }

    public boolean getIsDeleted() {
        return getDeleted();
    }

    public void setIsDeleted(boolean isDeleted) {
        setDeleted(isDeleted);
    }
}
