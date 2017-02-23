package com.example.bgolshan.birdappexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dbHelper db = new dbHelper(this);
        String logTag = "Birds";

        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        Log.d(logTag, "Inserting ..");
        db.addBird(new Bird("Bluebird", "Bluebird description"));
        db.addBird(new Bird("Cardinal", "Cardinal description"));
        db.addBird(new Bird("Crow", "Crow description"));
        db.addBird(new Bird("Hummingbird", "Hummingbird description"));

        // Reading all contacts
        Log.d(logTag, "Reading all contacts..");
        List<Bird> birds = db.getAllContacts();

        for (Bird b : birds) {
            String log = "Id: " + b.getId() + " ,Name: " + b.getName() + " ,Description: " + b.getDescription();
            // Writing Contacts to log
            Log.d(logTag, log);
        }




    }
}
