package retailmanager.spyhunter272.in.retailmanager.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import retailmanager.spyhunter272.in.retailmanager.room.RetailDatabase;
import retailmanager.spyhunter272.in.retailmanager.room.table.Invoice;
import retailmanager.spyhunter272.in.retailmanager.room.table.InvoiceProduct;
import retailmanager.spyhunter272.in.retailmanager.room.table.Product;
import retailmanager.spyhunter272.in.retailmanager.room.tabledao.InvoiceDao;
import retailmanager.spyhunter272.in.retailmanager.room.tabledao.InvoiceProductDao;

public class InvoiceViewModel extends AndroidViewModel {


    private InvoiceDao invoiceProductDao;

    public InvoiceViewModel(@NonNull Application application) {
        super(application);

        RetailDatabase retailDatabase = RetailDatabase.getInstance(application);
        invoiceProductDao =  retailDatabase.invoiceDao();
    }

    private static final int INSERT = 1;
    private static final int UPDATE = 2;
    private static final int DELETE = 3;

    private Object[]  objects;


    public void insert(Invoice product){

        objects=new Object[2];
        objects[0] = INSERT;
        objects[1] = product;


        new BGWorker().execute(objects);

    }

    public void delete(Invoice product){

        objects=new Object[2];
        objects[0] = DELETE;
        objects[1] = product;

        new BGWorker().execute(objects);

    }

//    public LiveData<List<Product>> getProductsForList(int limit, int offset , int category, String filterNameHsn){

//        if(!filterNameHsn.equals("")) {
//            String temp = filterNameHsn;
//            filterNameHsn = "%"+temp+"%";
//            return invoiceProductDao.getInvoiceProductForList(limit, offset);
//        }
//
//        if(category==0)
//            return productDao.getProductsForList(limit,offset);
//        else
//            return productDao.getProductsForList(limit,offset,category);

//    }



    private class BGWorker extends AsyncTask<Object,Void,Void> {

        @Override
        protected Void doInBackground(Object... objects) {
            int flag = (int) objects[0];
            Invoice product = (Invoice) objects[1];

            switch (flag){
                case INSERT:
                    long id = invoiceProductDao.insert(product);
                    Log.e("products",id+"");
                    break;

                case DELETE:
                    invoiceProductDao.delete(product);
                    break;
            }

            return null;
        }

    }

}
