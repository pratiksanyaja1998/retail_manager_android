package retailmanager.spyhunter272.in.retailmanager.model;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retailmanager.spyhunter272.in.retailmanager.R;
import retailmanager.spyhunter272.in.retailmanager.activity.InvoiceFromActivity;

public class InvoiceViewHolder extends BaseObservable {

    private String date;
    private int offset;
    private Context context;
    private boolean isNoData=false;
    private Calendar myCalendar = Calendar.getInstance();
    public static int INVOICE_SHOW_LIMIT =10;


    private InvoiceViewDataChangeLisn invoiceViewDataChangeLisn;

    public InvoiceViewHolder(Context context,InvoiceViewDataChangeLisn invoiceViewDataChangeLisn) {
        this.context = context;
        this.invoiceViewDataChangeLisn = invoiceViewDataChangeLisn;
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        date =  sdf.format(myCalendar.getTime());

    }

    public Calendar getMyCalendar() {
        return myCalendar;
    }

    public boolean isNoData() {
        return isNoData;
    }

    public void setNoData(boolean noData) {
        isNoData = noData;
        notifyChange();
    }

    public String getDate() {
        return date;
    }

    public int getOffset() {
        return offset;
    }

    public void setDate(String date) {
        this.date = date;
        invoiceViewDataChangeLisn.onFilterDataChage();
        notifyChange();
    }

    public void setOffset(int offset) {
        this.offset = offset;
        invoiceViewDataChangeLisn.onFilterDataChage();
        notifyChange();
    }

    public void onClick(View v){

        switch (v.getId()){

            case R.id.btn_open_dialog_date:

                new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String myFormat = "MM/dd/yy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                        setDate(sdf.format(myCalendar.getTime()));

                    }
                }, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                break;

            case R.id.fbtn_add:
                context.startActivity(new Intent(context,InvoiceFromActivity.class));
                break;


            case R.id.ib_next:
                setOffset(offset+INVOICE_SHOW_LIMIT);
                break;
            case R.id.ib_prev:
                setOffset(offset-INVOICE_SHOW_LIMIT);
                break;

        }
    }

    public interface InvoiceViewDataChangeLisn{
        public void onFilterDataChage();

    }

}
