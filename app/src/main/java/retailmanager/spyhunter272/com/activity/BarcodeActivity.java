package retailmanager.spyhunter272.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import retailmanager.spyhunter272.com.R;

public class BarcodeActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int FLAG_SHOW_BARCODE = 5;
    public static final int FLAG_SCAN_BARCODE = 6;
    public static final String INTENT_KEY_FOR_SHOW = "show";
    public static final String KEY_INTENT_SEND_CONTENT = "data";
    public static final String BARCODE_RESULT_BACK_ACT = "rs";

    private ImageView barcodeImageView;
    private TextView contentTxt;
    private Button btnOk, btnCancel, btnScan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        barcodeImageView = findViewById(R.id.img_barcode_view);
        contentTxt = findViewById(R.id.tv_barcode_content);

        btnOk = findViewById(R.id.btn_ok_barcode);
        btnOk.setOnClickListener(this);
        btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this::onClick);
        btnScan = findViewById(R.id.btn_scan_again);
        btnScan.setOnClickListener(this::onClick);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int flag= getIntent().getIntExtra(INTENT_KEY_FOR_SHOW, -1);
        if (flag == FLAG_SHOW_BARCODE) {

            String content = getIntent().getStringExtra(KEY_INTENT_SEND_CONTENT);
            ganareteBarcode(content, BarcodeFormat.CODE_128);

        } else if(flag==FLAG_SCAN_BARCODE) {

            scanBarcode();

        }

    }


    private void scanBarcode() {
        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        scanIntegrator.setOrientationLocked(true);
        scanIntegrator.setBeepEnabled(true);
        scanIntegrator.setCaptureActivity(CaptureActivityPortrait.class);
        scanIntegrator.initiateScan();

    }


    private void ganareteBarcode(String content, BarcodeFormat barcodeFormat) {

        // Whatever you need to encode in the QR code

        contentTxt.setText(content);

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(content, barcodeFormat, 900, 400);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            barcodeImageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanningResult != null && resultCode == Activity.RESULT_OK) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            ganareteBarcode(scanContent, BarcodeFormat.valueOf(scanFormat));
//            formatTxt.setText(scanFormat);
            contentTxt.setText(scanContent);
        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_ok_barcode:

                resultOK();
                break;

            case R.id.btn_cancel:
                resultCancel();

                break;

            case R.id.btn_scan_again:

                scanBarcode();

                break;

        }
    }


    private void resultOK() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(BARCODE_RESULT_BACK_ACT, contentTxt.getText());
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    private void resultCancel() {
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, resultIntent);
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            resultCancel();
        }

        return true;
    }
}
