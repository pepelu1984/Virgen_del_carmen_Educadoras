package com.dynamicdroides.virgendelcarmen;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarUtil
{

	public static Date getInitialDailyDate()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.AM_PM,0);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		return calendar.getTime();
	}
	
	public static Date getFinalDailyDate()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.AM_PM,0);
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	/*
	public static Date getInitialWeeklyDate()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
	    int i = calendar.get(Calendar.DAY_OF_WEEK) - calendar.getFirstDayOfWeek();
	    calendar.add(Calendar.DATE, -i);		
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}	
	
	public static Date getFinalWeeklyDate()
	{
		GregorianCalendar calendar = (GregorianCalendar)GregorianCalendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		int i = calendar.get(Calendar.DAY_OF_WEEK) - calendar.getFirstDayOfWeek();
	    calendar.add(Calendar.DATE, -i);
	    calendar.add(Calendar.DATE, 6);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}	
	*/
	
}
