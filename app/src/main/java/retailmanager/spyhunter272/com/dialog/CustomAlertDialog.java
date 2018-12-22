package retailmanager.spyhunter272.com.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import retailmanager.spyhunter272.com.R;

public class CustomAlertDialog  {



    public static void show(Context context,CustomAlertDialogEvent customAlertDialogEvent){

        Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);

        dialog.setContentView(R.layout.dialog_alert);
        dialog.findViewById(R.id.btn_alert_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customAlertDialogEvent.eventDone();
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btn_alert_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customAlertDialogEvent.eventCancel();
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();

    }

    public interface CustomAlertDialogEvent{
        public void eventCancel();
        public void eventDone();
    }



}
