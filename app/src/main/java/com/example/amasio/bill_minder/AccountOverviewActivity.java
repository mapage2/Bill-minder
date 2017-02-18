package com.example.amasio.bill_minder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class AccountOverviewActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener,
        AccountOverviewFragment.onNewItemSelectedListener, NewBillFragment.newBillListener{

    TextView navName;
    TextView navEmail;
    ImageView navImage;

    private static String TAG = "Account Overview";
    private final int BILL_FRAGMENT = 1;
    private final int SUBSCRIPTION_FRAGMENT = 2;
    private final int EXPENSE_FRAGMENT = 3;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;

    private GoogleApiClient mGoogleApiClient;

    //ADD REFERENCES FOR DATA STRUCTURES
    private DatabaseReference mRootReference = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.overview_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Account Overview");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.account_overview_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        navName = (TextView) header.findViewById(R.id.nav_name_textview);
        navEmail = (TextView) header.findViewById(R.id.nav_email_textView);
        navImage = (ImageView) header.findViewById(R.id.imageView);

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
                    Intent i = new Intent(AccountOverviewActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        };

        String name = mAuth.getCurrentUser().getDisplayName();
        navName.setText(name);

        String email = mAuth.getCurrentUser().getEmail();
        navEmail.setText(email);

        Uri picUrl = mAuth.getCurrentUser().getPhotoUrl();
        if (!(picUrl == null)) {
            Picasso.with(this).load(picUrl).into(navImage);
        }

        if (findViewById(R.id.account_overview_layout) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            AccountOverviewFragment overviewFragment = new AccountOverviewFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            overviewFragment.setArguments(getIntent().getExtras());


            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_frame, overviewFragment).commit();
        }
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.account_overview_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.account_overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(AccountOverviewActivity.this, "Settings Page coming soon", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_profile) {
            Toast.makeText(AccountOverviewActivity.this, "Profile Page coming soon", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bills) {

            Toast.makeText(AccountOverviewActivity.this, "Bills interface coming soon", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_subscriptions) {

            Toast.makeText(AccountOverviewActivity.this, "Subscription interface coming soon", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_expenses) {

            Toast.makeText(AccountOverviewActivity.this, "Expenses interface coming soon", Toast.LENGTH_SHORT).show();
        }/* else if (id == R.id.nav_manage) {

        }*/ else if (id == R.id.nav_list_view) {

            Toast.makeText(AccountOverviewActivity.this, "List interface coming soon", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_signout) {

            signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.account_overview_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void signOut() {

        mAuth.signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        LoginManager.getInstance().logOut();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(AccountOverviewActivity.this, "Google Play Services error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNewItemSelected(int button) {

        if(button == BILL_FRAGMENT){

            NewBillFragment bill = new NewBillFragment();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fragment_frame, bill);
            transaction.addToBackStack(null);
            transaction.commit();

        }
    }

    @Override
    public void onNewBill(Uri uri) {

    }
}
