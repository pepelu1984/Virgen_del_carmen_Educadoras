package com.dynamicdroides.virgendelcarmen.fragments;

import com.dynamicdroides.virgendelcarmen.R;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentNavigator 
{

	public static FragmentActivity fragmentActivity;
	
	public static Fragment getDefaultFragment()
	{
		FragmentManager fm = fragmentActivity.getSupportFragmentManager();
		Fragment oldFragment = fm.findFragmentById(R.id.fragment_main_layout);
		if (oldFragment != null)
			return oldFragment;
		
		return new StudentFragment();
	}
	
	public static void add(Fragment fragment)
	{
		FragmentNavigator.add(fragment, true);
	}
	
	public static void add(Fragment fragment, boolean addToBackStack)
	{
		FragmentManager fm = fragmentActivity.getSupportFragmentManager();
		Fragment oldFragment = fm.findFragmentById(R.id.fragment_main_layout);
		
		FragmentTransaction ft = fm.beginTransaction();
		if (oldFragment == null)
			ft.add(R.id.fragment_main_layout, fragment);
		else
		{
			ft.replace(R.id.fragment_main_layout, fragment);
			if (addToBackStack)
				ft.addToBackStack(null);
		}
		ft.commit();
	}
	
	public static void replace(Fragment fragment)
	{
		FragmentManager fm = fragmentActivity.getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.fragment_main_layout, fragment);
		ft.commit();
	}	
	
	public static void redrawFragment()
	{
		FragmentManager fm = fragmentActivity.getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragment_main_layout);
		
		FragmentTransaction ft = fm.beginTransaction();
		ft.detach(fragment);
		ft.attach(fragment);
		ft.commit();
	}
	
	public static void terminateFragment()
	{
		FragmentManager fm = fragmentActivity.getSupportFragmentManager();
		fm.popBackStack();
	}
	
	public static void cleanBackStack()
	{
		FragmentManager fm = fragmentActivity.getSupportFragmentManager();
		for (int i = 0; i < fm.getBackStackEntryCount(); ++i)    
		    fm.popBackStack();
	}
	
}
