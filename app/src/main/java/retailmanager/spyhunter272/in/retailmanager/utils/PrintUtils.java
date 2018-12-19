package retailmanager.spyhunter272.in.retailmanager.utils;

import android.os.CancellationSignal;
import android.print.PageRange;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;

import java.io.File;

public class PrintUtils {


//    https://stackoverflow.com/questions/26841501/save-pdf-from-webview-on-android
    public void print(PrintDocumentAdapter printAdapter, final File path, final String fileName) {
//        printAdapter.onLayout(null, printAttributes, null, new PrintDocumentAdapter.LayoutResultCallback() {
//            @Override
//            public void onLayoutFinished(PrintDocumentInfo info, boolean changed) {
//                printAdapter.onWrite(null, getOutputFile(path, fileName), new CancellationSignal(), new PrintDocumentAdapter.WriteResultCallback() {
//                    @Override
//                    public void onWriteFinished(PageRange[] pages) {
//                        super.onWriteFinished(pages);
//                    }
//                });
//            }
//        }, null);
    }


}
