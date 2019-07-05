package e.anonymous.lesson02productactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {
    public static ArrayList<Product> mProductArrayList = new ArrayList<>();
    RecyclerView mRecyclerView;
    private String[] productNames = {"Hoverboard v-4 s","Natural light googles","Black Lether Wallet","Nike supra 3","Beat wireless earphones","Khaki handbags","Brown Rubber"};
    private String[] productStrikedPrices={"20000","1500","1800","4000","2000","1700","2700"};
    private String[] productPrices={"17000","800","1500","3500","1500","1200","2200"};

    private int[] productImages = {R.drawable.hover_board, R.drawable.googles, R.drawable.wallet, R.drawable.nike, R.drawable.beat_headphones, R.drawable.h_bag, R.drawable.rubber_shoe};

    ProductListAdapter mProductListAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        mRecyclerView=(RecyclerView) findViewById(R.id.product_list_recyclerView);
        mProductListAdapter=new ProductListAdapter(ProductListActivity.this,mProductArrayList);

        populateRecyclerView();
        mRecyclerView.setAdapter(mProductListAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(ProductListActivity.this,2));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ProductListActivity.this,ProductActivity.class);
                startActivity(i);
            }
        });
    }
    public  void populateRecyclerView(){
        mProductArrayList.clear();
        for(int index =0;index<productNames.length;index++){
            Product product=new Product();
            product.setName(productNames[index]);
            product.setPrice(productPrices[index]);
            product.setStrikedPrice(productStrikedPrices[index]);
            product.setImage(productImages[index]);
            mProductArrayList.add(product);
        }
        mProductListAdapter.notifyDataSetChanged();
    }


}