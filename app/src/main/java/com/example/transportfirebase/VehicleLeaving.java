package com.example.transportfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import java.util.ArrayList;
import java.util.UUID;

public class VehicleLeaving extends AppCompatActivity {
DatabaseReference firebasereference;
Leaved leaved;
ImageButton camera2;
ImageButton image2;
ImageButton search;
EditText vehno8;
EditText vehname8;
EditText drivername8;
EditText drivercnic8;
EditText driveremail8;
Button firebase2;
Button upload2;
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    private final int PICK_IMAGE_REQUEST = 22;
    StorageReference storage;
    Uri mImageUri2;
    ArrayList<Uri> ImageList=new ArrayList<Uri>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_leaving);
        vehno8=(EditText)findViewById(R.id.edittextvehnumber7);
        vehname8=(EditText)findViewById(R.id.edittextvehname7);
        drivername8=(EditText)findViewById(R.id.edittextvehdrivername7);
        drivercnic8=(EditText)findViewById(R.id.edittextvehdrivercnic7);
        driveremail8=(EditText)findViewById(R.id.edittextdriveremail7);
        firebase2=(Button)findViewById(R.id.firebasebtn2);
        camera2=(ImageButton) findViewById(R.id.camera2);
        image2=(ImageButton) findViewById(R.id.imagebtn41);
        upload2=(Button) findViewById(R.id.upload2);
        leaved=new Leaved();
        firebasereference= FirebaseDatabase.getInstance().getReference().child("Leaved Vehicles");
        storage= FirebaseStorage.getInstance().getReference();
        firebase2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vehno2=Integer.parseInt(vehno8.getText().toString().trim());
                String vehname2=vehname8.getText().toString().trim();
                String vehdrivername2=drivername8.getText().toString().trim();
                String vehdrivecnic2=drivercnic8.getText().toString().trim();
                String vehdriveremail2=driveremail8.getText().toString().trim();
                leaved.setVehno2(vehno2);
                leaved.setVehname2(vehname8.getText().toString());
                leaved.setDrivername2(drivername8.getText().toString());
                leaved.setDrivercnic2(drivercnic8.getText().toString());
                leaved.setDriveremail(driveremail8.getText().toString());
                firebasereference.push().setValue(leaved);
                Toast.makeText(VehicleLeaving.this,"data inserted successfully",Toast.LENGTH_SHORT).show();
            }
        });
        search=(ImageButton)findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentsearch=new Intent(VehicleLeaving.this,Search.class);
                startActivity(intentsearch);
            }
        });
        camera2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermissions();
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery=new Intent();
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery,2);
            }
        });
        upload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mImageUri2!=null){
                    uploadFile(mImageUri2);
                }else{
                    Toast.makeText(VehicleLeaving.this,"select image",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void askCameraPermissions() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        super.onActivityResult(requestCode, resultCode, data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        mImageUri2=data.getData();
        image2.setImageURI(mImageUri2);
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
        if (mImageUri2 != null) {
            final StorageReference fileReference2 = storage.child(UUID.randomUUID().toString() + "." + getFileExtendsion(mImageUri2));
            fileReference2.putFile(mImageUri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            ///store the uri
                            Model model = new Model(uri.toString());
                            String modelId = firebasereference.push().getKey();
                            firebasereference.child(modelId).setValue(model);
                            Toast.makeText(VehicleLeaving.this, "uploaded successfully", Toast.LENGTH_SHORT).show();
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
                .load(mImageUri2)
                .centerCrop()
                .into(image2);

        return image2;
    }

}