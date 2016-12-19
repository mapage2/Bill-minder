package com.example.amasio.bill_minder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Login";

    EditText email;
    EditText password;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.txtEmail);
        password = (EditText) findViewById(R.id.txtPassword);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
//                    Intent i = new Intent(LoginActivity.this, LoginActivity.class);
//                    startActivity(i);
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

    public void signIn(View v) {

        final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "Please Wait...",
                "Processing...", true);

        final String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        Log.d(TAG, "signIn:" + userEmail);
        if (validateForm()) {
            mAuth.signInWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                                Toast.makeText(LoginActivity.this, "Welcome "+userEmail, Toast.LENGTH_LONG).show();
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                signOut();
                            }else{
                                Log.e("ERROR", task.getException().toString());
                                Toast.makeText(LoginActivity.this, "Incorrect Email or password",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else{
            progressDialog.dismiss();
            Toast.makeText(LoginActivity.this, "Login Faliure",Toast.LENGTH_SHORT).show();
        }


    }

    private boolean validateForm() {
        boolean valid;
        String emailRegex = "([-\\w._+%]+(?:\\.[-\\w._+%]+)*@(?:[\\w-]+\\.)+[a-zA-Z]{2,7})";

        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if(isEmail(userEmail, emailRegex) && isPassword(userPassword)){

            valid = true;
        } else {
            valid = false;
        }
        return valid;
    }

    private void signOut() {
        mAuth.signOut();
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
    boolean isPassword(String userPassword) {

        if (TextUtils.isEmpty(userPassword)) {
            password.setError("Required.");
            return false;
        } else if (userPassword.length() < 6 || userPassword.length() > 15) {
            password.setError("This password is too long");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }
}
