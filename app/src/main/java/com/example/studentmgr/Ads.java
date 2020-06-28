package com.example.studentmgr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

public class Ads extends AppCompatActivity {

    Boolean inActivity=true;
    Button adsButton;

    public int time=5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);
        adsButton=findViewById(R.id.ads_button);
        adsButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inActivity=false;
                        SwitchActivity();

                    }
                }
        );
        WaitAds();
    }

    void WaitAds()
    {
        new Thread(){
            @Override
            public void run() {
                try
                {
                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    for(int i=time;i>0;i--)
                    {
                        final int x=i;
                        mainHandler.post(new Runnable() {

                            @Override
                            public void run() {
                                adsButton.setText(Integer.toString(x));
                            }
                        });
                        Thread.sleep(1000);
                        //adsButton.setText(Integer.toString(i));  放在主线程
                    }
                    if(inActivity) {
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                SwitchActivity();
                            }
                        });
                    }
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    void WaitSeconds(int time)
    {

    }
    void SwitchActivity()
    {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}
