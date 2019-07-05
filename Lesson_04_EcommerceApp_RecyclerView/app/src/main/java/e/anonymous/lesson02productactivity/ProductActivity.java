package e.anonymous.lesson02productactivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    public static String CURRENT_POSITION="e.anonymous.lesson02productactivity.ProductActivity";
    private Product newProduct;
    private static int DEFAULT_CURRENT_POSITION=-2;
    private EditText productName,productDescription,productPrice;
    private Button addProduct;
    private Spinner categorySpinner;
    private ImageView productImageView;
    private File mPhotoFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        productName=(EditText) findViewById(R.id.product_name_edit);
        productDescription=(EditText) findViewById(R.id.product_description_edit);
        productPrice=(EditText) findViewById(R.id.product_price_edit);
        categorySpinner=(Spinner) findViewById(R.id.category_spinner);
        productImageView=(ImageView) findViewById(R.id.product_image);
        addProduct=(Button) findViewById(R.id.add_product) ;


        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isErrors()){

                    return;
                }else{
                   Toast.makeText(ProductActivity.this,"item will be added",Toast.LENGTH_LONG).show();
                }
            }
        });

        populateSpinner();
       fillData();
    }

    public boolean isErrors() {

        String name = productName.getText().toString();
        String price = productPrice.getText().toString();
        String description = productDescription.getText().toString();
        if (name.length()<3) {
            productName.setError("name is too short");
            return true;}
        if (TextUtils.isEmpty(price)) {
            productPrice.setError("price is required");
            return true;
        }

        if (description.length()<5) {
            productDescription.setError("Description is too short");
            return true;
        }else {
            return false;
        }
    }
    public void populateSpinner(){
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Electronic");
        spinnerArray.add("Beauty Products");
        spinnerArray.add("Kitchen Products");
        spinnerArray.add("Kids Ware");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorySpinner.setAdapter(adapter);



    }
   public void fillData(){
       Intent i=getIntent();
       int position=i.getIntExtra(ProductListAdapter.CURRENT_POSITION_VALUE,-2);
       Product product=ProductListActivity.mProductArrayList.get(position);
       productName.setText(product.getName());
       productImageView.setImageResource(product.getImage());
       productPrice.setText(product.getPrice());
   }
}
