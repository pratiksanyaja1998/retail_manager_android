package retailmanager.spyhunter272.com.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileWriter;

import retailmanager.spyhunter272.com.room.table.Invoice;

public class StaticInfoUtils {

//    public static File RETAIL_LOGO_FILE = new File(Environment.getExternalStorageDirectory(), "retailLogo .png");


    public static File retailLogoFile(Context mcoContext){
        File dir = new File(mcoContext.getFilesDir(),"retailInfo");
        if(!dir.exists()){
            dir.mkdir();
        }

        return new File(dir,"retailLogo.png");
    }

    public static File getInvoiceDir(Context mcoContext, Invoice invoice){
        File dir = new File(getRtManagerRootFolder(),"/invoice/"+invoice.getYyyy()+"/"+invoice.getMm()+"/"+invoice.getDd());
        if(!dir.exists()){
            dir.mkdirs();
        }

        return dir;
    }

    public static File getRtManagerRootFolder(){
        File dir = new File(Environment.getExternalStorageDirectory(),"RetailManager");
        if(!dir.exists()){
            dir.mkdir();
        }

        return dir;
    }


    public static File getInvoiceFile(Context mcoContext, Invoice invoice){

        return new File(getInvoiceDir(mcoContext,invoice),getInvoiceFileName(mcoContext,invoice));
    }

    public static String getInvoiceFileName(Context mcoContext, Invoice invoice){

        return "IN00"+invoice.getId()+".pdf";
    }


    public static final String[] MONTH_LIST = new String[]{"Jan","Feb","Mar","Apr","May","June","July",
            "Aug","Sep","Oct","Nov","Dec"};


}
