package retailmanager.spyhunter272.in.retailmanager.room.table;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;

@Entity(tableName = "invoice")
public class Invoice extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int customer;
    private String name;
    private int gsttype; //  for  0 None / 1 GST / 2 IGST
    private int gst; // 0 5 12 18 28
    private double total;
    private float discount;
    private String desciption;
    private boolean tprchage;
    private String paymentMethrd;
    private int dd;
    private int mm;
    private int yyyy;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getCustomer() {
        return customer;
    }

    public int getGsttype() {
        return gsttype;
    }

    public int getGst() {
        return gst;
    }

    public double getTotal() {
        return total;
    }

    public float getDiscount() {
        return discount;
    }

    public String getDesciption() {
        return desciption;
    }

    public boolean isTprchage() {
        return tprchage;
    }

    public String getPaymentMethrd() {
        return paymentMethrd;
    }

    public int getDd() {
        return dd;
    }

    public int getMm() {
        return mm;
    }

    public int getYyyy() {
        return yyyy;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setCustomer(int customer) {
        this.customer = customer;
    }

    public void setGsttype(int gsttype) {
        this.gsttype = gsttype;
    }

    public void setGst(int gst) {
        this.gst = gst;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public void setTprchage(boolean tprchage) {
        this.tprchage = tprchage;
    }

    public void setPaymentMethrd(String paymentMethrd) {
        this.paymentMethrd = paymentMethrd;
    }

    public void setDd(int dd) {
        this.dd = dd;
    }

    public void setMm(int mm) {
        this.mm = mm;
    }

    public void setYyyy(int yyyy) {
        this.yyyy = yyyy;
    }
}
