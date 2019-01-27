package retailmanager.spyhunter272.com;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import retailmanager.spyhunter272.com.fragment.InvoiceViewFragment;
import retailmanager.spyhunter272.com.fragment.ProductSettingPrefrenceFragment;
import retailmanager.spyhunter272.com.fragment.ProductViewFragment;
import retailmanager.spyhunter272.com.R;

public class ProductActivity extends AppCompatActivity {


    private ProductViewFragment productViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        productViewFragment = new ProductViewFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_root, productViewFragment, "view");
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
                if (productViewFragment.isVisible())
                    productViewFragment.searchOnQueryTextSubmit(query);
                myActionMenuItem.collapseActionView();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (productViewFragment.isVisible())
                    productViewFragment.searchOnQueryTextSubmit(s);
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
                fragmentTransaction.replace(R.id.fragment_root, new ProductSettingPrefrenceFragment(), "settings");
                fragmentTransaction.addToBackStack("settings");
                fragmentTransaction.commit();
            }

            setTitle("Product Settings");
            menu.findItem(R.id.action_search).setVisible(false);
            item.setVisible(false);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

//        if (getSupportFragmentManager().findFragmentById(R.id.fragment_root) instanceof ProductViewFragment) {

        setTitle(getResources().getString(R.string.products));
        menu.findItem(R.id.action_search).setVisible(true);
        menu.findItem(R.id.menu_settings).setVisible(true);

        super.onBackPressed();
            overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);

//        }else {

//            if (!getSupportFragmentManager().popBackStackImmediate("view", 0)) {
//                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_root, new ProductViewFragment(), "view");
//                fragmentTransaction.commit();
//            }
//        }

    }

    public interface SearchViewDataChangeListner {

        void searchOnQueryTextSubmit(String query);

    }

}
