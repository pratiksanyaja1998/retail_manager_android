package retailmanager.spyhunter272.com.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import retailmanager.spyhunter272.com.room.RetailDatabase;
import retailmanager.spyhunter272.com.room.table.Product;
import retailmanager.spyhunter272.com.room.tabledao.ProductDao;

public class ProductViewModel extends AndroidViewModel {

    private ProductDao productDao;

    public ProductViewModel(@NonNull Application application) {
        super(application);

        RetailDatabase retailDatabase = RetailDatabase.getInstance(application);
        productDao = retailDatabase.productDao();

    }

    private static final int INSERT = 1;
    private static final int UPDATE = 2;
    private static final int DELETE = 3;

    private Object[]  objects;

    public void insert(Product product){

        objects=new Object[2];
        objects[0] = INSERT;
        objects[1] = product;


        new BGWorker().execute(objects);

    }

    public void update(Product product){

        objects=new Object[2];
        objects[0] = UPDATE;
        objects[1] = product;

        new BGWorker().execute(objects);

    }

    public void delete(Product product){

        objects=new Object[2];
        objects[0] = DELETE;
        objects[1] = product;

        new BGWorker().execute(objects);

    }

    public LiveData<List<Product>> getProductsForList(int limit,int offset ,int category,String filterNameHsn){

        if(!filterNameHsn.equals("")) {
            String temp = filterNameHsn;
            filterNameHsn = "%"+temp+"%";
            return productDao.getProductsForList(limit, offset, filterNameHsn);
        }

        if(category==0)
            return productDao.getProductsForList(limit,offset);
        else
            return productDao.getProductsForList(limit,offset,category);

    }


//    private class BGWorkerGetProduct extends  AsyncTask<Integer[],Void, LiveData<List<Product>>>{
//
//        @Override
//        protected  LiveData<List<Product>> doInBackground(Integer[]... integers) {
//            return productDao.getProductsForList(integers[0][0],integers[0][0]);
//        }
//
//        @Override
//        protected void onPostExecute(LiveData<List<Product>> listLiveData) {
//
//        }
//    }

    private class BGWorker extends AsyncTask<Object,Void,Void>{

        @Override
        protected Void doInBackground(Object... objects) {
            int flag = (int) objects[0];
            Product product = (Product) objects[1];

            switch (flag){
                case INSERT:
                    long id = productDao.insert(product);
                    Log.e("products",id+"");
                    break;
                case UPDATE:
                    productDao.update(product);
                    break;
                case DELETE:
                    productDao.delete(product);
                    break;
            }

            return null;
        }

    }



}
