package com.dynamicdroides.virgendelcarmen.service.sender;

import android.app.Activity;

import com.dynamicdroides.virgendelcarmen.data.StudentData;
import com.dynamicdroides.virgendelcarmen.service.WSMethod;

public class MultipleFormSender extends FormSender
{
	
	public MultipleFormSender(Activity activity)
	{
		super(activity);
	}
	
	@Override
	public WSMethod[] createMethods(StudentData student)
	{
		FormSender sender;
		if (student.isDaily)
			sender = new DailyFormSender(activity);
		else
			sender = new WeeklyFormSender(activity);
		return sender.createMethods(student);
	}
	
}
