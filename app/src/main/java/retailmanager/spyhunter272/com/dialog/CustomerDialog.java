package retailmanager.spyhunter272.com.dialog;

import android.annotation.SuppressLint;
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

import android.support.v4.app.DialogFragment;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import retailmanager.spyhunter272.com.R;
import retailmanager.spyhunter272.com.holder.CustomerDialogHolder;
import retailmanager.spyhunter272.com.room.table.Customer;
import retailmanager.spyhunter272.com.viewmodel.CustomerViewModel;

import retailmanager.spyhunter272.com.databinding.DialogCustomerBinding;

@SuppressLint("ValidFragment")
public class CustomerDialog extends DialogFragment implements View.OnClickListener {

    private SharedPreferences myPreference;
    private Customer customer;

    private boolean isUpdate;


    public CustomerDialog(Customer customer, boolean isUpdate) {
        this.customer = customer;
        this.isUpdate = isUpdate;
    }

    private  DialogCustomerBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(inflater,R.layout.dialog_customer,container,false);

        binding.setCustomer(customer);

        myPreference =PreferenceManager.getDefaultSharedPreferences(getContext());

        binding.setCustomerDialogHolder(new CustomerDialogHolder(myPreference.getBoolean("mobile",true),myPreference.getBoolean("mobile",true),
                myPreference.getBoolean("baddr",true),myPreference.getBoolean("saddr",true) ));

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myPreference = PreferenceManager.getDefaultSharedPreferences(getContext());

//        ArrayAdapter arrayAdapter =  new ArrayAdapter(getContext(), android.R.layout.simple_dropdown_item_1line,
//                getResources().getStringArray(R.array.state_name));

        binding.btnDialogClose.setOnClickListener(this);
        binding.btnDialogOk.setOnClickListener(this);
        binding.ibtnPicContact.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_dialog_ok:

                    String error = customer.isValied();

                    if(error!=null){
                        Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
                        binding.getRoot().startAnimation(shake);
                        Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();
                        return;
                    }


                    if (isUpdate) {

                        ViewModelProviders.of(this).get(CustomerViewModel.class).update(customer);

                    } else {

                        ViewModelProviders.of(this).get(CustomerViewModel.class).insert(customer);
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

//     https://www.google.com/search?ei=58jBW6XoJsvbvASC6qyoDA&q=action+pick+contect&oq=action+pick+contect&gs_l=psy-ab.3...5677.13417.0.13605.20.18.0.0.0.0.916.1758.0j1j1j0j1j0j1.5.0....0...1c.1.64.psy-ab..15.5.1919.6..0j35i39k1j0i67k1j0i131k1j0i22i30k1j0i22i10i30k1.161.18zooYuMOGY
//     https://stackoverflow.com/questions/14069375/get-specific-contact-information-from-uri-returned-from-intent-action-pick

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
