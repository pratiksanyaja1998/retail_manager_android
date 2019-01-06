package retailmanager.spyhunter272.com.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

import retailmanager.spyhunter272.com.room.RetailDatabase;
import retailmanager.spyhunter272.com.room.table.Customer;
import retailmanager.spyhunter272.com.room.tabledao.CustomerDao;

public class CustomerViewModel extends AndroidViewModel {

    CustomerDao customerDao;

    private static final int INSERT = 1;
    private static final int UPDATE = 2;
    private static final int DELETE = 3;

    private Object[]  objects;


    public CustomerViewModel(@NonNull Application application) {
        super(application);
        RetailDatabase retailDatabase = RetailDatabase.getInstance(application);
        customerDao = retailDatabase.customerDao();
    }

    public void insert(Customer customer){

        objects=new Object[2];
        objects[0] = INSERT;
        objects[1] = customer;

        new BGWorker().execute(objects);

    }

    public void update(Customer customer){

        objects=new Object[2];
        objects[0] = UPDATE;
        objects[1] = customer;

        new BGWorker().execute(objects);

    }

    public void delete(Customer customer){

        objects=new Object[2];
        objects[0] = DELETE;
        objects[1] = customer;

        new BGWorker().execute(objects);

    }


    public LiveData<List<Customer>> getCustomerForList(int limit, int offset ,  String filterNameHsn){

        if(filterNameHsn!=null)
        if(!filterNameHsn.equals("")) {
            String temp = filterNameHsn;
            filterNameHsn = "%"+temp+"%";
            return customerDao.getCustomerForList(limit, offset, filterNameHsn);
        }

        return customerDao.getCustomerForList(limit,offset);

    }

    public LiveData<Integer> getCustomerCount(){

        return customerDao.getCustomerCount();
    }


    private class BGWorker extends AsyncTask<Object,Void,Void> {

        @Override
        protected Void doInBackground(Object... objects) {
            int flag = (int) objects[0];
            Customer customer = (Customer) objects[1];

            switch (flag){
                case INSERT:
                    customerDao.insert(customer);
                    break;
                case UPDATE:
                    customerDao.update(customer);
                    break;
                case DELETE:
                    customerDao.delete(customer);
                    break;
            }

            return null;
        }

    }



}
