package com.example.madhu.emailapp_original;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by madhu on 15-04-2017.
 * Child thread to retrieve all contacts from Firebase
 */

public class ImportAsyncTask extends AsyncTask<Void, Void, ArrayList<Person>> {

    IData activity;
    private DatabaseReference mRef;
    private ArrayList<Person> personList = new ArrayList<>();;

    private static final String TAG = "ImportActivity";
    private static final String TABLE_NAME = "Contacts";


    public ImportAsyncTask(IData activity, DatabaseReference mRef) {
        this.activity = activity;
        this.mRef = mRef;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.showProgress();
    }

    @Override
    protected ArrayList<Person> doInBackground(Void... strings) {

        mRef.child(TABLE_NAME).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    String email_id = ds.child("email").getValue().toString();
                    String f_name = ds.child("firstName").getValue().toString();
                    String l_name = ds.child("lastName").getValue().toString();

                    Person person = new Person(f_name, l_name, email_id);
                    personList.add(person);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Failed to read value");
            }
        });
        return personList;
    }

    @Override
    protected void onPostExecute(ArrayList<Person> personList) {
        super.onPostExecute(personList);
        activity.hideProgress();
        activity.processImports(personList);
    }
}

interface IData{
    void showProgress();
    void hideProgress();
    void processImports(ArrayList<Person> personList);
}