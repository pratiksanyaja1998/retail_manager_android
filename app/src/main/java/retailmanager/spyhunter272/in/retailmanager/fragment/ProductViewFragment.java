package retailmanager.spyhunter272.in.retailmanager.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retailmanager.spyhunter272.in.retailmanager.adapter.ProdCateSpinnerBaseAdepter;
import retailmanager.spyhunter272.in.retailmanager.dialog.ProductDialog;
import retailmanager.spyhunter272.in.retailmanager.R;
import retailmanager.spyhunter272.in.retailmanager.activity.ProductActivity;
import retailmanager.spyhunter272.in.retailmanager.room.table.Product;
import retailmanager.spyhunter272.in.retailmanager.room.table.ProductCategory;
import retailmanager.spyhunter272.in.retailmanager.viewmodel.ProductCategoryViewModel;
import retailmanager.spyhunter272.in.retailmanager.viewmodel.ProductViewModel;

//9913577510 mama khakhariya
public class ProductViewFragment extends Fragment implements View.OnClickListener,ProductActivity.SearchViewDataChangeListner{

    private ProductViewModel productViewModel;
    private ProductCategoryViewModel productCategoryViewModel;

    private RecyclerView recyclerView;
    private RcProductAdepter rcProductAdepter;
    private ImageView linearEmtyProduct;

    private final int  PRODUCT_SHOW_LIMIT  = 10;
    private int offSet = 0;

    private Spinner spinnerProductCategory;
    private ImageButton iBtnNext,iBtnPrev;

    private EditText edOffset;

