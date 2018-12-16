package retailmanager.spyhunter272.in.retailmanager.dialog;

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
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import retailmanager.spyhunter272.in.retailmanager.adapter.ProdCateSpinnerBaseAdepter;
import retailmanager.spyhunter272.in.retailmanager.R;
import retailmanager.spyhunter272.in.retailmanager.activity.BarcodeActivity;
import retailmanager.spyhunter272.in.retailmanager.model.ProductDialogHolder;
import retailmanager.spyhunter272.in.retailmanager.room.table.Product;
import retailmanager.spyhunter272.in.retailmanager.room.table.ProductCategory;
import retailmanager.spyhunter272.in.retailmanager.viewmodel.ProductCategoryViewModel;
import retailmanager.spyhunter272.in.retailmanager.viewmodel.ProductViewModel;

import retailmanager.spyhunter272.in.retailmanager.databinding.DialogProductBinding;


//https://stackoverflow.com/questions/47718872/rowid-after-insert-in-room?rq=1
//https://stackoverflow.com/questions/44364240/android-room-get-the-id-of-new-inserted-row-with-auto-generate

public class ProductDialog extends BottomSheetDialogFragment implements View.OnClickListener {

    private static int BARCODE_ACT_REQ_CODE = 55;

    private boolean isNewProducts = false ;

    private ProductViewModel productViewModel;

    private TextView tvBarcode;
    private Spinner spProductCategory ;
    private Product product;
    private ProductCategoryViewModel productCategoryViewModel;

    private ProdCateSpinnerBaseAdepter prodCateSpinnerBaseAdepter;
    private boolean isUpdate = false;
    private SharedPreferences myPreference;
    private   ProductDialogHolder productDialogHolder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DialogProductBinding dialogCustomerBinding = DataBindingUtil.inflate(inflater,R.layout.dialog_product,container,false);

        product = new Product("","",0,0,1,0,"",0);
        dialogCustomerBinding.setProduct(product);
        myPreference =PreferenceManager.getDefaultSharedPreferences(getContext());
        productDialogHolder = new ProductDialogHolder(myPreference.getBoolean("hsn",true),myPreference.getBoolean("bprice",true),
                myPreference.getBoolean("barcode",true),myPreference.getBoolean("productGst",true) );
        dialogCustomerBinding.setProductDialogHolder(productDialogHolder);

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


        spProductCategory = dialog.findViewById(R.id.spinner_product_category);
        prodCateSpinnerBaseAdepter = new ProdCateSpinnerBaseAdepter();
        spProductCategory.setAdapter(prodCateSpinnerBaseAdepter);

//      setup object
        productCategoryViewModel = ViewModelProviders.of(this).get(ProductCategoryViewModel.class);

        getProductCategory();

        Bundle bundle=getArguments();

        if(bundle!=null){

            Product productUpdateble = Product.setProductFromBundle(bundle);
            product.setName(productUpdateble.getName());
            product.setGst(productUpdateble.getGst());
            product.setBarcode(productUpdateble.getBarcode());
            product.setIn_stock_qty(productUpdateble.getIn_stock_qty());
            product.setCategory(productUpdateble.getCategory());
            product.setId(productUpdateble.getId());
            product.setS_price(productUpdateble.getS_price());
            product.setB_price(productUpdateble.getB_price());
            product.setHsn(productUpdateble.getHsn());

            productDialogHolder.setUpdate(true);

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

            product.notifyChange();

        }else {

            productDialogHolder.setUpdate(false);

        }

        productDialogHolder.notifyChange();
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

    }

    public void setNewProducts(boolean newCustomer) {
        isNewProducts = newCustomer;
    }

    private void getProductCategory() {

        productCategoryViewModel.getAllProductCategory().observe(this, new Observer<List<ProductCategory>>() {
            @Override
            public void onChanged(@Nullable List<ProductCategory> productCategories) {
                prodCateSpinnerBaseAdepter.setProductCategories(productCategories);

                    int counter = 1;
                    for (ProductCategory item : productCategories) {
                        if (item.getId() == product.getCategory()) {

//                            spProductCategory.setSelection(counter);
                                product.setCategory(counter);
                            Log.e("category added",product.getCategory()+"");

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

                product.setCategory( prodCateSpinnerBaseAdepter.productCategories.get(product.getCategory()).getId());

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


                Log.e("selected added" , " cate "+product.getCategory()+" gst "+product.getGst());


                if(isNewProducts && productsLisner!=null){

                    productsLisner.lisnProductsFromDialog(product);

                }else {

                    if (productDialogHolder.isUpdate() && product.checkValidation()) {

                        productViewModel.update(product);

                    } else {

                        productViewModel.insert(product);

                    }

                }

                dismiss();

            break;

        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

//            Log.e("barcode","data recived");

            if(requestCode==BARCODE_ACT_REQ_CODE){

                if(resultCode==Activity.RESULT_OK){

//                    Log.e("barcode","data recived");

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

        void lisnProductsFromDialog(Product product);

    }

}