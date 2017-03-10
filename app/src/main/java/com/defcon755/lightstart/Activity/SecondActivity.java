package com.defcon755.lightstart.Activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.defcon755.lightstart.Detector.ShakeDetector;
import com.defcon755.lightstart.R;
import com.defcon755.lightstart.Services.BackgroundSoundService;

import java.util.ArrayList;

public class SecondActivity extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor lightSensor;
    private Sensor accSensor;
    private TextView text;
    private StringBuilder msg = new StringBuilder(2048);
    private VideoView videoView;
    private LinearLayout llMain;
    private TextView tvMessageOne;
    private TextView tvMessageTwo;
    private TextView tvMessageThree;

    private ImageView imageViewHappy;
    private ImageView imageViewBirthday;
    private ImageView imageViewName;

    private Animation animZoomIn;
    private Animation animRotate;
    private Animation animTogether;
    private Animation animZoomOut;
    private Animation animFadeIn;
    private Animation animBlink;
    private Animation animSequential;

    private ObjectAnimator objectAnimatorHappy;
    private ObjectAnimator objectAnimatorBirthday;
    private ObjectAnimator objectAnimatorName;

    private boolean firstTime = true;
    private Intent svc;

    private ShakeDetector mShakeDetector;

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        BackgroundSoundService.currentUser = "P";
        svc = new Intent(this, BackgroundSoundService.class);

        mSensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);

        lightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        accSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake(int count) {
                handleShakeEvent(count);
            }
        });

        text = (TextView) findViewById(R.id.text_view_message);

        llMain = (LinearLayout) findViewById(R.id.ll_main);
        tvMessageOne = (TextView) findViewById(R.id.tv_message_one);
        tvMessageTwo = (TextView) findViewById(R.id.tv_message_two);
        tvMessageThree = (TextView) findViewById(R.id.tv_message_three);

        imageViewHappy = (ImageView) findViewById(R.id.image_view_happy);
        imageViewBirthday = (ImageView) findViewById(R.id.image_view_birthday);
        imageViewName = (ImageView) findViewById(R.id.image_view_name);

        objectAnimatorHappy = ObjectAnimator.ofFloat(imageViewHappy, "Y", 300);
        objectAnimatorBirthday = ObjectAnimator.ofFloat(imageViewBirthday, "X", 195);//155
        objectAnimatorName = ObjectAnimator.ofFloat(imageViewName, "X", 200);


    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(this, lightSensor);
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
        stopService(svc);
    }

    @Override
    protected void onResume() {
        mSensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mShakeDetector, accSensor, SensorManager.SENSOR_DELAY_UI);
        super.onResume();
        startService(svc);
    }

    private void handleShakeEvent(int count) {
        if (count > 0) {
            Intent intentMain = new Intent(SecondActivity.this, MainActivity.class);
            startActivity(intentMain);
            finish();
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.values[0] > 50) {
            llMain.setBackgroundResource(R.drawable.teddy_p);

            if (isMyServiceRunning(BackgroundSoundService.class)) {
                stopService(svc);
            }

            text.setText("Please switch off the Light");
            text.setTextColor(Color.RED);

            tvMessageOne.setText("");
            tvMessageTwo.setText("");
            tvMessageThree.setText("");

            imageViewHappy.setImageResource(0);
            imageViewBirthday.setImageResource(0);
            imageViewName.setImageResource(0);

            return;
        } else {
            if (!isMyServiceRunning(BackgroundSoundService.class)) {
                startService(svc);
            }
            llMain.setBackgroundResource(0);
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.background_tiles_second);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(bmp);
            bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            llMain.setBackgroundDrawable(bitmapDrawable);

            imageViewHappy.setImageResource(R.drawable.happy_new_2);
            imageViewBirthday.setImageResource(R.drawable.bday_new_2);
            imageViewName.setImageResource(R.drawable.phani_new_2);

            text.setText("");
            playAnimation();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void playAnimation() {
        animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        animRotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        animTogether = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.together);
        animZoomOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);
        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        animBlink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        animSequential = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sequential);

        tvMessageOne.setText("2");
        tvMessageOne.setTextColor(Color.RED);
        tvMessageTwo.setText("2");
        tvMessageTwo.setTextColor(Color.BLUE);
        tvMessageThree.setText("2");
        tvMessageThree.setTextColor(Color.GREEN);
        if (firstTime) {
            tvMessageOne.startAnimation(animSequential);
            tvMessageTwo.startAnimation(animSequential);
            tvMessageThree.startAnimation(animSequential);

            objectAnimatorHappy.setDuration(4000);
            objectAnimatorHappy.start();

            objectAnimatorBirthday.setDuration(4000);
            objectAnimatorBirthday.setStartDelay(4000);
            objectAnimatorBirthday.start();

            objectAnimatorName.setDuration(4000);
            objectAnimatorName.setStartDelay(8000);
            objectAnimatorName.start();

            firstTime = false;
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {

        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(SecondActivity.this, "Please ckick back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }
}
