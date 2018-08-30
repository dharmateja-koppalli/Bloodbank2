package com.example.dharma.bloodbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class profile extends AppCompatActivity {
    EditText name,email,password,age,pincode,mobile;
    Spinner gender,group,state,city;
    Button register;
    private FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    String uid,uemail;
    String s1,s2,s3,s4,s5,s6,s7,s8,s9,s10;
    String b_group[]={"A+","A-","B+","B-","O+","O-","AB+","AB-"};
    String states[]={"Andhra pradesh","Tamil nadu","Karnataka","Kerala"};
    String cities[]={"Hyderabad","Chennai","Banglore","Kochi"};
    String genders[]={"Male","Female"};
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = (EditText) findViewById(R.id.profile_name);
        email = (EditText) findViewById(R.id.profile_email);
        password = (EditText) findViewById(R.id.profile_password);
        age = (EditText) findViewById(R.id.profile_age);
        pincode = (EditText) findViewById(R.id.profile_pincode);
        gender = (Spinner) findViewById(R.id.profile_gender);
        group = (Spinner) findViewById(R.id.profile_group);
        state = (Spinner) findViewById(R.id.profile_state);
        city = (Spinner) findViewById(R.id.profile_city);
        mobile = (EditText) findViewById(R.id.profile_Mobile);
        register = (Button) findViewById(R.id.profile_register);
        ArrayAdapter adp_gender = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, genders);
        gender.setAdapter(adp_gender);
        ArrayAdapter adp_group = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, b_group);
        group.setAdapter(adp_group);
        ArrayAdapter adp_state = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, states);
        state.setAdapter(adp_state);
        ArrayAdapter adp_city = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cities);
        city.setAdapter(adp_city);



        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = ProgressDialog.show(profile.this,"Please Wait ","While Registering",true);
                register();
            }
        });

    }
    public void register(){
        s1=name.getText().toString().trim();
        s2=email.getText().toString().trim();
        s3=password.getText().toString().trim();
        s4=age.getText().toString().trim();
        s5=mobile.getText().toString().trim();
        s6=gender.getSelectedItem().toString();
        s7=group.getSelectedItem().toString();
        s8=state.getSelectedItem().toString();
        s9=city.getSelectedItem().toString();
        s10=pincode.getText().toString().trim();


        if (!isValidEmail(s2)) {
            progressDialog.dismiss();
            email.setError("please enter your gmail account");
        }
        else if (TextUtils.isEmpty(s2)) {
            progressDialog.dismiss();
            email.setError( "Email is required!" );
        }
        else if(TextUtils.isEmpty(s3)){
            progressDialog.dismiss();
            password.setError( "Password!" );
        }
        else if (TextUtils.isEmpty(s1)){
            progressDialog.dismiss();
            name.setError( "Name!" );
        }
        else if (TextUtils.isEmpty(s4)){
            progressDialog.dismiss();
            age.setError( "Age!" );
        }
        else if(TextUtils.isEmpty(s5)){
            progressDialog.dismiss();
            mobile.setError( "Mobile number!" );
        }
        else if(TextUtils.isEmpty(s10)){
            progressDialog.dismiss();
            pincode.setError( "Pincode!" );
        }
        else {
            firebaseAuth.createUserWithEmailAndPassword(s2,s3).addOnCompleteListener(profile.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        HashMap<String,Object> hashMap=new HashMap<String,Object>();
                        hashMap.put("name",s1);
                        hashMap.put("email",s2);
                        hashMap.put("age",s4);
                        hashMap.put("mobile",s5);
                        hashMap.put("gender",s6);
                        hashMap.put("bloodgroup",s7);
                        hashMap.put("state",s8);
                        hashMap.put("city",s9);
                        hashMap.put("pincode",s10);

                        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                        String s=firebaseUser.getUid();

                        firebaseFirestore.collection("users").document(s).set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent(profile.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                    else{
                        FirebaseAuthException e = (FirebaseAuthException) task.getException();
                        Toast.makeText(profile.this, "error" + " " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[a-z0-9](\\.?[a-z0-9]){5,}@g(oogle)?mail\\.com$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }



}
