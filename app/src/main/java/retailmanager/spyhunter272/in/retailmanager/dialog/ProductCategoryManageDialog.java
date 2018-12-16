package retailmanager.spyhunter272.in.retailmanager.dialog;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retailmanager.spyhunter272.in.retailmanager.R;
import retailmanager.spyhunter272.in.retailmanager.room.table.ProductCategory;
import retailmanager.spyhunter272.in.retailmanager.viewmodel.ProductCategoryViewModel;

public class ProductCategoryManageDialog extends BottomSheetDialogFragment implements View.OnClickListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.dialog_category_manage);
        edProductCategory = bottomSheetDialog.findViewById(R.id.ed_product_category);
        rcViewCategory = bottomSheetDialog.findViewById(R.id.rc_product_category);
        ibSaveCategory = bottomSheetDialog.findViewById(R.id.ib_save_cateroy);
        ibSaveCategory.setOnClickListener(this);

        rcProductCategoryAdepter = new RcProductCategoryAdepter();
        rcViewCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        rcViewCategory.setHasFixedSize(true);
        rcViewCategory.setAdapter(rcProductCategoryAdepter);

        getProductCategory();
        return bottomSheetDialog;
    }

    private RecyclerView rcViewCategory;
    private EditText edProductCategory;
    private ImageButton ibSaveCategory;

    private RcProductCategoryAdepter rcProductCategoryAdepter;
    private ProductCategoryViewModel productCategoryViewModel;

    private void getProductCategory(){

        productCategoryViewModel = ViewModelProviders.of(this).get(ProductCategoryViewModel.class);
        productCategoryViewModel.getAllProductCategory().observe(this, new Observer<List<ProductCategory>>() {
            @Override
            public void onChanged(@Nullable List<ProductCategory> productCategories) {

                rcProductCategoryAdepter.setProductCategories(productCategories);

            }
        });

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_save_cateroy:

                productCategoryViewModel.insert(new ProductCategory(edProductCategory.getText().toString()+"",""));
                Log.e("Saved item",edProductCategory.getText().toString()+"");
                break;

        }
    }

    private void deleteProductCategory(ProductCategory productCategory){
        productCategoryViewModel.delete(productCategory);
    }


    class RcProductCategoryAdepter extends RecyclerView.Adapter<RcProductCategoryAdepter.VHolder> {

        List<ProductCategory> productCategories= new ArrayList<>();

        public void setProductCategories(List<ProductCategory> productCategories) {

            this.productCategories = productCategories;

            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public VHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new VHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_product_category,viewGroup,false));

        }

        @Override
        public void onBindViewHolder(@NonNull RcProductCategoryAdepter.VHolder vHolder, int i) {

            vHolder.tvCategory.setText(productCategories.get(i).getName());
            vHolder.ibDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteProductCategory(productCategories.get(i));
                }
            });

        }

        @Override
        public int getItemCount() {
            return productCategories.size();
        }

        class VHolder extends RecyclerView.ViewHolder{

            ImageButton ibDelete;
            TextView tvCategory;

            public VHolder(@NonNull View itemView) {
                super(itemView);
                ibDelete = itemView.findViewById(R.id.row_ib_product_category_delete);
                tvCategory  = itemView.findViewById(R.id.row_tv_category);
            }

        }
    }

}
