public class _10PercentDiscount {
    private String code;
    private MyDate expirationDate = new MyDate();
    private int userId;
    private boolean isUsed;

    public _10PercentDiscount(String code, String expirationDate, int userId, boolean isUsed){
        this.code = code;
        this.userId = userId;
        this.expirationDate.setDate(expirationDate);
        this.isUsed = isUsed;
    }

    public _10PercentDiscount(){
    }

    public String getCode() {
        return code;
    }

    public MyDate getExpirationDate() {
        return expirationDate;
    }

    public boolean getIsUsed() {
        return isUsed;
    }

    public int getUserId() {
        return userId;
    }

    public void setIsUsed(boolean used) {
        isUsed = used;
    }

    public void print(){
        System.out.println("    the code: " + code);
        System.out.print("    the expiration date: ");
        expirationDate.print();
        System.out.print("\n");
    }
}
