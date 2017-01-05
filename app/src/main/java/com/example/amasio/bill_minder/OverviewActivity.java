package com.example.amasio.bill_minder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.View;
import android.widget.Toast;
import android.widget.FrameLayout;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OverviewActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener{

    private Toolbar toolbar;
    FloatingActionButton fabNewItem;
    FloatingActionButton fabBill;
    FloatingActionButton fabSubscription;
    FloatingActionButton fabExpense;
    CoordinatorLayout baseLayout;

    private boolean FAB_Status = false;

    Animation show_fab_bill;
    Animation hide_fab_bill;
    Animation show_fab_subscription;
    Animation hide_fab_subscription;
    Animation show_fab_expense;
    Animation hide_fab_expense;

    private static String TAG = "Overview";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;

    private GoogleApiClient mGoogleApiClient;

    //ADD REFERENCES FOR DATA STRUCTURES
    private DatabaseReference mRootReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        setTitle("Overview");

        baseLayout = (CoordinatorLayout) findViewById(R.id.activity_overview);

        fabNewItem = (FloatingActionButton) findViewById(R.id.fab_new_item);
        fabBill = (FloatingActionButton) findViewById(R.id.fab_bill);
        fabSubscription = (FloatingActionButton) findViewById(R.id.fab_subscription);
        fabExpense = (FloatingActionButton) findViewById(R.id.fab_expense);

        show_fab_bill = AnimationUtils.loadAnimation(getApplication(), R.anim.fab_bill_show);
        hide_fab_bill = AnimationUtils.loadAnimation(getApplication(), R.anim.fab_bill_hide);
        show_fab_subscription = AnimationUtils.loadAnimation(getApplication(), R.anim.fab_subscription_show);
        hide_fab_subscription = AnimationUtils.loadAnimation(getApplication(), R.anim.fab_subscription_hide);
        show_fab_expense = AnimationUtils.loadAnimation(getApplication(), R.anim.fab_expense_show);
        hide_fab_expense = AnimationUtils.loadAnimation(getApplication(), R.anim.fab_expense_hide);

        fabNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (FAB_Status == false) {
                    //Display FAB menu
                    expandFabMenu();
                    FAB_Status = true;
                } else {
                    //Close FAB menu
                    hideFabMenu();
                    FAB_Status = false;
                }
            }
        });

        fabBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(), "New bill coming soon", Toast.LENGTH_SHORT).show();
            }
        });

        fabSubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(), "New subscription coming soon", Toast.LENGTH_SHORT).show();
            }
        });

        fabExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(), "New expense coming soon", Toast.LENGTH_SHORT).show();
            }
        });

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
                    Intent i = new Intent(OverviewActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        };
    }

    @Override
    public void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
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

    public void signOut(View v) {

        mAuth.signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        LoginManager.getInstance().logOut();
    }

    private void expandFabMenu(){

        //Bill FAB
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fabBill.getLayoutParams();
        layoutParams.rightMargin += (int) (fabBill.getWidth() * 1.7);
        layoutParams.bottomMargin += (int) (fabBill.getHeight() * 0.25);
        fabBill.setLayoutParams(layoutParams);
        fabBill.startAnimation(show_fab_bill);
        fabBill.setClickable(true);

        //Subscription FAB
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fabSubscription.getLayoutParams();
        layoutParams2.rightMargin += (int) (fabSubscription.getWidth() * 1.5);
        layoutParams2.bottomMargin += (int) (fabSubscription.getHeight() * 1.5);
        fabSubscription.setLayoutParams(layoutParams2);
        fabSubscription.startAnimation(show_fab_subscription);
        fabSubscription.setClickable(true);

        //Expense FAB
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fabExpense.getLayoutParams();
        layoutParams3.rightMargin += (int) (fabExpense.getWidth() * 0.25);
        layoutParams3.bottomMargin += (int) (fabExpense.getHeight() * 1.7);
        fabExpense.setLayoutParams(layoutParams3);
        fabExpense.startAnimation(show_fab_expense);
        fabExpense.setClickable(true);
    }

    private void hideFabMenu() {

        //Bill FAB
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fabBill.getLayoutParams();
        layoutParams.rightMargin -= (int) (fabBill.getWidth() * 1.7);
        layoutParams.bottomMargin -= (int) (fabBill.getHeight() * 0.25);
        fabBill.setLayoutParams(layoutParams);
        fabBill.startAnimation(hide_fab_bill);
        fabBill.setClickable(true);

        //Subscription FAB
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fabSubscription.getLayoutParams();
        layoutParams2.rightMargin -= (int) (fabSubscription.getWidth() * 1.5);
        layoutParams2.bottomMargin -= (int) (fabSubscription.getHeight() * 1.5);
        fabSubscription.setLayoutParams(layoutParams2);
        fabSubscription.startAnimation(hide_fab_subscription);
        fabSubscription.setClickable(true);

        //Expense FAB
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fabExpense.getLayoutParams();
        layoutParams3.rightMargin -= (int) (fabExpense.getWidth() * 0.25);
        layoutParams3.bottomMargin -= (int) (fabExpense.getHeight() * 1.7);
        fabExpense.setLayoutParams(layoutParams3);
        fabExpense.startAnimation(hide_fab_expense);
        fabExpense.setClickable(true);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(OverviewActivity.this, "Google Play Services error", Toast.LENGTH_SHORT).show();
    }
}
