/*
 * Copyright (c) 2017, Progrema Studio. All rights reserved.
 */

package com.imobilenetid.helloj_oxy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;

//import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpActivity extends BaseActivity {

    private static final String TAG = "sign-up-activity";

    private final int INTENT_CAMERA = 0;

    private DatabaseReference mDatabase;

    private FirebaseStorage mStorage;

    private FirebaseAuth mAuth;

    private EditText mDisplayNameField;

    private EditText mEmailField;

    private EditText mPasswordField;

    private EditText mdataAlamat;

    private EditText mdataAlamatLengkap;

    private EditText mPhoneNumberField;

    //private CircleImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        Initiate activity layout
         */
        setContentView(R.layout.activity_signup);

        /*
        Initiate firebase database instance
         */
        mDatabase = FirebaseDatabase.getInstance().getReference();

        /*
        Initialize Firebase Storage reference
         */
        mStorage = FirebaseStorage.getInstance();

        /*
        Initiate firebase authentication instance
         */
        mAuth = FirebaseAuth.getInstance();

        /*
        Initiate user input field
         */
        mDisplayNameField = (EditText) findViewById(R.id.other_display_name_field);
        mEmailField = (EditText) findViewById(R.id.email_field);
        mdataAlamat = (EditText) findViewById(R.id.data_alamat);
        mdataAlamatLengkap = (EditText) findViewById(R.id.data_alamat_lengkap);
        mPhoneNumberField = (EditText) findViewById(R.id.phone_number_field);
        mPasswordField = (EditText) findViewById(R.id.password_field);




        /*
        Initiate button
         */
        Button signUpButton = (Button) findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });


    }





    /**
     * User Sign-up Process
     */
    private void signUp() {

        /*
        Validate user input
         */
        if (!validateForm()) {
            return;
        }

        /*
        Show progress dialog
         */
        showProgressDialog();

        /*
        Get email and password
         */
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        /*
        Sign-up with email and password
         */
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        /*
                        Sign-up completes and hide progress dialog
                         */
                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                        hideProgressDialog();

                        if (task.isSuccessful()) {
                            /*
                            Sign-up success
                             */
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            /*
                            Sign-up failed
                             */
                            Toast.makeText(SignUpActivity.this,
                                    getString(R.string.str_Sign_Up_failed), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Validate user input form to make sure it's following android security standard
     *
     * @return result of the checking. TRUE is okay. Otherwise, FALSE.
     */
    private boolean validateForm() {

        /*
        Initiate checking result
         */
        boolean result = true;

        /*
        Check name field
         */
        if (TextUtils.isEmpty(mDisplayNameField.getText().toString())) {
            mDisplayNameField.setError("Required");
            result = false;
        } else {
            mDisplayNameField.setError(null);
        }

        /*
        Check email field
         */
        if (TextUtils.isEmpty(mEmailField.getText().toString())) {
            mEmailField.setError("Required");
            result = false;
        } else {
            mEmailField.setError(null);
        }

        /*
        Check Alamat field
         */
        if (TextUtils.isEmpty(mdataAlamat.getText().toString())) {
            mdataAlamat.setError("Required");
            result = false;
        } else {
            mdataAlamat.setError(null);
        }

        /*
        Check Alamat Lengkap field
         */
        if (TextUtils.isEmpty(mdataAlamatLengkap.getText().toString())) {
            mdataAlamatLengkap.setError("Required");
            result = false;
        } else {
            mdataAlamatLengkap.setError(null);
        }

        /*
        Check password field
         */
        if (TextUtils.isEmpty(mPasswordField.getText().toString())) {
            mPasswordField.setError("Required");
            result = false;
        } else {
            mPasswordField.setError(null);
        }

        /*
        Check phone number field
         */
        if (TextUtils.isEmpty(mPhoneNumberField.getText().toString())) {
            mPhoneNumberField.setError("Required");
            result = false;
        } else {
            mPhoneNumberField.setError(null);
        }

        /*
        TODO: to add more comprehensive password checking
         */

        /*
        Return checking result
         */
        return result;
    }

    /**
     * Action to do if User Authentication is success
     */
    private void onAuthSuccess(final FirebaseUser firebaseUser) {

        /*
        Generate random key for addressing every new post
         */
        final String key = mDatabase.child(FirebasePath.USERS).child(getUid()).push().getKey();

        /*
        Create storage reference used by Firebase
         */
        StorageReference pictureReference = mStorage.getReference()
                .child(FirebasePath.USERS).child(firebaseUser.getUid()).child(key).child("profile_picture.jpg");

        /*
        Create access to profile picture and check if it's exist
         */
        File directoryPath = new File(getFilesDir(), "post");
        File filePath = new File(directoryPath.getPath() + File.separator + "accident.jpg");

        /*
        Check image file existence
         */
        if(filePath.exists()) {

            /*
            Create bitmap for image posting
             */
            Bitmap bitmap = BitmapFactory.decodeFile(filePath.getAbsolutePath());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] data = stream.toByteArray();

            /*
            Upload image to Firebase-Storage server
             */
            UploadTask uploadTask = pictureReference.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    /*
                    Handle unsuccessful uploads
                     */
                    Log.d(TAG, "Upload fail");
                }

            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    /*
                    Handle successful uploads
                     */
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Log.d(TAG, "Download Url " + downloadUrl.toString());

                    /*
                    Write remaining details of post with image exist
                     */
                    createNewUserData(firebaseUser, downloadUrl.toString());
                }
            });

        } else {
            /*
            Create new user without picture
             */
            createNewUserData(firebaseUser, AppSharedPreferences.NO_URL);
        }
    }

    /**
     * Create new user data in Firebase
     *
     * @param firebaseUser reference to firebase user
     * @param pictureUrl reference to user profile picture
     */
    private void createNewUserData(final FirebaseUser firebaseUser, String pictureUrl) {

        /*
        Prepare user data
         */
        String email = firebaseUser.getEmail();
        String userId = firebaseUser.getUid();
        String displayName = mDisplayNameField.getText().toString();
        String phoneNumber = mPhoneNumberField.getText().toString();
        String dataAlamat = mdataAlamat.getText().toString();
        String dataAlamatLengkap = mdataAlamatLengkap.getText().toString();

        /*
        Store current user details to shared-preference
         */
        AppSharedPreferences.storeUserInformation(
                this,
                getUid(),
                displayName,
                email,
                dataAlamat,
                dataAlamatLengkap,
                phoneNumber,
                pictureUrl
        );

        /*
        Create user object in database
         */
        User user = new User(
                getUid(),
                displayName,
                email,
                dataAlamat,
                dataAlamatLengkap,
                phoneNumber,
                pictureUrl
        );
        mDatabase.child(FirebasePath.USERS).child(userId).setValue(user.toMap());

        /*
        Create new messaging token from notification
         */
        String token = FirebaseInstanceId.getInstance().getToken();
        if (token != null) {
            mDatabase.child(FirebasePath.TOKEN).child(token).setValue("true");
        }

        /*
        Go to main activity and close this activity
         */
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*
        Go back to Sign-In activity
         */
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }
}
