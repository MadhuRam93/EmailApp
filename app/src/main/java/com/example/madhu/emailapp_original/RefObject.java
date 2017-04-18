package com.example.madhu.emailapp_original;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by madhu on 15-04-2017.
 * Creates only one Database Reference object - Singleton
 */

public class RefObject {

    private static DatabaseReference mRef = null;

    private RefObject(){ }

    public static DatabaseReference getReference(){
        if(mRef == null){
            mRef = FirebaseDatabase.getInstance().getReference();
        }
        return mRef;
    }
}
