package com.dynamicdroides.virgendelcarmen;

import android.app.Activity;
import android.app.Application;

import com.dynamicdroides.virgendelcarmen.data.StudentData;
import com.dynamicdroides.virgendelcarmen.data.UserData;
import com.dynamicdroides.virgendelcarmen.fragments.StudentFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by noel on 9/2/16.
 */
public class App extends Application
{

    public Activity activity;

    public StudentData student;

    public UserData user;

    public StudentFragment.ViewMode viewMode;

    public List<StudentData> savedStudentList;
    public List<String> commentList = new ArrayList<String>();
    public Date savedDate;
    public UserData savedUser;

}
