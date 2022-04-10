public class Books{
    private String author;
    private String title;
    private String description;
    private int bookId;
    private boolean isLended;
    public Books(){}
    public Books(int bookId, String author, String title, String description, boolean isLended){
        this.author = author;
        this.title = title;
        this.description = description;
        this.bookId = bookId;
        this.isLended = isLended;
    }
    public boolean getIsLended() {
        return isLended;
    }

    public void setIsLended(boolean lended) {
        isLended = lended;
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
