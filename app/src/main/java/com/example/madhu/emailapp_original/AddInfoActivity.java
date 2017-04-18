package com.example.madhu.emailapp_original;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 *Created by madhu on 15-04-2017.
 *Displays fields to enter valid First Name, Last Name & Email ID
 */

public class AddInfoActivity extends AppCompatActivity {

    private EditText fname, lname, email;
    private String f_name, l_name, email_id;
    private Button btn_upload;

    private static final String TAG = "UploadActivity";
    private static final String TABLE_NAME = "Contacts";

    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);

        fname = (EditText) findViewById(R.id.editText_fname);
        lname = (EditText) findViewById(R.id.editText_lname);
        email = (EditText) findViewById(R.id.editText_email);
        btn_upload = (Button) findViewById(R.id.button_upload);

        mRef = RefObject.getReference();

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                f_name = fname.getText().toString();
                l_name = lname.getText().toString();
                email_id = EncodeString(email.getText().toString());

                if (!f_name.equals("") && !l_name.equals("") && !email_id.equals("")) {
                    if(f_name.contains("@") || f_name.contains(".") || l_name.contains("@") || l_name.contains(".") ||
                            !email_id.contains("@") || !email_id.contains(",")){

                        displayToast("Please fill Email ID in the last field.");
                        fname.setText("");
                        lname.setText("");
                        email.setText("");

                        f_name = l_name = email_id = "";
                    } else {
                        uploadInfo();
                    }
                } else {
                    displayToast("Please fill all the details");
                }
            }
        });

    //    Toast.makeText(this, "In Add Info Activity", Toast.LENGTH_SHORT).show();
    }

    /**
     * Adds information to Contacts table in Firebase
     */
    private void uploadInfo() {
    //    displayToast(fname + " " + l_name + " " + email_id);

        mRef.child(TABLE_NAME).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot ds = dataSnapshot.child(email_id);
                Log.d(TAG, "Key: " + ds);

                //If contact doesn't exist
                if (ds.getValue() == null) {
                    Person person = new Person(f_name, l_name, email_id);
                    mRef.child(TABLE_NAME).child(email_id).setValue(person);

                    displayToast("Contact Added");
                    finish();
                } else {
                    displayToast("Email ID already exists!");
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Failed to read value");
            }
        });
    }

    /**
     * Helper Function
     * @param string
     * @return string with special character removed
     */
    public static String EncodeString(String string) {
        return string.replace(".", ",");
    }

    /**
     * Helper Function
     * @param msg
     * @return displays Toast
     */
    private void displayToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}