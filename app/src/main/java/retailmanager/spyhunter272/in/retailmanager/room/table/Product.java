package retailmanager.spyhunter272.in.retailmanager.room.table;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.os.Bundle;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "product"
//        foreignKeys= {
//        @ForeignKey(entity = ProductCategory.class,
//                        parentColumns = "id",
//                        childColumns = "category",
//                onDelete = CASCADE,
//                onUpdate = CASCADE)
//        }
)

public class Product extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    private String name;

    private String hsn;

    private String barcode;

    private double b_price;

    private int gst;

    private double s_price;

    private int in_stock_qty;

    @ColumnInfo(name = "category")
    private int category;

    @Ignore
    private double total;

    public Product(String name, String hsn, double b_price, double s_price, int in_stock_qty, int category, String barcode,int gst) {
        this.name = name;
        this.hsn = hsn;
        this.b_price = b_price;
        this.s_price = s_price;
        this.in_stock_qty = in_stock_qty;
        this.category = category;
        this.barcode = barcode;
        this.gst = gst;
    }

    public boolean checkValidation(){

        return true;
    }



    public Bundle getBundle(){
        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        bundle.putString("name",name);
        bundle.putString("hsn",hsn);
        bundle.putDouble("bprice",b_price);
        bundle.putDouble("sprice",s_price);
        bundle.putInt("category",category);
        bundle.putInt("stock",in_stock_qty);
        bundle.putString("barcode",barcode);
        bundle.putInt("gst",gst);
        return bundle;
    }

    static public Product setProductFromBundle(Bundle bundle){
        Product product=  new Product(
        bundle.getString("name"),
         bundle.getString("hsn"),
         bundle.getDouble("bprice"),
        bundle.getDouble("sprice"),
                bundle.getInt("stock"),
         bundle.getInt("category"),
                bundle.getString("barcode"),bundle.getInt("gst"));
        product.id = bundle.getInt("id");

        return product;

    }


    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHsn() {
        return hsn;
    }

    public double getB_price() {
        return b_price;
    }

    public double getS_price() {
        return s_price;
    }

    public int getIn_stock_qty() {
        return in_stock_qty;
    }

    public int getCategory() {
        return category;
    }

    public String getBarcode() {
        return barcode;
    }

    public int getGst() {
        return gst;
    }

    public void setGst(int gst) {
        this.gst = gst;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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

    public void setB_price(double b_price) {
        this.b_price = b_price;
    }

    public void setS_price(double s_price) {
        this.s_price = s_price;
    }

    public void setIn_stock_qty(int in_stock_qty) {
        this.in_stock_qty = in_stock_qty;
    }

    public void setCategory(int category) {
        this.category = category;
    }


}
