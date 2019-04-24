package com.example.androvote;

import android.annotation.SuppressLint;
        import android.content.Intent;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
import android.widget.AdapterView;
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
public class UserPage1 extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private Button b,bttnn;
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page1);
        mListView = findViewById(R.id.listview);
        firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Issues");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<String> array = new ArrayList<>();
                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    //.add
                    Listlink user1 = new Listlink();
                    user1.setCreatedBy(ds.getValue(Listlink.class).getCreatedBy());
                    user1.setIssue(ds.getValue(Listlink.class).getIssue());
                    user1.setDescription(ds.getValue(Listlink.class).getDescription());
                    array.add("Topic of issue: "+user1.getIssue()+"\n"+"\n"+"Description: "+user1.getDescription()+"\n"+"\n"+"Created By : "+ user1.getCreatedBy());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter(UserPage1.this,android.R.layout.simple_list_item_1,array);
                mListView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error In processing", Toast.LENGTH_SHORT).show();
            }

        });
        mListView.setClickable(true);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = mListView.getItemAtPosition(position);
                String str = (String)o; //As you are using Default String Adapter
                Toast.makeText(getBaseContext(),str,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(UserPage1.this,Vote.class);
                intent.putExtra("name", str);
                startActivity(intent);
            }
            });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.logout:{
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(UserPage1.this, MainActivity.class));
                break;
            }
            case R.id.create: {
                startActivity(new Intent(UserPage1.this,CreateIssue.class));
                break;
            }
            case R.id.vote:{
                startActivity(new Intent(UserPage1.this,VoteIssue.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
