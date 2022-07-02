import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MyLastDate {
    private MyDate myLastDate = new MyDate();
    private ArrayList <String> date = new ArrayList<>();
    private GetConnection conn = GetConnection.getInstance();

    public MyLastDate(){}

    public MyDate getLastDate() throws SQLException {
        MyDate tempDate = new MyDate();
        Connection connection = conn.getConnection();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT INVOICE_DATE FROM INVOICE");
        while(result.next()){
            date.add(result.getString(1));
        }
        statement.close();
        myLastDate.setDate(date.get(0));
        for (int i = 1; i < date.size(); i++){
            tempDate.setDate(date.get(i));
            if(tempDate.isBiggerThan(myLastDate))
                myLastDate.setDate(tempDate.getDate());
        }
        return myLastDate;
    }
}
