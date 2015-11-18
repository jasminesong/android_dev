package ruoyun.brandeis.edu.expenselog;
import java.util.Date;
/**
 * Created by reallifejasmine on 11/17/15.
 */
public class ExpenseLogEntryData {

    private String heading;
    private String note;
    private String date;

    public ExpenseLogEntryData(String heading,String note){
        this.heading = heading;
        this.note = note;
        this.date = (new Date()).toString();

    }

    //getters & setters


    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
