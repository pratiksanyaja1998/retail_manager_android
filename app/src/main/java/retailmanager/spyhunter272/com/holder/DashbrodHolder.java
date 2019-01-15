package retailmanager.spyhunter272.com.holder;

import android.databinding.BaseObservable;
import android.util.Log;

import java.util.Calendar;

import static retailmanager.spyhunter272.com.utils.StaticInfoUtils.MONTH_LIST;

public class DashbrodHolder extends BaseObservable {

    private String dateString;
    private String productCount;
    private String invoiceCount;
    private String customerCount;
    int mYear, mMonth;
    private String retailName;

private float totalIncome;


    public DashbrodHolder(){
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH)+1;

        Log.e("post","dashbord "+mMonth);

        dateString =  MONTH_LIST[mMonth-1] + " "+mYear;
    }


    public String getRetailName() {
        return retailName;
    }

    public void setRetailName(String retailName) {
        this.retailName = retailName;
        notifyChange();
    }

    public float getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(float totalIncome) {
        this.totalIncome = totalIncome;
        notifyChange();
    }

    public int getmYear() {
        return mYear;
    }

    public void setmYear(int mYear) {
        this.mYear = mYear;
    }

    public int getmMonth() {
        return mMonth;
    }

    public void setmMonth(int mMonth) {
        this.mMonth = mMonth;
    }

    public void setMonthYear(int month,int year){
        this.mMonth = month-1;
        this.mYear = year;

        dateString =  MONTH_LIST[mMonth] + " "+mYear;
        notifyChange();
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
        notifyChange();
    }

    public String getProductCount() {
        return productCount;
    }

    public void setProductCount(String productCount) {
        this.productCount = productCount;
        notifyChange();
    }

    public String getInvoiceCount() {
        return invoiceCount;
    }

    public void setInvoiceCount(String invoiceCount) {
        this.invoiceCount = invoiceCount;
        notifyChange();
    }

    public String getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(String customerCount) {
        this.customerCount = customerCount;
        notifyChange();
    }

}
