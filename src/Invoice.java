public class Invoice{
    private int invoiceId;
    private String invoiceDate;
    private LentBooks returnedBook = new LentBooks();
    private Users userThatLent = new Users();
    private String cardOrCash;
    public Invoice(int invoiceId, String invoiceDate, LentBooks returnedBook, Users userThatLent, String cardOrCash){
        this.invoiceId = invoiceId;
        this.invoiceDate = invoiceDate;
        this.returnedBook = returnedBook;
        this.userThatLent = userThatLent;
        this.cardOrCash = cardOrCash;
    }

    public Invoice(){

    }

    public void print(){
        System.out.println("invoice id: " + invoiceId);
        System.out.println("lent book: ");
        System.out.println("    book id: " + returnedBook.getBookId());
        System.out.println("    author: " + returnedBook.getAuthor());
        System.out.println("    title: " + returnedBook.getTitle());
        System.out.println("    description: " + returnedBook.getDescription());
        System.out.println("    issued date: " + returnedBook.getIssuedDate());
        if(returnedBook.getExceededDays() > 0) {
            System.out.println("    return date: " + returnedBook.getReturnDate() + " + exceeded with " + returnedBook.getExceededDays() + " days");
            System.out.println("    additional price: 3 x " + returnedBook.getExceededDays() + " = " + returnedBook.getExceededPrice() + " ron");
            System.out.println("user that lent the book: ");
            System.out.println("    user id: " + userThatLent.getUserId());
            System.out.println("    the name of the user: " + userThatLent.getUserName());
            System.out.println("    payed with: " + cardOrCash);
        }
        else {
            System.out.println("    return date: " + returnedBook.getReturnDate());
            System.out.println("    price: 2 x " + returnedBook.getDaysFromIssue() + " = " + returnedBook.getPrice() + " ron");
            System.out.println("user that lent the book: ");
            System.out.println("    user id: " + userThatLent.getUserId());
            System.out.println("    the name of the user: " + userThatLent.getUserName());
            System.out.println("    payed with: " + cardOrCash);
            System.out.println("Note: For every day that exceeds the return day you have to pay an additional 3 ron");
        }
    }
}
