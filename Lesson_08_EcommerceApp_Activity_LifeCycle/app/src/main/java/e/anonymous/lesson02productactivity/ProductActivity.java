package e.anonymous.lesson02productactivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 433;
    public static String CURRENT_POSITION="e.anonymous.lesson02productactivity.ProductActivity";
    private Product mProduct;
    private static int DEFAULT_CURRENT_POSITION=-2;
    private EditText productName,productDescription,productPrice;
    private Button addProduct,emailBtn;
    private Spinner categorySpinner;
    private ImageView productImageView;
    private int mPosition;



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
        emailBtn=(Button) findViewById(R.id.email);
        productImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCamera();
            }
        });
        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });

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
    @Override
    protected void onStart() {
        super.onStart();

        mPosition = getIntent().getIntExtra(ProductListAdapter.CURRENT_POSITION_VALUE,DEFAULT_CURRENT_POSITION);
        Log.d("start","Activity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("start","Activity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("start","Activity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("start","Activity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("start","Activity onDestroy");
    }

    private void startCamera() {
        Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,CAMERA_REQUEST_CODE);
    }

    private void sendEmail() {
        String subjectProduct=mProduct.getName();
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc2822");
        intent.putExtra(Intent.EXTRA_SUBJECT,subjectProduct);
        intent.putExtra(Intent.EXTRA_TEXT,"The product "+subjectProduct+" had some issues");
        intent.putExtra(Intent.EXTRA_EMAIL,"d@m.com");
        startActivity(intent);

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

       if(mPosition !=DEFAULT_CURRENT_POSITION){
       mProduct= ProductListActivity.mProductArrayList.get(mPosition);
       productName.setText(mProduct.getName());
       productImageView.setImageResource(mProduct.getImage());
       productPrice.setText(mProduct.getPrice());
       }
   }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode !=RESULT_OK){
            return;
        }
        if(requestCode==CAMERA_REQUEST_CODE && data !=null){
            Bitmap bitmap=(Bitmap) data.getExtras().get("data");
            productImageView.setImageBitmap(bitmap);
        }
    }

}
