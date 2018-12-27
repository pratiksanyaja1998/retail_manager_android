package retailmanager.spyhunter272.com.bgworker;

import android.os.AsyncTask;
import android.view.View;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import retailmanager.spyhunter272.com.dialog.PreviewInvoiceDialog;
import retailmanager.spyhunter272.com.room.RetailDatabase;
import retailmanager.spyhunter272.com.room.table.Invoice;

public class GetInvoiceBgWorker extends AsyncTask<Void,Void,Invoice> {

    private RetailDatabase retailDatabase;
    private long invoiceId;
    private GetInvoiceListener getInvoiceListener;

    public GetInvoiceBgWorker(RetailDatabase retailDatabase,long invoiceId,GetInvoiceListener getInvoiceListener) {
        this.retailDatabase = retailDatabase;
        this.invoiceId = invoiceId;
        this.getInvoiceListener = getInvoiceListener;
    }

    @Override
    protected Invoice doInBackground(Void... voids) {

        Invoice  invoice =  retailDatabase.invoiceDao().getInvoice(invoiceId);
        invoice.setInvoiceProducts(retailDatabase.invoiceProductDao().getInvoiceProducts(invoiceId));
        invoice.setCustomer(retailDatabase.customerDao().getInvoiceCustomer(invoice.getCustomerId()));

        return invoice;
    }

    @Override
    protected void onPostExecute(Invoice invoice) {
        getInvoiceListener.InvoiceGetSuccess(invoice);
//        progressBar.setVisibility(View.GONE);
//        PreviewInvoiceDialog.this.invoice=invoice;
//
//        String postData = null;
//
//        try {
//            postData = "data=" + URLEncoder.encode(invoice.getJsonData(), "UTF-8") + "&message=" + URLEncoder.encode("true", "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            postData = "message=false";
//        }
//
//        webView.postUrl("file:///android_asset/default.html",postData.getBytes());

    }

    public interface GetInvoiceListener{
        public void InvoiceGetSuccess(Invoice invoice);

    }

}
