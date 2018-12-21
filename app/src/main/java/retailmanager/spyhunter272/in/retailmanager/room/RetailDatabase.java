package retailmanager.spyhunter272.in.retailmanager.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import retailmanager.spyhunter272.in.retailmanager.room.table.Address;
import retailmanager.spyhunter272.in.retailmanager.room.table.Customer;
import retailmanager.spyhunter272.in.retailmanager.room.table.Invoice;
import retailmanager.spyhunter272.in.retailmanager.room.table.InvoiceProduct;
import retailmanager.spyhunter272.in.retailmanager.room.table.Product;
import retailmanager.spyhunter272.in.retailmanager.room.table.ProductCategory;
import retailmanager.spyhunter272.in.retailmanager.room.tabledao.CustomerDao;
import retailmanager.spyhunter272.in.retailmanager.room.tabledao.InvoiceDao;
import retailmanager.spyhunter272.in.retailmanager.room.tabledao.InvoiceProductDao;
import retailmanager.spyhunter272.in.retailmanager.room.tabledao.ProductCategoryDao;
import retailmanager.spyhunter272.in.retailmanager.room.tabledao.ProductDao;

@Database(entities = {
        Product.class,
        ProductCategory.class,
        Customer.class,
        Invoice.class,
        InvoiceProduct.class
},
 version = 5, exportSchema = false)

public abstract class RetailDatabase extends RoomDatabase {

    private  static  RetailDatabase instance;

    public abstract ProductDao productDao();

    public abstract CustomerDao customerDao();

    public abstract InvoiceDao invoiceDao();

    public abstract InvoiceProductDao invoiceProductDao();

    public abstract ProductCategoryDao productCategoryDao();

    public static synchronized RetailDatabase getInstance(Context context){
        if(instance == null){

            instance = Room.databaseBuilder(context.getApplicationContext(),
                    RetailDatabase.class,"retailDatabase")
                    .addCallback(roomCallback)
                    .fallbackToDestructiveMigration()
                    .build();

        }

        return instance;
    }


    private static RoomDatabase.Callback roomCallback  =new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void> {

        private ProductDao productDao;
        private CustomerDao customerDao;
        private ProductCategoryDao productCategoryDao;

        private PopulateDbAsyncTask(RetailDatabase db) {
            this.productDao = db.productDao();
            this.customerDao = db.customerDao();
            this.productCategoryDao = db.productCategoryDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

//                productCategoryDao.insert(new ProductCategory("Select Category",""));

//                productDao.insert(new Product("test Pro","234234",500,600,10,1));
//
//                customerDao.insert(
//                        new Customer("customer one","9099228842","AAAA22SSWWAA23A","pratiksanyaja1998@gmail.com",
//                        new Address("203 XXX XXXXX","Surat","Gujrat",365241),
//                        false,
//                        new Address("204 XXX XXXXX","Surat","Gujrat",365221)
//                        )
//                );

            return null;
        }
    }


}
