package com.example.studentmgr;

import java.io.Serializable;

public class StudentItem implements Serializable {
    public String name;
    public String academy;
    public String major;
    public StudentItem(){}
    public StudentItem(String name,String academy,String major)
    {
        this.name=name;
        this.academy=academy;
        this.major=major;
    }
}
