package retailmanager.spyhunter272.com.room.tabledao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import retailmanager.spyhunter272.com.room.table.Customer;

@Dao
public interface CustomerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Customer customer);

    @Update
    void update(Customer customer);

    @Delete
    void delete(Customer customer);

    @Query("DELETE FROM customer")
    void deleteAll();

    @Query("SELECT * from customer order by name desc")
    LiveData<List<Customer>> getAllCustomer();


    @Query("SELECT COUNT() FROM customer")
    LiveData<Integer> getCustomerCount();

    @Query("select * from customer LIMIT :limits OFFSET :offsets ")
    LiveData<List<Customer>> getCustomerForList(int limits,int offsets);

    @Query("select * from customer where name like :filterNameHsn or mobile like :filterNameHsn LIMIT :limits OFFSET :offsets ")
    LiveData<List<Customer>> getCustomerForList(int limits,int offsets,String filterNameHsn);

    @Query("select * from customer where id=:customerId")
    Customer getInvoiceCustomer(long customerId);

}
