package retailmanager.spyhunter272.com.dialog;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retailmanager.spyhunter272.com.R;
import retailmanager.spyhunter272.com.room.table.Customer;
import retailmanager.spyhunter272.com.room.table.Product;
import retailmanager.spyhunter272.com.viewmodel.CustomerViewModel;
import retailmanager.spyhunter272.com.viewmodel.ProductViewModel;

public class CustProPickerDialog extends DialogFragment implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return inflater.inflate(R.layout.dialog_customer_product_picker,container);
    }

    private ListView listView ;
    private SearchView searchView;
    private boolean isCustomerOrProduct ;
    private ListAdepter listAdepter;
    private ProgressBar progressBar;
    private ImageView imageViewDataNoFound;

    public void setCustomerOrProduct(boolean customerOrProduct) {
        isCustomerOrProduct = customerOrProduct;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

       listView = view.findViewById(R.id.listview_dialog);
       searchView = view.findViewById(R.id.search_view_dialog);
       imageViewDataNoFound = view.findViewById(R.id.image_not_found);
       progressBar = view.findViewById(R.id.progress_circular);
       searchView.onActionViewExpanded();
       searchView.setOnQueryTextListener(this);


       listAdepter = new ListAdepter();
       listView.setAdapter(listAdepter);
       listView.setOnItemClickListener(this);

       getData("");
    }

    private void getData(String searchData) {

        if(isCustomerOrProduct) {

            CustomerViewModel customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);

            customerViewModel.getCustomerForList(10, 0, searchData).observe(this, new Observer<List<Customer>>() {
                @Override
                public void onChanged(@Nullable List<Customer> customers) {
                    if (customers.size() > 0) {
                        listAdepter.setCustomerList(customers);
                        dataFound();

                    }else{
                        dataNotFound();
                    }



                }
            });

        }else {

            ProductViewModel productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

            productViewModel.getProductsForList(10,0,0,searchData).observe(this, new Observer<List<Product>>() {
                @Override
                public void onChanged(@Nullable List<Product> products) {
                   if (products.size()>0){
                       listAdepter.setProductList(products);
                        dataNotFound();

                   }else {

                       dataNotFound();
                   }
                }
            });


        }


    }


    private void dataFound(){

        progressBar.setVisibility(View.GONE);
        imageViewDataNoFound.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
    }

    private void dataNotFound(){

        listView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        imageViewDataNoFound.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if(isCustomerOrProduct){

            dialogSearchItemSelectLisner.lisnCustomerFormSearchDialog(listAdepter.customerList.get(position));

        }else {
            dialogSearchItemSelectLisner.lisnProductFormSearchDialog(listAdepter.productList.get(position));

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


    class ListAdepter extends BaseAdapter{

        List<Product> productList = new ArrayList<>();

        List<Customer> customerList = new ArrayList<>();

        public void setCustomerList(List<Customer> customerList) {
            this.customerList = customerList;
            notifyDataSetChanged();
        }

        public void setProductList(List<Product> productList) {
            this.productList = productList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            if(isCustomerOrProduct)
            return customerList.size();
            else
                return productList.size();
        }

        @Override
        public Object getItem(int position) {
            if(isCustomerOrProduct)
            return customerList.get(position);
            else
                return productList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dialog_picker,parent,false);

            TextView textView1 =  view.findViewById(R.id.tv_simple_row1);
            TextView textView2 = view.findViewById(R.id.tv_simple_row2);

            if (isCustomerOrProduct) {
                textView1.setText(customerList.get(position).getName());
                textView2.setText(customerList.get(position).getMobile()+"");
            }else {
                textView1.setText(productList.get(position).getName());
                textView2.setText(productList.get(position).getS_price()+"");
            }
            return view;
        }



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
