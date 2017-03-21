package com.example.amasio.bill_minder;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewBillFragment extends Fragment {

    private static String TAG = "Bill Fragment";
    ProgressTask progress;

    EditText txtBillName;
    EditText txtCompanyName;
    EditText txtBillAmount;
    Spinner billType;
    ImageButton btnCalendarFrag;
    TextView txtSelectedStartDate;
    Button btnCancel;
    Button btnAdd;

    boolean cleanForm = true;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private DatabaseReference mRootReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mBillReference;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private newBillListener mListener;

    View newBillInflater;

    public interface newBillListener {
        // TODO: Update argument type and name
        void onNewBill(Uri uri);
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewBillFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewBillFragment newInstance(String param1, String param2) {
        NewBillFragment fragment = new NewBillFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mBillReference = mRootReference.child("Bill");
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        newBillInflater = inflater.inflate(R.layout.fragment_new_bill, container, false);

        txtBillName = (EditText) newBillInflater.findViewById(R.id.editText_billName);
        txtCompanyName = (EditText) newBillInflater.findViewById(R.id.editText_companyName);
        txtBillAmount = (EditText) newBillInflater.findViewById(R.id.editText_billAmount);

        billType = (Spinner) newBillInflater.findViewById(R.id.spinner_billType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.expense_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        billType.setAdapter(adapter);

        btnCalendarFrag = (ImageButton) newBillInflater.findViewById(R.id.btn_calendarFrag);
        txtSelectedStartDate = (TextView) newBillInflater.findViewById(R.id.txt_selectedStartDate);

        btnAdd = (Button) newBillInflater.findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                writeNewBill();
            }
        });

        btnCancel = (Button) newBillInflater.findViewById(R.id.btn_cancel);

        return newBillInflater;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onNewBill(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof newBillListener) {
            mListener = (newBillListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public boolean validateForm(){

        boolean valid;

        String nameRegex = "^[A-Za-z\\\\s]+$";
        String companyRegex = "^[.@&]?[a-zA-Z0-9 ]+[ !.@&()]?[ a-zA-Z0-9!()]+";
        String amountRegex = "(\\$)?[0-9]+\\.*[0-9]*";

        String billName = txtBillName.getText().toString();
        String companyName = txtCompanyName.getText().toString();
        String billAmount = txtBillAmount.getText().toString();

        if(isBillName(billName, nameRegex) && isCompanyName(companyName, companyRegex)
                && isBillAmount(billAmount, amountRegex)){
            valid = true;
        } else{
            valid = false;
        }

        return valid;
    }

    public boolean isBillName(String name, String regex){

        if(name.length()>25){
            txtBillName.setError("Name length under 25 characters");
            return false;
        } else if(!name.matches(regex)){
            txtBillName.setError("Please enter a valid Bill name");
            return false;
        } else{
            txtBillName.setError(null);
            return true;
        }
    }

    public boolean isCompanyName(String name, String regex){

        if(name.length()>25){
            txtCompanyName.setError("Name length under 25 characters");
            return false;
        } else if(!name.matches(regex)){
            txtCompanyName.setError("Please enter a valid Company name");
            return false;
        } else{
            txtCompanyName.setError(null);
            return true;
        }
    }

    public boolean isBillAmount(String amount, String regex){

        if(amount.length()>10){
            txtBillAmount.setError("Amount should not exceed 10 characters");
            return false;
        } else if(!amount.matches(regex)){
            txtBillAmount.setError("Please enter a valid Bill amount (e.x. 00.00)");
            return false;
        } else{
            txtBillAmount.setError(null);
            return true;
        }
    }

    public boolean isCleanForm(){

        String billName = txtBillName.getText().toString();
        String companyName = txtCompanyName.getText().toString();
        String billAmount = txtBillAmount.getText().toString();

        if (TextUtils.isEmpty(billName) && TextUtils.isEmpty(companyName) && TextUtils.isEmpty(billAmount)) {
            cleanForm = true;
            return cleanForm;
        } else{
            return false;
        }
    }

    public void writeNewBill(){

        if(validateForm()){

            String ownerId = mAuth.getCurrentUser().getUid();
            String billName = txtBillName.getText().toString();
            String companyName = txtCompanyName.getText().toString();
            String billAmountStr = txtBillAmount.getText().toString();
            Double billAmount = Double.parseDouble(billAmountStr);
            String billFreq = billType.getSelectedItem().toString();
            String startDate = txtSelectedStartDate.getText().toString();
            int billDate = 21;

            Bill bill = new Bill(ownerId, billName, companyName, startDate, startDate, billDate, billAmount, billFreq);

            progress = new ProgressTask();
            progress.execute(bill);





        }else{

            Toast.makeText(getActivity(), "Invalid input", Toast.LENGTH_SHORT).show();
        }
    }

    class ProgressTask extends AsyncTask<Bill, Void, Void>{

        private ProgressDialog progressDialog;


        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Adding Bill");
            progressDialog.setMessage("Processing...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Bill... bill) {

//            try {
//                String userId = mAuth.getCurrentUser().getUid();
//                mBillReference.child(userId).setValue(bill);
//            } catch (DatabaseException e) {
//
//                Log.e(TAG, "Database issue, Bill not added");
//            }
            DatabaseReference newBill = mBillReference.push();
            StringBuilder string = new StringBuilder(newBill.getKey());
            string = string.deleteCharAt(0);
            String billKey = string.toString();

            mBillReference.child(billKey).setValue(bill[0]);

            return null;

        }
        @Override
        protected void onPostExecute(Void voids){

            super.onPostExecute(voids);
            Toast.makeText(getActivity(), "Bill added", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "bill added successfully");
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}


