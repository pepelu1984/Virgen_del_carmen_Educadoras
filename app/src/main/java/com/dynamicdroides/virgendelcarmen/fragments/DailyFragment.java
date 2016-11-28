package com.dynamicdroides.virgendelcarmen.fragments;

import java.text.SimpleDateFormat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.dynamicdroides.virgendelcarmen.App;
import com.dynamicdroides.virgendelcarmen.CalendarUtil;
import com.dynamicdroides.virgendelcarmen.MainActivity;
import com.dynamicdroides.virgendelcarmen.R;
import com.dynamicdroides.virgendelcarmen.data.StudentData;
import com.dynamicdroides.virgendelcarmen.service.sender.DailyFormSender;
import com.dynamicdroides.virgendelcarmen.service.sender.FormSender;
import com.dynamicdroides.virgendelcarmen.service.sender.FormSenderListener;

public class DailyFragment extends Fragment implements OnClickListener
{

	App app;
	BaseStudentFragment baseFragment;
	
	StudentData student;

	Spinner salad;
	Spinner dish1;
	Spinner dish2;
	Spinner dessert;
	Spinner happy;
	Spinner sleep;
	Spinner pee;
	Spinner bag;	
	Spinner clothes;	
	EditText summaryText;
	Spinner comments;
	
	public DailyFragment()
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

		View view = inflater.inflate(R.layout.fragment_daily_form, container, false);
		
		TextView textView = (TextView)view.findViewById(R.id.nameTextView);
		textView.setText(nameStr);
		
		/*
		textView = (TextView)view.findViewById(R.id.courseTextView);
		textView.setText(CommonData.course.name);
		*/		
		
		textView = (TextView)view.findViewById(R.id.week2TextView);
		String formattedDate = new SimpleDateFormat("dd MMMM yyyy").format(CalendarUtil.getInitialDailyDate());
		textView.setText(formattedDate);
		
		Button sendButton = (Button)view.findViewById(R.id.sendDailyButton);
		sendButton.setOnClickListener(this);

        String[] opt2 = getResources().getStringArray(R.array.options2);
        String[] opt3 = getResources().getStringArray(R.array.options3);
        String[] opt4 = getResources().getStringArray(R.array.options4);

		salad = (Spinner)view.findViewById(R.id.saladDailySpinner);
		salad.setSelection(app.student.getSelected(StudentData.SALAD, opt3));
		dish1 = (Spinner)view.findViewById(R.id.dish1DailySpinner);
		dish1.setSelection(app.student.getSelected(StudentData.DISH1, opt3));
		dish2 = (Spinner)view.findViewById(R.id.dish2DailySpinner);
		dish2.setSelection(app.student.getSelected(StudentData.DISH2, opt3));
		dessert = (Spinner)view.findViewById(R.id.dessertDailySpinner);
		dessert.setSelection(app.student.getSelected(StudentData.DESSERT, opt3));
		happy = (Spinner)view.findViewById(R.id.happyDailySpinner);
		happy.setSelection(app.student.getSelected(StudentData.HAPPY, opt2));
		sleep = (Spinner)view.findViewById(R.id.sleepDailySpinner);
		sleep.setSelection(app.student.getSelected(StudentData.SLEEP, opt2));
		pee = (Spinner)view.findViewById(R.id.peeDailySpinner);
		pee.setSelection(app.student.getSelected(StudentData.PEE, opt2));
		bag = (Spinner)view.findViewById(R.id.bagDailySpinner);
		bag.setSelection(app.student.getSelected(StudentData.BAG, opt2));
		clothes = (Spinner)view.findViewById(R.id.clothesDailySpinner);
		clothes.setSelection(app.student.getSelected(StudentData.CLOTHES, opt2));
		
		summaryText = (EditText)view.findViewById(R.id.summaryDailyEditText);	
		summaryText.setText(app.student.getString(StudentData.SUMMARY));
		
		comments = (Spinner)view.findViewById(R.id.commentsDailySpinner);
		comments.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, app.commentList));
		comments.setOnItemSelectedListener(new OnItemSelectedListener() 
		{
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) 
			{
				String text = summaryText.getText() + app.commentList.get(position);
				summaryText.setText(text);
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
		s.putString(StudentData.HAPPY, happy.getSelectedItem().toString());
		s.putString(StudentData.SLEEP, sleep.getSelectedItem().toString());
		s.putString(StudentData.PEE, pee.getSelectedItem().toString());
		s.putString(StudentData.BAG, bag.getSelectedItem().toString());
		s.putString(StudentData.CLOTHES, clothes.getSelectedItem().toString());
		s.putString(StudentData.SUMMARY, summaryText.getText().toString());

		FormSender sender = new DailyFormSender(getActivity());
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
