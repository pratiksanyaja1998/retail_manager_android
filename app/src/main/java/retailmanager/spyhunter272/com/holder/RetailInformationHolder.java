package retailmanager.spyhunter272.com.holder;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.support.v7.preference.PreferenceManager;

public class RetailInformationHolder extends BaseObservable {


    private String gstin,retailName,mobile,email,address,city,state,pincode;

    private SharedPreferences myPreference;

    public static   final String SP_KEY_FOR_RETAIL_INFO_NAME = "retailInfoName";
    public static  final String SP_KEY_FOR_RETAIL_INFO_ADDRESS = "retailInfoAddr";
    public static  final String SP_KEY_FOR_RETAIL_INFO_GSTIN = "retailInfoGstin";
    public static  final String SP_KEY_FOR_RETAIL_INFO_MOBILE = "retailInfoMobile";
    public static  final String SP_KEY_FOR_RETAIL_INFO_EMAIL = "retailInfoEmail";
    public static  final String SP_KEY_FOR_RETAIL_INFO_CITY = "retailInfoCity";
    public static  final String SP_KEY_FOR_RETAIL_INFO_PINCODE = "retailInfoPincode";
    public static  final String SP_KEY_FOR_RETAIL_INFO_STATE = "retailInfoState";

    public RetailInformationHolder(Context context){

        myPreference =PreferenceManager.getDefaultSharedPreferences(context);

        retailName = myPreference.getString(SP_KEY_FOR_RETAIL_INFO_NAME,"");
        address = myPreference.getString(SP_KEY_FOR_RETAIL_INFO_ADDRESS,"");
        gstin =myPreference.getString(SP_KEY_FOR_RETAIL_INFO_GSTIN,"");
        mobile = myPreference.getString(SP_KEY_FOR_RETAIL_INFO_MOBILE,"");
        email = myPreference.getString(SP_KEY_FOR_RETAIL_INFO_EMAIL,"");
        city =myPreference.getString(SP_KEY_FOR_RETAIL_INFO_CITY,"");
        state = myPreference.getString(SP_KEY_FOR_RETAIL_INFO_STATE,"");
        pincode = myPreference.getString(SP_KEY_FOR_RETAIL_INFO_PINCODE,"");

    }


    public String saveInfo(){

        if(retailName.equals("")){


            return "Retail name must require !";
        }else if(gstin.length()!=15 && !gstin.equals("")){


            return "Enter vaild GSTIN";
        }else if(mobile.length()!=10){

            return "Enter vaild Mobile Number";
        }


        SharedPreferences.Editor editor = myPreference.edit();

        editor.putString(SP_KEY_FOR_RETAIL_INFO_NAME,retailName);
        editor.putString(SP_KEY_FOR_RETAIL_INFO_EMAIL,email);
        editor.putString(SP_KEY_FOR_RETAIL_INFO_MOBILE,mobile);
        editor.putString(SP_KEY_FOR_RETAIL_INFO_GSTIN,gstin);
        editor.putString(SP_KEY_FOR_RETAIL_INFO_ADDRESS,address);
        editor.putString(SP_KEY_FOR_RETAIL_INFO_STATE,state);
        editor.putString(SP_KEY_FOR_RETAIL_INFO_CITY,city);
        editor.putString(SP_KEY_FOR_RETAIL_INFO_PINCODE,pincode);

        editor.commit();

        return "";

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
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
