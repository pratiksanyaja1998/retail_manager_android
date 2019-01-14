package retailmanager.spyhunter272.com.bgworker;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import retailmanager.spyhunter272.com.room.RetailDatabase;
import retailmanager.spyhunter272.com.room.table.Customer;
import retailmanager.spyhunter272.com.room.table.Invoice;
import retailmanager.spyhunter272.com.room.table.InvoiceProduct;
import retailmanager.spyhunter272.com.room.table.Product;

import static retailmanager.spyhunter272.com.template.DefaultTemplate.brandDetails;
import static retailmanager.spyhunter272.com.template.DefaultTemplate.customer;
import static retailmanager.spyhunter272.com.template.DefaultTemplate.footer;
import static retailmanager.spyhunter272.com.template.DefaultTemplate.header;
import static retailmanager.spyhunter272.com.template.DefaultTemplate.products;

public class SaveInvoiceBgWorker extends AsyncTask<Void,Void,Long> {

    private RetailDatabase retailDatabase;
    private Invoice invoice;
    private OnProgressCompliteLisn onProgressCompliteLisn;

    public SaveInvoiceBgWorker(Context context, Invoice invoice,OnProgressCompliteLisn onProgressCompliteLisn) {
        this.retailDatabase = RetailDatabase.getInstance(context);
        this.invoice = invoice;
        this.onProgressCompliteLisn = onProgressCompliteLisn;
    }

    @Override
    protected Long doInBackground(Void... voids) {

        Customer customer =invoice.getCustomer();

        if(customer.isNew()) {

                Log.e("post", "new customer");
                invoice.setCustomerId(retailDatabase.customerDao().insert(customer));

        }else if(customer.isUpdate()){

            Log.e("post","update customer ++ " +customer.getId());

            retailDatabase.customerDao().update(customer);
            invoice.setCustomerId(customer.getId());

        }else {

            Log.e("post","normal customer");

            invoice.setCustomerId(customer.getId());

        }

        long invoiceId = retailDatabase.invoiceDao().insert(invoice);
        Log.e("post",invoiceId+" invoice id");

        List<InvoiceProduct>  invoiceProductList = new ArrayList<>();
        List<Product> productList = invoice.getProductList();

        for(int i=0;i<productList.size();i++){

            invoiceProductList.add(new InvoiceProduct(
                    productList.get(i).getName(),
                    productList.get(i).getHsn(),
                    productList.get(i).getBarcode(),
                    productList.get(i).getS_price(),
                    productList.get(i).getTotal(),
                    productList.get(i).getIn_stock_qty(),
                    productList.get(i).getGst(),
                    invoiceId));

        }

        retailDatabase.invoiceProductDao().addAllInvoiceProduct(invoiceProductList);

        return invoiceId;
    }

    @Override
    protected void onPostExecute(Long invoiceId) {
        onProgressCompliteLisn.onProgressComplited(invoiceId);
    }


    public interface OnProgressCompliteLisn{
        void onProgressComplited(Long invoiceId);
    }

}
