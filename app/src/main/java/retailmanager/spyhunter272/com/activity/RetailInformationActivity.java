package retailmanager.spyhunter272.com.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import retailmanager.spyhunter272.com.R;
import retailmanager.spyhunter272.com.bgworker.SaveRetailLogoBgWorker;
import retailmanager.spyhunter272.com.databinding.ActivityRetailInformationBinding;
import retailmanager.spyhunter272.com.dialog.CustomAlertDialog;
import retailmanager.spyhunter272.com.holder.RetailInformationHolder;
import retailmanager.spyhunter272.com.utils.StaticInfoUtils;

import static retailmanager.spyhunter272.com.activity.SettingsActivity.KEY_SP_NOTIFICATION;

public class RetailInformationActivity extends AppCompatActivity  {

    private RetailInformationHolder retailInformationHolder;
    private  ActivityRetailInformationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_retail_information);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        retailInformationHolder = new RetailInformationHolder(this);
        binding.setRetailInfo(retailInformationHolder);

       new Handler().post(() -> {
           if(StaticInfoUtils.retailLogoFile(RetailInformationActivity.this).exists()){

               Bitmap myBitmap = BitmapFactory.decodeFile(StaticInfoUtils.retailLogoFile(RetailInformationActivity.this).getAbsolutePath());
               binding.imageLogo.setImageBitmap(myBitmap);

           }
       });

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        boolean notification = sharedPreferences.getBoolean(KEY_SP_NOTIFICATION, true);
        if(notification){
            FirebaseMessaging.getInstance().subscribeToTopic("news");
        }

    }


    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            onBackPressed();
            return true;
        }

        return true;
    }


    public void onClick(View v){

        switch (v.getId()){

            case R.id.btn_change_logo:

                Intent intent = new Intent();
                intent.setType("image/*");
                String[] mimeTypes = {"image/png"};
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);

                break;

            case R.id.btn_save:

                String error = retailInformationHolder.saveInfo();
                 if(!error.equals("")){
                     Toast.makeText(this,error,Toast.LENGTH_SHORT).show();
                     Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
                     binding.root.startAnimation(shake);
                 }else {
                     Toast.makeText(this,"Information save successfully",Toast.LENGTH_SHORT).show();
                     overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                     startActivity(new Intent(this,HomeActivity.class));
                     finish();
                 }

                break;
        }

    }

    private static int SELECT_PICTURE =4;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_PICTURE) {

                if(data.getData()!=null)
                    new SaveRetailLogoBgWorker(this, bitmap -> binding.imageLogo.setImageBitmap(bitmap)).execute(data.getData());

            }

        }

    }




}
