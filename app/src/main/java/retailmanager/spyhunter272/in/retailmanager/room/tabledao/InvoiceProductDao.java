package retailmanager.spyhunter272.in.retailmanager.room.tabledao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import retailmanager.spyhunter272.in.retailmanager.room.table.InvoiceProduct;

@Dao
public interface InvoiceProductDao {

    @Insert
    long insert(InvoiceProduct invoiceProduct);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    public long[] addAllInvoiceProduct(List<InvoiceProduct> list);

    @Delete
    void delete(InvoiceProduct invoiceProduct);

    @Query("select * from invoiceProducts LIMIT :limits OFFSET :offsets ")
    LiveData<List<InvoiceProduct>> getInvoiceProductForList(int limits, int offsets);

}
