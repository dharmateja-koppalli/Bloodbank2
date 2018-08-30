package com.example.dharma.bloodbank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class search extends AppCompatActivity {

    Spinner s1,s2,s3;
    String st1,st2,st3,st4;
    Button b1;
    String b_group[]={"A+","A-","B+","B-","O+","O-","AB+","AB-"};
    String states[]={"Andhra pradesh","Tamil nadu","Karnataka","Kerala"};
    String cities[]={"Hyderabad","Chennai","Banglore","Kochi"};
    private FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    String uid,uemail;
    TextView t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        b1=(Button)findViewById(R.id.search_search);


        t1=(TextView)findViewById(R.id.search_b_group);
        Bundle b=getIntent().getExtras();
        st4=b.getString("group");
        t1.setText(st4);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                st1=s1.getSelectedItem().toString();
//                st2=s2.getSelectedItem().toString();
//                st3=s3.getSelectedItem().toString();
                Intent intent=new Intent(search.this,donors.class);
                intent.putExtra("group",st4);
//                intent.putExtra("city",st2);
//                intent.putExtra("state",st3);
                startActivity(intent);

            }
        });

    }
}
