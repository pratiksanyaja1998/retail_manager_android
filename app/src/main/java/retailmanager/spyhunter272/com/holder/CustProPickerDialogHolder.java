package retailmanager.spyhunter272.com.holder;

import android.databinding.BaseObservable;

public class CustProPickerDialogHolder extends BaseObservable {

    private boolean isCustomerOrProduct ,isDataFound=true ,isLoding = true;
    private long productCategoryId;


    public void setCustomerOrProduct(boolean customerOrProduct) {
        isCustomerOrProduct = customerOrProduct;
        notifyChange();
    }

    public boolean isCustomerOrProduct() {
        return isCustomerOrProduct;
    }

    public boolean isDataFound() {
        return isDataFound;
    }

    public void setDataFound(boolean dataFound) {
        isDataFound = dataFound;
        isLoding=false;
        notifyChange();
    }

    public void setLoding(boolean loding) {
        isLoding = loding;
        notifyChange();
    }

    public boolean isLoding() {
        return isLoding;
    }

    public long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }
}
