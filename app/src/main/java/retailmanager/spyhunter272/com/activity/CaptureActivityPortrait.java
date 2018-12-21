package retailmanager.spyhunter272.com.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.journeyapps.barcodescanner.CaptureActivity;

import retailmanager.spyhunter272.com.R;

public class CaptureActivityPortrait extends CaptureActivity {

    @Override
    public void onCreate( @Nullable Bundle savedInstanceState,  @Nullable PersistableBundle persistentState) {
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

}