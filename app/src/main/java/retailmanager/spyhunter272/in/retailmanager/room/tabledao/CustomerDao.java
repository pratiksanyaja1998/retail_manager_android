package retailmanager.spyhunter272.in.retailmanager.room.tabledao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import retailmanager.spyhunter272.in.retailmanager.room.table.Customer;
import retailmanager.spyhunter272.in.retailmanager.room.table.Product;

@Dao
public interface CustomerDao {

    @Insert
    long insert(Customer customer);

    @Update
    void update(Customer customer);

    @Delete
    void delete(Customer customer);

    @Query("DELETE FROM customer")
    void deleteAll();

    @Query("SELECT * from customer order by name desc")
    LiveData<List<Customer>> getAllCustomer();


    @Query("select * from customer LIMIT :limits OFFSET :offsets ")
    LiveData<List<Customer>> getCustomerForList(int limits,int offsets);

    @Query("select * from customer where name like :filterNameHsn or mobile like :filterNameHsn LIMIT :limits OFFSET :offsets ")
    LiveData<List<Customer>> getCustomerForList(int limits,int offsets,String filterNameHsn);

    @Query("select * from customer where id=:customerId")
    Customer getInvoiceCustomer(long customerId);

}
