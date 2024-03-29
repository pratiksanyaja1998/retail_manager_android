package retailmanager.spyhunter272.com.room.tabledao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import retailmanager.spyhunter272.com.room.table.Product;

@Dao
public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Product product);

    @Update
    void update(Product product);

    @Delete
    void delete(Product product);

    @Query("DELETE FROM product")
    void deleteAll();

    @Query("SELECT COUNT() FROM product")
    LiveData<Integer> getProductCount();

    @Query("SELECT * from product order by category desc")
    LiveData<List<Product>> getAllProducts();

//    @Query("select * from product LIMIT :limits OFFSET :offsets ")
    @Query("select * from product")
    LiveData<List<Product>> getProductsForList();

//    @Query("select * from product where name like :filterNameHsn LIMIT :limits OFFSET :offsets ")
//        LiveData<List<Product>> getProductsForList(int limits,int offsets,String filterNameHsn);
    @Query("select * from product where name like :filterNameHsn")
    LiveData<List<Product>> getProductsForList(String filterNameHsn);


//    @Query("select * from product where category==:category LIMIT :limits OFFSET :offsets ")
//    LiveData<List<Product>> getProductsForList(int limits,int offsets,long category);

    @Query("select * from product where category==:category")
    LiveData<List<Product>> getProductsForList(long category);

}
