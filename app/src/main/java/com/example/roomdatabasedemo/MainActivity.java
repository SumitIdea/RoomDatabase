package com.example.roomdatabasedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Insert;
import androidx.room.Room;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    EditText et_id,et_name,et_email;
    Button btn_save,btn_read,btn_update,btn_delete;
    static MyDatabase myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_id=findViewById(R.id.userIdEdt);
        et_name=findViewById(R.id.userNameEdt);
        et_email=findViewById(R.id.userEmailEdt);
        btn_save=findViewById(R.id.btn_save);
        btn_read=findViewById(R.id.btn_read);
        btn_update=findViewById(R.id.btn_update);
        btn_delete=findViewById(R.id.btn_delete);


        myDatabase= Room.databaseBuilder(getApplicationContext(),MyDatabase.class,"userdb")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveData();
            }
        });


        btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getData();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UpdateData();;
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DeleteData();
            }
        });

    }



    public void saveData()
    {


        if(TextUtils.isEmpty(et_id.getText().toString()))
        {
            et_id.setError("Please Enter ID");
        }
        else if(TextUtils.isEmpty(et_name.getText().toString()))
        {
            et_name.setError("Please Enter Name");
        }
        else if(TextUtils.isEmpty(et_email.getText().toString()))
        {
            et_email.setError("Please Enter Email");
        }
        else {
            int id= Integer.parseInt(et_id.getText().toString());
            String username=et_name.getText().toString();
            String email=et_email.getText().toString();
            User user = new User();
            user.setId(id);
            user.setName(username);
            user.setEmail(email);

            myDatabase.myDao().addUser(user);

            Toast.makeText(this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();

            et_id.setText("");
            et_name.setText("");
            et_email.setText("");
        }
    }

    public void getData()
    {
        List<User> users=myDatabase.myDao().getUser();

        String info="";
        for (User usr:users)
        {
            int id=usr.getId();
            String name=usr.getName();
            String email=usr.getEmail();

            info=info+"\n\n"+"User Id is "+id+"\n"+"User Name is "+name+"\n"+"User Email is "+email+"\n\n";

            Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
        }
    }

    public void UpdateData() {
        if (TextUtils.isEmpty(et_id.getText().toString())) {
            et_id.setError("Please Enter ID");
        } else if (TextUtils.isEmpty(et_name.getText().toString())) {
            et_name.setError("Please Enter Name");
        } else if (TextUtils.isEmpty(et_email.getText().toString())) {
            et_email.setError("Please Enter Email");
        } else {
            int id = Integer.parseInt(et_id.getText().toString());
            String name = et_name.getText().toString();
            String email = et_email.getText().toString();

            User user = new User();
            user.setId(id);
            user.setName(name);
            user.setEmail(email);

            myDatabase.myDao().updateUser(user);

            Toast.makeText(this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public  void DeleteData()
    {
        int id=Integer.parseInt(et_id.getText().toString());
        User user = new User();
        user.setId(id);

        myDatabase.myDao().deleteUser(user);

        Toast.makeText(this, "Data Deleted Successfully", Toast.LENGTH_SHORT).show();

        et_id.setText("");
    }
}