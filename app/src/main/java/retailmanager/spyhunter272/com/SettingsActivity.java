package retailmanager.spyhunter272.com;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import java.io.File;

import retailmanager.spyhunter272.com.R;
import retailmanager.spyhunter272.com.utils.Common;

public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            TextView tv = findViewById(R.id.tv_version);
            tv.setText(version+"");

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Switch notificationSwitch = findViewById(R.id.switch_notification);

        sharedPreferences = getPreferences(MODE_PRIVATE);
        boolean notification = sharedPreferences.getBoolean(KEY_SP_NOTIFICATION, true);

        notificationSwitch.setChecked(notification);

        notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> changeNotificationStatus(isChecked));

    }

    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_export_db:
                Common.exportDB(this);
                break;

            case R.id.btn_import_db:
                Intent chooseFile;
                Intent intent;
                chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
                chooseFile.setType("*/*");
                intent = Intent.createChooser(chooseFile, "Choose a databse");
                startActivityForResult(intent, SELECT_DB_FILE);
                break;
        }

    }

    private SharedPreferences sharedPreferences;
    public static final String KEY_SP_NOTIFICATION = "notification";

    private void changeNotificationStatus(boolean isChecked) {

        if (isChecked) {

            FirebaseMessaging.getInstance().subscribeToTopic("news");

        } else {

            FirebaseMessaging.getInstance().unsubscribeFromTopic("news");

        }

        sharedPreferences.edit().putBoolean(KEY_SP_NOTIFICATION, isChecked).apply();

    }

    private static final int SELECT_DB_FILE = 3;

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK) return;

        if(requestCode ==SELECT_DB_FILE)
        {
            if(data.getData()!=null) {

                Uri uri = data.getData();

                String FilePath = Common.getRealPathFromURI(uri, this);

                if(FilePath!=null && FilePath.contains(".db")) {
                    Common.importDB(this, new File(FilePath));
                }else {
                    Toast.makeText(this,getResources().getString(R.string.file_select_error),Toast.LENGTH_SHORT).show();
                }

            }
        }

    }

}
