package com.example.androvote;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddUser extends AppCompatActivity {
    private EditText fname,lname,phno,paswd,cpasswd,eml;
    private Button bttn;
    private FirebaseAuth firebaseAuth;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        fname=(EditText)findViewById(R.id.e1);
        lname=(EditText)findViewById(R.id.e2);
        phno=(EditText)findViewById(R.id.e4);
        paswd=(EditText)findViewById(R.id.e5);
        cpasswd=(EditText)findViewById(R.id.e6);
        eml=(EditText)findViewById(R.id.e3);
        bttn=(Button)findViewById(R.id.button1);
        radioGroup=findViewById(R.id.radioGroup);
        //radioButton=findViewById(R.id.rd1);
        firebaseAuth=FirebaseAuth.getInstance();
        final FirebaseUser user=firebaseAuth.getCurrentUser();
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkFun()){
                    String e_mail=eml.getText().toString().trim();
                    String password= paswd.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(e_mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "SignUp Successful", Toast.LENGTH_SHORT).show();
                                user.sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(getApplicationContext(), "Email Sent Successfully", Toast.LENGTH_SHORT).show();
                                                }
                                                else{
                                                    Toast.makeText(getApplicationContext(), "Email not sent", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                Intent change = new Intent(AddUser.this, Admin_first.class);
                                startActivity(change);
                            }
                           else{
                                Toast.makeText(getApplicationContext(), "SignUp Unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                    }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(), "Data Missing!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public boolean checkFun(){
        String first_name=fname.getText().toString().trim();
        String last_name=lname.getText().toString().trim();
        String phone_no=phno.getText().toString().trim();
        String pass=paswd.getText().toString().trim();
        String email=eml.getText().toString().trim();
        String cpass=cpasswd.getText().toString().trim();
        boolean result=false;
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Data Login");

        ref.child(first_name).child("First Name").setValue(first_name);
        ref.child(first_name).child("Last Name").setValue(last_name);
        ref.child(first_name).child("Phone No").setValue(phone_no);
        ref.child(first_name).child("E-Mail").setValue(email);
        if(first_name.isEmpty() || last_name.isEmpty() || phone_no.isEmpty() || pass.isEmpty() || email.isEmpty() ||cpass.isEmpty() && paswd.equals(cpasswd)){
            result=false;
        }
        else{
            result=true;
        }
        return result;
    }
}
