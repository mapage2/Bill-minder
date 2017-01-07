package com.example.amasio.bill_minder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.text.TextUtils;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = "Registration";

    EditText firstName;
    EditText lastName;
    EditText email;
    EditText password;
    Toolbar toolbar;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;

    private DatabaseReference mRootReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mUserReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        toolbar.setTitle("Account Registration");
        setSupportActionBar(toolbar);

        firstName = (EditText) findViewById(R.id.txtFirstName);
        lastName = (EditText) findViewById(R.id.txtLastName);
        email = (EditText) findViewById(R.id.txtEmail);
        password = (EditText) findViewById(R.id.txtPassword);

        mUserReference = mRootReference.child("User");

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUser = firebaseAuth.getCurrentUser();
                if(mUser != null){
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + mUser.getUid());
                    String displayName = firstName.getText().toString() + " " + lastName.getText().toString();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(displayName).build();
                    mUser.updateProfile(profileUpdates);
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    public void createAccount(View v) {

        final ProgressDialog progressDialog = ProgressDialog.show(RegistrationActivity.this, "Please Wait...",
                "Processing...", true);

        final String fName = firstName.getText().toString();
        final String lName = lastName.getText().toString();
        final String userEmail = email.getText().toString();
        final String userPassword = password.getText().toString();

        Log.d(TAG, "createAccount:" + fName+" "+lName);
        if (validateForm()) {
            (mAuth.createUserWithEmailAndPassword(userEmail, userPassword))
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            progressDialog.dismiss();

                            if(task.isSuccessful()){
                                Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                                Toast.makeText(RegistrationActivity.this, "Registration Successful",
                                        Toast.LENGTH_SHORT).show();
                                writeNewUser(fName, lName, userEmail, userPassword);
                                Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                                startActivity(i);
                            }else{
                                Log.e("ERROR", task.getException().toString());
                                if(task.getException().toString().contains("AuthUserCollisionException")){
                                    Toast.makeText(RegistrationActivity.this, "Email already exists",
                                            Toast.LENGTH_SHORT).show();
                                    email.getText().clear();
                                    password.getText().clear();
                                }else{
                                    Toast.makeText(RegistrationActivity.this, task.getException().toString(),
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                    });
        } else{
            progressDialog.dismiss();
            Toast.makeText(RegistrationActivity.this, "Invalid input", Toast.LENGTH_SHORT);
        }
    }

    private boolean validateForm() {

        boolean valid;

        String nameRegex = "[A-Z][a-z]+( [A-Z][a-z]+)?";
        String emailRegex = "([-\\w._+%]+(?:\\.[-\\w._+%]+)*@(?:[\\w-]+\\.)+[a-zA-Z]{2,7})";

        String userFName = firstName.getText().toString();
        String userLName = lastName.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if(isFirstName(userFName, nameRegex) && isLastName(userLName, nameRegex) && isEmail(userEmail, emailRegex)
                && isPassword(userPassword)){
            valid = true;
        } else{
            valid = false;
        }

        return valid;
    }

    private void signOut() {
        mAuth.signOut();
    }

    boolean isFirstName(String name, String regex){
        if(name.length()>25){
            firstName.setError("Name length under 25 characters");
            return false;
        } else if(!name.matches(regex)){
            firstName.setError("Please enter a valid first name");
            return false;
        } else{
            firstName.setError(null);
            return true;
        }
    }

    boolean isLastName(String name, String regex){
        if(name.length()>25){
            lastName.setError("Name length under 25 characters");
            return false;
        } else if(!name.matches(regex)){
            lastName.setError("Please enter a valid last name");
            return false;
        } else{
            lastName.setError(null);
            return true;
        }
    }

    boolean isEmail(String userEmail, String regex){
        if (!userEmail.matches(regex)) {
            email.setError("Enter a valid email.");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }
    boolean isPassword(String userPassword){

        if (TextUtils.isEmpty(userPassword)) {
            password.setError("Required.");
            return false;
        } else if(userPassword.length()<6 || userPassword.length()>15){
            password.setError("Password length between 6-15 characters");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    void writeNewUser(String fName, String lName, String userEmail, String userPassword){

        String userId = mUser.getUid();
        User user = new User(fName, lName, userEmail, userPassword);

        mUserReference.child(userId).setValue(user);
    }
}


