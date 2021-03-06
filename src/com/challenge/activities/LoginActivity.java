package com.challenge.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.challenge.async.AddUser;
import com.challenge.GlobalDataStore;
import com.challenge.R;

public class LoginActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void login(View view) {
        EditText textView = (EditText)findViewById(R.id.LoginTextUserName);
        GlobalDataStore.currUser = textView.getText().toString();
        AddUser addUser = new AddUser();
        addUser.execute(GlobalDataStore.currUser);
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }
}
