package retailmanager.spyhunter272.com.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileWriter;

public class StaticInfoUtils {

//    public static File RETAIL_LOGO_FILE = new File(Environment.getExternalStorageDirectory(), "retailLogo .png");



    public static File retailLogoFile(Context mcoContext){
        File dir = new File(mcoContext.getFilesDir(),"retailInfo");
        if(!dir.exists()){
            dir.mkdir();
        }

        return new File(dir,"retailLogo.png");
    }


}
