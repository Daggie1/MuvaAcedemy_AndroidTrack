package e.anonymous.lesson02productactivity;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolders> {
    public static final String CURRENT_POSITION_VALUE = "e.anonymous.lesson02productactivity.current_value";
    private final Context mContext;

    private final   LayoutInflater mLayoutInflater;
    private final ArrayList<Product> mProductArrayList;


    public ProductListAdapter(Context context, ArrayList<Product> productsArrayList) {
        mContext = context;

        mProductArrayList = productsArrayList;
        mLayoutInflater= LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ProductViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

       View view=mLayoutInflater.inflate(R.layout.item_product,viewGroup,false);
        return new ProductViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolders productViewHolders, int position) {
        Product product= mProductArrayList.get(position);
        productViewHolders.productName.setText(product.getName());
        productViewHolders.productPrice.setText("Ksh "+product.getPrice());
        productViewHolders.strikedProductText.setText(product.getStrikedPrice());
        productViewHolders.productImage.setImageResource(product.getImage());
        productViewHolders.mcurrentPosition =position;


    }

    @Override
    public int getItemCount() {
        return mProductArrayList.size();
    }

    public class ProductViewHolders extends RecyclerView.ViewHolder{
        private final ImageView productImage,addToCart;
        private final TextView productName,productPrice,strikedProductText;
        public  int mcurrentPosition;//could be used to track perivous position
        public ProductViewHolders(@NonNull View itemView) {
            super(itemView);
            productImage=(ImageView) itemView.findViewById(R.id.product_image);
            addToCart=(ImageView) itemView.findViewById(R.id.add_to_cart_img);
            productName=(TextView) itemView.findViewById(R.id.product_name_txt);
            productPrice=(TextView) itemView.findViewById(R.id.product_price_txt);
            strikedProductText=(TextView) itemView.findViewById(R.id.product_price_striked_txt);
            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(mContext,ProductActivity.class);
                i.putExtra(CURRENT_POSITION_VALUE,mcurrentPosition);
                mContext.startActivity(i);
            }
        });

        }
    }

}
