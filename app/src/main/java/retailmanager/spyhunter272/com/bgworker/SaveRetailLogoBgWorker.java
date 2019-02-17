package retailmanager.spyhunter272.com.bgworker;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.fabric.sdk.android.services.concurrency.AsyncTask;
import retailmanager.spyhunter272.com.utils.StaticInfoUtils;


public class SaveRetailLogoBgWorker extends AsyncTask<Uri,Void,Bitmap> {

    private LisnOnSaveComplite lisnOnSaveComplite;
    private Context context;

    private ProgressDialog progressDialog;

    public SaveRetailLogoBgWorker( Context context,LisnOnSaveComplite lisnOnSaveComplite) {
        this.lisnOnSaveComplite = lisnOnSaveComplite;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Bitmap doInBackground(Uri... uris) {

        Bitmap bmp=null;
        FileOutputStream fOut = null;
        try {
            bmp = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uris[0]);
            fOut = new FileOutputStream(StaticInfoUtils.retailLogoFile(context));
            Log.e("pratik",StaticInfoUtils.retailLogoFile(context).toString());
            bmp.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        return bmp;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(progressDialog.isShowing())
            progressDialog.dismiss();
        lisnOnSaveComplite.onSaveComplite(bitmap);
    }


    public interface LisnOnSaveComplite{

        void onSaveComplite(Bitmap bitmap);
    }

}
