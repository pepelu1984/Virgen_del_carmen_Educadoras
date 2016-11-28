package com.dynamicdroides.virgendelcarmen.data;

public class CourseData
{
	
	public long id;
	public String name;
	public String responsible;
	public boolean isDaily;
	
	public CourseData(long id, String name, String responsible, boolean isDaily)
	{
		this.id = id;
		this.name = name;
		this.responsible = responsible;
		this.isDaily = isDaily;
	}
	
	@Override
	public String toString()
	{
		return "Curso " + name;
	}
}
