package com.dynamicdroides.virgendelcarmen.data;

import com.dynamicdroides.virgendelcarmen.service.data.User;

public class UserData
{

	public int id;
	public String name;
	public String lastName;
	public String email;
	public String username;
	public String password;
	public boolean isAdmin;

	public static UserData importFrom(User u)
	{
		return new UserData(u.getIduser(), u.getNombre(), u.getApellidos(), u.getEmail(), u.getUser(), u.getPassword(), u.getIsadmin());
	}

	public UserData(int id, String name, String lastName, String email, String username, String password, boolean isAdmin)
	{
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
	}
	
}
