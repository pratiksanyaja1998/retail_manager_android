package retailmanager.spyhunter272.in.retailmanager.fragment;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import retailmanager.spyhunter272.in.retailmanager.R;

public class CustomerSettingPrefrenceFragment extends PreferenceFragmentCompat {


    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        setPreferencesFromResource(R.xml.customer_preference, null);

    }


}
