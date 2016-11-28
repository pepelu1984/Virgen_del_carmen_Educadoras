package com.dynamicdroides.virgendelcarmen.fragments;

import java.util.HashMap;
import java.util.List;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dynamicdroides.virgendelcarmen.App;
import com.dynamicdroides.virgendelcarmen.R;
import com.dynamicdroides.virgendelcarmen.data.BehaviorData;
import com.dynamicdroides.virgendelcarmen.data.StudentData;
import com.dynamicdroides.virgendelcarmen.service.sender.DailyFormSender;
import com.dynamicdroides.virgendelcarmen.service.sender.FormSender;
import com.dynamicdroides.virgendelcarmen.service.sender.FormSenderListener;
import com.dynamicdroides.virgendelcarmen.service.sender.WeeklyFormSender;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.ViewHolder>
{

	public class ViewHolder extends RecyclerView.ViewHolder
	{
        private final RelativeLayout layout;
		private final TextView name;
		private final TextView behavior;

		ViewHolder(View v)
		{
			super(v);
            layout = (RelativeLayout)v.findViewById(R.id.studentCellLayout);
            name = (TextView)v.findViewById(R.id.studentNameTextView);
			behavior = (TextView)v.findViewById(R.id.studentBehaviorTextView);
		}
	}

	App app;
	BaseStudentFragment fragment;
	LayoutInflater inflater;
	List<StudentData> students;
	
	public StudentListAdapter(BaseStudentFragment fragment, List<StudentData> students)
	{
		this.app = (App)fragment.getActivity().getApplication();
		this.fragment = fragment;
		this.inflater = LayoutInflater.from(fragment.getActivity());
		this.students = students;
	}

	@Override
	public int getItemCount()
	{
		return students.size();
	}

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_student, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position)
    {
			
		final StudentData student = students.get(position);
		
		TextView name = viewHolder.name;
		name.setText(student.toString());
		
		TextView behavior = viewHolder.behavior;
		String behaviorString = "";
		for (BehaviorData b: student.behavior.values())
		{
			if (!behaviorString.equals(""))
				behaviorString += ";  ";
			behaviorString += "<b>" + BehaviorData.getStringFor(b.type) + "</b>: " + b.value;
		}
        behavior.setText(Html.fromHtml(behaviorString));

		RelativeLayout cell = viewHolder.layout;
		int color = fragment.getActivity().getResources().getColor(R.color.vdc_form_background);
		cell.setBackgroundColor(color);
        cell.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                app.student = student;
                if (app.student.isDaily)
				{
					DailyFragment df = new DailyFragment();
					df.setBaseFragment(fragment);
					FragmentNavigator.add(df);
				}
                else
				{
					WeeklyFragment wf = new WeeklyFragment();
					wf.setBaseFragment(fragment);
					FragmentNavigator.add(wf);
				}
            }
        });
	}
	


}