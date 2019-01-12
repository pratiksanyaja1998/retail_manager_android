package retailmanager.spyhunter272.com.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.iid.FirebaseInstanceId;

import io.fabric.sdk.android.Fabric;
import retailmanager.spyhunter272.com.fragment.DashbordHomeFragment;
import retailmanager.spyhunter272.com.R;
import retailmanager.spyhunter272.com.utils.StaticInfoUtils;

import static retailmanager.spyhunter272.com.holder.RetailInformationHolder.SP_KEY_FOR_RETAIL_INFO_EMAIL;
import static retailmanager.spyhunter272.com.holder.RetailInformationHolder.SP_KEY_FOR_RETAIL_INFO_NAME;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static  final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 3;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 4;


    private DrawerLayout drawer;
    private NavigationView navigationView;
    private TextView textCartItemCount;
    private int mCartItemCount = 10;
//    private DashbordHomeFragment dashbordHomeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_home);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        Log.e("id is",FirebaseInstanceId.getInstance().getToken()+"");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setRetailInfo( navigationView.getHeaderView(0));
    }

    private void setRetailInfo(View v){

        if(v.getId()==R.id.btn_set_info){

            drawer.closeDrawers();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(HomeActivity.this,RetailInformationActivity.class));
                }
            },200);

        }

        SharedPreferences myPreference;
        myPreference =PreferenceManager.getDefaultSharedPreferences(this);

        String retailName = myPreference.getString(SP_KEY_FOR_RETAIL_INFO_NAME,"");
       String  email = myPreference.getString(SP_KEY_FOR_RETAIL_INFO_EMAIL,"");
        TextView nameTv = (TextView) v.findViewById(R.id.tv_retail_name);
        TextView emailTv = v.findViewById(R.id.tv_email);
        ImageView imageView  = v.findViewById(R.id.iv_retail_logo);


        if(!retailName.equals("")){
            new Handler().post( new Runnable(){

                public void run() {
                    if(StaticInfoUtils.retailLogoFile(HomeActivity.this).exists()){

                        Bitmap myBitmap = BitmapFactory.decodeFile(StaticInfoUtils.retailLogoFile(HomeActivity.this).getAbsolutePath());
                        imageView.setImageBitmap(myBitmap);
                        imageView.setVisibility(View.VISIBLE);

                    }
                }
            });

           emailTv.setText(email);
           nameTv.setText(retailName);

       }else {
            v.findViewById(R.id.btn_set_info).setVisibility(View.VISIBLE);

           v.findViewById(R.id.btn_set_info).setOnClickListener(this::setRetailInfo);

        }




    }

    private void checkPermission(){

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

        }else if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){


            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

        }


    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        drawer.closeDrawers();

        switch (item.getItemId()){
            case R.id.nav_customer:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(HomeActivity.this,CustomerActivity.class));
                    }
                },200);
                break;

            case R.id.nav_product:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(HomeActivity.this,ProductActivity.class));
                    }
                },200);
                break;

            case R.id.nav_invoice:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(HomeActivity.this,InvoiceActivity.class));
                    }
                },200);
                break;

            case R.id.nav_store_info:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(HomeActivity.this,RetailInformationActivity.class));
                    }
                },200);
                break;

            case  R.id.nav_settings:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(HomeActivity.this,SettingsActivity.class));
                    }
                },200);
                break;

            case R.id.nav_share:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Retail Manager");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "Hi Friends, I am finding the retail manager app to create invoice and manage retail store. Why don’t you try it out on your phone  https://play.google.com/store/apps/details?id="+getPackageName() );
                startActivity(Intent.createChooser(sharingIntent, "Share Apps Using"));
                break;

            case R.id.nav_about:
                Uri uri = Uri.parse("http://www.spyhunter272.in");
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
                break;

            case R.id.nav_feedback:
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{getResources().getString(R.string.email)});
                email.putExtra(Intent.EXTRA_SUBJECT, "About Retail Manager");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
                break;

        }

        return true;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);

        final MenuItem menuItem = menu.findItem(R.id.menu_notification);

        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }


    private void setupBadge() {

        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }


    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);

        } else {

            super.onBackPressed();
            overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);



        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermission();
                } else {

                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermission();
                } else {

                }
                return;

            }


        }
    }

}
