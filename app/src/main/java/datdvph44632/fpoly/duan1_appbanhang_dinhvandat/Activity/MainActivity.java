package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.ActivityKH.Main_KH_Navi_Activity;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.ActivityNV_Admin.Main_Admin_Navi_Activity;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.ActivityNV_Admin.Main_NV_Navi_Activity;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.HelloWorld.HelloActivity;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.R;

public class MainActivity extends AppCompatActivity {

    String checkFT, isLogin, TAG = "MainActivity_____";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout linearLayout = findViewById(R.id.hello);

        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.alpha);
        linearLayout.startAnimation(animation);

        checkFirstTime();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (checkFT.equals("true")){
                    if (isLogin.equals("true")){
                        SharedPreferences pre = getSharedPreferences("Who_Login", MODE_PRIVATE);
                        if (pre != null) {
                            String role = pre.getString("role", "");
                            if (role.equals("ad")){
                                startActivity(new Intent(MainActivity.this, Main_Admin_Navi_Activity.class));
                            }
                            if (role.equals("nv")){
                                startActivity(new Intent(MainActivity.this, Main_NV_Navi_Activity.class));
                            }
                            if (role.equals("kh")){
                                startActivity(new Intent(MainActivity.this, Main_KH_Navi_Activity.class));
                            }
                        }
                    } else {
                        startActivity(new Intent(MainActivity.this, PickRole_Activity.class));
                    }
                } else {
                    startActivity(new Intent(MainActivity.this, HelloActivity.class));
                }
                finish();
            }
        }, 3000);
    }

    private void checkFirstTime() {
        SharedPreferences pref = getSharedPreferences("Check_FirstTime", MODE_PRIVATE);
        if (pref == null) {
            checkFT = "false";
        } else {
            checkFT = pref.getString("check", "");
        }

        SharedPreferences pre = getSharedPreferences("Who_Login", MODE_PRIVATE);
        if (pre == null) {
            Log.d(TAG, "checkFirstTime: pre null");
            isLogin = "false";
        } else {
            Log.d(TAG, "checkFirstTime: else");
            isLogin = pre.getString("isLogin", "");
            Log.d(TAG, "checkFirstTime: isLogin " + isLogin);
        }
    }
}