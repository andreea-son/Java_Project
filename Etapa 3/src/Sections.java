public class Sections{
    private int sectionId;
    private String sectionName;
    private boolean isDeleted;
    public Sections(int sectionId, String sectionName, boolean isDeleted){
        this.sectionName = sectionName;
        this.sectionId = sectionId;
        this.isDeleted = isDeleted;
    }

    public String getSectionName() {
        return sectionName;
    }

    public int getSectionId() {
        return sectionId;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
