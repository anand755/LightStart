package com.defcon755.lightstart.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.defcon755.lightstart.R;

public class LoginActivity extends AppCompatActivity {

    private EditText etCode;
    private ImageView ivLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etCode = (EditText) findViewById(R.id.et_code);
        ivLogin = (ImageView) findViewById(R.id.iv_login);

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }


        ivLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                );

                if (etCode.getText().toString().equals("2226")) {

                    //check the device is tablet or mobile

                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Incorrect Code", Toast.LENGTH_SHORT).show();
                }
            }
        });
        decideTimeAhdDisplay();
    }

    private void decideTimeAhdDisplay() {

        Time timeNow = new Time();
        timeNow.setToNow();
        String message = "";
        if (timeNow.hour >= 5 && timeNow.hour < 12) {
            message = "Good Morning !";
        } else if (timeNow.hour >= 12 && timeNow.hour < 16) {
            message = "Good After Noon !";
        } else if (timeNow.hour >= 16 && timeNow.hour < 18) {
            message = "Good Evening !";
        } else if (timeNow.hour >= 18 && timeNow.hour < 22) {
            message = "Nice to see you once again !";
        } else if (timeNow.hour >= 22 && timeNow.hour < 24) {
            message = "Hey U didn't sleep yet !";
        } else if (timeNow.hour >= 0 && timeNow.hour < 5) {
            message = "U R Awsm and u sud sleep now !";
        }
        startAnimation(message);
    }

    private void startAnimation(String message) {

        LinearLayout llWelcomeLayout = (LinearLayout) findViewById(R.id.ll_welcome_layout);
        TextView tvWelcomeMessage = (TextView) findViewById(R.id.tv_welcome_message);

        tvWelcomeMessage.setText(message);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        llWelcomeLayout.startAnimation(myAnim);
    }

}


