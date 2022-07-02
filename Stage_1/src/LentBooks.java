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
        if(super.getIsDeleted())
            System.out.println("    (the book was deleted)");
    }

}