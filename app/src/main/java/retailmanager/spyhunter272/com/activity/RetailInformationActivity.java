package retailmanager.spyhunter272.com.activity;

import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import retailmanager.spyhunter272.com.R;
import retailmanager.spyhunter272.com.bgworker.SaveRetailLogoBgWorker;
import retailmanager.spyhunter272.com.databinding.ActivityRetailInformationBinding;
import retailmanager.spyhunter272.com.model.RetailInformationHolder;
import retailmanager.spyhunter272.com.utils.StaticInfoUtils;

public class RetailInformationActivity extends AppCompatActivity {


    private RetailInformationHolder retailInformationHolder;
    private  ActivityRetailInformationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         binding = DataBindingUtil.setContentView(this,R.layout.activity_retail_information);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        retailInformationHolder = new RetailInformationHolder(this);
        binding.setRetailInfo(retailInformationHolder);


       new Handler().post( new Runnable(){

           public void run() {
               if(StaticInfoUtils.RETAIL_LOGO_FILE.exists()){

                   Bitmap myBitmap = BitmapFactory.decodeFile(StaticInfoUtils.RETAIL_LOGO_FILE.getAbsolutePath());
                   binding.imageLogo.setImageBitmap(myBitmap);

               }
           }
       });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_retail_info_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_save){

            retailInformationHolder.saveInfo();

        }

        return super.onOptionsItemSelected(item);
    }


    public void onClick(View v){
        switch (v.getId()){

            case R.id.btn_change_logo:

                Intent intent = new Intent();
                intent.setType("image/*");
                String[] mimeTypes = {"image/png"};
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTURE);

                break;
        }
    }

    private static int SELECT_PICTURE =4;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_PICTURE) {

                if(data.getData()!=null)
                    new SaveRetailLogoBgWorker(this,new SaveRetailLogoBgWorker.LisnOnSaveComplite(){

                        @Override
                        public void onSaveComplite(Bitmap bitmap) {

                            binding.imageLogo.setImageBitmap(bitmap);
                        }
                    }).execute(data.getData());


            }

        }
    }






    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

}
