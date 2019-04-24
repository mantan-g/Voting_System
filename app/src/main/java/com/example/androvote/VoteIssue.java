package com.example.androvote;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VoteIssue extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ListView listView;
    ArrayList<String> array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_issue);
        //b=(Button)findViewById(submit);
        array  = new ArrayList<>();
        listView = findViewById(R.id.listView);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Issues");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Listlink user1 = new Listlink();
                    user1.setIssue(ds.getValue(Listlink.class).getIssue());
                counter(user1.getIssue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error In processing", Toast.LENGTH_SHORT).show();
            }

        });


    }

    public void counter(final String issue) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Topic of issue: "+issue);
        System.out.println("------------------" +issue);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              int count=0;
            int count2=0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    resulter user1 = new resulter();
                    user1.setResult(ds.getValue(resulter.class).getResult());
                    System.out.println("---------------------------" + user1.getResult());
                    if (user1.getResult().equals("Yes")) {
                        count++;
                    }
                    else
                        count2++;
                }
                array.add("Topic of issue: "+issue  + "\nYes Votes : "+count+ "    No Votes : "+count2);
                ArrayAdapter<String> adapter = new ArrayAdapter(VoteIssue.this, android.R.layout.simple_list_item_1, array);
                listView.setAdapter(adapter);

                System.out.println(count);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //   Toast.makeText(getApplicationContext(), "Error In processing", Toast.LENGTH_SHORT).show();
            }

        });
    }

}