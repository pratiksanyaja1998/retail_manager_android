package retailmanager.spyhunter272.com.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import retailmanager.spyhunter272.com.R;
import retailmanager.spyhunter272.com.databinding.RowInvoiceFormProductBinding;
import retailmanager.spyhunter272.com.room.table.Product;

public class InvoiceFormProductListAdapter extends BaseAdapter {

    private List<Product> proLists = new ArrayList<>();
    private double totalAmtProduct = 0;

    private ProductListLiner productListLiner;

    public List<Product> getProLists() {
        return proLists;
    }

    public InvoiceFormProductListAdapter(ProductListLiner productListLiner) {
        this.productListLiner = productListLiner;
    }

    private void deleteProduct(int i){
        totalAmtProduct-=proLists.get(i).getTotal();
        productListLiner.updateTotal(totalAmtProduct);
        proLists.remove(i);
        notifyDataSetChanged();
    }

    public boolean addProduct(Product product) {

        for(int i = 0;i<proLists.size();i++){
            if(proLists.get(i).getId()==product.getId()){
                return false;
            }
        }

        product.setIn_stock_qty(1);
        double total =  product.getIn_stock_qty()*product.getS_price();
        totalAmtProduct+=total;
        productListLiner.updateTotal(totalAmtProduct);
        product.setTotal(Math.round(total));
        proLists.add(product);
        notifyDataSetChanged();
        return true;
    }

    public void updateProduct(Product product){

        for (int i = 0;i<proLists.size();i++){

            if(product.getId()==proLists.get(i).getId()){

                totalAmtProduct-=proLists.get(i).getTotal();

                double total = product.getIn_stock_qty()*product.getS_price();
                totalAmtProduct+=total;

                productListLiner.updateTotal(totalAmtProduct);
                product.setTotal(Math.round(total));
                proLists.set(i,product);
                notifyDataSetChanged();
            }

        }

    }

    @Override
    public int getCount() {
        return proLists.size();
    }

    @Override
    public Object getItem(int i) {
        return proLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        RowInvoiceFormProductBinding binding;

        if (view == null) {

            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_invoice_form_product, null);
            binding = DataBindingUtil.bind(view);
            view.setTag(binding);

        } else {

            binding = (RowInvoiceFormProductBinding) view.getTag();

        }

        binding.setProduct(proLists.get(i));

        binding.rowIbtnprodelete.setOnClickListener(v -> deleteProduct(i));
        binding.rowBtnEdit.setOnClickListener(v -> productListLiner.openProduct(proLists.get(i)));
        binding.rowPname.setOnClickListener(v -> productListLiner.openProduct(proLists.get(i)));
        binding.rowPqty.setOnClickListener(v->productListLiner.openProduct(proLists.get(i)));
        binding.rowPamt.setOnClickListener(v->productListLiner.openProduct(proLists.get(i)));
        binding.rowPtotal.setOnClickListener(v->productListLiner.openProduct(proLists.get(i)));


        return binding.getRoot();


    }

   public interface ProductListLiner {

        void openProduct(Product product);
        void updateTotal(double total);

    }

}
