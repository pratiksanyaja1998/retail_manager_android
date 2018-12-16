package retailmanager.spyhunter272.in.retailmanager.fragment;

import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

import retailmanager.spyhunter272.in.retailmanager.R;
import retailmanager.spyhunter272.in.retailmanager.dialog.ProductCategoryManageDialog;


public class ProductSettingPrefrenceFragment extends PreferenceFragmentCompat {

    private ProductCategoryManageDialog productCategoryManageDialog;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        setPreferencesFromResource(R.xml.product_preference, null);

        PreferenceScreen customPref = (PreferenceScreen) findPreference("customPreference");


        productCategoryManageDialog = new ProductCategoryManageDialog();

        Preference button = (Preference)getPreferenceManager().findPreference("btnProductCate");
        if (button != null) {
            button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference arg0) {
                    productCategoryManageDialog.show(getFragmentManager(),null);
                    return true;
                }
            });
        }

    }


}
