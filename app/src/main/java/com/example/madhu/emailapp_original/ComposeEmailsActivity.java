package com.example.madhu.emailapp_original;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by madhu on 15-04-2017.
 * Provides two fields: Subject & Body, of the Email
 * Retrieves all contacts from Firebase table
 */

public class ComposeEmailsActivity extends BaseActivity implements IData {

    private ProgressDialog dialog;
    private EditText et_subject, et_body;
    private String subject, body;
    private Button send;

    private DatabaseReference mRef;

    public final static String LIST_KEY = "personsList";
    public final static String BUNDLE_KEY = "bundle";
    public final static String SUB_KEY = "subject_key";
    public final static String BODY_KEY = "body_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_emails2);

        mRef = RefObject.getReference();

        et_subject = (EditText) findViewById(R.id.editText_subject);
        et_body = (EditText) findViewById(R.id.editText_body);
        send = (Button) findViewById(R.id.button_sendEmail);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subject = et_subject.getText().toString();
                body = et_body.getText().toString();

                if(!subject.equals("") && subject.length()>0 && !body.equals("") && body.length()>0){
                    importList();
                } else{
                    displayToast("Please enter both Subject and Body fields");
                }
            }
        });
    }

    /**
     * Calls the AsyncTask to retrieve contacts from Firebase table Contacts
     */
    private void importList(){
        new ImportAsyncTask(this, mRef).execute();
    }

    /**
     * Handles the list of contacts retrieved from database
     * Calls NewEmailActivity to send emails
     * @param personList
     */
    public void processImports(ArrayList<Person> personList){

        if(personList != null){
            displayToast("Compose :: Contacts Retrieved");

            Intent intent = new Intent(ComposeEmailsActivity.this, NewEmailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(LIST_KEY, (Serializable)personList);
            intent.putExtra(BUNDLE_KEY, bundle);
            intent.putExtra(SUB_KEY, subject);
            intent.putExtra(BODY_KEY, body);
            startActivity(intent);

        } else {
            displayToast("There are no contacts to import.");
            finish();
        }
    }

    @Override
    public void showProgress() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading contacts. Please wait!");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void hideProgress() {
        dialog.dismiss();
    }

    private void displayToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
