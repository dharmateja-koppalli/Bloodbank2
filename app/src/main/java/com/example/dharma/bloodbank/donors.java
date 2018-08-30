package com.example.dharma.bloodbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class donors extends AppCompatActivity {

    ListView l1;
    private FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    String uid,uemail;
    List<String> list1=new ArrayList<>();
    List<String> list2=new ArrayList<>();
    List<String> list3=new ArrayList<>();
    List<String> list4=new ArrayList<>();
    DocumentReference documentReference;
    ProgressDialog progressDialog;
    TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donors);
        l1=(ListView)findViewById(R.id.donor_list);
        t1=(TextView)findViewById(R.id.no_donors);

//        CustomAdapter ca=new CustomAdapter();
//        l1.setAdapter(ca);
        progressDialog = ProgressDialog.show(donors.this, "Please Wait ", "Fetching donors", true);


        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        Bundle b=getIntent().getExtras();
        final String s1=b.getString("group");
        String s2=b.getString("state");
        String s3=b.getString("city");
        firebaseFirestore.collection("users").whereEqualTo("bloodgroup",s1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (final DocumentSnapshot doc : task.getResult()) {

                    String s=doc.getString("name");
                    String st1=doc.getString("mobile");
                    String st2=doc.getString("age");
                    String st3=doc.getString("email");
                    list1.add(s);
                    list2.add(st1);
                    list3.add(st2);
                    list4.add(st3);

                }

                firebaseFirestore.collection("admin").whereEqualTo("bloodgroup",s1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (final DocumentSnapshot doc : task.getResult()) {

                            String s=doc.getString("name");
                            Log.d("tag11",s);
                            String st1=doc.getString("mobile");
                            String st2=doc.getString("age");
                            String st3=doc.getString("email");
                            list1.add(s);
                            list2.add(st1);
                            list3.add(st2);
                            list4.add(st3);

                        }
                        Log.d("tag11",list1.toString());
                        if(list1.isEmpty()){
                            progressDialog.dismiss();
                            t1.setText("No Donors!!!");
                            Toast.makeText(donors.this, "No Donors", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            ArrayAdapter adp = new ArrayAdapter(donors.this, android.R.layout.simple_list_item_1, list1);
                            l1.setAdapter(adp);
                            progressDialog.dismiss();
                            l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Intent intent = new Intent(donors.this, details.class);
                                    intent.putExtra("name", list1.get(i));
                                    intent.putExtra("age", list3.get(i));
                                    intent.putExtra("mobile", list2.get(i));
                                    intent.putExtra("email", list4.get(i));
                                    startActivity(intent);
//                        Toast.makeText(donors.this, list2.get(i), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }


                    }
                });




            }
        });




    }


//    class CustomAdapter extends BaseAdapter{
//
//        @Override
//        public int getCount() {
//            return list1.size();
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//            view=getLayoutInflater().inflate(R.layout.first,null);
//
//            TextView tv=(TextView)view.findViewById(R.id.t1);
//            TextView tv1=(TextView)view.findViewById(R.id.t2);
//
//            tv.setText(list1.get(i));
//            tv1.setText(list2.get(i));
//            return view;
//
//        }
//    }
}
