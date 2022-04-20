abstract class TypeOfUser {
    private String name;
    private String password;
    private String email;
    private int id;
    private boolean isDeleted;

    public TypeOfUser(int id, String name, String password, String email, boolean isDeleted){
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = id;
        this.isDeleted = isDeleted;
    }

    public TypeOfUser(){

    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public abstract void print();
}
