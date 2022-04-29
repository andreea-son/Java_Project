public class LentBooks extends Books{
    private long daysFromIssue;
    private MyDate issuedDate = new MyDate();
    private float price;
    private float exceededPrice;
    private MyDate returnDate = new MyDate();
    private long exceededDays;
    private int userId;

    public LentBooks(int bookId, boolean isDeleted, String author, String title, String description, String issuedDate, long daysFromIssue, boolean isLent, int sectionId, int partnerId, int userId){
        super(bookId, author, title, description, isLent, isDeleted, sectionId, partnerId);
        this.daysFromIssue = daysFromIssue;
        this.price = 2 * daysFromIssue;
        this.issuedDate.setDate(issuedDate);

        this.exceededDays = 0;
        this.exceededPrice = 0;

        this.returnDate.setDate(this.issuedDate.addDays(daysFromIssue).getDate());

        this.userId = userId;
    }

    public LentBooks(){
    }

    public int getUserId() {
        return userId;
    }

    public MyDate getIssuedDate() {
        return issuedDate;
    }

    public MyDate getReturnDate() {
        return returnDate;
    }

    public float getPrice() {
        return price;
    }

    public long getDaysFromIssue() {
        return daysFromIssue;
    }

    public long getExceededDays() {
        return exceededDays;
    }

    public float getExceededPrice() {
        return exceededPrice;
    }

    public void setExceededPrice1(long exceededPrice) {
        this.exceededPrice = exceededPrice;
    }

    public void setExceededDays1(long exceededDays) {
        this.exceededDays = exceededDays;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate.setDate(returnDate);
    }

    public void setPrice(float price) {
        this.price = price;
    }
}