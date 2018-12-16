package retailmanager.spyhunter272.in.retailmanager.model;

import android.app.DatePickerDialog;
import android.databinding.BaseObservable;

import java.util.Calendar;

import retailmanager.spyhunter272.in.retailmanager.R;
import retailmanager.spyhunter272.in.retailmanager.room.table.Customer;

public class InvoiceFromHolder extends BaseObservable {

    private String description , dateForShow ;

    private Customer customer;
    private int paymethord;
    private boolean tprcharge,isUpdateCustomer;

    private double totalInvoice,totalWithDiscount;
    private double discount;

    public InvoiceFromHolder(){
        Calendar c = Calendar.getInstance();

        int mYear, mMonth, mDay, mHour, mMinute;

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        dateForShow = mDay + "-" + mMonth+ "-"+mYear ;

    }

    public void setUpdateCustomer(boolean updateCustomer) {
        isUpdateCustomer = updateCustomer;
    }

    public boolean isUpdateCustomer() {
        return isUpdateCustomer;
    }

    public String getDescription() {
        return description;
    }


    public double getTotalWithDiscount() {
        return totalWithDiscount;
    }

    public void setTotalWithDiscount(double totalWithDiscount) {
        this.totalWithDiscount = totalWithDiscount;
    }

    public double getTotalInvoice() {
        return totalInvoice;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
        this.totalInvoice = totalInvoice;
        this.totalWithDiscount = totalInvoice - ((discount*totalInvoice)/100);
        notifyChange();

    }

    public double getDiscount() {
        return discount;
    }

    public void setTotalInvoice(double totalInvoice) {
        this.totalInvoice = totalInvoice;
        this.totalWithDiscount = totalInvoice - ((discount*totalInvoice)/100);
        notifyChange();
    }



    public int getPaymethord() {
        return paymethord;
    }

    public boolean isTprcharge() {
        return tprcharge;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyChange();
    }

    public void setDateForShow(String dateForShow) {
        this.dateForShow = dateForShow;
        notifyChange();
    }

    public void setPaymethord(int paymethord) {
        this.paymethord = paymethord;
    }

    public void setTprcharge(boolean tprcharge) {
        this.tprcharge = tprcharge;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        notifyChange();
    }

    public String getCustomer() {
        if(customer!=null)
            return customer.toString();
        else
            return new Customer().toString();
    }

    public Customer getCustomerObj(){
        if(customer!=null)
            return customer;
        else
            return new Customer();
    }

    public String getDateForShow() {
        return dateForShow;
    }

}
