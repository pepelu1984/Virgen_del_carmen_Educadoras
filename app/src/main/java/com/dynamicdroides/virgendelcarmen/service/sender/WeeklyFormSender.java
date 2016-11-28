package com.dynamicdroides.virgendelcarmen.service.sender;

import android.app.Activity;

import com.dynamicdroides.virgendelcarmen.CalendarUtil;
import com.dynamicdroides.virgendelcarmen.Preferences;
import com.dynamicdroides.virgendelcarmen.R;
import com.dynamicdroides.virgendelcarmen.data.StudentData;
import com.dynamicdroides.virgendelcarmen.service.WSMethod;
import com.dynamicdroides.virgendelcarmen.service.impl.WSAddBehavior;

public class WeeklyFormSender extends FormSender
{
	
	public WeeklyFormSender(Activity activity)
	{
		super(activity);
		this.startDate = CalendarUtil.getInitialDailyDate();
	    this.endDate = CalendarUtil.getInitialDailyDate();
	}
	
	@Override
	public WSMethod[] createMethods(StudentData student)
	{
		String opt3 = activity.getResources().getStringArray(R.array.options3)[0];
		String opt4 = activity.getResources().getStringArray(R.array.options4)[1];
		
		return new WSMethod[] 
		{
			createWSMethod(student, "ensalada", student.getString(StudentData.SALAD, opt4)),
			createWSMethod(student, "plato1", student.getString(StudentData.DISH1, opt4)),
			createWSMethod(student, "plato2", student.getString(StudentData.DISH2, opt4)),
			createWSMethod(student, "postre", student.getString(StudentData.DESSERT, opt4)),
			
			createWSMethod(student, "fila", student.getString(StudentData.ROW, opt3)),
			createWSMethod(student, "comedor", student.getString(StudentData.DINNINGROOM, opt3)),
			createWSMethod(student, "juegos", student.getString(StudentData.GAMES, opt3)),
			createWSMethod(student, "aseo", student.getString(StudentData.WASHING, opt3)),
			
			createWSMethod(student, "observaciones", student.getString(StudentData.SUMMARY))
		};
	}	

	private WSAddBehavior createWSMethod(StudentData student, String type, String value)
	{
		Preferences pref = new Preferences(activity);
		String user = pref.get(Preferences.PREF_USERNAME);
		String password = pref.get(Preferences.PREF_PASSWORD);	
		
		return new WSAddBehavior(user, password, type, value, student.id, startDate, endDate);
	}

}
