package retailmanager.spyhunter272.com.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.webviewtopdf.PdfView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import retailmanager.spyhunter272.com.R;
import retailmanager.spyhunter272.com.bgworker.GetInvoiceBgWorker;
import retailmanager.spyhunter272.com.dialog.PreviewInvoiceDialog;
import retailmanager.spyhunter272.com.room.RetailDatabase;
import retailmanager.spyhunter272.com.room.table.Invoice;
import retailmanager.spyhunter272.com.utils.StaticInfoUtils;

public class InvoiceShowActivity extends AppCompatActivity implements GetInvoiceBgWorker.GetInvoiceListener {

    private WebView webView;
    private ProgressBar progressBar;
    private Spinner spinner;
    private Invoice invoice=null;

    public static String KEY_INVOICE_ID = "invoiceId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_show);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);


        long id = getIntent().getLongExtra(KEY_INVOICE_ID,-1);

        if(id==-1){
            Toast.makeText(this,"Invoice id is not valied",Toast.LENGTH_SHORT).show();
            finish();
        }

        progressBar = findViewById(R.id.progress_circular);
        webView = findViewById(R.id.webview_preview_invoice);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setAllowFileAccess(true);


        new GetInvoiceBgWorker(RetailDatabase.getInstance(this),id,this).execute();

    }


    @Override
    public void InvoiceGetSuccess(Invoice invoice) {
        progressBar.setVisibility(View.GONE);
        this.invoice=invoice;

        String postData = null;

        try {
            postData = "data=" + URLEncoder.encode(invoice.getJsonData(), "UTF-8") + "&message=" + URLEncoder.encode("true", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            postData = "message=false";
        }

        webView.postUrl("file:///android_asset/default.html",postData.getBytes());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_invoice_show,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.menu_print:
                createWebPagePrint(webView);

                break;

            case R.id.menu_share:
                savePdf(invoice);
                break;

        }

        return super.onOptionsItemSelected(item);
    }


    public  void createWebPagePrint(WebView webView) {
		/*if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return;*/
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();
        String jobName = getString(R.string.app_name) + " Document";
        PrintAttributes.Builder builder = new PrintAttributes.Builder();
        builder.setMediaSize(PrintAttributes.MediaSize.ISO_A5);
        PrintJob printJob = printManager.print(jobName, printAdapter, builder.build());

        if(printJob.isCompleted()){
            Toast.makeText(this, R.string.print_complete, Toast.LENGTH_LONG).show();
        }
        else if(printJob.isFailed()){
            Toast.makeText(this, R.string.print_failed, Toast.LENGTH_LONG).show();
        }
        // Save the job object for later status checking
    }


    private void savePdf(Invoice invoice){

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.show();

        PdfView.createWebPrintJob(this, webView, StaticInfoUtils.getInvoiceDir(this,invoice), StaticInfoUtils.getInvoiceFileName(this,invoice), new PdfView.Callback() {

            @Override
            public void success(String path) {
                progressDialog.dismiss();
                Toast.makeText(InvoiceShowActivity.this,"Invoice Saved Successfully",Toast.LENGTH_SHORT).show();
//                PdfView.openPdfFile(getContext(),getString(R.string.app_name),"Do you want to open the pdf file?"+fileName,path);
                startActivity(new Intent(Intent.ACTION_VIEW).setDataAndType(Uri.fromFile(StaticInfoUtils.getInvoiceFile(InvoiceShowActivity.this,invoice)), "application/pdf"));
            }

            @Override
            public void failure() {
                progressDialog.dismiss();

            }
        });

    }


}
