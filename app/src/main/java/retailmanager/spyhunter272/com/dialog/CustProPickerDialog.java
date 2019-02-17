package retailmanager.spyhunter272.com.dialog;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retailmanager.spyhunter272.com.R;
import retailmanager.spyhunter272.com.adapter.ListCustProPickerAdepter;
import retailmanager.spyhunter272.com.adapter.ProdCateSpinnerBaseAdepter;
import retailmanager.spyhunter272.com.databinding.DialogCustomerProductPickerBinding;
import retailmanager.spyhunter272.com.holder.CustProPickerDialogHolder;
import retailmanager.spyhunter272.com.room.table.Customer;
import retailmanager.spyhunter272.com.room.table.Product;
import retailmanager.spyhunter272.com.room.table.ProductCategory;
import retailmanager.spyhunter272.com.viewmodel.CustomerViewModel;
import retailmanager.spyhunter272.com.viewmodel.ProductCategoryViewModel;
import retailmanager.spyhunter272.com.viewmodel.ProductViewModel;

public class CustProPickerDialog extends DialogFragment implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener {


    private DialogCustomerProductPickerBinding binding;
    private CustProPickerDialogHolder holder;

    public CustProPickerDialog(){

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Dialog dialog  =super.onCreateDialog(savedInstanceState);

        dialog. getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        return dialog;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.dialog_customer_product_picker,container,false);
        binding.setHolder(holder);

        return binding.getRoot();
    }

    private ListCustProPickerAdepter listAdepter;

    public void setCustomerOrProduct(boolean customerOrProduct) {
        holder= new CustProPickerDialogHolder();
        holder.setCustomerOrProduct(customerOrProduct);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


       binding.searchViewDialog.onActionViewExpanded();
       binding.searchViewDialog.setOnQueryTextListener(this);


       listAdepter = new ListCustProPickerAdepter(holder.isCustomerOrProduct());
       binding.listviewDialog.setAdapter(listAdepter);
       binding.listviewDialog.setOnItemClickListener(this);

       getData("");

       if(!holder.isCustomerOrProduct()){

           prodCateSpinnerBaseAdepter = new ProdCateSpinnerBaseAdepter();
           binding.spinnerProductCategory.setAdapter(prodCateSpinnerBaseAdepter);
           binding.spinnerProductCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
               @Override
               public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   holder.setProductCategoryId(prodCateSpinnerBaseAdepter.productCategories.get(position).getId());
                   getData("");
               }

               @Override
               public void onNothingSelected(AdapterView<?> parent) {

               }
           });

           getProductCategory();

       }

    }

    private ProdCateSpinnerBaseAdepter prodCateSpinnerBaseAdepter;

    private void getProductCategory() {

        ProductCategoryViewModel productCategoryViewModel= ViewModelProviders.of(this).get(ProductCategoryViewModel.class);

        productCategoryViewModel.getAllProductCategory().observe(this, new Observer<List<ProductCategory>>() {
            @Override
            public void onChanged(@Nullable List<ProductCategory> productCategories) {

                prodCateSpinnerBaseAdepter.setProductCategories(productCategories);
            }
        });

    }

    private void getData(String searchData) {

        if(holder.isCustomerOrProduct()) {

            CustomerViewModel customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);

            customerViewModel.getCustomerForList(10, 0, searchData).observe(this, new Observer<List<Customer>>() {
                @Override
                public void onChanged(@Nullable List<Customer> customers) {
                    listAdepter.setCustomerList(customers);

                    if (customers.size() > 0) {
                        holder.setDataFound(true);
                    }else {

                        holder.setDataFound(false);
                    }

                }
            });

        }else {

            ProductViewModel productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

            productViewModel.getProductsForList(10,0,holder.getProductCategoryId(),searchData).observe(this, new Observer<List<Product>>() {
                @Override
                public void onChanged(@Nullable List<Product> products) {
                    listAdepter.setProductList(products);

                    if (products.size()>0){
                        holder.setDataFound(true);
                   }else {

                       holder.setDataFound(false);
                   }
                }
            });


        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if(holder.isCustomerOrProduct()){

            dialogSearchItemSelectLisner.lisnCustomerFormSearchDialog(listAdepter.getCustomerList().get(position));

        }else {

            dialogSearchItemSelectLisner.lisnProductFormSearchDialog(listAdepter.getProductList().get(position));

        }
        dismiss();

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
       return onQueryTextChange(query);

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        getData(newText);
        return true;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof DialogSearchItemSelectLisner){
            dialogSearchItemSelectLisner = (DialogSearchItemSelectLisner) context;

        }else {
            throw new Error("Must Req Dialog Lisner.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        dialogSearchItemSelectLisner = null;
    }

    private DialogSearchItemSelectLisner dialogSearchItemSelectLisner;



    public interface DialogSearchItemSelectLisner{

        public void lisnCustomerFormSearchDialog(Customer customer);
        public  void lisnProductFormSearchDialog(Product product);

    }


}
