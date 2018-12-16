package retailmanager.spyhunter272.in.retailmanager.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retailmanager.spyhunter272.in.retailmanager.R;
import retailmanager.spyhunter272.in.retailmanager.activity.InvoiceActivity;
import retailmanager.spyhunter272.in.retailmanager.activity.InvoiceFromActivity;


public class InvoiceViewFragment extends Fragment implements InvoiceActivity.SearchViewDataChangeListner, View.OnClickListener {


    public InvoiceViewFragment() {

    }

    private Calendar myCalendar = Calendar.getInstance();
    private TextView tvShowDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_invoice_view, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        view.findViewById(R.id.btn_open_dialog_date).setOnClickListener(this);
        view.findViewById(R.id.fbtn_add).setOnClickListener(this::onClick);

        tvShowDate = view.findViewById(R.id.tv_show_date);
        updateLabel();

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_open_dialog_date:

                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabel();
                    }
                }, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                break;

            case R.id.fbtn_add:

                startActivity(new Intent(getContext(),InvoiceFromActivity.class));

                break;
        }
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        tvShowDate.setText(sdf.format(myCalendar.getTime()));
    }


    @Override
    public void searchOnQueryTextSubmit(String query) {

    }

    @Override
    public void searchDoneByQuery() {

    }

    @Override
    public void searchCancel() {

    }


}
