package retailmanager.spyhunter272.in.retailmanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retailmanager.spyhunter272.in.retailmanager.R;
import retailmanager.spyhunter272.in.retailmanager.room.table.ProductCategory;

public class ProdCateSpinnerBaseAdepter extends BaseAdapter {

    public List<ProductCategory> productCategories = new ArrayList<>();

    public void setProductCategories(List<ProductCategory> productCategories) {
        this.productCategories = new ArrayList<>(productCategories);
        this.productCategories.add(0,new ProductCategory("Select Category",""));

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return productCategories.size();

    }

    @Override
    public Object getItem(int position) {
        return productCategories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_simple_spinner_textview,parent,false);

        TextView textView =  view.findViewById(R.id.row_tv_simple_list_text);
        textView.setText(productCategories.get(position).getName());

        return view;
    }




}
