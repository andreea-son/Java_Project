abstract class TypeOfUser {
    private String name;
    private String password;
    private String email;
    private int id;

    public TypeOfUser(int id, String name, String password, String email){
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = id;
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

    public void setId(int id) {
        this.id = id;
    }

    public abstract void print();


}
