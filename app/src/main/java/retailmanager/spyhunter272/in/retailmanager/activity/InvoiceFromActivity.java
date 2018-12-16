package retailmanager.spyhunter272.in.retailmanager.activity;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retailmanager.spyhunter272.in.retailmanager.dialog.CustProPickerDialog;
import retailmanager.spyhunter272.in.retailmanager.dialog.CustomerDialog;
import retailmanager.spyhunter272.in.retailmanager.R;
import retailmanager.spyhunter272.in.retailmanager.customview.NestedListView;
import retailmanager.spyhunter272.in.retailmanager.databinding.ActivityInvoiceFromBinding;
import retailmanager.spyhunter272.in.retailmanager.dialog.CustomerInvoiceFormDialog;
import retailmanager.spyhunter272.in.retailmanager.dialog.ProductDialog;
import retailmanager.spyhunter272.in.retailmanager.dialog.ProductInvoiceFormDialog;
import retailmanager.spyhunter272.in.retailmanager.model.InvoiceFromHolder;
import retailmanager.spyhunter272.in.retailmanager.room.RetailDatabase;
import retailmanager.spyhunter272.in.retailmanager.room.table.Customer;
import retailmanager.spyhunter272.in.retailmanager.room.table.Invoice;
import retailmanager.spyhunter272.in.retailmanager.room.table.InvoiceProduct;
import retailmanager.spyhunter272.in.retailmanager.room.table.Product;

import retailmanager.spyhunter272.in.retailmanager.databinding.RowInvoiceFormProductBinding;
import retailmanager.spyhunter272.in.retailmanager.room.tabledao.InvoiceDao;
import retailmanager.spyhunter272.in.retailmanager.viewmodel.InvoiceViewModel;

public class InvoiceFromActivity extends AppCompatActivity implements ProductInvoiceFormDialog.ProductsLisner,CustProPickerDialog.DialogSearchItemSelectLisner, CustomerInvoiceFormDialog.CustomerLisner {

