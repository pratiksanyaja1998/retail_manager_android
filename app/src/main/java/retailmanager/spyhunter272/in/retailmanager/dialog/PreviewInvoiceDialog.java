package retailmanager.spyhunter272.in.retailmanager.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import retailmanager.spyhunter272.in.retailmanager.R;

@SuppressLint("ValidFragment")
public class PreviewInvoiceDialog extends DialogFragment implements View.OnClickListener {


    private long invoiceId;

    public PreviewInvoiceDialog(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    private WebView webView;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_invoice_preview);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(this);
        dialog.findViewById(R.id.btn_print).setOnClickListener(this);
        webView = dialog.findViewById(R.id.webview_preview_invoice);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        String postData = null;
        try {
            postData = "username=" + URLEncoder.encode("{'name':'pratik','address':'203 opera','data':[{'saads':'asdf','saads':'asdf'},{'saads':'asdf'}]}", "UTF-8") + "&password=" + URLEncoder.encode("pratik", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            postData = "message=false";
        }
        webView.postUrl("http://192.168.0.108/print.php",postData.getBytes());

        return dialog;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel:

                dismiss();
                break;

            case R.id.btn_print:

                createWebPagePrint(webView);

                break;

        }
    }


    public  void createWebPagePrint(WebView webView) {
		/*if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return;*/
        PrintManager printManager = (PrintManager) getContext().getSystemService(Context.PRINT_SERVICE);
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();
        String jobName = getString(R.string.app_name) + " Document";
        PrintAttributes.Builder builder = new PrintAttributes.Builder();
        builder.setMediaSize(PrintAttributes.MediaSize.ISO_A5);
        PrintJob printJob = printManager.print(jobName, printAdapter, builder.build());

        if(printJob.isCompleted()){
            Toast.makeText(getContext(), R.string.print_complete, Toast.LENGTH_LONG).show();
        }
        else if(printJob.isFailed()){
            Toast.makeText(getContext(), R.string.print_failed, Toast.LENGTH_LONG).show();
        }
        // Save the job object for later status checking
    }
}
