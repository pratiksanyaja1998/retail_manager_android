package retailmanager.spyhunter272.com.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.support.v7.preference.PreferenceManager;

public class RetailInformationHolder extends BaseObservable {


    private String gstin,retailName,mobile,email,address;

    private SharedPreferences myPreference;

   public static   final String SP_KEY_FOR_RETAIL_INFO_NAME = "retailInfoName";
    public static  final String SP_KEY_FOR_RETAIL_INFO_ADDRESS = "retailInfoAddr";
    public static   final String SP_KEY_FOR_RETAIL_INFO_GSTIN = "retailInfoGstin";
    public static   final String SP_KEY_FOR_RETAIL_INFO_MOBILE = "retailInfoMobile";
    public static    final String SP_KEY_FOR_RETAIL_INFO_EMAIL = "retailInfoEmail";

    public RetailInformationHolder(Context context){

        myPreference =PreferenceManager.getDefaultSharedPreferences(context);

        retailName = myPreference.getString(SP_KEY_FOR_RETAIL_INFO_NAME,"");
        address = myPreference.getString(SP_KEY_FOR_RETAIL_INFO_EMAIL,"");
        gstin =myPreference.getString(SP_KEY_FOR_RETAIL_INFO_GSTIN,"");
        mobile = myPreference.getString(SP_KEY_FOR_RETAIL_INFO_MOBILE,"");
        email = myPreference.getString(SP_KEY_FOR_RETAIL_INFO_EMAIL,"");

    }


    public void saveInfo(){

        SharedPreferences.Editor editor = myPreference.edit();

        editor.putString(SP_KEY_FOR_RETAIL_INFO_NAME,retailName);
        editor.putString(SP_KEY_FOR_RETAIL_INFO_EMAIL,email);
        editor.putString(SP_KEY_FOR_RETAIL_INFO_MOBILE,mobile);
        editor.putString(SP_KEY_FOR_RETAIL_INFO_GSTIN,gstin);
        editor.putString(SP_KEY_FOR_RETAIL_INFO_ADDRESS,address);

        editor.commit();
        editor.clear();

    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getGstin() {
        return gstin;
    }

    public String getRetailName() {
        return retailName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public void setRetailName(String retailName) {
        this.retailName = retailName;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
