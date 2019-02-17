package retailmanager.spyhunter272.com.room.table;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.os.Bundle;
import android.view.View;

import retailmanager.spyhunter272.com.R;

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
    private int id;

    private String name;

    private String hsn;

    private String barcode;

    private double s_price;

    private int in_stock_qty;

    private int category;

    @Ignore
    private double total;

    @Ignore
    public Product(){

    }

    public Product(String name, String hsn, double s_price, int in_stock_qty, int category, String barcode) {
        this.name = name;
        this.hsn = hsn;
        this.s_price = s_price;
        this.in_stock_qty = in_stock_qty;
        this.category = category;
        this.barcode = barcode;
    }

    public boolean checkValidation(){

        return true;
    }


    public void onClick(View view){
        switch (view.getId()){
            case R.id.ibtn_qty_add:
                in_stock_qty +=1;
                break;
            case R.id.ibtn_qty_remove:
                in_stock_qty-=1;
                break;

        }

        notifyChange();
    }

    public Bundle getBundle(){
        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        bundle.putString("name",name);
        bundle.putString("hsn",hsn);
        bundle.putDouble("sprice",s_price);
        bundle.putInt("category",category);
        bundle.putInt("stock",in_stock_qty);
        bundle.putString("barcode",barcode);
        return bundle;
    }

    static public Product getProductFromBundle(Bundle bundle){
        Product product=  new Product(
        bundle.getString("name"),
         bundle.getString("hsn"),
        bundle.getDouble("sprice"),
                bundle.getInt("stock"),
         bundle.getInt("category"),
                bundle.getString("barcode"));
        product.id = bundle.getInt("id");

        return product;

    }

    public String isValied(){

        if(name==null){
            return  "Please Enter Product Name !";
        }else if(name.equals("")){
            return  "Please Enter Product Name !";
        }else  if(category==0){
            return "Please Select Category !";
        }



        return null;
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
