package ruoyun.brandeis.edu.expenselog;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ruoyun.brandeis.edu.expenselog.ExpenseLogEntryData;

/**
 * Created by reallifejasmine on 11/17/15.
 */
public class ExpenseTrackerAdapter extends BaseAdapter {

    ArrayList<ExpenseLogEntryData> expenseLogEntryDatas = new ArrayList<ExpenseLogEntryData>();
    Activity mContext;

    public ExpenseTrackerAdapter(){

        ExpenseLogEntryData entry1 = new ExpenseLogEntryData("100 dollars","had an dinner");
        ExpenseLogEntryData entry2 = new ExpenseLogEntryData("33 dollars","bought a coat");
        ExpenseLogEntryData entry3 = new ExpenseLogEntryData("777 dollars","boat");


        expenseLogEntryDatas.add(entry1);
        expenseLogEntryDatas.add(entry2);
        expenseLogEntryDatas.add(entry3);

    };

    public void addActivity(Activity activity){
        mContext = activity;
    };

    public void addData(ExpenseLogEntryData myData){
        expenseLogEntryDatas.add(myData);
    };

    @Override
    public int getCount() {
        return expenseLogEntryDatas.size();

    }

    @Override
    public Object getItem(int position) {
        return expenseLogEntryDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View row = inflater.inflate(R.layout.expense_entry, parent, false);
            TextView heading, note, myDate;
            heading = (TextView) row.findViewById(R.id.textView2);
            note = (TextView) row.findViewById(R.id.textView3);
            myDate = (TextView) row.findViewById(R.id.textView4);
            heading.setText(expenseLogEntryDatas.get(position).getHeading());
            note.setText(expenseLogEntryDatas.get(position).getNote());
            myDate.setText(expenseLogEntryDatas.get(position).getDate());

            return (row);

    }
}
