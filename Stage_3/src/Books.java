import java.util.Objects;

public class Books{
    private String author;
    private String title;
    private String description;
    private int bookId;
    private int partnerId;
    private int sectionId;
    private boolean isLent;
    private boolean isDeleted;
    public Books(){}
    public Books(int bookId, String author, String title, String description, boolean isLent, boolean isDeleted, int sectionId, int partnerId){
        this.author = author;
        this.title = title;
        this.description = description;
        this.bookId = bookId;
        this.isLent = isLent;
        this.partnerId = partnerId;
        this.sectionId = sectionId;
        this.isDeleted = isDeleted;
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

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public int getPartnerId() {
        return partnerId;
    }

    public int getSectionId() {
        return sectionId;
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
}
