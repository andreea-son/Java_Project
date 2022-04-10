public class Test {
    public static void split(){
        String partnerEmail = "ana1@yahoo.com";
        String password[] = partnerEmail.split("@");
        String partnerPassword = password[0];
        System.out.println(partnerPassword);
    }
    public static void main(String[] args) {
        split();
    }
}
