package com.ingeniumbd.buyerapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ingeniumbd.buyerapp.R;
import com.ingeniumbd.buyerapp.infrastructure.Constant;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class RegisterActivity extends AppCompatActivity {

    // xml instance
    private EditText userNameEt,userPhoneEt,userEmailEt,userPasswordEt,userConfirmPasswordEt,userCountryEt,userCityEt,userAddressEt;
    private ImageView image;

    /**
     *--------class instance---------
     **/
    private static final int GALLERY_REQUEST_CODE = 1;
    private static final int PICK_IMAGE_REQUEST = 234;
    private Uri uri;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference userRole;
    boolean isActive = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        /**
         *-------xml initialization-----------
         **/
        userNameEt = findViewById(R.id.register_activity_name);
        userPhoneEt = findViewById(R.id.register_activity_phone);
        userEmailEt = findViewById(R.id.register_activity_email);
        userPasswordEt = findViewById(R.id.register_activity_password);
        userConfirmPasswordEt = findViewById(R.id.register_activity_confirmPassword);
        userCountryEt = findViewById(R.id.register_activity_country);
        userCityEt = findViewById(R.id.register_activity_city);
        userAddressEt = findViewById(R.id.register_activity_address);
        image = (ImageView) findViewById(R.id.image);

        userRole = FirebaseDatabase.getInstance().getReference().child("Role");


        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("attempting to register...");
        progressDialog.setCancelable(false);
    }

    public void register(View view) {

        /**
         *------ get all text / data from user input field
         **/
        final String userName = userNameEt.getText().toString();
        final String userPhone = userPhoneEt.getText().toString();
        final String userEmail = userEmailEt.getText().toString();
        final String userPassword = userPasswordEt.getText().toString();
        String userConfirmPassword = userConfirmPasswordEt.getText().toString();
        final String userCountry = userCountryEt.getText().toString();
        final String userCity = userCityEt.getText().toString();
        final String userAddress = userAddressEt.getText().toString();

        /**
         *------- check there is any field is empty ????
         **/
        if (userName.equals("")){
            userNameEt.setError("Please put your name");
        }
        if (userPhone.equals("")){
            userPhoneEt.setError("Please put your phone number");
        }
        if (userEmail.equals("")){
            userEmailEt.setError("Please put your email");
        }
        if (userPassword.equals("")){
            userPasswordEt.setError("please put your password");
        }
        if (userCountry.equals("")){
            userCountryEt.setError("Please put your country");
        }
        if (userCity.equals("")){
            userCityEt.setError("Please put your city");
        }
        if (userAddress.equals("")){
            userAddressEt.setError("Please put your address");
        }

        if (userPassword.length()<6 || userConfirmPassword.length()<6){
            Toast.makeText(getApplicationContext(),"password length must be six character",Toast.LENGTH_SHORT).show();
            return;
        }

        /**
         *------check password match or not??----
         **/
        if (!userPassword.equals(userConfirmPassword)){
            Toast.makeText(getApplicationContext(),"password doesn't match",Toast.LENGTH_SHORT).show();
            return;
        }


        /**
         *----alright now register the user------
         **/
        if (!userName.equals("") && !userPhone.equals("") && !userEmail.equals("") && !userPassword.equals("")
                && !userConfirmPassword.equals("") && !userCountry.equals("") && !userCity.equals("") && !userAddress.equals("")){

            progressDialog.show();
            if (uri != null){

                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(Constant.DATABASE_BUYER_REFERENCE).push();
                /**
                 *---save image file----
                 **/
                StorageReference reference = FirebaseStorage.getInstance().getReference();
                StorageReference filePath = reference.child("Photos of " + databaseReference.getKey()).child(databaseReference.getKey());

                filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                       final String downloadUri = taskSnapshot.getDownloadUrl().toString();
                        firebaseAuth.createUserWithEmailAndPassword(userEmail,userPassword)
                               .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                   @Override
                                   public void onComplete(@NonNull Task<AuthResult> task) {
                                       if (task.isSuccessful()){
                                           String user_id = firebaseAuth.getCurrentUser().getUid();

                                           DatabaseReference roleofuser_db = userRole.child(user_id);
                                           roleofuser_db.child("role").setValue("buyer");
                                           roleofuser_db.child("userId").setValue(user_id);
                                           roleofuser_db.child("isActive").setValue(isActive);

                                           databaseReference.child("id").setValue(databaseReference.getKey());
                                           databaseReference.child("role").setValue("buyer");
                                           databaseReference.child("userName").setValue(userName);
                                           databaseReference.child("userPhone").setValue(userPhone);
                                           databaseReference.child("profilepic").setValue(downloadUri);
                                           databaseReference.child("userEmail").setValue(userEmail);
                                           databaseReference.child("country").setValue(userCountry);
                                           databaseReference.child("city").setValue(userCity);
                                           databaseReference.child("address").setValue(userAddress);
                                           databaseReference.child("userId").setValue(user_id);
                                           Toast.makeText(getApplicationContext(),"Account Registered",Toast.LENGTH_SHORT).show();
                                           startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                           finish();
                                           firebaseAuth.signOut();

                                       }
                                   }
                               }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }
                });


            }else {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Please upload an image",Toast.LENGTH_SHORT).show();
            }


        }


    }

    /**
     *---------image upload event-------
     **/
    public void imageUpload(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select AddOfferData"), PICK_IMAGE_REQUEST);
    }

    /**
     * ---when back from activity
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            uri = data.getData();
            CropImage.activity(uri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                uri = result.getUri();
                image.setImageResource(R.drawable.upload_documents_icon_red);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

}
