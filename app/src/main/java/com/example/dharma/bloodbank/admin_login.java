package com.example.dharma.bloodbank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class admin_login extends AppCompatActivity {

    Button b1;
    EditText e1,e2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        e1=(EditText)findViewById(R.id.admin_username);
        e2=(EditText)findViewById(R.id.admin_password);
        b1=(Button)findViewById(R.id.admin_login);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1=e1.getText().toString();
                String s2=e2.getText().toString();

                if(s1.equals("koppalli.dharmateja@gmail.com")&&s2.equals("9392802044")){
                    Intent i=new Intent(admin_login.this,admin_home.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(admin_login.this, "Wrong credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
