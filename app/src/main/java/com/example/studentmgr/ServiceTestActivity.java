package com.example.studentmgr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;
public class ServiceTestActivity extends AppCompatActivity {

    final  String serviceDebug="serviceDebug";
    TextView year;
    TextView month;
    TextView day;
    TextView result;
    ServiceConnection conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InitViews();
    }
    private  void InitConn()
    {

        conn=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                QueryWeekdaylInterface binder=QueryWeekdaylInterface.Stub.asInterface(service);
                try {
                    int result=binder.QueryWeekday(Integer.parseInt(year.getText().toString()),
                            Integer.parseInt(month.getText().toString()),Integer.parseInt(day.getText().toString()));
                    SetResult(result);
                }
                catch (Exception e)
                {
                    Log.d("servicedebug",e.getLocalizedMessage());
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
    }

    private void InitViews()
    {
        setContentView(R.layout.activity_service_test);

        year=findViewById(R.id.service_test_text_year);
        month=findViewById(R.id.service_test_text_month);
        day=findViewById(R.id.service_tset_text_day);
        result=findViewById(R.id.service_test_text_result);
        Button bindButton=findViewById(R.id.service_test_button_bind);
        bindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bind();
            }
        });
    }

    void SetResult(int result)
    {

        try {
            String s="";
            switch (result)
            {
                case 1:
                    s="星期天"; break;
                case  2 :
                    s="星期一"; break;
                case 3:
                    s="星期二"; break;
                case 4:
                    s="星期三"; break;
                case 5:
                    s="星期四"; break;
                case 6:
                    s="星期五"; break;
                case 7:
                    s="星期六"; break;
            }
            this.result.setText(s);
        }
        catch (Exception e)
        {
            Log.d("servicedebug",e.getLocalizedMessage());
        }
    }


    private void Bind()
    {
        Log.d("servicedebug","bind");
        InitConn();
        bindService(new Intent(this,QueryWeekdayService.class),conn,BIND_AUTO_CREATE);
    }

}
