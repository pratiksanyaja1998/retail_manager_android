package retailmanager.spyhunter272.in.retailmanager.model;

import android.app.DatePickerDialog;
import android.databinding.BaseObservable;

import java.util.Calendar;

import retailmanager.spyhunter272.in.retailmanager.R;
import retailmanager.spyhunter272.in.retailmanager.room.table.Customer;

public class InvoiceFromHolder extends BaseObservable {

    String description , dateForShow ;

    Customer customer;
    int paymethord;
    boolean tprcharge;

    public InvoiceFromHolder(){
        Calendar c = Calendar.getInstance();

        int mYear, mMonth, mDay, mHour, mMinute;

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        dateForShow = mDay + "-" + mMonth+ "-"+mYear ;


    }

    public String getDescription() {
        return description;
    }

    public int getPaymethord() {
        return paymethord;
    }

    public boolean isTprcharge() {
        return tprcharge;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateForShow(String dateForShow) {
        this.dateForShow = dateForShow;
    }

    public void setPaymethord(int paymethord) {
        this.paymethord = paymethord;
    }

    public void setTprcharge(boolean tprcharge) {
        this.tprcharge = tprcharge;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getCustomer() {
        if(customer!=null)
        return customer.toString()+"";
        else
            return "";
    }

    public String getDateForShow() {
        return dateForShow;
    }

}