    public ProductViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_product_view, container, false);

    }


    private ProdCateSpinnerBaseAdepter prodCateSpinnerBaseAdepter;
    private int activeProductCategoryId = 1;
    private String productNameHsnFilterText  = "";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        linearEmtyProduct  = view.findViewById(R.id.linear_show_emty_list);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        rcProductAdepter = new RcProductAdepter(getContext());
        recyclerView.setAdapter(rcProductAdepter);

        view.findViewById(R.id.fbtn_add).setOnClickListener(this);
        iBtnNext =  view.findViewById(R.id.ib_next);
                iBtnNext.setOnClickListener(this);
        iBtnPrev = view.findViewById(R.id.ib_prev);
        iBtnPrev.setOnClickListener(this);

        edOffset = view
                .findViewById(R.id.ed_offset);


        edOffset.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    setOffSet( Integer.parseInt("0"+edOffset.getText().toString()));
                    getProducts();
                    return false;
                }

                return true;
            }
        });


        spinnerProductCategory = view.findViewById(R.id.spinner_product_category);
        prodCateSpinnerBaseAdepter = new ProdCateSpinnerBaseAdepter();
        spinnerProductCategory.setAdapter(prodCateSpinnerBaseAdepter);

        spinnerProductCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               activeProductCategoryId =  prodCateSpinnerBaseAdepter.productCategories.get(position).getId();
               Log.e("select id",activeProductCategoryId+"");
               setOffSet(0);
               getProducts();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void setOffSet(int offSet){
        this.offSet = offSet;
        edOffset.setText(offSet+"");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productCategoryViewModel = ViewModelProviders.of(this).get(ProductCategoryViewModel.class);

        getProductCategory();

    }

    private void getProductCategory() {

        productCategoryViewModel.getAllProductCategory().observe(this, new Observer<List<ProductCategory>>() {
            @Override
            public void onChanged(@Nullable List<ProductCategory> productCategories) {

                prodCateSpinnerBaseAdepter.setProductCategories(productCategories);
            }
        });

    }

    private void getProducts(){

        productViewModel.getProductsForList(PRODUCT_SHOW_LIMIT,offSet,activeProductCategoryId,productNameHsnFilterText).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> products) {
                setListNavigation(products.size());
                rcProductAdepter.setProduct(products);
            }
        });

    }

    private void setListNavigation(int listSize){

        if(listSize==0){
            linearEmtyProduct.setVisibility(View.VISIBLE);

        }else {
            linearEmtyProduct.setVisibility(View.INVISIBLE);
        }

//        if(listSize>=PRODUCT_SHOW_LIMIT){
//            iBtnNext.setVisibility(View.VISIBLE);
//        }else {
//            iBtnNext.setVisibility(View.GONE);
//            iBtnPrev.setVisibility(View.VISIBLE);
//        }
//
//        if(offSet==0){
//            iBtnPrev.setVisibility(View.GONE);
//        }else {
//            iBtnPrev.setVisibility(View.VISIBLE);
//        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.fbtn_add:
                new ProductDialog().show(getFragmentManager(),null);
                break;

            case R.id.ib_next:

                setOffSet(offSet+ PRODUCT_SHOW_LIMIT);
                getProducts();

                break;

            case R.id.ib_prev:

                setOffSet(offSet- PRODUCT_SHOW_LIMIT);
                getProducts();

                break;
        }

    }

    //    recylerview
    public void editProduct(Product product) {
       ProductDialog productDialog =  new ProductDialog();
        productDialog.setArguments(product.getBundle());
        productDialog.show(getFragmentManager(),null);
    }

    public void deleteProduct(Product product) {
        productViewModel.delete(product);
    }


    @Override
    public void searchOnQueryTextSubmit(String query) {
         productNameHsnFilterText  =query;
         getProducts();
    }

    @Override
    public void searchDoneByQuery() {

    }

    @Override
    public void searchCancel() {

    }



    class RcProductAdepter  extends RecyclerView.Adapter<RcProductAdepter.VHolder> {


        private List<Product> products = new ArrayList<>();

        private Context context;

        private SharedPreferences myPreference;

        public void setProduct(List<Product> product) {
            this.products = product;
            notifyDataSetChanged();
        }

        public RcProductAdepter(Context context) {
            this.context = context;
            myPreference =PreferenceManager.getDefaultSharedPreferences(context);
        }

        @NonNull
        @Override
        public VHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            VHolder vHolder= new VHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_product,viewGroup,false));

            if(!myPreference.getBoolean("hsn",true))
                vHolder.hsn.setVisibility(View.GONE);

            if(!myPreference.getBoolean("bprice",true)){
                vHolder.b_price.setVisibility(View.GONE);
            }

            Log.e("pref",myPreference.getBoolean("hsn",true)+"");
            Log.e("pref",myPreference.getBoolean("bprice",true)+"");

            return vHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull VHolder vHolder, int i) {
            Product product = products.get(i);

            if(product.getIn_stock_qty()<=0){
                vHolder.qty.setBackgroundResource(R.drawable.oval_less_0);
            }else if(product.getIn_stock_qty()<=5){
                vHolder.qty.setBackgroundResource( R.drawable.oval_less_5);
            }else {
                vHolder.qty.setBackgroundResource(R.drawable.oval);
            }

            vHolder.p_name.setText(product.getName());
            vHolder.hsn.setText(product.getHsn());
            vHolder.s_price.setText( "Sell :"+product.getS_price());
            vHolder.b_price.setText("Buy :"+product.getS_price());
            vHolder.qty.setText(""+product.getIn_stock_qty());
            vHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteProduct(product);
                }
            });

            vHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editProduct(product);
                }
            });

            setFadeAnimation(vHolder.itemView);
        }

        private void setFadeAnimation(View view) {
            AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(1000);
            view.startAnimation(anim);
        }

//       https://stackoverflow.com/questions/26724964/how-to-animate-recyclerview-items-when-they-appear

        private void setScaleAnimation(View view) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(1000);
            view.startAnimation(anim);
        }

        @Override
        public int getItemCount() {
            return products.size();
        }

        class VHolder extends RecyclerView.ViewHolder{

            TextView p_name,hsn,b_price,s_price,qty;
            ImageButton btnEdit,btnDelete;

            public VHolder(@NonNull View itemView) {
                super(itemView);
                p_name  = itemView.findViewById(R.id.tv_product_name);
                hsn = itemView.findViewById(R.id.tv_hsn);
                s_price = itemView.findViewById(R.id.tv_s_price);
                b_price = itemView.findViewById(R.id.tv_b_price);
                qty = itemView.findViewById(R.id.tv_qty);
                btnEdit = itemView.findViewById(R.id.ibtn_edit);
                btnDelete  =itemView.findViewById(R.id.ibtn_delete);
            }

        }

    }

}
