package retailmanager.spyhunter272.com.dialog;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import retailmanager.spyhunter272.com.R;
import retailmanager.spyhunter272.com.databinding.DialogCustomerBinding;
import retailmanager.spyhunter272.com.holder.CustomerDialogHolder;
import retailmanager.spyhunter272.com.room.table.Address;
import retailmanager.spyhunter272.com.room.table.Customer;
import retailmanager.spyhunter272.com.viewmodel.CustomerViewModel;

public class CustomerInvoiceFormDialog extends BottomSheetDialogFragment implements View.OnClickListener {

    private SharedPreferences myPreference;
    private Customer customer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        DialogCustomerBinding dialogCustomerBinding = DataBindingUtil.inflate(inflater,R.layout.dialog_customer,container,false);

        customer = new Customer("","","","",new Address("","","",""),false,new Address("","","",""));
        dialogCustomerBinding.setCustomer(customer);
        myPreference =PreferenceManager.getDefaultSharedPreferences(getContext());

        dialogCustomerBinding.setCustomerDialogHolder(new CustomerDialogHolder(myPreference.getBoolean("mobile",true),myPreference.getBoolean("mobile",true),
                myPreference.getBoolean("baddr",true),myPreference.getBoolean("saddr",true) ));

        return dialogCustomerBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myPreference = PreferenceManager.getDefaultSharedPreferences(getContext());

//        ArrayAdapter arrayAdapter =  new ArrayAdapter(getContext(), android.R.layout.simple_dropdown_item_1line,
//                getResources().getStringArray(R.array.state_name));

//      init view
        view.findViewById(R.id.btn_dialog_close).setOnClickListener(this);
        view.findViewById(R.id.btn_dialog_ok).setOnClickListener(this);
        view.findViewById(R.id.ibtn_pic_contact).setOnClickListener(this::onClick);


        Bundle bundle=getArguments();

        if(bundle!=null){

            Customer customerUpdateble  = Customer.setCustomerFromBundle(bundle);
            customer.setName(customerUpdateble.getName());
            customer.setEmail(customerUpdateble.getEmail());
            customer.setBilling_address(customerUpdateble.getBilling_address());
            customer.setShipping_address(customerUpdateble.getShipping_address());
            customer.setGstin(customerUpdateble.getGstin());
            customer.setMobile(customerUpdateble.getMobile());
            customer.setId(customerUpdateble.getId());
            customer.setIs_same_b_s(customerUpdateble.isIs_same_b_s());
            customer.setNew(customerUpdateble.isNew());

        }else {

            customer.setNew(true);

        }


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_dialog_ok:

                if( customerLisner!=null){

                    if(customer.isNew()){
                        Log.e("post","new customer id +"+customer.getId());
                        ViewModelProviders.of(this).get(CustomerViewModel.class).update(customer);
                    }else {
                        Log.e("post","new customer id else +"+customer.getId() );

                    }

                    customerLisner.lisnCustomerFromDialog(customer);
                    dismiss();
                    return;

                }

                dismiss();
                break;

            case R.id.btn_dialog_close:
                dismiss();
                break;

            case R.id.ibtn_pic_contact:
                Uri uri = Uri.parse("content://contacts");
                Intent intent = new Intent(Intent.ACTION_PICK, uri);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent, PICK_CONTACT);

                break;

        }
    }

    private static int PICK_CONTACT = 33;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_CONTACT) {
                Uri contactURI = data.getData();

                String[] projection = { ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME };

                Cursor cursor = getActivity().getContentResolver().query(contactURI, projection,
                        null, null, null);
                cursor.moveToFirst();

                int numberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberColumnIndex);

                int nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String name = cursor.getString(nameColumnIndex);

                customer.setName(name);
                customer.setMobile(number);
                customer.notifyChange();

            }
        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof CustomerLisner){
            customerLisner = (CustomerLisner) context;

        }else {
            customerLisner = null;
        }

    }


    @Override
    public void onDetach() {
        super.onDetach();

        customerLisner = null;
    }

    private CustomerLisner customerLisner;

    public interface CustomerLisner{

        public void lisnCustomerFromDialog(Customer customer);

    }

}
