package retailmanager.spyhunter272.com.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import retailmanager.spyhunter272.com.R;
import retailmanager.spyhunter272.com.databinding.DialogProductInvoiceFormBinding;
import retailmanager.spyhunter272.com.holder.ProductDialogHolder;
import retailmanager.spyhunter272.com.room.table.Product;

@SuppressLint("ValidFragment")
public class ProductInvoiceFormDialog extends BottomSheetDialogFragment implements View.OnClickListener {


    private Product product;

    private SharedPreferences myPreference;
    private ProductDialogHolder productDialogHolder;


    public ProductInvoiceFormDialog(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle!=null){
            product = Product.getProductFromBundle(bundle);
        }else {
            product = new Product();
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        DialogProductInvoiceFormBinding dialogCustomerBinding = DataBindingUtil.inflate(inflater,R.layout.dialog_product_invoice_form,container,false);

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

        dialog.findViewById(R.id.btn_dialog_close).setOnClickListener(this);
        dialog.findViewById(R.id.btn_dialog_ok).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){


            case R.id.btn_dialog_close:
                dismiss();

                break;

            case R.id.btn_dialog_ok:

                if( productsLisner!=null){

                    productsLisner.lisnProductsFromDialog(product);

                }

                dismiss();

                break;

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

         void lisnProductsFromDialog(Product product);

    }

}