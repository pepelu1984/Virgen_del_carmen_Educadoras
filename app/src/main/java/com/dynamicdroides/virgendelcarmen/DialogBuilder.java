package com.dynamicdroides.virgendelcarmen;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class DialogBuilder
{

	public static AlertDialog showOneButtonDialog(Activity activity, String text)
	{
		AlertDialog dialog = new AlertDialog.Builder(activity).create();
		dialog.setMessage(text);
		dialog.setButton(AlertDialog.BUTTON_POSITIVE, activity.getString(R.string.button_ok), new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
			}
		});
		dialog.show();
		return dialog;
	}
	
	public static AlertDialog showSimpleDialog(Activity activity, String text)
	{
		AlertDialog dialog = new AlertDialog.Builder(activity).create();
		dialog.setMessage(text);
		dialog.show();
		return dialog;
	}
	
}
