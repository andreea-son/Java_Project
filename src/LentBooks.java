import java.time.LocalDate;
import java.lang.Integer;
public class LentBooks extends Books{
    private long daysFromIssue;
    private String issuedDate;
    private long price;
    private long exceededPrice;
    private String returnDate;
    private long exceededDays;

    public LentBooks(int bookId, String author, String title, String description, String issuedDate, long daysFromIssue, boolean isLent){
        super(bookId, author, title, description, isLent);
        this.daysFromIssue = daysFromIssue;
        this.price = 2 * daysFromIssue;
        this.issuedDate = issuedDate;

        String date[] = this.issuedDate.split("-0|-");
        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);

        LocalDate aux1 = LocalDate.of(year, month, day).plusDays(daysFromIssue);

        this.exceededDays = 0;
        this.exceededPrice = 0;

        this.returnDate = aux1.toString();
    }

    public LentBooks(){
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public long getPrice() {
        return price;
    }

    public long getDaysFromIssue() {
        return daysFromIssue;
    }

    public long getExceededDays() {
        return exceededDays;
    }

    public long getExceededPrice() {
        return exceededPrice;
    }

    public void setExceededPrice1(long exceededPrice) {
        this.exceededPrice = exceededPrice;
    }

    public void setExceededDays1(long exceededDays) {
        this.exceededDays = exceededDays;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public void print(){
        super.print();
        System.out.println("    the issued date of the book: " + issuedDate);
        System.out.println("    the book should be returned before: " + returnDate);
        if(exceededDays > 0) {
            System.out.println("    the initial price: " + price + " ron");
            System.out.println("    the added price after exceeding the return date: " + exceededPrice + " ron");
        }
        else
            System.out.println("    price: " + price + " ron");
    }

}