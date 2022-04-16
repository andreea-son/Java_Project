import java.time.LocalDate;
import java.lang.Integer;
import java.util.Objects;

public class LentBooks extends Books{
    private long daysFromIssue;
    private MyDate issuedDate = new MyDate();
    private float price;
    private float exceededPrice;
    private MyDate returnDate = new MyDate();
    private long exceededDays;
    private boolean isDeleted;

    public LentBooks(int bookId, boolean isDeleted, String author, String title, String description, String issuedDate, long daysFromIssue, boolean isLent){
        super(bookId, author, title, description, isLent);
        this.daysFromIssue = daysFromIssue;
        this.price = 2 * daysFromIssue;
        this.issuedDate.setDate(issuedDate);

        this.exceededDays = 0;
        this.exceededPrice = 0;

        this.returnDate.setDate(this.issuedDate.addDays(daysFromIssue).getDate());

        this.isDeleted = isDeleted;
    }

    public LentBooks(int bookId, boolean isDeleted, String author, String title, String description, String issuedDate, boolean isLent){
        super(bookId, author, title, description, isLent);
        this.price = 0;
        this.issuedDate.setDate(issuedDate);

        this.exceededDays = 0;
        this.exceededPrice = 0;

        this.returnDate.setDate(this.issuedDate.addDays(daysFromIssue).getDate());

        this.isDeleted = isDeleted;
    }

    public LentBooks(){
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

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setExceededPrice1(long exceededPrice) {
        this.exceededPrice = exceededPrice;
    }

    public void setExceededDays1(long exceededDays) {
        this.exceededDays = exceededDays;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate.setDate(returnDate);
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void print(){
        super.print();
        System.out.print("    issued date: ");
        issuedDate.print();
        System.out.print("\n");
        if(exceededDays > 0) {
            System.out.print("    return date: ");
            returnDate.print();
            System.out.print(" + exceeded with " + exceededDays + " days\n");
            System.out.print("    the initial price: ");
            System.out.printf("%.02f", price);
            System.out.print(" ron\n");
            System.out.print("    the added price after exceeding the return date: ");
            System.out.printf("%.02f", exceededPrice);
            System.out.print(" ron\n");
        }
        else{
            System.out.print("    return date: ");
            returnDate.print();
            System.out.print("\n");
            System.out.print("    the price: ");
            System.out.printf("%.02f", price);
            System.out.print(" ron\n");
        }
        if(isDeleted)
            System.out.println("    (the book was deleted)");
    }

}