package com.example.studentmgr;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class QueryWeekdayService extends Service {
    public QueryWeekdayService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        WeekdayBind bind=new WeekdayBind();
        return bind.asBinder();
    }
}