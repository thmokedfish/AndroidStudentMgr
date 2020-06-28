package com.example.studentmgr;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Presentation;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
private String name="1";
private String password="1";
private AlertDialog.Builder builder;
private AlertDialog dialog;
private EditText nameText;
private  EditText passwordText;
public boolean exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InitViews();
        DefaultLogin();
        ShowProgress();

        StartService();

    }
    private  void StartService()
    {
        startService(new Intent(this,QueryWeekdayService.class));
        startService(new Intent(this,ConnectionService.class));
    }


    private void InitViews()
    {
        setContentView(R.layout.activity_login);

        nameText=findViewById(R.id.login_name);
        passwordText=findViewById(R.id.login_password);
        Button loginButton=(Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClick();
                //ShowProgress();
                // Login();
            }
        });



    }


    private void DefaultLogin()
    {
        SharedPreferences settings= PreferenceManager.getDefaultSharedPreferences(this);
        nameText.setText(settings.getString(SettingsActivity.userkey,"1"));
        passwordText.setText(settings.getString(SettingsActivity.passwordkey,"1"));
    }


    private void ShowProgress()
    {
        int llPadding = 30;
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setPadding(llPadding, llPadding, llPadding, llPadding);
        ll.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams llParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        llParam.gravity = Gravity.CENTER;
        ll.setLayoutParams(llParam);


       ProgressBar progressBar=new ProgressBar(this,null,android.R.attr.progressBarStyleLarge);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        progressBar.setPadding(0, 0, llPadding, 0);
        progressBar.setLayoutParams(llParam);

        llParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        llParam.gravity = Gravity.CENTER;
        TextView tvText = new TextView(this);
        tvText.setText("Loading ...");
        tvText.setTextColor(Color.parseColor("#000000"));
        tvText.setTextSize(20);
        tvText.setLayoutParams(llParam);

        ll.addView(progressBar);
        ll.addView(tvText);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setView(ll);

        this.dialog=builder.create();
        //dialog.show();


        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(dialog.getWindow().getAttributes());
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(layoutParams);
        }
    }

    private void OnClick()
    {
       // ShowProgress();
        this.dialog.show();
        new Thread(){
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Login();
                        }
                    });
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }


    private void Login()
    {
        this.dialog.hide();
        if(nameText.getText().toString().equals(this.name))
        {
            if(passwordText.getText().toString().equals(this.password))
            {
                Intent intent=new Intent(this,Ads.class);
                startActivity(intent);
                return;
            }
        }
        builder=new AlertDialog.Builder(this).setIcon(R.mipmap.ic_launcher)
                .setMessage("用户名或密码错误").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
       AlertDialog dialog= builder.create();
       dialog.show();
    }

}
