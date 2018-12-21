package retailmanager.spyhunter272.com.fragment;

import android.app.DatePickerDialog;
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


public class DashbordHomeFragment extends Fragment implements View.OnClickListener {


    public DashbordHomeFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashbord_home, container, false);
    }


    private TextView tvDateShow;
    int mYear, mMonth, mDay, mHour, mMinute;
    private DatePickerDialog datePickerDialog;
    final Calendar c = Calendar.getInstance();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        tvDateShow = view.findViewById(R.id.tv_show_date);
        tvDateShow.setText(mDay + "-" + mMonth+ "-"+mYear );
        view.findViewById(R.id.btn_open_dialog_date).setOnClickListener(this);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;

                        tvDateShow.setText(mDay + "-" + mMonth+ "-"+mYear );
                    }

                }, mYear, mMonth, mDay);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_open_dialog_date:
                datePickerDialog.show();
                break;


        }
    }
}
