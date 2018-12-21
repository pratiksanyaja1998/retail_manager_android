package retailmanager.spyhunter272.com.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Calendar;

import retailmanager.spyhunter272.com.adapter.InvoiceFormProductListAdapter;
import retailmanager.spyhunter272.com.bgworker.SaveInvoiceBgWorker;
import retailmanager.spyhunter272.com.dialog.CustProPickerDialog;
import retailmanager.spyhunter272.com.R;
import retailmanager.spyhunter272.com.customview.NestedListView;
import retailmanager.spyhunter272.com.databinding.ActivityInvoiceFromBinding;
import retailmanager.spyhunter272.com.dialog.CustomerInvoiceFormDialog;
import retailmanager.spyhunter272.com.dialog.PreviewInvoiceDialog;
import retailmanager.spyhunter272.com.dialog.ProductInvoiceFormDialog;
import retailmanager.spyhunter272.com.model.InvoiceFromHolder;
import retailmanager.spyhunter272.com.room.table.Customer;
import retailmanager.spyhunter272.com.room.table.Invoice;
import retailmanager.spyhunter272.com.room.table.Product;

public class InvoiceFromActivity extends AppCompatActivity implements ProductInvoiceFormDialog.ProductsLisner,
        CustProPickerDialog.DialogSearchItemSelectLisner,
        CustomerInvoiceFormDialog.CustomerLisner,
        InvoiceFormProductListAdapter.ProductListLiner {

    private InvoiceFromHolder invoiceFromHolder;
    private NestedListView listView;
    private InvoiceFormProductListAdapter listAdapter;
    private ActivityInvoiceFromBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_invoice_from);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        invoiceFromHolder = new InvoiceFromHolder(this);
        binding.setInvoiceFromHolder(invoiceFromHolder);

        listView  = findViewById(R.id.listproductholder);
        listAdapter = new InvoiceFormProductListAdapter(this);
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

    private PreviewInvoiceDialog previewInvoiceDialog;

    private void saveInvoice(){

        if(previewInvoiceDialog==null)
            previewInvoiceDialog = new PreviewInvoiceDialog();

        previewInvoiceDialog.show(getSupportFragmentManager(),null);

        Customer customer =invoiceFromHolder.getCustomerObj();
        Invoice invoice = new Invoice();
        invoice.setCustomer(customer);
        invoice.setName(customer.getName());
        invoice.setMobile(customer.getMobile());
        Calendar calendar = invoiceFromHolder.getCalendar();
        invoice.setDd(calendar.get(Calendar.DAY_OF_MONTH));
        invoice.setMm(calendar.get(Calendar.MONTH));
        invoice.setYyyy(calendar.get(Calendar.YEAR));
        invoice.setTotal(invoiceFromHolder.getTotalWithDiscount());
        invoice.setDiscount(invoiceFromHolder.getDiscount());
        invoice.setGsttype(binding.spGsttype.getSelectedItemPosition());
        invoice.setTprchage(invoiceFromHolder.isTprcharge());
        invoice.setDesciption(invoiceFromHolder.getDescription());
        invoice.setPaymentMethrd(invoiceFromHolder.getPaymethordString());
        invoice.setProductList(listAdapter.getProLists());

        new SaveInvoiceBgWorker(this,invoice,new SaveInvoiceBgWorker.OnProgressCompliteLisn(){
            @Override
            public void onProgressComplited(Long invoiceId) {
                if(previewInvoiceDialog!=null)
                    previewInvoiceDialog.setInvoiceId(invoiceId);
            }
        }).execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.invoice_form, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_print) {

            saveInvoice();
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

    @Override
    public void openProduct(Product product) {
        ProductInvoiceFormDialog productDialog =  new ProductInvoiceFormDialog(product);
        productDialog.show(getSupportFragmentManager(),null);
    }

    @Override
    public void updateTotal(double total) {
        invoiceFromHolder.setTotalInvoice(total);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }


}
