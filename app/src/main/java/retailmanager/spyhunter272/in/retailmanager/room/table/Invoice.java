package retailmanager.spyhunter272.in.retailmanager.room.table;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;

import java.util.List;

@Entity(tableName = "invoice")
public class Invoice extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long customerId;
    private String name;
    private String mobile;
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

    @Ignore
    private List<Product> productList;
    @Ignore List<InvoiceProduct> invoiceProducts;

    @Ignore

    Customer customer;


    public Customer getCustomer() {
        return customer;
    }


    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<InvoiceProduct> getInvoiceProducts() {
        return invoiceProducts;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setInvoiceProducts(List<InvoiceProduct> invoiceProducts) {
        this.invoiceProducts = invoiceProducts;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public long getCustomerId() {
        return customerId;
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


    public void setId(long id) {
        this.id = id;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
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

    public String getJsonData(){
        StringBuffer stringBuffer = new StringBuffer();

//        "{'name':'pratik','address':'203 opera','data':[{'saads':'asdf','saads':'asdf'},{'saads':'asdf'}]}"

        stringBuffer.append("{");

//      invoice info
        stringBuffer.append("'id':'"+getId()+"',");
        stringBuffer.append("'dd':'"+getDd()+"',");
        stringBuffer.append("'mm':'"+getMm()+"',");
        stringBuffer.append("'yyyy':'"+getYyyy()+"',");
        stringBuffer.append("'tprchage':'"+isTprchage()+"',");

//        customer
        stringBuffer.append("'customer':"+customer.getJsonData()+",");

        stringBuffer.append("}");

        return stringBuffer.toString();
    }

}
