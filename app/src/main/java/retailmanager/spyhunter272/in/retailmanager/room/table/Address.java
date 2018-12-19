package retailmanager.spyhunter272.in.retailmanager.room.table;


import android.arch.persistence.room.ColumnInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;

import static retailmanager.spyhunter272.in.retailmanager.utils.Common.isEpty;

public class Address {
    public String street;
    public String state;
    public String city;

    @ColumnInfo(name = "post_code")
    public String postCode;

    public Address(String street,  String city, String state, String postCode) {
        this.street = street;
        this.state = state;
        this.city = city;
        this.postCode = postCode;
    }

    public String getStreet() {
        return street;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getPostCode() {
        return postCode;
    }

    public Bundle getBundle(){
        Bundle bundle = new Bundle();
        bundle.putString("street",street);
        bundle.putString("state",state);
        bundle.putString("city",city);
        bundle.putString("pincode",postCode);
        return bundle;
    }

    static public Address setAddressFromBundle(Bundle bundle){
        Address address=  new Address(
                bundle.getString("street"),
                bundle.getString("city"),
                bundle.getString("state"),
                bundle.getString("pincode")
               );

        return address;

    }


    public void setStreet(String street) {
        this.street = street;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @NonNull
    @Override
    public String toString() {
        String data="";

        if(!isEpty(street))
            data +=street+", ";
        if(!isEpty(city))
            data +=city+", ";
        if(!isEpty(state))
            data +=state+", ";
        if(!isEpty(postCode))
            data +="-"+postCode;


        return data;
    }



}