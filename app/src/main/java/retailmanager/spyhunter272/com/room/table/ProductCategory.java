package retailmanager.spyhunter272.com.room.table;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "productCate" ,indices = {@Index(value = {"name"},
        unique = true)})
public class ProductCategory {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;


    private String name;

    private String description;

    public ProductCategory(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }
}
