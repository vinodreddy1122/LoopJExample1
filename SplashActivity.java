package lab1.adsalesapp;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;


import java.util.Timer;
import java.util.TimerTask;

import lab1.adsalesapp.Utils.PrefManager;

public class SplashActivity extends AppCompatActivity {

    // VIEWS
    private ImageView mImageViewLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        mImageViewLogo = (ImageView) findViewById(R.id.imgLogo);
        mImageViewLogo.setScaleX(0);
        mImageViewLogo.setScaleY(0);


        initSplashScreen();
    }

    // METHODS
    private void initSplashScreen() {
        mImageViewLogo.animate().scaleX(1).scaleY(1)
                .setDuration(500).setInterpolator(new OvershootInterpolator())
                .setStartDelay(200).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                       /* if (PrefManager.getUserId(SplashActivity.this, "emp_id").isEmpty()) {
                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }*/
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                    }
                };

                new Timer().schedule(timerTask, 1000);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        }).start();
    }
}