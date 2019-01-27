package retailmanager.spyhunter272.com.holder;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
    private boolean tprcharge,isUpdateCustomer,isShowCustomer,isShowtprcharge;
    private double totalInvoice,productTotalAmt,discountAmt,gstAmt;
    private int discount,gst;
    private Calendar calendar = Calendar.getInstance();
    private Context context;
    static final String myFormat = "MM/dd/yy";

    public InvoiceFromHolder(Context context){
        this.context = context;
        SharedPreferences myPreference = PreferenceManager.getDefaultSharedPreferences(context);
        setShowtprcharge(myPreference.getBoolean("tprcharge",true));
        setShowCustomer(myPreference.getBoolean("customer",true));
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        setDateForShow(sdf.format(calendar.getTime()));
    }

    public void onClick(View v){

        switch (v.getId()){

            case R.id.btn_open_dialog_date:

                new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    setDateForShow(sdf.format(calendar.getTime()));

                }, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();

                break;

        }
    }

    public int getGst() {
        return gst;
    }

    public void setGst(int gst) {
        this.gst = gst;
        setTotalInvoice(productTotalAmt);
    }

    public double getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(double discountAmt) {
        this.discountAmt = discountAmt;
    }

    public double getGstAmt() {
        return gstAmt;
    }

    public void setGstAmt(double gstAmt) {
        this.gstAmt = gstAmt;
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

    public double getTotalInvoice() {
        return totalInvoice;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
        setTotalInvoice(productTotalAmt);
    }

    public float getDiscount() {
        return discount;
    }

    public void setTotalInvoice(double productTotalAmt) {
        this.productTotalAmt = productTotalAmt;
        this.discountAmt = ((discount*productTotalAmt)/100);
        this.gstAmt = ((gst*(productTotalAmt - discountAmt))/100);
        this.totalInvoice = Math.round(productTotalAmt - discountAmt + gstAmt);
        this.gstAmt = Math.round(gstAmt);
        discountAmt = Math.round(discountAmt);
        notifyChange();
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

    public void onGstChange(AdapterView<?> parent, View view, int pos, long id){
       switch (pos){
           case 0:
               gst = 0;
               break;
           case 1:
               gst = 5;
               break;
           case 2:
               gst = 12;
               break;
           case 3:
               gst = 18;
               break;
           case 4:
               gst = 28;
               break;
       }
       setTotalInvoice(productTotalAmt);
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
