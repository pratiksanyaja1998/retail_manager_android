package retailmanager.spyhunter272.in.retailmanager.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

import retailmanager.spyhunter272.in.retailmanager.room.RetailDatabase;
import retailmanager.spyhunter272.in.retailmanager.room.table.Invoice;
import retailmanager.spyhunter272.in.retailmanager.room.tabledao.InvoiceDao;

public class InvoiceViewModel extends AndroidViewModel {


    private InvoiceDao invoiceProductDao;

    public InvoiceViewModel(@NonNull Application application) {
        super(application);

        RetailDatabase retailDatabase = RetailDatabase.getInstance(application);
        invoiceProductDao =  retailDatabase.invoiceDao();
    }

    private static final int INSERT = 1;
    private static final int DELETE = 3;

    private Object[]  objects;


    public void insert(Invoice invoice){

        objects=new Object[2];
        objects[0] = INSERT;
        objects[1] = invoice;

        new BGWorker().execute(objects);

    }

    public void delete(Invoice product){

        objects=new Object[2];
        objects[0] = DELETE;
        objects[1] = product;

        new BGWorker().execute(objects);

    }

    public LiveData<List<Invoice>> getInvoiceForList(int limit, int offset, Calendar calendar){

        int mYear, mMonth, mDay;

        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);


        return invoiceProductDao.getInvoiceForList(limit,offset,mDay,mMonth,mYear);


    }

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
