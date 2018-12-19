package retailmanager.spyhunter272.in.retailmanager.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import retailmanager.spyhunter272.in.retailmanager.R;
import retailmanager.spyhunter272.in.retailmanager.databinding.RowInvoiceFormProductBinding;
import retailmanager.spyhunter272.in.retailmanager.room.table.Product;

public class InvoiceFormProductListAdapter extends BaseAdapter {

    private List<Product> proLists = new ArrayList<>();
    private float totalInvoice = 0;

    private ProductListLiner productListLiner;

    public List<Product> getProLists() {
        return proLists;
    }

    public InvoiceFormProductListAdapter(ProductListLiner productListLiner) {
        this.productListLiner = productListLiner;
    }

    public void setProLists(List<Product> proLists) {
        this.proLists = proLists;
        notifyDataSetChanged();
    }

    private void deleteProduct(int i){
        totalInvoice-=proLists.get(i).getTotal();
        productListLiner.updateTotal(totalInvoice);
        proLists.remove(i);
        notifyDataSetChanged();
    }

    public void addProduct(Product product) {
        float total = (float) (product.getIn_stock_qty()*product.getS_price());
        total =total+((total*product.getGst())/100);
        totalInvoice+=total;
        productListLiner.updateTotal(totalInvoice);
        product.setTotal(total);
        proLists.add(product);
        notifyDataSetChanged();
    }

    public void updateProduct(Product product){

        for (int i = 0;i<proLists.size();i++){

            if(product.getId()==proLists.get(i).getId()){

                totalInvoice-=proLists.get(i).getTotal();

                float total = (float) (product.getIn_stock_qty()*product.getS_price());
                total =total + ((total*product.getGst())/100);
                totalInvoice+=total;

                productListLiner.updateTotal(totalInvoice);
                product.setTotal(total);
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

        binding.rowIbtnprodelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct(i);
            }
        });

        binding.rowPname.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                productListLiner.openProduct(proLists.get(i));
            }
        });

        binding.rowPtex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productListLiner.openProduct(proLists.get(i));
            }
        });

        return binding.getRoot();


    }

   public interface ProductListLiner {

        void openProduct(Product product);
        void updateTotal(double total);

    }

}
