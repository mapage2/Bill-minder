package com.example.amasio.bill_minder;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.Toast;


public class AccountOverviewFragment extends Fragment{

    onNewItemSelectedListener itemListener;

    FloatingActionButton fabNewItem;
    FloatingActionButton fabBill;
    FloatingActionButton fabSubscription;
    FloatingActionButton fabExpense;

    private boolean FAB_Status = false;

    Animation show_fab_bill;
    Animation hide_fab_bill;
    Animation show_fab_subscription;
    Animation hide_fab_subscription;
    Animation show_fab_expense;
    Animation hide_fab_expense;




    public interface onNewItemSelectedListener{
        public void onNewItemSelected(int button);
    }

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private final int BILL = 1;
    private final int SUBSCRIPTION = 2;
    private final int EXPENSE = 3;

    View overviewInflater;

    private static String TAG = "Account Overview Frag";

    private String mParam1;
    private String mParam2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        show_fab_bill = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_bill_show);
        hide_fab_bill = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_bill_hide);
        show_fab_subscription = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_subscription_show);
        hide_fab_subscription = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_subscription_hide);
        show_fab_expense = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_expense_show);
        hide_fab_expense = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_expense_hide);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        overviewInflater = inflater.inflate(R.layout.fragment_account_overview, container, false);

        fabNewItem = (FloatingActionButton) overviewInflater.findViewById(R.id.fab_new_item);
        fabBill = (FloatingActionButton) overviewInflater.findViewById(R.id.fab_bill);
        fabSubscription = (FloatingActionButton) overviewInflater.findViewById(R.id.fab_subscription);
        fabExpense = (FloatingActionButton) overviewInflater.findViewById(R.id.fab_expense);

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
                itemListener.onNewItemSelected(BILL);
                //Toast.makeText(getActivity(), "New bill coming soon", Toast.LENGTH_SHORT).show();
            }
        });

        fabSubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListener.onNewItemSelected(SUBSCRIPTION);
                //Toast.makeText(getActivity(), "New subscription coming soon", Toast.LENGTH_SHORT).show();
            }
        });

        fabExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListener.onNewItemSelected(EXPENSE);
                //Toast.makeText(getActivity(), "New expense coming soon", Toast.LENGTH_SHORT).show();
            }
        });

        return overviewInflater;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onNewItemSelectedListener) {
            itemListener = (onNewItemSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onNewItemSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        itemListener = null;
    }

    private void expandFabMenu() {

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
