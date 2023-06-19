package com.dineInOrder.application.Activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dineInOrder.application.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ChangeProfile extends AppCompatActivity {
    private static final int PIC_IMAGE_REQUEST =1 ;
    private ImageView imageViewUploadPic;
    private StorageReference storageReference;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private FirebaseUser firebaseUser;
    private Uri uriImage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changeprofile);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Upload Profile Picture");
        auth = FirebaseAuth.getInstance();
        Button choosePicBtn=findViewById(R.id.choosePicBtn);
        Button uploadPicBtn=findViewById(R.id.uploadPicBtn);
        progressBar = findViewById(R.id.progressBar);
        imageViewUploadPic=findViewById(R.id.selectedPicture);
        firebaseUser = auth.getCurrentUser();
        storageReference= FirebaseStorage.getInstance().getReference("Display Pics");
        assert firebaseUser != null;
        Uri uri= firebaseUser.getPhotoUrl();
        Picasso.get().load(uri).into(imageViewUploadPic);
        choosePicBtn.setOnClickListener(view -> openFileChooser());
        uploadPicBtn.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            UploadPic();
        });
    }

    private void UploadPic() {
    if(uriImage!=null){
        StorageReference fileReference = storageReference.child(Objects.requireNonNull(auth.getCurrentUser()).getUid()+"."+getFileExtension(uriImage));
    fileReference.putFile(uriImage).addOnSuccessListener(taskSnapshot -> {
        fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
            firebaseUser=auth.getCurrentUser();
            UserProfileChangeRequest profileUpdate=new UserProfileChangeRequest.Builder().setPhotoUri(uri).build();
        firebaseUser.updateProfile(profileUpdate);
        });
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, "Upload Successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("openFragment", "fragmentProfile");
        startActivity(intent);
        finish();
    }).addOnFailureListener(e -> {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    });
    }else{
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, "No file was selected", Toast.LENGTH_SHORT).show();
    }
    }

    private String getFileExtension(Uri uriImage) {
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uriImage));
    }



    private void openFileChooser() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PIC_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PIC_IMAGE_REQUEST &&resultCode==RESULT_OK && data!=null&&data.getData()!=null){
            uriImage = data.getData();
            imageViewUploadPic.setImageURI(uriImage);
        }
    }
}
