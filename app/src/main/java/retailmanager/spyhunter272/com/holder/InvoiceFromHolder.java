package retailmanager.spyhunter272.com.holder;

import android.app.DatePickerDialog;
import android.content.Context;
import android.databinding.BaseObservable;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retailmanager.spyhunter272.com.R;
import retailmanager.spyhunter272.com.room.table.Customer;

public class InvoiceFromHolder extends BaseObservable {

    private String description , dateForShow ;

    private Customer customer;
    private int paymethord;
    private boolean tprcharge,isUpdateCustomer,isShowCustomer,isShowtprcharge,isSHowDescription;

    private double totalInvoice,totalWithDiscount;
    private int discount;
    private Calendar calendar = Calendar.getInstance();

    private Context context;
    static final String myFormat = "MM/dd/yy"; //In which you need put here


    public InvoiceFromHolder(Context context){
        this.context = context;

        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        setDateForShow(sdf.format(calendar.getTime()));

    }


    public void onClick(View v){

        switch (v.getId()){

            case R.id.btn_open_dialog_date:

                new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        setDateForShow(sdf.format(calendar.getTime()));

                    }
                }, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();

                break;

        }
    }

    public boolean isShowCustomer() {
        return isShowCustomer;
    }

    public void setShowCustomer(boolean showCustomer) {
        isShowCustomer = showCustomer;
    }

    public boolean isShowtprcharge() {
        return isShowtprcharge;
    }

    public void setShowtprcharge(boolean showtprcharge) {
        isShowtprcharge = showtprcharge;
    }

    public boolean isSHowDescription() {
        return isSHowDescription;
    }

    public void setSHowDescription(boolean SHowDescription) {
        isSHowDescription = SHowDescription;
    }

    public Calendar getCalendar() {
        return calendar;
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

    public void setDiscount(int discount) {
        this.discount = discount;
        this.totalWithDiscount = totalInvoice - ((discount*totalInvoice)/100);
        notifyChange();

    }

    public float getDiscount() {
        return discount;
    }

    public void setTotalInvoice(double totalInvoice) {
        this.totalInvoice =Math.round( totalInvoice);
        this.totalWithDiscount = Math.round(totalInvoice - ((discount*totalInvoice)/100));
        notifyChange();
    }


    public void onChangeDiscount(){

    }

    public int getPaymethord() {
        return paymethord;
    }

    public String getPaymethordString(){
        String[] ptype=  context.getResources().getStringArray(R.array.ptype);

        if(paymethord==0){
            return  "";
        }

        return ptype[paymethord];

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
