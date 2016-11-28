package com.dynamicdroides.virgendelcarmen.fragments;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.dynamicdroides.virgendelcarmen.App;
import com.dynamicdroides.virgendelcarmen.CalendarUtil;
import com.dynamicdroides.virgendelcarmen.MainActivity;
import com.dynamicdroides.virgendelcarmen.R;
import com.dynamicdroides.virgendelcarmen.data.StudentData;
import com.dynamicdroides.virgendelcarmen.service.sender.FormSender;
import com.dynamicdroides.virgendelcarmen.service.sender.FormSenderListener;
import com.dynamicdroides.virgendelcarmen.service.sender.WeeklyFormSender;

public class WeeklyFragment extends Fragment implements OnClickListener
{

	App app;
	BaseStudentFragment baseFragment;
	
	Date startDate;
	Date endDate;
	
	Spinner salad;
	Spinner dish1;
	Spinner dish2;
	Spinner dessert;
	Spinner row;
	Spinner dinningroom;
	Spinner games;
	Spinner washing;
	EditText summary;
	Spinner comments;
	
	public WeeklyFragment()
	{}

	public void setBaseFragment(BaseStudentFragment bsf)
	{
		baseFragment = bsf;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		app = (App)getActivity().getApplication();
		String nameStr = app.student.name + " " + app.student.lastName;

		MainActivity activity = (MainActivity)getActivity();
		activity.getSupportActionBar().setTitle(nameStr);

		View view = inflater.inflate(R.layout.fragment_weekly_form, container, false);

		TextView textView = (TextView)view.findViewById(R.id.nameTextView);
		textView.setText(nameStr);
		
		/*
		textView = (TextView)view.findViewById(R.id.courseTextView);
		textView.setText(CommonData.course.name);
		*/		
		
	    startDate = CalendarUtil.getInitialDailyDate();
	    endDate = CalendarUtil.getFinalDailyDate();
	    /*
	    String weekStr = new SimpleDateFormat("dd MMMM yyyy").format(startDate);
	    textView = (TextView)view.findViewById(R.id.week1TextView);
	    textView.setText(weekStr);
	    */
	    String weekStr = new SimpleDateFormat("dd MMMM yyyy").format(endDate);
	    textView = (TextView)view.findViewById(R.id.week2TextView);
	    textView.setText(weekStr);	    
	    
		Button sendButton = (Button)view.findViewById(R.id.sendWeeklyButton);
		sendButton.setOnClickListener(this);

        String[] opt3 = getResources().getStringArray(R.array.options3);
        String[] opt4 = getResources().getStringArray(R.array.options4);
		
		salad = (Spinner)view.findViewById(R.id.saladSpinner);
		salad.setSelection(app.student.getSelected(StudentData.SALAD, opt4, 1));
		dish1 = (Spinner)view.findViewById(R.id.dish1Spinner);
		dish1.setSelection(app.student.getSelected(StudentData.DISH1, opt4, 1));
		dish2 = (Spinner)view.findViewById(R.id.dish2Spinner);
		dish2.setSelection(app.student.getSelected(StudentData.DISH2, opt4, 1));
		dessert = (Spinner)view.findViewById(R.id.dessertSpinner);
		dessert.setSelection(app.student.getSelected(StudentData.DESSERT, opt4, 1));
		
		row = (Spinner)view.findViewById(R.id.rowSpinner);
		row.setSelection(app.student.getSelected(StudentData.ROW, opt3));
		dinningroom = (Spinner)view.findViewById(R.id.dinningroomSpinner);
		dinningroom.setSelection(app.student.getSelected(StudentData.DINNINGROOM, opt3));
		games = (Spinner)view.findViewById(R.id.gamesSpinner);
		games.setSelection(app.student.getSelected(StudentData.GAMES, opt3));
		washing = (Spinner)view.findViewById(R.id.washingSpinner);
		washing.setSelection(app.student.getSelected(StudentData.WASHING, opt3));
		
		summary = (EditText)view.findViewById(R.id.summaryWeeklyEditText);
		summary.setText(app.student.getString(StudentData.SUMMARY));
		
		comments = (Spinner)view.findViewById(R.id.commentsWeeklySpinner);
		comments.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, app.commentList));
		comments.setOnItemSelectedListener(new OnItemSelectedListener() 
		{
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) 
			{
				String text = summary.getText() + app.commentList.get(position);
				summary.setText(text);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) 
			{}
		});
		
		return view;
	}
	
	@Override
	public void onClick(View v) 
	{
		StudentData s = app.student;
		s.putString(StudentData.SALAD, salad.getSelectedItem().toString());
		s.putString(StudentData.DISH1, dish1.getSelectedItem().toString());
		s.putString(StudentData.DISH2, dish2.getSelectedItem().toString());
		s.putString(StudentData.DESSERT, dessert.getSelectedItem().toString());
		s.putString(StudentData.ROW, row.getSelectedItem().toString());
		s.putString(StudentData.DINNINGROOM, dinningroom.getSelectedItem().toString());
		s.putString(StudentData.GAMES, games.getSelectedItem().toString());
		s.putString(StudentData.WASHING, washing.getSelectedItem().toString());
		s.putString(StudentData.SUMMARY, summary.getText().toString());

		FormSender sender = new WeeklyFormSender(getActivity());
		sender.setFormSenderListener(new FormSenderListener()
		{
			@Override
			public void onSenderFinished()
			{
				FragmentNavigator.terminateFragment();
				baseFragment.startStudentsWS(0);
			}
		});
		sender.addBehavior(new StudentData[] {s});
	}
	
}
