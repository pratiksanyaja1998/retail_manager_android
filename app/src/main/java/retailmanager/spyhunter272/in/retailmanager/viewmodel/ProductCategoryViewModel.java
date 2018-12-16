package retailmanager.spyhunter272.in.retailmanager.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

import retailmanager.spyhunter272.in.retailmanager.room.RetailDatabase;
import retailmanager.spyhunter272.in.retailmanager.room.table.Product;
import retailmanager.spyhunter272.in.retailmanager.room.table.ProductCategory;
import retailmanager.spyhunter272.in.retailmanager.room.tabledao.ProductCategoryDao;
import retailmanager.spyhunter272.in.retailmanager.room.tabledao.ProductDao;

public class ProductCategoryViewModel extends AndroidViewModel {

    private ProductCategoryDao productCategoryDao;
    private LiveData<List<ProductCategory>> allProductCategory;

    private static final int INSERT = 1;
    private static final int DELETE = 3;

    public ProductCategoryViewModel(@NonNull Application application) {
        super(application);
        RetailDatabase retailDatabase = RetailDatabase.getInstance(application);
        productCategoryDao = retailDatabase.productCategoryDao();
        allProductCategory = productCategoryDao.getAllProductCategory();
    }

    private Object[]  objects;

    public void insert(ProductCategory productCategory){

        objects=new Object[2];
        objects[0] = INSERT;
        objects[1] = productCategory;

        new BGWorker().execute(objects);

    }

    public void delete(ProductCategory productCategory){

        objects=new Object[2];
        objects[0] = DELETE;
        objects[1] = productCategory;

        new BGWorker().execute(objects);

    }

    public LiveData<List<ProductCategory>> getAllProductCategory(){
        return allProductCategory;
    }

    private class BGWorker extends AsyncTask<Object,Void,Void> {

        @Override
        protected Void doInBackground(Object... objects) {
            int flag = (int) objects[0];
            ProductCategory productCategory = (ProductCategory) objects[1];

            switch (flag){
                case INSERT:
                    productCategoryDao.insert(productCategory);
                    break;

                case DELETE:
                    productCategoryDao.delete(productCategory);
                    break;
            }

            return null;
        }

    }

}
