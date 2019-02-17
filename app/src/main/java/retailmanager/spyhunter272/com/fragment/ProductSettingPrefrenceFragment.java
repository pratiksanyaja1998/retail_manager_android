package retailmanager.spyhunter272.com.fragment;

import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

import retailmanager.spyhunter272.com.R;
import retailmanager.spyhunter272.com.dialog.ProductCategoryManageDialog;


public class ProductSettingPrefrenceFragment extends PreferenceFragmentCompat {

    private ProductCategoryManageDialog productCategoryManageDialog;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        setPreferencesFromResource(R.xml.product_preference, null);

        PreferenceScreen customPref = (PreferenceScreen) findPreference("customPreference");


        productCategoryManageDialog = new ProductCategoryManageDialog();

        Preference button = (Preference)getPreferenceManager().findPreference("btnProductCate");
        if (button != null) {
            button.setOnPreferenceClickListener(arg0 -> {
                productCategoryManageDialog.show(getFragmentManager(),null);
                return true;
            });
        }

    }


}
