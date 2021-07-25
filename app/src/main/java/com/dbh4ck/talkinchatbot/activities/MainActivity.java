package com.dbh4ck.talkinchatbot.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.dbh4ck.talkinchatbot.R;
import com.dbh4ck.talkinchatbot.service.OperationController;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userName, userPassword, roomName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = findViewById(R.id.bot_username);
        userPassword = findViewById(R.id.bot_password);
        roomName = findViewById(R.id.room_name);
        Button loginBtn = findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String user = userName.getText().toString().trim();
        String pwd = userPassword.getText().toString().trim();
        String room = roomName.getText().toString().trim();

        if(user.isEmpty() || pwd.isEmpty() || room.isEmpty()){
            Toast.makeText(this, "Please enter all fields!", Toast.LENGTH_SHORT).show();
            return;
        }
        OperationController.getController().attemptLogin(user, pwd, room);

    }
}