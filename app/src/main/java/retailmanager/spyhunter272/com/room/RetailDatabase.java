package retailmanager.spyhunter272.com.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import retailmanager.spyhunter272.com.room.table.Customer;
import retailmanager.spyhunter272.com.room.table.Invoice;
import retailmanager.spyhunter272.com.room.table.InvoiceProduct;
import retailmanager.spyhunter272.com.room.table.Product;
import retailmanager.spyhunter272.com.room.table.ProductCategory;
import retailmanager.spyhunter272.com.room.tabledao.CustomerDao;
import retailmanager.spyhunter272.com.room.tabledao.InvoiceDao;
import retailmanager.spyhunter272.com.room.tabledao.InvoiceProductDao;
import retailmanager.spyhunter272.com.room.tabledao.ProductCategoryDao;
import retailmanager.spyhunter272.com.room.tabledao.ProductDao;

@Database(entities = {
        Product.class,
        ProductCategory.class,
        Customer.class,
        Invoice.class,
        InvoiceProduct.class
},
 version = 1, exportSchema = false)

public abstract class RetailDatabase extends RoomDatabase {

    public static final String RT_DATABASE_NAME = "retailDatabase";

    private  static  RetailDatabase instance;

    public abstract ProductDao productDao();

    public abstract CustomerDao customerDao();

    public abstract InvoiceDao invoiceDao();

    public abstract InvoiceProductDao invoiceProductDao();

    public abstract ProductCategoryDao productCategoryDao();

    public static synchronized RetailDatabase getInstance(Context context){
        if(instance == null){

            instance = Room.databaseBuilder(context.getApplicationContext(),
                    RetailDatabase.class,RT_DATABASE_NAME)
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
//            new PopulateDbAsyncTask(instance).execute();
        }
    };

//    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void> {
//
//        private ProductDao productDao;
//        private CustomerDao customerDao;
//        private ProductCategoryDao productCategoryDao;
//
//        private PopulateDbAsyncTask(RetailDatabase db) {
//            this.productDao = db.productDao();
//            this.customerDao = db.customerDao();
//            this.productCategoryDao = db.productCategoryDao();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//
////                productCategoryDao.insert(new ProductCategory("Select Category",""));
//
////                productDao.insert(new Product("test Pro","234234",500,600,10,1));
////
////                customerDao.insert(
////                        new Customer("customer one","9099228842","AAAA22SSWWAA23A","pratiksanyaja1998@gmail.com",
////                        new Address("203 XXX XXXXX","Surat","Gujrat",365241),
////                        false,
////                        new Address("204 XXX XXXXX","Surat","Gujrat",365221)
////                        )
////                );
//
//            return null;
//        }
//    }


}
