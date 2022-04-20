public class Invoice{
    private int invoiceId;
    private MyDate invoiceDate = new MyDate();
    private int userId;
    private String cardOrCash;
    private boolean discount;

    public Invoice(int invoiceId, String invoiceDate, int userId, String cardOrCash, boolean discount){
        this.invoiceId = invoiceId;
        this.invoiceDate.setDate(invoiceDate);
        this.userId = userId;
        this.cardOrCash = cardOrCash;
        this.discount = discount;
    }

    public void print(){
        LentBooks book = new LentBooks();
        Users user = new Users();
        String tempDate;

        for(int i = 0; i < LibrarianService.getUserLentBooks().get(userId - 1).size(); i++) {
            if (LibrarianService.getUserLentBooks().get(userId - 1).get(i).getIsLent() && LibrarianService.getUserLentBooks().get(userId - 1).get(i).getIssuedDate().getDate().equals(invoiceDate.getDate()))
                book = LibrarianService.getUserLentBooks().get(userId - 1).get(i);
            else if (!LibrarianService.getUserLentBooks().get(userId - 1).get(i).getIsLent()) {
                tempDate = LibrarianService.getUserLentBooks().get(userId - 1).get(i).getReturnDate().addDays(LibrarianService.getUserLentBooks().get(userId - 1).get(i).getExceededDays()).getDate();
                if (tempDate.equals(invoiceDate.getDate()))
                    book = LibrarianService.getUserLentBooks().get(userId - 1).get(i);
            }
        }

        for(int i = 0; i < LibrarianService.getUsers().size(); i++)
            if(LibrarianService.getUsers().get(i).getUserId() == userId)
                user = LibrarianService.getUsers().get(i);

        System.out.println("invoice ID: " + invoiceId);
        System.out.print("invoice date: ");
        invoiceDate.print();
        System.out.print("\n");
        System.out.println("lent book: ");
        System.out.println("    book ID: " + book.getBookId());
        System.out.println("    author: " + book.getAuthor());
        System.out.println("    title: " + book.getTitle());
        System.out.println("    description: " + book.getDescription());
        System.out.print("    issued date: ");
        book.getIssuedDate().print();
        System.out.print("\n");
        if(book.getExceededDays() > 0) {
            System.out.print("    return date: ");
            book.getReturnDate().print();
            System.out.print(" + exceeded with " + book.getExceededDays() + " days\n");
            System.out.print("    additional price: 3.00 x " + book.getExceededDays() + " = ");
            System.out.printf("%.02f", book.getExceededPrice());
            System.out.print(" ron\n");
            System.out.println("user that lent the book: ");
            System.out.println("    user ID: " + user.getUserId());
            System.out.println("    the name of the user: " + user.getUserName());
            System.out.println("    payed with: " + cardOrCash);
        }
        else {
            System.out.print("    return date: ");
            book.getReturnDate().print();
            System.out.print("\n");
            if(discount) {
                float discountPrice;
                discountPrice = book.getPrice() - book.getPrice() / 10;
                System.out.print("    price: 2.00 x " + book.getDaysFromIssue() + " = ");
                System.out.printf("%.02f", book.getPrice());
                System.out.print(" ron\n");
                System.out.println("    discount: 10%");
                System.out.print("    price after discount: ");
                System.out.printf("%.02f", discountPrice);
                System.out.print(" ron\n");
            }
            else {
                System.out.print("    price: 2.00 x " + book.getDaysFromIssue() + " = ");
                System.out.printf("%.02f", book.getPrice());
                System.out.print(" ron\n");
            }
            System.out.println("user that lent the book: ");
            System.out.println("    user ID: " + userId);
            System.out.println("    the name of the user: " + user.getUserName());
            System.out.println("    payed with: " + cardOrCash);
            System.out.println("Note: For every day that exceeds the return day you have to pay an additional 3 ron");
        }
    }
}