    private InvoiceFromHolder invoiceFromHolder;
    private NestedListView listView;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityInvoiceFromBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_invoice_from);
        invoiceFromHolder = new InvoiceFromHolder(this);
        binding.setInvoiceFromHolder(invoiceFromHolder);

        listView  = findViewById(R.id.listproductholder);
        listAdapter = new ListAdapter();
        listView.setAdapter(listAdapter);

    }

    public void onClick(View view){

        switch (view.getId()){

            case R.id.btn_new_customer:
                CustomerInvoiceFormDialog customerDialog = new CustomerInvoiceFormDialog();
                customerDialog.show(getSupportFragmentManager(),null);
                break;

            case R.id.btn_search_customer:
                CustProPickerDialog custProPickerDialog = new CustProPickerDialog();
                custProPickerDialog.setCustomerOrProduct(true);
                custProPickerDialog.show(getSupportFragmentManager(),null);
                break;

            case R.id.btn_search_product:
                CustProPickerDialog custProPickerDialog1 = new CustProPickerDialog();
                custProPickerDialog1.setCustomerOrProduct(false);
                custProPickerDialog1.show(getSupportFragmentManager(),null);
                break;

//            case R.id.btnaddnewpro:
//                ProductDialog productDialog = new ProductDialog();
//                productDialog.setNewProducts(true);
//                productDialog.show(getSupportFragmentManager(),null);
//                break;

            case R.id.btn_update_customer:
                CustomerInvoiceFormDialog customerDialog2 = new CustomerInvoiceFormDialog();
                customerDialog2.setArguments(invoiceFromHolder.getCustomerObj().getBundle());
                customerDialog2.show(getSupportFragmentManager(),null);
                break;

        }

    }

    private void printInvoice(){

        Customer customer =invoiceFromHolder.getCustomerObj();
        Invoice invoice = new Invoice();
        invoice.setName(customer.getName());

        int mYear, mMonth, mDay;
        Calendar calendar = invoiceFromHolder.getCalendar();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        invoice.setDd(mDay);
        invoice.setMm(mMonth);
        invoice.setYyyy(mYear);
        invoice.setTotal(invoiceFromHolder.getTotalWithDiscount());
        invoice.setDiscount(invoiceFromHolder.getDiscount());
        invoice.setTprchage(invoiceFromHolder.isTprcharge());
        invoice.setDesciption(invoiceFromHolder.getDescription());
        invoice.setPaymentMethrd(invoiceFromHolder.getPaymethordString());

        new InsertInvoiceBgWorker(RetailDatabase.getInstance(this),invoice,listAdapter.proLists).execute();

    }

    class InsertInvoiceBgWorker extends AsyncTask<Void,Void,Void>{


        private RetailDatabase retailDatabase;
        private Invoice invoice;
        private List<Product> productList;
        private ProgressDialog progressDialog;

        public InsertInvoiceBgWorker(RetailDatabase retailDatabase, Invoice invoice, List<Product> productList) {
            this.retailDatabase = retailDatabase;
            this.invoice = invoice;
            this.productList = productList;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(InvoiceFromActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            long invoiceId = retailDatabase.invoiceDao().insert(invoice);
            Log.e("idis",invoiceId+"");

            List<InvoiceProduct>  invoiceProductList = new ArrayList<>();

            for(int i=0;i<productList.size();i++){

                invoiceProductList.add(new InvoiceProduct(
                        productList.get(i).getName(),
                        productList.get(i).getHsn(),
                        productList.get(i).getBarcode(),
                        productList.get(i).getS_price(),
                        productList.get(i).getTotal(),
                        productList.get(i).getIn_stock_qty(),
                        invoiceId

                ));

            }

            retailDatabase.invoiceProductDao().addAllInvoiceProduct(invoiceProductList);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

         getMenuInflater().inflate(R.menu.invoice_form, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_print) {

            printInvoice();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void lisnCustomerFromDialog(Customer customer) {
        invoiceFromHolder.setCustomer(customer);
        invoiceFromHolder.setUpdateCustomer(true);
        invoiceFromHolder.notifyChange();
    }

    @Override
    public void lisnCustomerFormSearchDialog(Customer customer) {
        lisnCustomerFromDialog(customer);
    }

    @Override
    public void lisnProductFormSearchDialog(Product product) {
        listAdapter.addProduct(product);
    }

    @Override
    public void lisnProductsFromDialog(Product product) {
        listAdapter.updateProduct(product);
    }

    public void onListItemClick( int position) {
        ProductInvoiceFormDialog productDialog =  new ProductInvoiceFormDialog(listAdapter.proLists.get(position));
        Product product = listAdapter.proLists.get(position);
        productDialog.setArguments(product.getBundle());
        productDialog.show(getSupportFragmentManager(),null);
    }

    private void updateTotal(float total){
        invoiceFromHolder.setTotalInvoice(total);
    }

    class ListAdapter extends BaseAdapter {

        List<Product> proLists = new ArrayList<>();
        float totalInvoice = 0;

        public void setProLists(List<Product> proLists) {
            this.proLists = proLists;
            notifyDataSetChanged();
        }

        private void deleteProduct(int i){
            totalInvoice-=proLists.get(i).getTotal();
            updateTotal(totalInvoice);
            proLists.remove(i);
            notifyDataSetChanged();
        }

        public void addProduct(Product product) {
            float total = (float) (product.getIn_stock_qty()*product.getS_price());
            total =total+((total*product.getGst())/100);
            totalInvoice+=total;
            updateTotal(totalInvoice);
            product.setTotal(total);
            proLists.add(product);
            notifyDataSetChanged();
        }

        public void updateProduct(Product product){

            for (int i = 0;i<proLists.size();i++){

                if(product.getId()==proLists.get(i).getId()){

                    totalInvoice-=proLists.get(i).getTotal();

                    float total = (float) (product.getIn_stock_qty()*product.getS_price());
                    total =total + ((total*product.getGst())/100);
                    totalInvoice+=total;

                    updateTotal(totalInvoice);
                    product.setTotal(total);
                    proLists.set(i,product);
                    notifyDataSetChanged();
                }

            }

        }

        @Override
        public int getCount() {
            return proLists.size();
        }

        @Override
        public Object getItem(int i) {
            return proLists.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            RowInvoiceFormProductBinding binding;

            if (view == null) {

                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_invoice_form_product, null);
                binding = DataBindingUtil.bind(view);
                view.setTag(binding);

            } else {

                binding = (RowInvoiceFormProductBinding) view.getTag();

            }

            binding.setProduct(proLists.get(i));

            binding.rowIbtnprodelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteProduct(i);
                }
            });

            binding.rowPname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onListItemClick(i);
                }
            });

            binding.rowPtex.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onListItemClick(i);
                }
            });

            return binding.getRoot();


        }

    }

}
