package retailmanager.spyhunter272.com.fragment;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import retailmanager.spyhunter272.com.R;
import retailmanager.spyhunter272.com.customview.MonthYearPickerDialog;
import retailmanager.spyhunter272.com.databinding.FragmentDashbordHomeBinding;
import retailmanager.spyhunter272.com.holder.DashbrodHolder;
import retailmanager.spyhunter272.com.viewmodel.CustomerViewModel;
import retailmanager.spyhunter272.com.viewmodel.InvoiceViewModel;
import retailmanager.spyhunter272.com.viewmodel.ProductViewModel;

import static retailmanager.spyhunter272.com.utils.StaticInfoUtils.MONTH_LIST;


public class DashbordHomeFragment extends Fragment implements View.OnClickListener {


    public DashbordHomeFragment() {

    }


    private FragmentDashbordHomeBinding binding;
    private DashbrodHolder dashbrodHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment 7043046758

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_dashbord_home,container,false);
        dashbrodHolder = new DashbrodHolder();
        binding.setHolder(dashbrodHolder);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        binding.btnOpenDialogDate.setOnClickListener(this);

        initView();
    }

    private void initView(){

        ViewModelProviders.of(this).get(InvoiceViewModel.class).getInvoiceCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if(integer!=null) {
                    dashbrodHolder.setInvoiceCount(integer.toString());
                }
            }
        });


        ViewModelProviders.of(this).get(ProductViewModel.class).getProductCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if(integer!=null ) {
                    dashbrodHolder.setProductCount(integer.toString());
                }
            }
        });

        ViewModelProviders.of(this).get(CustomerViewModel.class).getCustomerCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if(integer!=null) {
                    dashbrodHolder.setCustomerCount(integer.toString());
                }
            }
        });


    }



//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//
//
////        datePickerDialog = new DatePickerDialog(getContext(),
////                new DatePickerDialog.OnDateSetListener() {
////
////                    @Override
////                    public void onDateSet(DatePicker view, int year,
////                                          int monthOfYear, int dayOfMonth) {
////                        mYear = year;
////                        mMonth = monthOfYear;
////                        mDay = dayOfMonth;
////
////                        tvDateShow.setText(mDay + "-" + mMonth+ "-"+mYear );
////                    }
////
////                }, mYear, mMonth, mDay);
//
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_open_dialog_date:

                MonthYearPickerDialog pd = MonthYearPickerDialog.newInstance(dashbrodHolder.getmMonth(),
                        1,dashbrodHolder.getmYear());

                pd.setListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        dashbrodHolder.setMonthYear(monthOfYear,year);

                    }
                });
                pd.show(getFragmentManager(), "MonthYearPickerDialog");

                break;


        }
    }
}
