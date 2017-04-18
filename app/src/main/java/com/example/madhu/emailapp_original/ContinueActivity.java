package com.example.madhu.emailapp_original;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by madhu on 15-04-2017.
 * Intermediate Activity that has two options: Add Contact Information and Send Emails
 */

public class ContinueActivity extends AppCompatActivity {

    private Button btn_add, btn_send;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue);

        mRef = RefObject.getReference();

        btn_add = (Button) findViewById(R.id.btn_add_info);
        btn_send = (Button) findViewById(R.id.btn_send_email);

        //Button to go to Add Information Activity
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    Toast.makeText(ContinueActivity.this, "In Continue Activity", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ContinueActivity.this, AddInfoActivity.class);
                startActivity(intent);
            }
        });

        //Button to go to Send Emails Activity
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContinueActivity.this, ComposeEmailsActivity.class);
                startActivity(intent);
            }
        });
    }
}
