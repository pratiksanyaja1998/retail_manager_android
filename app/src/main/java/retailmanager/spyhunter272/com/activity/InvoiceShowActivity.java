package retailmanager.spyhunter272.com.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.webviewtopdf.PdfView;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import retailmanager.spyhunter272.com.R;
import retailmanager.spyhunter272.com.bgworker.GetInvoiceBgWorker;
import retailmanager.spyhunter272.com.dialog.PreviewInvoiceDialog;
import retailmanager.spyhunter272.com.room.RetailDatabase;
import retailmanager.spyhunter272.com.room.table.Invoice;
import retailmanager.spyhunter272.com.template.DefaultTemplate;
import retailmanager.spyhunter272.com.template.ResponsiveTemplate;
import retailmanager.spyhunter272.com.utils.StaticInfoUtils;

public class InvoiceShowActivity extends AppCompatActivity implements GetInvoiceBgWorker.GetInvoiceListener, AdapterView.OnItemSelectedListener {

    private WebView webView;
    private ProgressBar progressBar;
    private Spinner spinnerTemplate;
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
        spinnerTemplate = findViewById(R.id.sp_select_templete);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setAllowFileAccess(true);

        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        new GetInvoiceBgWorker(RetailDatabase.getInstance(this),id,this).execute();

    }


    @Override
    public void InvoiceGetSuccess(Invoice invoice) {
        progressBar.setVisibility(View.GONE);
        this.invoice=invoice;

        spinnerTemplate.setOnItemSelectedListener(this);
        webView.loadData(DefaultTemplate.getTemplateData(invoice,this),"text/html", "UTF-8");
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (position){
            case 0:
                webView.loadData(DefaultTemplate.getTemplateData(invoice,this),"text/html", "UTF-8");

//                webView.loadUrl("file:///android_asset/default.html");
//                webView.loadData(DefaultTemplate.getTemplateData(invoice,this),"text/html", "UTF-8");
                break;

            case 1:
                webView.loadData(ResponsiveTemplate.getTemplateData(invoice,this),"text/html", "UTF-8");
//                webView.loadUrl("file:///android_asset/withImage.html");
//                webView.loadData(DefaultTemplate.getTemplateData(invoice,this),"text/html", "UTF-8");
                break;
//            case  2:
////                webView.loadUrl("file:///android_asset/color.html");
//                break;
//            case 3:
////                webView.loadUrl("file:///android_asset/widthLogo.html");
//                break;
//            case 4:
////                webView.loadUrl("file:///android_asset/simple.html");
//                break;

//            default:

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                sharePdf(invoice);
                break;

        }

        return super.onOptionsItemSelected(item);
    }


    public  void createWebPagePrint(WebView webView) {

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

    }


    private void sharePdf(Invoice invoice){

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.show();

        File invoiceFile = StaticInfoUtils.getInvoiceFile(this,invoice);

        if(invoiceFile.exists()){
            invoiceFile.delete();
        }

        PdfView.createWebPrintJob(this, webView, StaticInfoUtils.getInvoiceDir(this,invoice), StaticInfoUtils.getInvoiceFileName(this,invoice), new PdfView.Callback() {

            @Override
            public void success(String path) {

                Log.e("post","path "+path );

                progressDialog.dismiss();

//                Toast.makeText(InvoiceShowActivity.this,"Invoice Saved Successfully",Toast.LENGTH_SHORT).show();
//                PdfView.openPdfFile(getContext(),getString(R.string.app_name),"Do you want to open the pdf file?"+fileName,path);
//                startActivity(new Intent(Intent.ACTION_SEND).setDataAndType(Uri.fromFile(StaticInfoUtils.getInvoiceFile(InvoiceShowActivity.this,invoice)), "application/pdf"));



//                Intent intentShareFile = new Intent(Intent.ACTION_SEND);
//                File fileWithinMyDir = new File(path);
//
//                if(fileWithinMyDir.exists()) {
//                    intentShareFile.setType("application/pdf");
//                    intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(fileWithinMyDir));
//                    intentShareFile.putExtra(Intent.EXTRA_SUBJECT, "Sharing File...");
//                    intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File...");
//
//                    startActivity(Intent.createChooser(intentShareFile, "Share File"));
//
//                }else {
//                    Toast.makeText(InvoiceShowActivity.this,"Something Wrong ! Try Again !",Toast.LENGTH_SHORT).show();
//                }

                shareFile(path);

            }

//            /storage/emulated/0/RetailManager/invoice/2019/0/13/IN002.pdf

            @Override
            public void failure() {
                progressDialog.dismiss();

            }
        });

    }

    private void shareFile(String filePath) {


        Intent intentShareFile = new Intent(Intent.ACTION_SEND);
        File fileWithinMyDir = new File(filePath);

        if (fileWithinMyDir.exists()) {
            intentShareFile.setType("text/*");

           Uri  photoURI = FileProvider.getUriForFile(InvoiceShowActivity.this,
                    getString(R.string.file_provider_authority),
                   fileWithinMyDir);

            intentShareFile.putExtra(Intent.EXTRA_STREAM, photoURI);
            intentShareFile.putExtra(Intent.EXTRA_SUBJECT, "invoice");
            intentShareFile.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_string_email));

            this.startActivity(Intent.createChooser(intentShareFile, fileWithinMyDir.getName()));

        }else {

            Toast.makeText(InvoiceShowActivity.this,"Something Wrong ! Try Again !",Toast.LENGTH_SHORT).show();
        }
    }



}
