package retailmanager.spyhunter272.in.retailmanager.fragment;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import retailmanager.spyhunter272.in.retailmanager.R;
import retailmanager.spyhunter272.in.retailmanager.activity.InvoiceActivity;
import retailmanager.spyhunter272.in.retailmanager.databinding.FragmentInvoiceViewBinding;
import retailmanager.spyhunter272.in.retailmanager.databinding.RowInvoiceBinding;

import retailmanager.spyhunter272.in.retailmanager.model.InvoiceViewHolder;
import retailmanager.spyhunter272.in.retailmanager.room.table.Invoice;
import retailmanager.spyhunter272.in.retailmanager.viewmodel.InvoiceViewModel;

import static retailmanager.spyhunter272.in.retailmanager.model.InvoiceViewHolder.INVOICE_SHOW_LIMIT;


public class InvoiceViewFragment extends Fragment implements InvoiceActivity.SearchViewDataChangeListner, InvoiceViewHolder.InvoiceViewDataChangeLisn {



    public InvoiceViewFragment() {

    }

    private InvoiceViewHolder invoiceViewHolder ;
    private  FragmentInvoiceViewBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         binding  = DataBindingUtil.inflate(inflater,R.layout.fragment_invoice_view,container,false);
        invoiceViewHolder=new InvoiceViewHolder(getContext(),this);
        binding.setInvoiceViewHolder(invoiceViewHolder);
        return binding.getRoot();
    }

    private InvoiceListAdepter invoiceListAdepter;

    private InvoiceViewModel invoiceViewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerview.setHasFixedSize(true);
        invoiceListAdepter = new InvoiceListAdepter();
        binding.recyclerview.setAdapter(invoiceListAdepter);

        invoiceViewModel = ViewModelProviders.of(this).get(InvoiceViewModel.class);

        getInvoiceData();

    }

    private void getInvoiceData(){

        invoiceViewModel.getInvoiceForList(INVOICE_SHOW_LIMIT,invoiceViewHolder.getOffset(),invoiceViewHolder.getMyCalendar()).observe(this, new Observer<List<Invoice>>() {
            @Override
            public void onChanged(@Nullable List<Invoice> invoices) {
                if(invoices.size()<=0){
                    invoiceViewHolder.setNoData(true);
                }else {
                    invoiceViewHolder.setNoData(false);
                }
                invoiceListAdepter.setInvoiceList(invoices);

            }
        });

    }

    private void deleteInvoice(Invoice invoice){

        invoiceViewModel.delete(invoice);

    }

    private void showInvoice(Invoice invoice){

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

    @Override
    public void onFilterDataChage() {
        getInvoiceData();
    }


    class InvoiceListAdepter extends RecyclerView.Adapter<InvoiceListAdepter.InvoiceViewHolder>{

        List<Invoice> invoiceList = new ArrayList<>();

        public void setInvoiceList(List<Invoice> invoiceList) {
            this.invoiceList = invoiceList;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public InvoiceViewHolder  onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            RowInvoiceBinding binding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.row_invoice,viewGroup,false);

            return new InvoiceViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull InvoiceViewHolder invoiceViewHolder, int i) {

            invoiceViewHolder.binding.setInvoiceHolder(invoiceList.get(i));
            invoiceViewHolder.binding.ibtnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteInvoice(invoiceList.get(i));
                }
            });

            invoiceViewHolder.binding.ibtnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showInvoice(invoiceList.get(i));
                }
            });

        }


        @Override
        public int getItemCount() {
            return invoiceList.size();
        }

        class InvoiceViewHolder extends RecyclerView.ViewHolder{

            RowInvoiceBinding binding;
            public InvoiceViewHolder(@NonNull RowInvoiceBinding  binding) {
                super(binding.getRoot());
                this.binding=binding;

            }

        }
    }

}
