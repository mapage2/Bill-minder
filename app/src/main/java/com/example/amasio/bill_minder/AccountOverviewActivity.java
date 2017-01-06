package com.example.amasio.bill_minder;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.Toast;

public class AccountOverviewActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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

    private static String TAG = "Account Overview";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.overview_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
}
