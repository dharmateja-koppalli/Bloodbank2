package com.example.dharma.bloodbank;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class profile_update extends AppCompatActivity {

    EditText e1,e2,e3,e4;
    Spinner s1,s2,s3,s4;
    String b_group[]={"A+","A-","B+","B-","O+","O-","AB+","AB-"};
    String states[]={"Andhra pradesh","Tamil nadu","Karnataka","Kerala"};
    String cities[]={"Hyderabad","Chennai","Banglore","Kochi"};
    String genders[]={"Male","Female"};
    Button b1;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    DocumentReference documentReference,documentReference1,documentReference2;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    String ui;
    String n1,n2,n3,n4,n5,n6,n7,n8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        e1=(EditText)findViewById(R.id.update_name);
        e2=(EditText)findViewById(R.id.update_age);
        e3=(EditText)findViewById(R.id.update_mobile);
        e4=(EditText)findViewById(R.id.update_pincode);
        s1=(Spinner)findViewById(R.id.update_gender);
        s2=(Spinner)findViewById(R.id.update_group);
        s3=(Spinner)findViewById(R.id.update_state);
        s4=(Spinner)findViewById(R.id.update_city);
        b1=(Button)findViewById(R.id.update_update);
        ArrayAdapter adp_gender = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, genders);
        s1.setAdapter(adp_gender);
        ArrayAdapter adp_group = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, b_group);
        s2.setAdapter(adp_group);
        ArrayAdapter adp_state = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, states);
        s3.setAdapter(adp_state);
        ArrayAdapter adp_city = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cities);
        s4.setAdapter(adp_city);


        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        ui = firebaseUser.getUid();


        documentReference1= firebaseFirestore.collection("users").document(ui);
        documentReference1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot= task.getResult();
                    n1=documentSnapshot.getString("name");
                    n2 = documentSnapshot.getString("age");
                    n3 = documentSnapshot.getString("mobile");
                    n4 = documentSnapshot.getString("pincode");

                    e1.setText(n1);
                    e2.setText(n2);
                    e3.setText(n3);
                    e4.setText(n4);


                }

            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = ProgressDialog.show(profile_update.this,"Please Wait ","While Updating",true);
                updatedata();
            }
        });



    }

    public void updatedata() {

        documentReference=firebaseFirestore.collection("users").document(ui);
        documentReference.update("name",e1.getText().toString());
        documentReference.update("age",e2.getText().toString());
        documentReference.update("mobile",e3.getText().toString());
        documentReference.update("gender",s1.getSelectedItem().toString());
        documentReference.update("bloodgroup",s2.getSelectedItem().toString());
        documentReference.update("state",s3.getSelectedItem().toString());
        documentReference.update("city",s4.getSelectedItem().toString());
        documentReference.update("pincode",e4.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    if(isNetworkAvailable()) {
                        Toast.makeText(profile_update.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(profile_update.this, home.class);
                        startActivity(intent);
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(profile_update.this, "No network", Toast.LENGTH_SHORT).show();
                    }
                }

                else {
                    Toast.makeText(profile_update.this, "Unsuccessfull", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    private boolean isNetworkAvailable() {
        // Using ConnectivityManager to check for Network Connection
        ConnectivityManager connectivityManager = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
