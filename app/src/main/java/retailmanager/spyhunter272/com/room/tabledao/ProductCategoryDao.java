package retailmanager.spyhunter272.com.room.tabledao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import retailmanager.spyhunter272.com.room.table.ProductCategory;

@Dao
public interface ProductCategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(ProductCategory productCategory);

    @Update
    void update(ProductCategory productCategory);

    @Delete
    void delete(ProductCategory productCategory);

    @Query("DELETE FROM productCate")
    void deleteAll();

    @Query("SELECT * from productCate order by id ")
    LiveData<List<ProductCategory>> getAllProductCategory();

}
