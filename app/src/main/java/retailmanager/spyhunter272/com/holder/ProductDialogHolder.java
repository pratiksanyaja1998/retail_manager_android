package retailmanager.spyhunter272.com.holder;

import android.databinding.BaseObservable;

public class ProductDialogHolder extends BaseObservable {

    boolean hsn;
    boolean bprice;
    boolean barcode;
    boolean productGst;
    boolean isUpdate;
    private int updateQty;

    public int getUpdateQty() {
        return updateQty;
    }

    public void setUpdateQty(int updateQty) {
        this.updateQty = updateQty;
    }

    public ProductDialogHolder(boolean hsn, boolean bprice, boolean barcode, boolean productGst) {
        this.hsn = hsn;
        this.bprice = bprice;
        this.barcode = barcode;
        this.productGst = productGst;
    }


    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public boolean isHsn() {
        return hsn;
    }

    public boolean isBprice() {
        return bprice;
    }

    public boolean isBarcode() {
        return barcode;
    }

    public boolean isProductGst() {
        return productGst;
    }


}
