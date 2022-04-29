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

    public MyDate getInvoiceDate() {
        return invoiceDate;
    }
}
