package com.example.sqlite_tutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.sqlite_tutorial.databinding.ActivityMainBinding;
import com.example.sqlite_tutorial.sqlite_db.DatabaseHelper;
import com.example.sqlite_tutorial.model.UserModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnAddUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // ToDo: get data: name, age, active -> save to sqlite
                try {
                    DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                    UserModel userModel = new UserModel(-1, binding.evName.getText().toString(), Integer.parseInt(binding.evAge.getText().toString()), binding.swActive.isChecked());
                    if(databaseHelper.addFriend(userModel)) Toast.makeText(MainActivity.this, "Friend saved to db",Toast.LENGTH_LONG).show();
                    else Toast.makeText(MainActivity.this, "Error at saving to db",Toast.LENGTH_LONG).show();
                    //Toast.makeText(MainActivity.this, userModel.toString(),Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Error creating friend  ",Toast.LENGTH_LONG).show();
                }
            }
        });
        binding.btnViewAll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // ToDo: query sqlite to get all users -> update view
                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                List<UserModel> friends = databaseHelper.getAll();
                Toast.makeText(MainActivity.this, friends.toString(),Toast.LENGTH_LONG).show();
                // ToDo: create adapter & set data for the rw from friends list
            }
        });
    }
}