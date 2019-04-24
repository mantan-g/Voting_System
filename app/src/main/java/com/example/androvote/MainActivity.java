package com.example.androvote;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText edit1,edit2;
    private Button b1,b2;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit1=(EditText)findViewById(R.id.ed1);
        edit2=(EditText)findViewById(R.id.ed2);
        b1=(Button)findViewById(R.id.signin);
        b2=(Button)findViewById(R.id.modify);
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();
     /*   if(user!=null){
            finish();
            startActivity(new Intent(MainActivity.this,UserPage1.class));

        }*/

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check()) {
                    validate(edit1.getText().toString(),edit2.getText().toString());
                }
                else{
                    Toast.makeText(getApplicationContext(), "Data Missing!!", Toast.LENGTH_SHORT).show();
                }


            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent modify = new Intent(MainActivity.this, ModifyPassword.class);
                startActivity(modify);
            }
        });


        }
    public boolean check(){
        boolean result=false;
        String email=edit1.getText().toString();
        String passwd=edit2.getText().toString();
        if(email.isEmpty() || passwd.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }
    public void validate (String id, String passwd){
        if (id.equals("1711981098") && passwd.equals("gaurav")) {
            Intent i = new Intent(MainActivity.this, Admin_first.class);
            startActivity(i);
        }
        else
        {
            firebaseAuth.signInWithEmailAndPassword(id,passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Sign-In Success!!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,UserPage1.class));
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Wrong Credential!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}
