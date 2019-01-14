package retailmanager.spyhunter272.com.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retailmanager.spyhunter272.com.adapter.ProdCateSpinnerBaseAdepter;
import retailmanager.spyhunter272.com.R;
import retailmanager.spyhunter272.com.activity.BarcodeActivity;
import retailmanager.spyhunter272.com.holder.ProductDialogHolder;
import retailmanager.spyhunter272.com.room.table.Product;
import retailmanager.spyhunter272.com.room.table.ProductCategory;
import retailmanager.spyhunter272.com.utils.Common;
import retailmanager.spyhunter272.com.viewmodel.ProductCategoryViewModel;
import retailmanager.spyhunter272.com.viewmodel.ProductViewModel;

import retailmanager.spyhunter272.com.databinding.DialogProductBinding;


//https://stackoverflow.com/questions/47718872/rowid-after-insert-in-room?rq=1
//https://stackoverflow.com/questions/44364240/android-room-get-the-id-of-new-inserted-row-with-auto-generate

@SuppressLint("ValidFragment")
public class ProductDialog extends DialogFragment implements View.OnClickListener {

    private static int BARCODE_ACT_REQ_CODE = 55;

    private ProductViewModel productViewModel;

    private Product product;
    private ProductCategoryViewModel productCategoryViewModel;

    private ProdCateSpinnerBaseAdepter prodCateSpinnerBaseAdepter;
    private boolean isUpdate ;
    private SharedPreferences myPreference;
    private   ProductDialogHolder productDialogHolder;


    public ProductDialog(Product product, boolean isUpdate) {
        this.product = product;
        this.isUpdate = isUpdate;

//        product.setGstFromNumber();
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

    }


    private DialogProductBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        binding = DataBindingUtil.inflate(inflater,R.layout.dialog_product,container,false);

        binding.setProduct(product);
        myPreference =PreferenceManager.getDefaultSharedPreferences(getContext());
        productDialogHolder = new ProductDialogHolder(myPreference.getBoolean("hsn",true),myPreference.getBoolean("bprice",true),
                myPreference.getBoolean("barcode",true),myPreference.getBoolean("productGst",true) );
        productDialogHolder.setUpdate(isUpdate);
        binding.setProductDialogHolder(productDialogHolder);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View dialog, @Nullable Bundle savedInstanceState) {

        myPreference = PreferenceManager.getDefaultSharedPreferences(getContext());

//      init view
        dialog.findViewById(R.id.btn_dialog_close).setOnClickListener(this);
        dialog.findViewById(R.id.btn_dialog_ok).setOnClickListener(this);

        binding.tvProductBarcode.setOnClickListener(this::onClick);

        prodCateSpinnerBaseAdepter = new ProdCateSpinnerBaseAdepter();
        binding.spinnerProductCategory.setAdapter(prodCateSpinnerBaseAdepter);

//      setup object
        productCategoryViewModel = ViewModelProviders.of(this).get(ProductCategoryViewModel.class);

        getProductCategory();

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

    }

    private void getProductCategory() {

        productCategoryViewModel.getAllProductCategory().observe(this, new Observer<List<ProductCategory>>() {
            @Override
            public void onChanged(@Nullable List<ProductCategory> productCategories) {
                prodCateSpinnerBaseAdepter.setProductCategories(productCategories);

                int counter = 1;

                for (ProductCategory item : productCategories) {

                    if (item.getId() == product.getCategory()) {

                        product.setCategory(counter);

                        product.notifyChange();
                        continue;
                    }
                    counter++;

                }

            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.tv_product_barcode:

                Intent intent = new Intent(getActivity(),BarcodeActivity.class);

                if(product!=null) {
                    if ( product.getBarcode() != null) {
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
                product.setCategory( prodCateSpinnerBaseAdepter.productCategories.get(product.getCategory()).getId());

                String error =product.isValied();
                if(error!=null){
                    Common.formErrorAnimation(getContext(),binding.getRoot(),error);
                    return;

                }

//                product.setGstFromNumber();
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

                if (productDialogHolder.isUpdate() && product.checkValidation()) {

                    productViewModel.update(product);
                }
                 else{
                    productViewModel.insert(product);
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


//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//        if(context instanceof ProductsLisner){
//            productsLisner = (ProductsLisner) context;
//
//        }else {
//            productsLisner = null;
//        }
//
//    }
//
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//
//        productsLisner = null;
//    }
//
//    private ProductsLisner productsLisner;
//
//
//    public interface ProductsLisner{
//
//        void lisnProductsFromDialog(Product product);
//
//    }

}