package retailmanager.spyhunter272.com.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import retailmanager.spyhunter272.com.R;
import retailmanager.spyhunter272.com.room.RetailDatabase;

import static android.app.Activity.RESULT_OK;

public class Common {

    public static boolean isEpty(String s){

        return (s==null || s.equals("")) ? true:false;
    }


    // put the image file path into this method
    public static String getFileToByte(String filePath){
        Bitmap bmp = null;
        ByteArrayOutputStream bos = null;
        byte[] bt = null;
        String encodeString = null;
        try{
            bmp = BitmapFactory.decodeFile(filePath);
            bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bt = bos.toByteArray();
            encodeString = Base64.encodeToString(bt, Base64.DEFAULT);
        }catch (Exception e){
            e.printStackTrace();
        }
        return encodeString;
    }


    public static void importDB(Context ctx,File dbFile) {

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data  = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String  currentDBPath= "//data//" + ctx.getPackageName()
                        + "//databases//" + RetailDatabase.RT_DATABASE_NAME+".db";
                String backupDBPath  = "/BackupFolder/DatabaseName";
                File  backupDB= new File(ctx.getDatabasePath(RetailDatabase.RT_DATABASE_NAME).getAbsolutePath());
//                File currentDB  = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(dbFile).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(ctx,"Database import successfully!",
                        Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {

            Toast.makeText(ctx, e.toString(), Toast.LENGTH_LONG)
                    .show();

        }
    }

    public static String getRealPathFromURI(Uri contentUri,Context context) {
        String [] proj      = {MediaStore.Images.Media.DATA};
        Cursor cursor       = context.getContentResolver().query( contentUri, proj, null, null,null);
        if (cursor == null) return null;
        int column_index    = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    //exporting database
    public static void exportDB(Context ctx) {

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String  currentDBPath= "//data//" + ctx.getPackageName()
                        + "//databases//" + RetailDatabase.RT_DATABASE_NAME+".db";
                String backupDBPath  = "/BackupFolder/DatabaseName";
                File currentDB =   new File(ctx.getDatabasePath(RetailDatabase.RT_DATABASE_NAME).getAbsolutePath());
                File backupDB = new File(StaticInfoUtils.getRtManagerRootFolder(), RetailDatabase.RT_DATABASE_NAME+".db");

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(ctx, "Database export successfully!",
                        Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {

            Toast.makeText(ctx, e.toString(), Toast.LENGTH_LONG)
                    .show();

        }
    }



    public static void formErrorAnimation(Context context, View view,String error){

        Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
        view.startAnimation(shake);
        if(error!=null)
            Toast.makeText(context,error,Toast.LENGTH_SHORT).show();

    }



}
