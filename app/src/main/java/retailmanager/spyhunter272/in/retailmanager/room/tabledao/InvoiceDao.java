package retailmanager.spyhunter272.in.retailmanager.room.tabledao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import retailmanager.spyhunter272.in.retailmanager.room.table.Invoice;

@Dao
public interface InvoiceDao {

    @Insert
    long insert(Invoice invoice);

    @Delete
    void delete(Invoice invoice);

    @Query("select * from invoice LIMIT :limits OFFSET :offsets ")
    LiveData<List<Invoice>> getInvoiceForList(int limits,int offsets);

//    @Query("select * from invoice where name like :filterNameHsn LIMIT :limits OFFSET :offsets ")
//    LiveData<List<Invoice>> getInvoiceForList(int limits,int offsets,String filterNameHsn);

//    @Query("select * from invoice where category==:category LIMIT :limits OFFSET :offsets ")
//    LiveData<List<Product>> getInvoiceForList(int limits,int offsets,int category);

}