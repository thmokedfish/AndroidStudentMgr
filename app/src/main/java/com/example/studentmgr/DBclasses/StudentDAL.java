package com.example.studentmgr.DBclasses;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.studentmgr.StudentItem;

public class StudentDAL {
    private SQLiteDatabase db;
    final String DBprint="DBprint";
    public StudentDAL(Context context)
    {
        StudentDBHelper helper=new StudentDBHelper(context);
        db=helper.getWritableDatabase();
    }

    public void PrintDB()
    {
        Cursor cursor=db.rawQuery("select * from student",null);
        while(cursor.moveToNext())
        {
            int id=cursor.getInt(cursor.getColumnIndex("id"));
            String name=cursor.getString(cursor.getColumnIndex("name"));
            String academy=cursor.getString(cursor.getColumnIndex("academy"));
            String major=cursor.getString(cursor.getColumnIndex("major"));
            // StudentItem item=new StudentItem(name,academy,major);
            String output=id+name+" "+academy+" "+major;
            Log.d(DBprint,output);
        }
    }
    public  void AddToDB(StudentItem item) {
        String sql = "select count(*) from student";
        Cursor cursor1 = db.rawQuery(sql, null);
        cursor1.moveToFirst();
        long count = cursor1.getLong(0);
        cursor1.close();
        String name = item.name;
        if (name.isEmpty()) {
            name = "null";
        } else {
            name = "'" + name + "'";
        }
        String s = "insert into student values(" +
                (count+1) + "," + name + ",'" + item.academy + "','" + item.major + "'"
                + ")";
        Log.d(DBprint, s);
        db.execSQL(s);
        Log.d(DBprint, "ok");

    }



}
