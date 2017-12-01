package kmitl.project.bosstanayot.runranrun;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import info.hoang8f.widget.FButton;

public class ShareActivity extends AppCompatActivity {
double calorie;
String duration;
ImageView runbg,runbghori;
ScrollView scroll;
FButton sharebtn;
    String imageFileName;
    HorizontalScrollView horiscroll;
TextView share_cal, share_dura;
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        share_cal = findViewById(R.id.sharecal);
        share_dura = findViewById(R.id.duration);
        sharebtn = findViewById(R.id.sharebtn);
        sharebtn.setButtonColor(getResources().getColor(R.color.colorPrimary));
        scroll = findViewById(R.id.scroll);
        runbg = findViewById(R.id.runbg);
       // horiscroll = findViewById(R.id.horiscroll);
       // runbghori = findViewById(R.id.runbghori);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            calorie = bundle.getDouble("cal");
            duration = bundle.getString("duration");
            share_cal.setText(String.valueOf((int)calorie));
            share_dura.setText(duration);
        }
    }

    public void onShared(View view) {
        RelativeLayout imageView;
        imageView = findViewById(R.id.cap_Layout);
        Bitmap b = Screenshot.takescreenshot(imageView);
        saveBitmap(b);
        File imagePath = new File(this.getCacheDir(), "images");
        File newFile = new File(imagePath, "image.png");
        Uri contentUri = FileProvider.getUriForFile(this, "kmitl.project.bosstanayot.runranrun.fileprovider", newFile);
        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            startActivity(Intent.createChooser(shareIntent, "Choose an app"));

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_image, menu);
        return true;
    }

    private void saveBitmap(Bitmap bitmap) {
        // save bitmap to cache directory
        try {
            File cachePath = new File(this.getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void selectImage(MenuItem item){
        final CharSequence[] items={"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ShareActivity.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(items[i].equals("Camera")){
                    //Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //startActivityForResult(takePicture,0);
                    dispath();
                }else if (items[i].equals("Gallery")){
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto.createChooser(pickPhoto ,"Select File") , 1);
                }else if (items[i].equals("Cancel")){
                    dialogInterface.dismiss();
                }


            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
final int Camera_CPTURE = 0;
    private void dispath(){
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "kmitl.project.bosstanayot.runranrun.fileprovider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    updateImage(photoURI);
                    startActivityForResult(takePictureIntent, Camera_CPTURE);
                }
        }
    }
    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    public void updateImage(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(uri);
        sendBroadcast(intent);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode==RESULT_OK){
                    //Bundle selectedImage = imageReturnedIntent.getExtras();
                    //runbg.setImageURI(photoURI);
                    //selectedImagePath = getImagePath();
                    //imgUser.setImageBitmap(decodeFile(selectedImagePath));
                    //runbg.setImageURI(selectedImage);
                   // Uri bundle =  imageReturnedIntent.getData();
                    /*try {
                        Bitmap bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(),bundle);
                        setPic();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                    //runbg.setImageBitmap(bmp);
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, stream);
//        Bitmap.createScaledBitmap(bitmap, 120, 120, false);
                    rotateImage(setPic());
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    runbg.setImageURI(selectedImage);
                }
                break;
        }
    }
    private Bitmap setPic() {
        // Get the dimensions of the View
        int targetW = runbg.getWidth();
        int targetH = runbg.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        //Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        //runbg.setImageBitmap(bitmap);
        return BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
    }
    private void rotateImage(Bitmap bitmap){
        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(mCurrentPhotoPath);
        }catch (IOException e){
            e.printStackTrace();
        }
        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        Matrix matrix = new Matrix();
        switch (orientation){
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(270);
                break;

            default:
        }
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            runbg.setImageBitmap(rotatedBitmap);


    }
}
