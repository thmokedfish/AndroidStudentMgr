package com.example.studentmgr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.logging.Logger;

public class ActivityStudent extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private GestureDetector mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        InitButton();
        InitSpinner();

        InitDetctor();

    }
    private void InitButton()
    {
        Button button=findViewById(R.id.student_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClick(null);
            }
        });
    }


    private void InitSpinner()
    {
        Spinner spinner = findViewById(R.id.academy_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_academy, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String academy = (String) parent.getItemAtPosition(position);
                ArrayAdapter<CharSequence> adapter1 = null;
                if (academy.equals("计算机学院")) {
                    adapter1 = ArrayAdapter.createFromResource(ActivityStudent.this, R.array.spinner_cs_major, android.R.layout.simple_spinner_dropdown_item);
                } else if (academy.equals("电气学院")) {
                    adapter1 = ArrayAdapter.createFromResource(ActivityStudent.this, R.array.spinner_elect_major, android.R.layout.simple_spinner_dropdown_item);
                }
                Spinner s2 = findViewById(R.id.major_spinner);
                if (adapter1 != null) {
                    s2.setAdapter(adapter1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                } else {
                    SimpleSwitch();
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }
    public void SimpleSwitch()
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }



    public static final String STUDENT_MESSAGE="com.example.helloworld.MESSAGE";
    public void OnClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        EditText editText = (EditText) findViewById(R.id.student_name);
        String name = editText.getText().toString();
        editText = (EditText) findViewById(R.id.student_id);
        String num = editText.getText().toString();
        StudentItem item = new StudentItem();

        Spinner spinner = findViewById(R.id.academy_spinner);
        String academy = spinner.getSelectedItem().toString();

        spinner = findViewById(R.id.major_spinner);
        String major = spinner.getSelectedItem().toString();

        item.name = name;
        item.academy = academy;
        item.major = major;

        intent.putExtra(STUDENT_MESSAGE, item);


        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
