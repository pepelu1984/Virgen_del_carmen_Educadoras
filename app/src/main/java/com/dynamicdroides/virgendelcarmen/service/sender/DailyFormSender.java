package com.dynamicdroides.virgendelcarmen.service.sender;

import android.app.Activity;

import com.dynamicdroides.virgendelcarmen.CalendarUtil;
import com.dynamicdroides.virgendelcarmen.Preferences;
import com.dynamicdroides.virgendelcarmen.R;
import com.dynamicdroides.virgendelcarmen.data.StudentData;
import com.dynamicdroides.virgendelcarmen.service.WSMethod;
import com.dynamicdroides.virgendelcarmen.service.impl.WSAddBehavior;

public class DailyFormSender extends FormSender
{
	
	public DailyFormSender(Activity activity)
	{
		super(activity);
		this.startDate = CalendarUtil.getInitialDailyDate();
	    this.endDate = CalendarUtil.getFinalDailyDate();
	}
	
	@Override
	public WSMethod[] createMethods(StudentData student)
	{
		String opt2 = activity.getResources().getStringArray(R.array.options2)[0];	
		String opt3 = activity.getResources().getStringArray(R.array.options3)[0];
		
		return new WSMethod[] 
		{
			createWSMethod(student, "ensalada", student.getString(StudentData.SALAD, opt3)),
			createWSMethod(student, "plato1", student.getString(StudentData.DISH1, opt3)),
			createWSMethod(student, "plato2", student.getString(StudentData.DISH2, opt3)),
			createWSMethod(student, "postre", student.getString(StudentData.DESSERT, opt3)),
		
			createWSMethod(student, "contento", student.getString(StudentData.HAPPY, opt2)),
			createWSMethod(student, "suenno", student.getString(StudentData.SLEEP, opt2)),
			createWSMethod(student, "esfinter", student.getString(StudentData.PEE, opt2)),
			createWSMethod(student, "bolsaAseo", student.getString(StudentData.BAG, opt2)),
			createWSMethod(student, "ropaCambio", student.getString(StudentData.CLOTHES, opt2)),
			
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
