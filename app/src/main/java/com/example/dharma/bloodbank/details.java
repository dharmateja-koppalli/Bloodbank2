package com.example.dharma.bloodbank;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class details extends AppCompatActivity {

    TextView t1,t2,t3,t4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        t1=(TextView)findViewById(R.id.details_name);
        t2=(TextView)findViewById(R.id.details_age);
        t3=(TextView)findViewById(R.id.details_mobile);
        t4=(TextView)findViewById(R.id.details_email);
        Bundle b=getIntent().getExtras();
        t1.setText(b.getString("name"));
        t2.setText(b.getString("age"));
        t3.setText(b.getString("mobile"));
        t4.setText(b.getString("email"));
        registerForContextMenu(t3);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);
        menu.setHeaderTitle("Action");
        menu.add(0,v.getId(),0,"call");
        menu.add(0,v.getId(),1,"sms");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getTitle()=="call"){
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+t3.getText().toString()+""));
            startActivity(callIntent);
        }
        else {
            String mbn=t3.getText().toString();
            String msg="Required blood!!!";

            Intent smsIntent = new Intent(Intent.ACTION_SEND, Uri.parse("smsto:" + mbn));
            smsIntent.putExtra("sms_body", msg);
            smsIntent.setType("text/");
            startActivity(smsIntent);

        }
        return true;
    }

    public void send(View v){

        String[] TO = {t4.getText().toString()};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Blood Required");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "It's emergency required blood!!!!!!!!");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(details.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
