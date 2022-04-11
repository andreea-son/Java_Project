import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Books)) return false;
        Books books = (Books) o;
        return bookId == books.bookId && isLent == books.isLent && author.equals(books.author) && title.equals(books.title) && description.equals(books.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, title, description, bookId, isLent);
    }

    public void print(){
        System.out.println("book id: " + bookId);
        System.out.println("    author: " + author);
        System.out.println("    title: " + title);
        System.out.println("    description: " + description);
    }
}
