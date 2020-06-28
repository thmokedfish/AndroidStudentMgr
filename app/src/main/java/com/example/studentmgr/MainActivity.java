package com.example.studentmgr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.studentmgr.DBclasses.StudentDAL;
import com.example.studentmgr.DBclasses.StudentDBHelper;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    final String TAG="lifeTag";
    private GestureDetector mDetector;
    private ListView listView;
    private ArrayList<StudentItem> studentItems;
    private int[] imageID = {R.drawable.image_emoticon16, R.drawable.image_emoticon23};
    public static final String PREF_KEY = "STUDENT_ITEMS";
   // public PopupMenu popupMenu;
    public StudentDAL studentDAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG,"on create");

        studentDAL=new StudentDAL(this);

        InitData();

        InitButton();

        InitListView();

        InitDetctor();
    }

    private  void InitButton()
    {
       Button button=findViewById(R.id.button2);
       button.setOnClickListener(
               new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       SwitchActivity(null,ActivityStudent.class);
                   }
               }
       );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menulayout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_add:
                SwitchActivity(null,ActivityStudent.class);
                break;
            case R.id.menu_phone:
                SwitchActivity(null,ActivityPhonePlace.class);
                break;
            case R.id.menu_settings:
                SwitchActivity(null,SettingsActivity.class);
            case R.id.menu_service:
                SwitchActivity(null,ServiceTestActivity.class);
            default:
                    break;
        }
        return super.onOptionsItemSelected(item);
    }




    private void InitMenu(View view,int pos)
    {
        PopupMenu popupMenu = new PopupMenu(this,view);
        Menu menu = popupMenu.getMenu();

        menu.add(Menu.NONE, Menu.FIRST + 0, 0, "编辑");
        menu.add(Menu.NONE, Menu.FIRST + 1, 1, "删除");


        // 监听事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case Menu.FIRST + 0:
                        SwitchActivity(null,ActivityStudent.class);
                        break;
                    case Menu.FIRST + 1:
                        Delete(null);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e)
    {
        super.dispatchTouchEvent(e);
        return mDetector.onTouchEvent(e);
    }

    public void InitDetctor()
    {
        mDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2,
                                   float velocityX, float velocityY) {

                // e1: 开始的点的数据
                // e2: 结束的点的数据
                // velocityX:
                // velocityY:

                float x1 = e1.getX();
                float x2 = e2.getX();

                float y1 = e1.getY();
                float y2 = e2.getY();
                // 速率的过滤
                /*
                if (Math.abs(velocityX) < 80) {
                    return false;
                }
                */


                // 如果用户是垂直方向滑动不做操作
               // if (Math.abs(y1 - y2) > Math.abs(x1 - x2)) {
                //    return false;
               // }

                if (x1 > x2) {
                    Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
                    SwitchActivity(null,ActivityStudent.class);
                } else {
                    Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }
    private void InitData() {
        String readed = getPreferences(MODE_PRIVATE).getString(PREF_KEY, "");
        StudentList list = JSON.parseObject(readed, StudentList.class);//.parseObject(readed,ArrayList.class);
        if (list == null) {
            studentItems = new ArrayList<>();
        } else {
            studentItems = list.studentItems;
        }
        if (studentItems == null) {
            studentItems = new ArrayList<>();
        }

        Intent intent = getIntent();
        StudentItem studentItem = (StudentItem) intent.getSerializableExtra(ActivityStudent.STUDENT_MESSAGE);
        if (studentItem != null) {
            studentItems.add(studentItem);
            studentDAL.AddToDB(studentItem);
        }
        Save();
    }

    private void InitListView() {
        listView = (ListView) findViewById(R.id.studentList);
        BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return studentItems.size();
            }

            @Override
            public Object getItem(int position) {
                return studentItems.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                final View layout = View.inflate(MainActivity.this, R.layout.item_layout, null);
                if (studentItems.get(position) == null) {
                    return layout;
                }
                ImageView photo = (ImageView) layout.findViewById(R.id.textview_photo);
                TextView name = (TextView) layout.findViewById(R.id.textview_name);
                TextView major = (TextView) layout.findViewById(R.id.textview_major);
                TextView academy = (TextView) layout.findViewById(R.id.textview_academy);
                photo.setImageResource(imageID[1]);
                name.setText(studentItems.get(position).name);
                major.setText(studentItems.get(position).major);
                academy.setText(studentItems.get(position).academy);

                layout.setLongClickable(true);
                layout.setOnLongClickListener(
                        new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                InitMenu(layout,position);
                                return true;
                            }
                        }
                );

                return layout;
            }
        };
        listView.setAdapter(adapter);
    }

    public void Delete(View view) {
        studentItems = new ArrayList<>();
        Save();
       // SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
       // editor.clear();
       // editor.commit();
        InitListView();
    }

    void DeleteItem(int pos)
    {
        studentItems.remove(pos);
        InitListView();
    }



    private void Save() {
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        Log.d("size", studentItems.size() + " after");
        StudentList savelist = new StudentList();
        savelist.studentItems = this.studentItems;
        String json = JSON.toJSONString(savelist);
        editor.putString(PREF_KEY, json);
        editor.commit();
       // SaveToDB();
        studentDAL.PrintDB();
    }

/*
    private void SaveToDB()
    {
        String sql = "select count(*) from student";
        Cursor cursor1 = db.rawQuery(sql, null);
        cursor1.moveToFirst();
        long count = cursor1.getLong(0);
        cursor1.close();
        int size= studentItems.size();
        Log.d(DBprint,"size "+size);
        for(int i=0;i<size;i++)
        {
            String name=studentItems.get(i).name;
            if(name.isEmpty())
            {
                name="null";
            }
            else
            {
                name="'"+name+"'";
            }
            String s="insert into student values("+
                    count+","+name+",'"+studentItems.get(i).academy+"','"+studentItems.get(i).major+"'"
                    +")";
            Log.d(DBprint,s);
            db.execSQL(s);
            Log.d(DBprint,"ok");
            count++;
        }
    }
*/

    public void SwitchActivity(View view, Class<?> cls) {
        Intent intent = new Intent(this,cls);
        startActivity(intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }


    /*

    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;
    float distance=1;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //继承了Activity的onTouchEvent方法，直接监听点击事件
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
            Toast.makeText(MainActivity.this, "down", Toast.LENGTH_SHORT).show();
        }
        if(event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();
            Toast.makeText(MainActivity.this, "up", Toast.LENGTH_SHORT).show();

            if(y1 - y2 > distance) {
                Toast.makeText(MainActivity.this, "向上滑", Toast.LENGTH_SHORT).show();
            } else if(y2 - y1 >distance) {
                Toast.makeText(MainActivity.this, "向下滑", Toast.LENGTH_SHORT).show();
            } else if(x1 - x2 > distance) {
                Toast.makeText(MainActivity.this, "向左滑", Toast.LENGTH_SHORT).show();
            } else if(x2 - x1 > distance) {
                Toast.makeText(MainActivity.this, "向右滑", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onTouchEvent(event);
    }*/

}
