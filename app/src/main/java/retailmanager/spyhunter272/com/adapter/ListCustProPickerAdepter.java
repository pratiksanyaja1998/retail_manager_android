package retailmanager.spyhunter272.com.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retailmanager.spyhunter272.com.R;
import retailmanager.spyhunter272.com.room.table.Customer;
import retailmanager.spyhunter272.com.room.table.Product;

public class ListCustProPickerAdepter extends BaseAdapter {

    private boolean isCustomerOrProduct;

    public ListCustProPickerAdepter(boolean isCustomerOrProduct) {
        this.isCustomerOrProduct = isCustomerOrProduct;
    }

    public List<Product> getProductList() {
        return productList;
    }

    private List<Product> productList = new ArrayList<>();

    List<Customer> customerList = new ArrayList<>();

    public List<Customer> getCustomerList() {
        return customerList;
    }

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