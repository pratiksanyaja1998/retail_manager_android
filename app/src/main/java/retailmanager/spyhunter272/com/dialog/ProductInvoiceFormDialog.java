package retailmanager.spyhunter272.com.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import retailmanager.spyhunter272.com.R;
import retailmanager.spyhunter272.com.activity.BarcodeActivity;
import retailmanager.spyhunter272.com.databinding.DialogProductBinding;
import retailmanager.spyhunter272.com.holder.ProductDialogHolder;
import retailmanager.spyhunter272.com.room.table.Product;

@SuppressLint("ValidFragment")
public class ProductInvoiceFormDialog extends BottomSheetDialogFragment implements View.OnClickListener {

    private static int BARCODE_ACT_REQ_CODE = 55;

    private TextView tvBarcode;
    private Product product;

    private SharedPreferences myPreference;
    private ProductDialogHolder productDialogHolder;

    public ProductInvoiceFormDialog(Product product) {

        this.product = product;
        if(product.getGst()==0)
            product.setGst(0);

        else if(product.getGst()==5)
            product.setGst(1);

        else if(product.getGst()==12)
            product.setGst(2);

        else if(product.getGst()==18)
            product.setGst(3);

        else if(product.getGst()==28)
            product.setGst(4);
        else
            product.setGst(0);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DialogProductBinding dialogCustomerBinding = DataBindingUtil.inflate(inflater,R.layout.dialog_product,container,false);

        dialogCustomerBinding.setProduct(product);
        productDialogHolder = new ProductDialogHolder(true,true,true,true);
        myPreference =PreferenceManager.getDefaultSharedPreferences(getContext());
        productDialogHolder = new ProductDialogHolder(myPreference.getBoolean("hsn",true),false,
                false,myPreference.getBoolean("productGst",true) );
        dialogCustomerBinding.setProductDialogHolder(productDialogHolder);
        productDialogHolder.setUpdate(true);

        return dialogCustomerBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View dialog, @Nullable Bundle savedInstanceState) {

        myPreference = PreferenceManager.getDefaultSharedPreferences(getContext());

//      init view
        dialog.findViewById(R.id.btn_dialog_close).setOnClickListener(this);
        dialog.findViewById(R.id.btn_dialog_ok).setOnClickListener(this);

        tvBarcode = dialog.findViewById(R.id.tv_product_barcode);
        tvBarcode.setOnClickListener(this::onClick);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.tv_product_barcode:

                Intent intent = new Intent(getActivity(),BarcodeActivity.class);

                if(product!=null) {
                    if (!product.getBarcode().equals("") && product.getBarcode() != null) {
                        intent.putExtra(BarcodeActivity.KEY_INTENT_SEND_CONTENT, product.getBarcode());
                        intent.putExtra(BarcodeActivity.INTENT_KEY_FOR_SHOW, BarcodeActivity.FLAG_SHOW_BARCODE);
                    }
                }else{
                    intent.putExtra(BarcodeActivity.INTENT_KEY_FOR_SHOW, BarcodeActivity.FLAG_SCAN_BARCODE);
                }
                startActivityForResult(intent,BARCODE_ACT_REQ_CODE);

                break;

            case R.id.btn_dialog_close:
                dismiss();

                break;

            case R.id.btn_dialog_ok:

                Log.e("selected" , " cate "+product.getCategory()+" gst "+product.getGst());

                if(product.getGst()==0)
                    product.setGst(0);

                else if(product.getGst()==1)
                    product.setGst(5);

                else if(product.getGst()==2)
                    product.setGst(12);

                else if(product.getGst()==3)
                    product.setGst(18);

                else if(product.getGst()==4)
                    product.setGst(28);

                    product.setIn_stock_qty(product.getIn_stock_qty()+productDialogHolder.getUpdateQty());


                Log.e("selected added" , " cate "+product.getCategory()+" gst "+product.getGst());


                if( productsLisner!=null){

                    productsLisner.lisnProductsFromDialog(product);

                }

                dismiss();

                break;

        }

    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if(requestCode==BARCODE_ACT_REQ_CODE){

            if(resultCode==Activity.RESULT_OK){


                product.setBarcode(data.getStringExtra(BarcodeActivity.BARCODE_RESULT_BACK_ACT));
                product.notifyChange();
            }

        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof ProductsLisner){
            productsLisner = (ProductsLisner) context;

        }else {
            productsLisner = null;
        }

    }


    @Override
    public void onDetach() {
        super.onDetach();

        productsLisner = null;
    }

    private ProductsLisner productsLisner;


    public interface ProductsLisner{

        public void lisnProductsFromDialog(Product product);

    }

}