package retailmanager.spyhunter272.com.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import retailmanager.spyhunter272.com.fragment.CustomerSettingPrefrenceFragment;
import retailmanager.spyhunter272.com.fragment.CustomerViewFragment;
import retailmanager.spyhunter272.com.R;

public class CustomerActivity extends AppCompatActivity {


    private CustomerViewFragment customerViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        customerViewFragment = new CustomerViewFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_root, customerViewFragment, "view");
        fragmentTransaction.setCustomAnimations(R.animator.fade_out, R.animator.fade_in);
        fragmentTransaction.commit();

    }

    private SearchView searchView;
    private Menu menu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settigns, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (customerViewFragment.isVisible())
                    customerViewFragment.searchOnQueryTextSubmit(query);
                myActionMenuItem.collapseActionView();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (customerViewFragment.isVisible())
                    customerViewFragment.searchOnQueryTextSubmit(s);
                return true;
            }
        });

        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_settings) {

            if (!getSupportFragmentManager().popBackStackImmediate("settings", 0)) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.animator.fade_out, R.animator.fade_in).replace(R.id.fragment_root, new CustomerSettingPrefrenceFragment(), "settings");
                fragmentTransaction.addToBackStack("settings");
                fragmentTransaction.commit();
            }

//             fragmentTransaction.setCustomAnimations( R.animator.card_flip_right_in,
//                  R.animator.card_flip_right_out,
//                  R.animator.card_flip_left_in,
//                  R.animator.card_flip_left_out)

            setTitle("Customer Settings");
            menu.findItem(R.id.action_search).setVisible(false);
            item.setVisible(false);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().findFragmentById(R.id.fragment_root) instanceof CustomerViewFragment) {
            overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
            super.onBackPressed();
        }else {
            setTitle(getResources().getString(R.string.customer));
            menu.findItem(R.id.action_search).setVisible(true);
            menu.findItem(R.id.menu_settings).setVisible(true);
        }

    }




    public interface SearchViewDataChangeListner {

        void searchOnQueryTextSubmit(String query);

    }



}
