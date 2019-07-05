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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    private static final int SHOW_CAMERA_REQUEST_CODE = 457;
    private static final int CONTACTS_REQUEST_CODE = 455;
    private static final int PICK_PHOTO_REQUEST_CODE = 433;
    private File mPhotoFile;

    private ImageView mImageView;
    private ImageButton thumbnailImageBtn,toFilePathImageBtn,contactImageBtn,shareImageBtn,gallareyImageBtn;
    private boolean mCanTakePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
      mImageView=(ImageView) findViewById(R.id.imageView);
      thumbnailImageBtn=(ImageButton) findViewById(R.id.camera_for_thumbnail);
      toFilePathImageBtn=(ImageButton) findViewById(R.id.camera_for_file);
      contactImageBtn=(ImageButton) findViewById(R.id.gallarey);
      shareImageBtn=(ImageButton) findViewById(R.id.share);
      contactImageBtn=(ImageButton) findViewById(R.id.contacts);
      gallareyImageBtn=(ImageButton) findViewById(R.id.gallarey);
      thumbnailImageBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              showCamera();
          }
      });
      toFilePathImageBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              savePhotoToFilePathAndRetrieve();
          }
      });
      gallareyImageBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
loadFromGallarey();
          }
      });
        contactImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startContactsIntents();
            }
        });
      shareImageBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              sendEmail();
          }
      });
    }

    private void loadFromGallarey() {
        Intent photointent=new Intent();
        photointent.setType("image/*");
        photointent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(photointent,"Choose a product Photo"),PICK_PHOTO_REQUEST_CODE);

    }


    private void sendEmail(){
        String subject ="Application for position of software developer";
        String text="Am a Bsc graduate,software developer for 2.8 yrs";
        Intent i=new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc2822");
        i.putExtra(Intent.EXTRA_SUBJECT,subject);
        i.putExtra(Intent.EXTRA_TEXT,text);
        startActivity(i);

    }
    private void showCamera(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,SHOW_CAMERA_REQUEST_CODE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK){
            return;
        }
        else if(requestCode==PICK_PHOTO_REQUEST_CODE){
          Uri  photopath  =data.getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(this.getContentResolver(),photopath);
                mImageView.setImageBitmap(bitmap);



            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //for camera that saves to file
        else if ((requestCode==SHOW_CAMERA_REQUEST_CODE)  ) {
            Uri uri =
                    FileProvider.getUriForFile(ProductActivity.this,
                            "e.anonymous.lesson02productactivity.fileprovider",
                            mPhotoFile);
            revokeUriPermission(uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePhotoView();
        }
        //for camera that returns thumbnail
        else if ((requestCode==SHOW_CAMERA_REQUEST_CODE && data !=null)  ) {
            Uri uri =
                    FileProvider.getUriForFile(ProductActivity.this,
                            "e.anonymous.lesson02productactivity.fileprovider",
                            mPhotoFile);
            revokeUriPermission(uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePhotoView();
        }
        //for contactacts
        else if (requestCode == CONTACTS_REQUEST_CODE && data != null) {
            Uri contactUri = data.getData();
// Specify which fields you want your query to return
// values for
            String[] queryFields = new String[] {
                    ContactsContract.Contacts.DISPLAY_NAME
            };
// Perform your query - the contactUri islike a "where"
// clause here
            Cursor c = getContentResolver()
                    .query(contactUri, queryFields, null,
                            null, null);
            try {
// Double-check that you actually gotresults
                if (c.getCount() == 0) {
                    return;
                }
// Pull out the first column of the firstrow of data -
// that is your suspect's name
                c.moveToFirst();
                String suspect = c.getString(0);
                Toast.makeText(ProductActivity.this,"you selected"+suspect,Toast.LENGTH_LONG).show();
            } finally {
                c.close();
            }
        }
    }
    public String getPhotoFilename() {
        return "IMG_" + "rieng"+ ".jpg";
    }
    public File getPhotoFile() {
        File filesDir = getFilesDir();
        return new File(filesDir,
                getPhotoFilename());
    }
    private void updatePhotoView() {
        if (mPhotoFile == null ||
                !mPhotoFile.exists()) {
            mImageView.setImageDrawable(null);
        } else {
            Bitmap bitmap =
                    PictureUtils.getScaledBitmap(
                            mPhotoFile.getPath(),
                            ProductActivity.this);
            mImageView.setImageBitmap(bitmap);
        }
    }
    public void startContactsIntents(){
        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(pickContact,CONTACTS_REQUEST_CODE);
    }
public void savePhotoToFilePathAndRetrieve(){
    mPhotoFile=getPhotoFile();
    final Intent captureImage = new
            Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    mCanTakePhoto = mPhotoFile != null &&
            captureImage.resolveActivity(getPackageManager()) != null;
    Uri uri =
            FileProvider.getUriForFile(ProductActivity.this,
                    "e.anonymous.lesson02productactivity.fileprovider",
                    mPhotoFile);
    captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
    List<ResolveInfo> cameraActivities =
            ProductActivity.this
                    .getPackageManager().queryIntentActivities(captureImage,
                    PackageManager.MATCH_DEFAULT_ONLY);
    for (ResolveInfo activity :
            cameraActivities) {
        ProductActivity.this.grantUriPermission(activity.activityInfo.packageName,
                uri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
    }
    startActivityForResult(captureImage,
            SHOW_CAMERA_REQUEST_CODE);
}
}
