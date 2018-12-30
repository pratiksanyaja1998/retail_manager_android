package retailmanager.spyhunter272.com.room.table;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import static retailmanager.spyhunter272.com.utils.Common.isEpty;


@Entity(tableName = "customer")
public class Customer extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;

    private String mobile;

    private String gstin;

    private String email;

    @Embedded(prefix = "billing_")
    private Address billing_address;

    private boolean is_same_b_s;

    @Embedded(prefix = "shipping_")
    private Address shipping_address;

    @Ignore
    private boolean isNew = false;


    public Customer(String name, String mobile, String gstin, String email, Address billing_address, boolean is_same_b_s, Address shipping_address) {
        this.name = name;
        this.mobile = mobile;
        this.gstin = gstin;
        this.email = email;
        this.billing_address = billing_address;
        this.is_same_b_s = is_same_b_s;
        this.shipping_address = shipping_address;
    }

    @Ignore
    public Customer(){
        this.billing_address = new Address("","","","");
        this.shipping_address = new Address("","","","");

    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public String isValied(){

        if( name==null){
            return "Please Enter Customer Name !";
        }else if(name.equals("")){
            return "Please Enter Customer Name !";
        }

        return null;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getGstin() {
        return gstin;
    }

    public String getEmail() {
        return email;
    }

    public Address getBilling_address() {
        return billing_address;
    }

    public boolean isIs_same_b_s() {
        return is_same_b_s;
    }

    public Address getShipping_address() {
        return shipping_address;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBilling_address(Address billing_address) {
        this.billing_address = billing_address;
    }

    public void setIs_same_b_s(boolean is_same_b_s) {
        this.is_same_b_s = is_same_b_s;
    }

    public void setShipping_address(Address shipping_address) {
        this.shipping_address = shipping_address;
    }

    public Bundle getBundle(){
        Bundle bundle = new Bundle();
        bundle.putLong("id",id);
        bundle.putString("name",name);
        bundle.putString("mobile",mobile);
        bundle.putString("gstin",gstin);
        bundle.putString("email",email);
        bundle.putBoolean("isNew",isNew);
        bundle.putBundle("baddr",billing_address.getBundle());
        bundle.putBundle("saddr",shipping_address.getBundle());
        bundle.putBoolean("same_b_a",is_same_b_s);
        return bundle;
    }

    static public Customer setCustomerFromBundle(Bundle bundle){

        Customer customer=  new Customer(
                bundle.getString("name"),
                bundle.getString("mobile"),
                bundle.getString("gstin"),
                bundle.getString("email"),
                Address.setAddressFromBundle(bundle.getBundle("baddr")),
                bundle.getBoolean("same_b_a"),
                Address.setAddressFromBundle(bundle.getBundle("saddr")));

        customer.id = bundle.getInt("id");
        customer.setNew(bundle.getBoolean("isNew"));

        return customer;

    }

    public void onSameBSChangeChanged(boolean checked) {
        // implementation
        Log.e("checked",""+checked);
        is_same_b_s = checked;
        notifyChange();
    }


    @NonNull
    @Override
    public String toString() {

        String data="";
        if(!isEpty(name))
        data += "Customer Name : "+name;
        if(!isEpty(mobile))
            data += "\nMobile : +91 "+mobile;
        if(!isEpty(gstin))
            data += "\nGSTIN : "+gstin;
        if(!isEpty(billing_address.toString()))
            data += "\nBilling Address : "+billing_address.toString();

        if(is_same_b_s)
            data += "\nShipping Address : "+billing_address.toString();
        else if(!isEpty(shipping_address.toString()))
            data += "\nShipping Address : "+shipping_address.toString();

        return data;
    }


    public String getJsonData(){

        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("{");

        stringBuffer.append("'id':'"+getId()+"',");
        stringBuffer.append("'name':'"+getName()+"',");
        stringBuffer.append("'same_b_s':'"+isIs_same_b_s()+"',");
        stringBuffer.append("'b_adr':"+billing_address.getJsonData()+",");
        stringBuffer.append("'s_adr':"+shipping_address.getJsonData()+"");


        stringBuffer.append("}");


       return   stringBuffer.toString();
    }


}


