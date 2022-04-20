import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MyDate {
    private String date;

    MyDate(){
    }

    public boolean isBiggerThan(MyDate secondDate){
        String[] date1 = this.getDate().split("-0|-");
        int year1 = Integer.parseInt(date1[0]);
        int month1 = Integer.parseInt(date1[1]);
        int day1 = Integer.parseInt(date1[2]);

        String[] date2 = secondDate.getDate().split("-0|-");
        int year2 = Integer.parseInt(date2[0]);
        int month2 = Integer.parseInt(date2[1]);
        int day2 = Integer.parseInt(date2[2]);

        LocalDate aux1 = LocalDate.of(year1, month1, day1);
        LocalDate aux2 = LocalDate.of(year2, month2, day2);

        long aux = ChronoUnit.DAYS.between(aux1, aux2);
        return (aux < 0);
    }

    public long differenceInDays(MyDate secondDate){
        String[] date1 = this.getDate().split("-0|-");
        int year1 = Integer.parseInt(date1[0]);
        int month1 = Integer.parseInt(date1[1]);
        int day1 = Integer.parseInt(date1[2]);

        String[] date2 = secondDate.getDate().split("-0|-");
        int year2 = Integer.parseInt(date2[0]);
        int month2 = Integer.parseInt(date2[1]);
        int day2 = Integer.parseInt(date2[2]);

        LocalDate aux1 = LocalDate.of(year1, month1, day1);
        LocalDate aux2 = LocalDate.of(year2, month2, day2);

        return ChronoUnit.DAYS.between(aux1, aux2);
    }

    public MyDate addDays(long days){
        MyDate tempDate = new MyDate();
        tempDate.setDate(this.getDate());
        String[] date1 = tempDate.getDate().split("-0|-");
        int year1 = Integer.parseInt(date1[0]);
        int month1 = Integer.parseInt(date1[1]);
        int day1 = Integer.parseInt(date1[2]);

        tempDate.setDate(LocalDate.of(year1, month1, day1).plusDays(days).toString());
        return tempDate;
    }

    public MyDate addMonths(long n){
        MyDate tempDate = new MyDate();
        tempDate.setDate(this.getDate());
        String[] date1 = tempDate.getDate().split("-0|-");
        int year1 = Integer.parseInt(date1[0]);
        int month1 = Integer.parseInt(date1[1]);
        int day1 = Integer.parseInt(date1[2]);

        tempDate.setDate(LocalDate.of(year1, month1, day1).plusMonths(n).toString());
        return tempDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void print(){
        System.out.print(date);
    }
}
