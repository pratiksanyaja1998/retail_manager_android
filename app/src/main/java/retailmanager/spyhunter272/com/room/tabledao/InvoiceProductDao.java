package retailmanager.spyhunter272.com.room.tabledao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import retailmanager.spyhunter272.com.room.table.InvoiceProduct;

@Dao
public interface InvoiceProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(InvoiceProduct invoiceProduct);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public long[] addAllInvoiceProduct(List<InvoiceProduct> list);

    @Delete
    void delete(InvoiceProduct invoiceProduct);

    @Query("select * from invoiceProducts where invoiceId=:invoiceId ")
    List<InvoiceProduct> getInvoiceProducts(long invoiceId);

}
