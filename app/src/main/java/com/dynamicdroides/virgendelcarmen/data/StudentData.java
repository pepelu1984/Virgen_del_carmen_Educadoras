package com.dynamicdroides.virgendelcarmen.data;

import android.util.Log;

import com.dynamicdroides.virgendelcarmen.service.data.Behavior;
import com.dynamicdroides.virgendelcarmen.service.data.Student;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class StudentData
{

	public static final String SALAD = "ensalada";
	public static final String DESSERT = "postre";
	public static final String ROW = "fila";
	public static final String DINNINGROOM = "comedor";
	public static final String GAMES = "juegos";
	public static final String WASHING = "aseo";

	public static final String DISH1 = "plato1";
	public static final String DISH2 = "plato2";
	public static final String HAPPY = "contento";
	public static final String SLEEP = "suenno";
	public static final String PEE = "esfinter";
	public static final String BAG = "bolsaAseo";
	public static final String CLOTHES = "ropaCambio";

	public static final String SUMMARY = "observaciones";
	
	public int id;
	public String name;
	public String lastName;
	public String course;
	public boolean isDaily;
	public HashMap<String, BehaviorData> behavior = new HashMap<>();
	public Date initialDate;
	public Date finalDate;
	public String emailParents;
	public boolean dinning;

	public boolean isFormFilled;

	 public static StudentData importFrom(Student s)
     {
         boolean isDaily = Boolean.valueOf(s.getCurso()).booleanValue();
         Log.d(StudentData.class.getName(), "isDaily: " + isDaily + " getCurso:" + s.getCurso());
         boolean dinning = false;
		 HashMap<String, BehaviorData> behaviors = new HashMap<String, BehaviorData>();
		 for (Behavior b: s.getComportamientos())
		 	behaviors.put(b.getTipo(), BehaviorData.importFrom(b));
         return new StudentData(s.getIdalumno(), s.getNombre(), s.getApellidos(), s.getCursoname(), isDaily, s.isComportamientoFilled(), behaviors, s.getFechaInicio(), s.getFechaFin(), s.getCorreopadres(), dinning);
     }
	
	public StudentData(int id, String name, String lastName, String course, boolean isDaily, boolean isFormFilled, HashMap<String, BehaviorData> behavior, Date initialDate, Date finalDate, String emailParents, boolean dinning)
	{
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.course = course;
		this.isDaily = isDaily;
		this.isFormFilled = isFormFilled;
		this.behavior = behavior;
		this.initialDate = initialDate;
		this.finalDate = finalDate;
		this.emailParents = emailParents;
		this.dinning = dinning;
	}
	
	public void putString(String key, String value)
	{
		BehaviorData data = new BehaviorData(key, value);
		behavior.put(key, data);
	}
	
	public void putInt(String key, int value)
	{
		BehaviorData data = new BehaviorData(key, Integer.toString(value));
		behavior.put(key, data);
	}
	
	public String getString(String key)
	{
        if (behavior.get(key) == null)
            return "";
		String str = behavior.get(key).value;
		if (str == null)
            return "";
		return str;
	}
	
	public String getString(String key, String defValue)
	{
        if (behavior.get(key) == null)
            return defValue;
		String str = behavior.get(key).value;
		if (str == null)
			return defValue;
		return str;
	}
	
	public int getSelected(String key, String[] list)
	{
        if (behavior.get(key) == null)
            return 0;
        String str = behavior.get(key).value;
        if (str == null)
            return 0;
        for (int i = 0; i < list.length; i++)
            if (str.equals(list[i]))
                return i;
		return 0;
	}
	
	public int getSelected(String key, String[] list, int defValue)
	{
        if (behavior.get(key) == null)
            return defValue;
        String str = behavior.get(key).value;
        if (str == null)
            return defValue;
        for (int i = 0; i < list.length; i++)
            if (str.equals(list[i]))
                return i;
		return defValue;
	}
	
	@Override
	public String toString()
	{
		return name + " " + lastName;
	}
}
