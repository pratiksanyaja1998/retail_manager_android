package retailmanager.spyhunter272.com.fragment;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import retailmanager.spyhunter272.com.R;
import retailmanager.spyhunter272.com.activity.CustomerActivity;
import retailmanager.spyhunter272.com.activity.HomeActivity;
import retailmanager.spyhunter272.com.activity.InvoiceActivity;
import retailmanager.spyhunter272.com.activity.ProductActivity;
import retailmanager.spyhunter272.com.activity.RetailInformationActivity;
import retailmanager.spyhunter272.com.customview.MonthYearPickerDialog;
import retailmanager.spyhunter272.com.databinding.FragmentDashbordHomeBinding;
import retailmanager.spyhunter272.com.holder.DashbrodHolder;
import retailmanager.spyhunter272.com.room.model.InvoiceOverview;
import retailmanager.spyhunter272.com.room.table.Invoice;
import retailmanager.spyhunter272.com.utils.StaticInfoUtils;
import retailmanager.spyhunter272.com.viewmodel.CustomerViewModel;
import retailmanager.spyhunter272.com.viewmodel.InvoiceViewModel;
import retailmanager.spyhunter272.com.viewmodel.ProductViewModel;

import static retailmanager.spyhunter272.com.holder.RetailInformationHolder.SP_KEY_FOR_RETAIL_INFO_EMAIL;
import static retailmanager.spyhunter272.com.holder.RetailInformationHolder.SP_KEY_FOR_RETAIL_INFO_NAME;
import static retailmanager.spyhunter272.com.utils.StaticInfoUtils.MONTH_LIST;


public class DashbordHomeFragment extends Fragment implements View.OnClickListener {


    public DashbordHomeFragment() {

    }


    private FragmentDashbordHomeBinding binding;
    private DashbrodHolder dashbrodHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_dashbord_home,container,false);
        dashbrodHolder = new DashbrodHolder();
        binding.setHolder(dashbrodHolder);
        return  binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        binding.btnOpenDialogDate.setOnClickListener(this);
        binding.retailMoreText.setOnClickListener(this);
        binding.cardCustomer.setOnClickListener(this);
        binding.cardInvoice.setOnClickListener(this);
        binding.cardProduct.setOnClickListener(this);

        initView();
    }

    private void initView() {


        ViewModelProviders.of(this).get(ProductViewModel.class).getProductCount().observe(this, integer -> {
            if (integer != null) {
                dashbrodHolder.setProductCount(integer.toString());
            }
        });

        ViewModelProviders.of(this).get(CustomerViewModel.class).getCustomerCount().observe(this, integer -> {
            if (integer != null) {
                dashbrodHolder.setCustomerCount(integer.toString());
            }
        });

        getInvoiceTotal(dashbrodHolder.getmMonth(), dashbrodHolder.getmYear());

        setRetailInfo();

    }


    private void setRetailInfo(){

        SharedPreferences  myPreference = PreferenceManager.getDefaultSharedPreferences(getContext());

        String retailName = myPreference.getString(SP_KEY_FOR_RETAIL_INFO_NAME, "");

        dashbrodHolder.setRetailName(retailName);

        if (!retailName.equals("")) {

            if (StaticInfoUtils.retailLogoFile(getContext()).exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(StaticInfoUtils.retailLogoFile(getContext()).getAbsolutePath());
                binding.retailLogo.setImageBitmap(myBitmap);

            }

        }else {

            Toast.makeText(getContext(),getResources().getString(R.string.retail_not_set),Toast.LENGTH_SHORT).show();

            startActivity(new Intent(getContext(),RetailInformationActivity.class));
            getActivity().finish();

        }

    }


    private void getInvoiceTotal(int mm, int yyyy){

         ViewModelProviders.of(this).get(InvoiceViewModel.class).getInvoiceOverview(mm,yyyy).observe(this, invoiceOverview -> {
             if(invoiceOverview!=null){
                 dashbrodHolder.setTotalIncome(invoiceOverview.getTotal());
                 dashbrodHolder.setInvoiceCount(String.valueOf(invoiceOverview.getCount()));
             }

         });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_open_dialog_date:

                MonthYearPickerDialog pd = MonthYearPickerDialog.newInstance(dashbrodHolder.getmMonth(),
                        1,dashbrodHolder.getmYear());

                pd.setListener((view, year, monthOfYear, dayOfMonth) -> {

                    dashbrodHolder.setMonthYear(monthOfYear,year);
                    getInvoiceTotal(monthOfYear,year);
                });

                pd.show(getFragmentManager(), "MonthYearPickerDialog");

                break;

            case R.id.retail_more_text:

                startActivity(new Intent(getContext(),RetailInformationActivity.class));
                getActivity().finish();
                break;

            case R.id.card_customer:

                startActivity(new Intent(getContext(),CustomerActivity.class));
                break;

            case R.id.card_invoice:
                startActivity(new Intent(getContext(),InvoiceActivity.class));
                break;

            case R.id.card_product:
                startActivity(new Intent(getContext(),ProductActivity.class));
                break;

        }
    }



}
