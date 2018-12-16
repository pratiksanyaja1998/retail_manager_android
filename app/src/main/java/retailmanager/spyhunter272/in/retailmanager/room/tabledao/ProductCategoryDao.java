package retailmanager.spyhunter272.in.retailmanager.room.tabledao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import retailmanager.spyhunter272.in.retailmanager.room.table.ProductCategory;

@Dao
public interface ProductCategoryDao {

    @Insert
    void insert(ProductCategory productCategory);

    @Update
    void update(ProductCategory productCategory);

    @Delete
    void delete(ProductCategory productCategory);

    @Query("DELETE FROM productCate")
    void deleteAll();

    @Query("SELECT * from productCate order by id ")
    LiveData<List<ProductCategory>> getAllProductCategory();

}
