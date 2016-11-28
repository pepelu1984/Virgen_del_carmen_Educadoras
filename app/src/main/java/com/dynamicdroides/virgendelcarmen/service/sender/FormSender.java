package com.dynamicdroides.virgendelcarmen.service.sender;

import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;

import com.dynamicdroides.virgendelcarmen.DialogBuilder;
import com.dynamicdroides.virgendelcarmen.ErrorDialog;
import com.dynamicdroides.virgendelcarmen.R;
import com.dynamicdroides.virgendelcarmen.data.StudentData;
import com.dynamicdroides.virgendelcarmen.fragments.FragmentNavigator;
import com.dynamicdroides.virgendelcarmen.service.WSMethod;
import com.dynamicdroides.virgendelcarmen.service.impl.WSFinishBehavior;
import com.dynamicdroides.virgendelcarmen.service.WSRunner;
import com.dynamicdroides.virgendelcarmen.service.WSRunner.WSListener;

public abstract class FormSender implements WSListener 
{

	FormSenderListener listener;
	Activity activity;
	AlertDialog dialog;
	Date startDate;
	Date endDate;	
	StudentData[] students;
	int currentElement;
	
	public FormSender(Activity activity)
	{
		this.activity = activity;
	}

	public void setFormSenderListener(FormSenderListener listener)
	{
		this.listener = listener;
	}
	
	public abstract WSMethod[] createMethods(StudentData student);
	
	public void addBehavior(StudentData[] students)
	{
		this.students = students;
		
		currentElement = 0;
		dialog = DialogBuilder.showSimpleDialog(activity, activity.getString(R.string.text_wait));
		addNextStudent();
	}
	
	private void addNextStudent()
	{
		WSMethod[] methods = createMethods(students[currentElement]);
		new WSRunner(methods, this).execute();
	}
	
	@Override
	public void onWSResult(WSMethod[] methods) 
	{
		for (WSMethod m: methods)
        {
            if (m.hasError())
            {
                if (m.getResultType() == WSRunner.WSRunnerResult.FAIL_ON_CONNECTION)
                    ErrorDialog.showConnectionError(activity);
                else
                    ErrorDialog.showResultError(activity);
                FragmentNavigator.terminateFragment();
                if (dialog != null)
                    dialog.dismiss();
                if (listener != null)
                    listener.onSenderFinished();
                return;
            }
        }

		WSMethod m = methods[0];
		if (m instanceof WSFinishBehavior)
		{
			currentElement++;
			if (currentElement < students.length)
				addNextStudent();
			else
			{
				FragmentNavigator.terminateFragment();
				if (dialog != null)
					dialog.dismiss();
				if (listener != null)
					listener.onSenderFinished();
			}
		}
		else
			new WSRunner(new WSFinishBehavior(students[currentElement].id, startDate, endDate), this).execute();
	}
	
}
