public class Books{
    private String author;
    private String title;
    private String description;
    private int bookId;
    private boolean isLent;
    public Books(){}
    public Books(int bookId, String author, String title, String description, boolean isLent){
        this.author = author;
        this.title = title;
        this.description = description;
        this.bookId = bookId;
        this.isLent = isLent;
    }

    public boolean getIsLent() {
        return isLent;
    }

    public void setIsLent(boolean lent) {
        isLent = lent;
    }

    public int getBookId() {
        return bookId;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void print(){
        System.out.println("book id: " + bookId);
        System.out.println("    author: " + author);
        System.out.println("    title: " + title);
        System.out.println("    description: " + description);
    }
}
