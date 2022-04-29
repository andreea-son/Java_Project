public class Partners extends TypeOfUser{
    private int numOfLogins;

    public Partners(int partnerId, int numOfLogins, String partnerName, String partnerPassword, String partnerEmail, boolean isDeleted){
        super(partnerId, partnerName, partnerPassword, partnerEmail, isDeleted);
        this.numOfLogins = numOfLogins;
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

    public boolean getIsDeleted() {
        return getDeleted();
    }

    public void setIsDeleted(boolean isDeleted) {
         setDeleted(isDeleted);
    }
}
