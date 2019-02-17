package retailmanager.spyhunter272.com.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import retailmanager.spyhunter272.com.R;

public class CustomAlertDialog  {

    private static Dialog getDialog(Context
            context,CustomAlertDialogEvent customAlertDialogEvent){

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
        return  dialog;
    }


    public static void show(Context context,CustomAlertDialogEvent customAlertDialogEvent){


        getDialog(context,customAlertDialogEvent).show();

    }



    public static void showMessage(Context context,String message,CustomAlertDialogEvent customAlertDialogEvent){

        Dialog dialog = getDialog(context,customAlertDialogEvent);

        TextView messageTv = dialog.findViewById(R.id.tv_alert_message);
        messageTv.setText(message);

        dialog.show();

    }


    public interface CustomAlertDialogEvent{
         void eventCancel();
         void eventDone();
    }



}
