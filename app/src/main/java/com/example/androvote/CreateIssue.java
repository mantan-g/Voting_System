package com.example.androvote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateIssue extends AppCompatActivity {
    private EditText e1,e2,e3;
    private Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_issue);
        e1=(EditText)findViewById(R.id.issue1);
        e2=(EditText)findViewById(R.id.desc);
        e3=(EditText)findViewById(R.id.name);
        b=(Button) findViewById(R.id.proceed);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Issues");
                    String issue = e1.getText().toString();
                    String desc = e2.getText().toString();
                    String names=e3.getText().toString();

                    ref.child(issue).child("Issue").setValue(issue);
                    ref.child(issue).child("Description").setValue(desc);
                    ref.child(issue).child("CreatedBy").setValue(names);

                    DatabaseReference abc = FirebaseDatabase.getInstance().getReference("Display");
                    String issue1 = e1.getText().toString();
                    String desc1 = e2.getText().toString();
                    String names1=e3.getText().toString();

                    abc.child(issue1).child("Issue").setValue(issue1);
                    abc.child(issue1).child("Description").setValue(desc1);
                    abc.child(issue1).child("CreatedBy").setValue(names1);

                    Toast.makeText(getApplicationContext(), "issue created successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreateIssue.this, UserPage1.class));
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Data Missing", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public boolean validate(){
        String issue=e1.getText().toString().trim();
        String descc=e2.getText().toString().trim();
        String name=e3.getText().toString().trim();
        boolean result=true;
        if(issue.isEmpty() || descc.isEmpty()|| name.isEmpty())
        {
            result=false;
        }
        return result;
    }
}
