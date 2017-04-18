package com.example.madhu.emailapp_original;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * Created by madhu on 15-04-2017.
 * Send Emails to all contacts.
 */

public class NewEmailActivity extends AppCompatActivity {

    RowAdapter adapter;
    ArrayList<Email> emailList;
    ListView listView;
    String subject, body;
    ArrayList<Person> personList;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    String from_addr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_email);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        from_addr = user.getEmail();

        listView = (ListView) findViewById(R.id.listView);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(ComposeEmailsActivity.BUNDLE_KEY);
        personList = (ArrayList<Person>) bundle.getSerializable(ComposeEmailsActivity.LIST_KEY);
        subject = intent.getExtras().getString(ComposeEmailsActivity.SUB_KEY);
        body = intent.getExtras().getString(ComposeEmailsActivity.BODY_KEY);

        Log.d("demo", subject);
        Log.d("demo", body);
        Log.d("demo", from_addr);

        process();
    }

    /**
     * Helper Function.
     * Creates List of Email instances.
     */
    private void process(){
        emailList = new ArrayList<>();

        // Example object
        Email em = new Email("abc.com", processText(body, "first_name_of_contact"), subject);
        emailList.add(em);

        for(Person p: personList) {
            String email = DecodeString(p.getEmail());
            String body_s = processText(body, p.getFirstName());

            Log.d("demo", email + body_s);

            Email email_obj = new Email(email, subject, body_s);
            emailList.add(email_obj);
        }
        callEmailFun();
    }

    /**
     *Creates Mail object for each Contact instance and sends email.
     */

    private void callEmailFun(){

        for(Email em : emailList){
            Mail m = new Mail(from_addr, "");

            m.set_to(em.getTo());
            m.set_from(from_addr);
            m.set_subject(em.getSubject());
            m.setBody(em.getBody());

            try {
                if(m.send()) {
                    displayToast("Email was sent successfully.");
                } else {
                    displayToast("Email was not sent.");
                }
            } catch(Exception e) {
                //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                Log.e("MailApp", "Could not send email", e);
            }
        }
        renderList();
    }

    /**
     * Helper Function
     * Replaces firstName field in the body of the email to actual First Name of the Contact.
     * @param input
     * @param text_val
     * @return Body of email
     */
    public String processText(String input, String text_val){
        String pattern = "{{firstName}}";
        return input.replace(pattern, text_val);
    }

    /**
     * Helper Function
     * @param string
     * @return
     */
    public static String DecodeString(String string) {
        return string.replace(",", ".");
    }

    /**
     * Display a list of Email instances.
     */
    private void renderList(){
        adapter = new RowAdapter(this, R.layout.row_item, emailList, from_addr);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
    }

    private void displayToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
