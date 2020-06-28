package com.example.studentmgr;

import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WeekdayBind implements QueryWeekdaylInterface {
@Override
public int QueryWeekday(int year,int month,int day)
{
    Calendar cal=Calendar.getInstance();
    Date date=new Date(year,month,day);
    cal.setTime(date);
    int result=cal.get(Calendar.DAY_OF_WEEK);
    return result;
}

    @Override
    public IBinder asBinder() {
            return new QueryWeekdaylInterface.Stub(){
                @Override
                public int QueryWeekday(int year, int month, int day) throws RemoteException {
                    int result=0;
                    try {
                        String y=Integer.toString(year);
                        String m=Integer.toString(month);
                        if(m.length()==1){m="0"+m;}
                        String d=Integer.toString(day);
                        if(d.length()==1){d="0"+d;}
                        Calendar cal=Calendar.getInstance();
                        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                        Date date = format.parse(y+m+d);
                        Log.d("servicedebug", format.format(date));

                        cal.setTime(date);
                        result = cal.get(Calendar.DAY_OF_WEEK);
                    }
                    catch (Exception e){
                        Log.d("servicedebug", e.getLocalizedMessage());
                    }
                    return result;
                    }
            };
    }
}
