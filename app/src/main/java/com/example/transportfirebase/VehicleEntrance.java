package com.example.transportfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import static android.app.PendingIntent.getActivity;

public class VehicleEntrance extends AppCompatActivity {
    DatabaseReference firebaseReference;
    Entered entered;
    ImageButton camera;
    EditText vehno7;
    EditText vehname7;
    EditText drivername7;
    EditText drivercnic7;
    EditText driveremail7;
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    Button firebase;
    StorageReference filepath;
    ImageView imageView;
    private final int PICK_IMAGE_REQUEST = 22;
   StorageReference storage;
   Uri mImageUri;
   ImageButton image;
   Button upload;
   ArrayList<Uri> ImageList=new ArrayList<Uri>();
   int upload_count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_entrance);
        vehno7 = (EditText) findViewById(R.id.edittextvehnumber5);
        vehname7 = (EditText) findViewById(R.id.edittextvehname5);
        drivername7 = (EditText) findViewById(R.id.edittextvehdrivername5);
        drivercnic7 = (EditText) findViewById(R.id.edittextvehdrivercnic5);
        driveremail7=(EditText) findViewById(R.id.edittextdriveremail5);
        firebase = (Button) findViewById(R.id.firebasebtn);
        upload=(Button) findViewById(R.id.upload);
        image=(ImageButton) findViewById(R.id.image40);
        Glide.with(this).load("http://goo.gl/gEgYUd").into(image);
        camera = (ImageButton) findViewById(R.id.camera);
        entered = new Entered();
        firebaseReference = FirebaseDatabase.getInstance().getReference().child("Entered Vehicles");
        storage=FirebaseStorage.getInstance("gs://transportfirebase-3578e.appspot.com").getReference();
        firebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vehno = Integer.parseInt(vehno7.getText().toString().trim());
                String vehname = vehname7.getText().toString().trim();
                String drivername = drivername7.getText().toString().trim();
                String drivercnic = (drivercnic7.getText().toString().trim());
                 String driveremai=driveremail7.getText().toString().trim();
                entered.setVehno(vehno);
                entered.setVehname(vehname7.getText().toString().trim());
                entered.setDrivername(drivername7.getText().toString().trim());
                entered.setDrivercnic(drivercnic7.getText().toString().trim());
                entered.setDriveremail(driveremail7.getText().toString());
                firebaseReference.push().setValue(entered);
                Toast.makeText(VehicleEntrance.this, "data inserted successfully", Toast.LENGTH_SHORT).show();
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              askCameraPermissions();
            }
        });
         upload.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(mImageUri!=null){
                     uploadFile(mImageUri);
                 }else{
                     Toast.makeText(VehicleEntrance.this,"select image",Toast.LENGTH_SHORT).show();
                 }
             }
         });
         image.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent gallery=new Intent();
                 gallery.setAction(Intent.ACTION_GET_CONTENT);
                 gallery.setType("image/*");
                 startActivityForResult(gallery,2);
             }
         });
    }
    private void askCameraPermissions() {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }else {
            opencamera();
        }

    }



    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERM_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(this, "Camera Permission is Required to Use camera.", Toast.LENGTH_SHORT).show();
            }
        }
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            //imageView3.setImageBitmap(image);
            //UploadImagetofirebase(Uri);


        }*/





    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        super.onActivityResult(requestCode, resultCode, data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        mImageUri=data.getData();
        image.setImageURI(mImageUri);
        /*if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {

            // Get the Uri of data
            Uri uri=data.getData();
           StorageReference filepath=storage.child("photo");
           filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                   Toast.makeText(VehicleEntrance.this,"Uploading file",Toast.LENGTH_SHORT).show();
               }
           });
        }*/
    }
    public String getFileExtendsion(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void uploadFile(Uri uri) {
            if (mImageUri != null) {
                    final StorageReference fileReference = storage.child(UUID.randomUUID().toString() + "." + getFileExtendsion(mImageUri));
                    fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    ///store the uri
                                    Model model = new Model(uri.toString());
                                    String modelId = firebaseReference.push().getKey();
                                    firebaseReference.child(modelId).setValue(model);
                                    Toast.makeText(VehicleEntrance.this, "uploaded successfully", Toast.LENGTH_SHORT).show();
                                }

                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i("Fail", e.getMessage());
                        }
                    });
                }
            }


    private void opencamera(){
        Intent camera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera,102);
    }
    public View getView(int position, View recycled, ViewGroup container) {




        Glide
                .with(this)
                .load(mImageUri)
                .centerCrop()
                .into(image);

        return image;
    }

}