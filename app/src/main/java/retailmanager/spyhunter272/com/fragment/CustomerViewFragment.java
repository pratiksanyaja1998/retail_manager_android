package retailmanager.spyhunter272.com.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retailmanager.spyhunter272.com.R;
import retailmanager.spyhunter272.com.activity.CustomerActivity;
import retailmanager.spyhunter272.com.dialog.CustomAlertDialog;
import retailmanager.spyhunter272.com.dialog.CustomerDialog;
import retailmanager.spyhunter272.com.room.table.Customer;
import retailmanager.spyhunter272.com.viewmodel.CustomerViewModel;

public class CustomerViewFragment extends Fragment  implements  CustomerActivity.SearchViewDataChangeListner, View.OnClickListener {

    private final int  CUSTOMER_SHOW_LIMIT  = 10;
    private int offSet = 0;

    private RecyclerView recyclerView;
    private RcCustomerAdepter rcCustomerAdepter = new RcCustomerAdepter();
    private ImageView linearEmtyCustomer;
    private ImageButton iBtnNext,iBtnPrev;
    private SharedPreferences myPreference;
    private EditText edOffset;


    public CustomerViewFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_view, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        linearEmtyCustomer  = view.findViewById(R.id.image_bg_not_found);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(rcCustomerAdepter);

        view.findViewById(R.id.fbtn_add).setOnClickListener(this);
        iBtnNext =  view.findViewById(R.id.ib_next);
        iBtnNext.setOnClickListener(this);
        iBtnPrev = view.findViewById(R.id.ib_prev);
        iBtnPrev.setOnClickListener(this);

        myPreference= PreferenceManager.getDefaultSharedPreferences(getContext());

        edOffset = view
                .findViewById(R.id.ed_offset);


        edOffset.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    setOffSet( Integer.parseInt("0"+edOffset.getText().toString()));
                    getCustomer();
                    return false;
                }

                return true;
            }
        });


    }

    private CustomerViewModel customerViewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);

        getCustomer();
    }

    private String customerNameMobileFilterText= null;

    private void setOffSet(int offSet){
        this.offSet = offSet;
        edOffset.setText(offSet+"");
    }

    private void getCustomer() {

        customerViewModel.getCustomerForList(CUSTOMER_SHOW_LIMIT,offSet,customerNameMobileFilterText).observe(this, new Observer<List<Customer>>() {
            @Override
            public void onChanged(@Nullable List<Customer> customers) {
                setListNavigation(customers.size());
                Log.e("customer",""+customers.size());
                rcCustomerAdepter.setCustomers(customers);
            }
        });

    }

    private void deleteCustomer(Customer customer){

        CustomAlertDialog.show(getContext(), new CustomAlertDialog.CustomAlertDialogEvent() {
            @Override
            public void eventCancel() {

            }

            @Override
            public void eventDone() {
                customerViewModel.delete(customer);

            }
        });



    }

    private void editCustomer(Customer customer){

        CustomerDialog customerDialog = new CustomerDialog(customer,true);
        customerDialog.show(getFragmentManager(),null);

    }


    private void setListNavigation(int listSize){

        if(listSize==0){
            linearEmtyCustomer.setVisibility(View.VISIBLE);

        }else {
            linearEmtyCustomer.setVisibility(View.INVISIBLE);
        }

//        if(listSize>=CUSTOMER_SHOW_LIMIT){
//            iBtnNext.setVisibility(View.VISIBLE);
//        }else {
//            iBtnNext.setVisibility(View.GONE);
//            iBtnPrev.setVisibility(View.VISIBLE);
//        }
//
//        if(offSet==0){
//            iBtnPrev.setVisibility(View.GONE);
//        }else {
//            iBtnPrev.setVisibility(View.VISIBLE);
//        }

    }


//    searchview methord call from activity

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.fbtn_add:
                CustomerDialog customerDialog =new CustomerDialog(new Customer(),false);
                customerDialog.show(getFragmentManager(),null);
                break;

            case R.id.ib_next:

                setOffSet(offSet+CUSTOMER_SHOW_LIMIT);
                getCustomer();

                break;

            case R.id.ib_prev:

                setOffSet(offSet-CUSTOMER_SHOW_LIMIT);
                getCustomer();

                break;
        }

    }


    @Override
    public void searchOnQueryTextSubmit(String query) {
            customerNameMobileFilterText=query;
            getCustomer();
    }



    class RcCustomerAdepter extends RecyclerView.Adapter<RcCustomerAdepter.VHolder> {


        private List<Customer> customers = new ArrayList<>();

        public void setCustomers(List<Customer> customers) {
            this.customers = customers;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public VHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            VHolder vHolder= new VHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_customer,viewGroup,false));

            if(!myPreference.getBoolean("email",true))
                vHolder.tvEmail.setVisibility(View.GONE);

            if(!myPreference.getBoolean("mobile",true))
                vHolder.tvNumber.setVisibility(View.GONE);

            return vHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull VHolder vHolder, int i) {

            Customer customer = customers.get(i);

            vHolder.tvNumber.setText(customer.getMobile());
            vHolder.tvName.setText(customer.getName());
            vHolder.tvEmail.setText(customer.getEmail());

            vHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteCustomer(customer);
                }
            });

            vHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editCustomer(customer);
                }
            });



        }

        @Override
        public int getItemCount() {
            return customers.size();
        }

        class VHolder extends RecyclerView.ViewHolder{

            TextView tvName,tvNumber,tvEmail;
            ImageButton btnEdit,btnDelete;

            public VHolder(@NonNull View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.row_tv_customer_name);
                tvNumber = itemView.findViewById(R.id.row_tv_cust_mobile);
                tvEmail = itemView.findViewById(R.id.row_tv_customer_email);
                btnEdit = itemView.findViewById(R.id.ibtn_edit);
                btnDelete  =itemView.findViewById(R.id.ibtn_delete);
            }

        }
    }




}
