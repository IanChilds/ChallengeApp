package com.challenge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }
}
