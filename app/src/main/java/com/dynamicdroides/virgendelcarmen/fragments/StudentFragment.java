package com.dynamicdroides.virgendelcarmen.fragments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dynamicdroides.virgendelcarmen.App;
import com.dynamicdroides.virgendelcarmen.CalendarUtil;
import com.dynamicdroides.virgendelcarmen.ErrorDialog;
import com.dynamicdroides.virgendelcarmen.LoginActivity;
import com.dynamicdroides.virgendelcarmen.MainActivity;
import com.dynamicdroides.virgendelcarmen.Preferences;
import com.dynamicdroides.virgendelcarmen.R;
import com.dynamicdroides.virgendelcarmen.data.StudentData;
import com.dynamicdroides.virgendelcarmen.data.UserData;
import com.dynamicdroides.virgendelcarmen.service.WSMethod;
import com.dynamicdroides.virgendelcarmen.service.data.Comment;
import com.dynamicdroides.virgendelcarmen.service.data.Student;
import com.dynamicdroides.virgendelcarmen.service.impl.WSGetComments;
import com.dynamicdroides.virgendelcarmen.service.impl.WSGetStudents;
import com.dynamicdroides.virgendelcarmen.service.WSRunner;
import com.dynamicdroides.virgendelcarmen.service.WSRunner.WSListener;

public class StudentFragment extends  BaseStudentFragment implements WSListener
{

	public enum ViewMode
	{
		NOT_SENT,
		SENT
	}

	App app;
	
	List<StudentData> students;

	RecyclerView studentListView;
	ImageView noStudentsImageView;

	ViewMode viewMode;
	
	public StudentFragment()
	{
        viewMode = ViewMode.NOT_SENT;
	}

	public ViewMode getViewMode()
	{
		return viewMode;
	}

    public void setViewMode(ViewMode viewMode)
    {
        this.viewMode = viewMode;
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		setHasOptionsMenu(true);

        MainActivity activity = (MainActivity)getActivity();
        if (viewMode == ViewMode.NOT_SENT)
            activity.getSupportActionBar().setTitle(getActivity().getString(R.string.action_not_sent));
        if (viewMode == ViewMode.SENT)
            activity.getSupportActionBar().setTitle(getActivity().getString(R.string.action_sent));

        app = (App)getActivity().getApplication();

		/** View **/
		View view = inflater.inflate(R.layout.fragment_student, container, false);

    	studentListView = (RecyclerView)view.findViewById(R.id.studentListView);
		studentListView.setLayoutManager(new LinearLayoutManager(getActivity()));

		noStudentsImageView = (ImageView) view.findViewById(R.id.noStudentsImageView);

        /*
		acceptAll = (Button)view.findViewById(R.id.studentsAcceptAll);
		acceptAll.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v)
			{
				FormSender sender = new MultipleFormSender(getActivity());
				sender.setFormSenderListener(new FormSenderListener() 
				{
					@Override
					public void onSenderFinished() 
					{
						startStudentsWS(0);
					}
				});
				ArrayList<StudentData> list = new ArrayList<StudentData>();
				for (StudentData s: students)
					if (!s.isFormFilled)
						list.add(s);
				sender.addBehavior(list.toArray(new StudentData[] {}));
			}
		});
		*/
		
		return view;
	}
	
	@Override
	public void startStudentsWS(int position)
	{
		Date initialDate = CalendarUtil.getInitialDailyDate();
		Date finalDate = CalendarUtil.getFinalDailyDate();

		UserData user = app.user;
		WSRunner wsRunner = new WSRunner(new WSGetStudents(user.username, user.password, initialDate, finalDate), StudentFragment.this);
        wsRunner.execute();
	}
	
	@Override
	public void onPause() 
	{
	    super.onPause();
	    onSaveInstanceState(new Bundle());      
	}
	
	@Override
	public void onViewStateRestored(Bundle savedInstanceState)
	{
		super.onViewStateRestored(savedInstanceState);
		
		Date today = CalendarUtil.getInitialDailyDate();
		Date date = null;
		if (app.savedUser != null && app.user == app.savedUser)
		{
			date = app.savedDate;
			students = app.savedStudentList;
		}
		if (date == null || !today.equals(date) || students == null || students.size() == 0 || app.viewMode != viewMode)
			startStudentsWS(0);
		else
			setStudentList(students);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);

		Date today = CalendarUtil.getInitialDailyDate();
		app.savedStudentList = students;
		app.savedDate = today;
		app.savedUser = app.user;
	}	

	@Override
	public void onWSResult(WSMethod[] methods)
	{
		WSMethod method = methods[0];
		if (method.hasError())
		{
			if (method.getResultType() == WSRunner.WSRunnerResult.FAIL_ON_CONNECTION)
				ErrorDialog.showConnectionError(getActivity());
			else
				ErrorDialog.showResultError(getActivity());
			return;
		}

		if (method instanceof WSGetStudents)
		{
			WSGetStudents m = (WSGetStudents)method;
			ArrayList<StudentData> data = new ArrayList<StudentData>();
            for (Student s: m.getResult())
			{
                StudentData sd = StudentData.importFrom(s);
                if (!sd.isFormFilled && viewMode == ViewMode.NOT_SENT)
				    data.add(sd);
                if (sd.isFormFilled && viewMode == ViewMode.SENT)
                    data.add(sd);
			}
            app.viewMode = viewMode;
			setStudentList(data);
			
			UserData user = app.user;
			new WSRunner(new WSGetComments(user.username, user.password), StudentFragment.this).execute();
		}
		if (method instanceof WSGetComments)
		{
			WSGetComments m = (WSGetComments)method;
            ArrayList<String> data = new ArrayList<String>();
            for (Comment c: m.getResult())
                data.add(c.getObservaciones());
			app.commentList = data;
			app.commentList.add(0, "");
		}
	}
	
	private void setStudentList(List<StudentData> studentList)
	{
		students = studentList;

		noStudentsImageView.setVisibility(View.GONE);
		if (students.size() == 0)
		{
			noStudentsImageView.setVisibility(View.VISIBLE);
		}
		
		studentListView.setAdapter(new StudentListAdapter(this, students));
	}
	
}
