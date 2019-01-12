package retailmanager.spyhunter272.com.room.table;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "invoiceProducts")
public class InvoiceProduct {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String hsn;

    private String barcode;

    private double price;

    private double total;

    private int qty;

    private int gst;

    private long invoiceId;

    public InvoiceProduct(String name, String hsn, String barcode, double price, double total, int qty,int gst, long invoiceId) {
        this.name = name;
        this.hsn = hsn;
        this.barcode = barcode;
        this.price = price;
        this.total = total;
        this.qty = qty;
        this.gst = gst;
        this.invoiceId = invoiceId;
    }

    public int getGst() {
        return gst;
    }

    public void setGst(int gst) {
        this.gst = gst;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHsn() {
        return hsn+"";
    }

    public String getBarcode() {
        return barcode;
    }

    public double getPrice() {
        return price;
    }

    public double getTotal() {
        return total;
    }

    public int getQty() {
        return qty;
    }

    public long getInvoiceId() {
        return invoiceId;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHsn(String hsn) {
        this.hsn = hsn;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }
}
