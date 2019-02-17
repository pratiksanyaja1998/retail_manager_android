package retailmanager.spyhunter272.com.room.tabledao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import retailmanager.spyhunter272.com.room.model.InvoiceOverview;
import retailmanager.spyhunter272.com.room.table.Invoice;

@Dao
public interface InvoiceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Invoice invoice);

    @Delete
    void delete(Invoice invoice);

    @Query("select * from invoice where id=:invoiceId")
    Invoice getInvoice(long invoiceId);

//    @Query("SELECT COUNT() FROM invoice")
//    LiveData<Integer> getInvoiceCount();

    @Query("select * from invoice where dd = :dd and yyyy = :yyyy and mm = :mm LIMIT :limits OFFSET :offsets ")
    LiveData<List<Invoice>> getInvoiceForList(int limits,int offsets, int dd,int mm,int yyyy);

    @Query("select * from invoice where ( dd = :dd and yyyy = :yyyy and mm = :mm) and (name like :q or mobile like :q) LIMIT :limits OFFSET :offsets ")
    LiveData<List<Invoice>> getInvoiceForList(int limits,int offsets, int dd,int mm,int yyyy,String q);

    @Query("select SUM(total) as total, COUNT() as count from invoice where yyyy = :yyyy and mm = :mm ")
    LiveData<InvoiceOverview> getInvoiceOverview(int yyyy,int mm);

//    @Query("select * from invoice where name like :filterNameHsn LIMIT :limits OFFSET :offsets ")
//    LiveData<List<Invoice>> getInvoiceForList(int limits,int offsets,String filterNameHsn);

//    @Query("select * from invoice where category==:category LIMIT :limits OFFSET :offsets ")
//    LiveData<List<Product>> getInvoiceForList(int limits,int offsets,int category);

}
