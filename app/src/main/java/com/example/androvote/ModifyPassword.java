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
import com.google.firebase.auth.FirebaseAuth;

public class ModifyPassword extends AppCompatActivity {
    private EditText edd;
    private Button btt;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        edd=(EditText)findViewById(R.id.email);
        btt=(Button) findViewById(R.id.reset);
        firebaseAuth=FirebaseAuth.getInstance();
        btt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EMail=edd.getText().toString().trim();
                if(EMail.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please Enter your E-Mail above", Toast.LENGTH_SHORT).show();
                }
                else{
                    firebaseAuth.sendPasswordResetEmail(EMail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "E-Mail Sent Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ModifyPassword.this,MainActivity.class));
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Error in Sending the E-Mail;", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }
}
