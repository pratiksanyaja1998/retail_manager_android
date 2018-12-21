package retailmanager.spyhunter272.com.fragment;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import retailmanager.spyhunter272.com.R;


public class InvoiceSettingPrefrenceFragment extends PreferenceFragmentCompat {


    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        setPreferencesFromResource(R.xml.invoice_preference, null);

    }


}
