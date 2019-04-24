package com.example.androvote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Vote extends AppCompatActivity {
    private TextView t;
    private Button b;
    EditText e;
private RadioGroup rb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       final String name[]= getIntent().getStringExtra("name").split("\n");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        t=(TextView)findViewById(R.id.title);
        t.setText(name[0]);
        b=(Button)findViewById(R.id.submit);
        e=(EditText)findViewById(R.id.NAME);
        rb=findViewById(R.id.radioGroup3);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Uname=e.getText().toString().trim();
                int selectid=rb.getCheckedRadioButtonId();
               RadioButton radioSexButton = (RadioButton) findViewById(selectid);
                Toast.makeText(getApplicationContext(), radioSexButton.getText(), Toast.LENGTH_SHORT).show();
               if(Uname.isEmpty())
               {
                   Toast.makeText(getApplicationContext(), "Data Missing!!", Toast.LENGTH_SHORT).show();
               }
               else {
                   FirebaseDatabase database = FirebaseDatabase.getInstance();
                   DatabaseReference myRef = database.getReference(name[0]);

                   myRef.child(Uname).child("Result").setValue(radioSexButton.getText());
               }
                //myRef.child(Uname).child("Issue-").setValue(name[0]);
                //myRef.child(Uname).child("Result").setValue(radioSexButton.getText());
                // firebase
            }
        });
    }
    //firebase
}
