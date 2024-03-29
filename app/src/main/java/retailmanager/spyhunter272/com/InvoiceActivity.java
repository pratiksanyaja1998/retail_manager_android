package retailmanager.spyhunter272.com;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import retailmanager.spyhunter272.com.fragment.InvoiceSettingPrefrenceFragment;
import retailmanager.spyhunter272.com.fragment.InvoiceViewFragment;
import retailmanager.spyhunter272.com.R;

public class InvoiceActivity extends AppCompatActivity  {

    private InvoiceViewFragment invoiceViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        invoiceViewFragment = new InvoiceViewFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_root, invoiceViewFragment, "view");
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
                if (invoiceViewFragment.isVisible())
                    invoiceViewFragment.searchOnQueryTextSubmit(query);
                myActionMenuItem.collapseActionView();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (invoiceViewFragment.isVisible())
                    invoiceViewFragment.searchOnQueryTextSubmit(s);
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
                fragmentTransaction.replace(R.id.fragment_root, new InvoiceSettingPrefrenceFragment(), "settings");
                fragmentTransaction.addToBackStack("settings");
                fragmentTransaction.commit();
            }

//             fragmentTransaction.setCustomAnimations( R.animator.card_flip_right_in,
//                        R.animator.card_flip_right_out,
//                        R.animator.card_flip_left_in,
//                        R.animator.card_flip_left_out)

            setTitle("Invoice Settings");
            menu.findItem(R.id.action_search).setVisible(false);
            item.setVisible(false);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        setTitle(getResources().getString(R.string.invoice));
        menu.findItem(R.id.action_search).setVisible(true);
        menu.findItem(R.id.menu_settings).setVisible(true);



        super.onBackPressed();
            overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);



    }

    public interface SearchViewDataChangeListner {

        void searchOnQueryTextSubmit(String query);

    }

}
