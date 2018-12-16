package retailmanager.spyhunter272.in.retailmanager.activity;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import retailmanager.spyhunter272.in.retailmanager.R;
import retailmanager.spyhunter272.in.retailmanager.databinding.ActivityRetailInformationBinding;
import retailmanager.spyhunter272.in.retailmanager.model.RetailInformationHolder;

public class RetailInformationActivity extends AppCompatActivity {


    private RetailInformationHolder retailInformationHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityRetailInformationBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_retail_information);

        retailInformationHolder = new RetailInformationHolder(this);
        binding.setRetailInfo(retailInformationHolder);

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
}
