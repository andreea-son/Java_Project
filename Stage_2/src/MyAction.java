public class MyAction {
    private static String dateTime;
    private static String nameOfAction;
    public MyAction(){
        this.dateTime = "";
        this.nameOfAction = "";
    }

    public MyAction(String nameOfAction, String dateTime){
        this.dateTime = dateTime;
        this.nameOfAction = nameOfAction;
    }

    public static String getNameOfAction() {
        return nameOfAction;
    }

    public static String getDateTime() {
        return dateTime;
    }
}
