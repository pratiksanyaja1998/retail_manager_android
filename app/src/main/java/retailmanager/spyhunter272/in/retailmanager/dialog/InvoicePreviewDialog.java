package retailmanager.spyhunter272.in.retailmanager.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;

import retailmanager.spyhunter272.in.retailmanager.R;
import retailmanager.spyhunter272.in.retailmanager.room.table.Invoice;

@SuppressLint("ValidFragment")
public class InvoicePreviewDialog extends AppCompatDialogFragment {

    private Invoice invoice;

    public InvoicePreviewDialog(Invoice invoice) {
        this.invoice = invoice;
    }

    private WebView webView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog =new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_invoice_preview);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        webView = dialog.findViewById(R.id.webview_preview_invoice);

        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {



    }


}
