package com.dynamicdroides.virgendelcarmen;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dynamicdroides.virgendelcarmen.data.UserData;
import com.dynamicdroides.virgendelcarmen.fragments.FragmentNavigator;
import com.dynamicdroides.virgendelcarmen.fragments.StudentFragment;
import com.dynamicdroides.virgendelcarmen.service.WSMethod;
import com.dynamicdroides.virgendelcarmen.service.WSRunner;
import com.dynamicdroides.virgendelcarmen.service.impl.WSCheckUser;

public class MainActivity extends AppCompatActivity implements WSRunner.WSListener
{

    App app;

    DrawerLayout drawerLayout;
    NavigationView navigationView;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        app = (App)getApplication();
		app.activity = this;
		// WSRunner.setBaseUrl("http://10.42.0.1:8080/dynamicschool-app/");
        WSRunner.setBaseUrl("http://www.dynamicdroides.com:8080/SpringLoader/");
		FragmentNavigator.fragmentActivity = this;

        Preferences pref = new Preferences(app.activity);
        String user = pref.get(Preferences.PREF_USERNAME);
        String password = pref.get(Preferences.PREF_PASSWORD);
        String fullName = pref.get(Preferences.PREF_FULLNAME);
        String vm = pref.get(Preferences.PREF_VIEWMODE);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary)));
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        View headerView = navigationView.inflateHeaderView(R.layout.drawer_header);
        TextView userNameTextView = (TextView)headerView.findViewById(R.id.drawer_header_user_name);
        userNameTextView.setText(fullName);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem)
            {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                switch (menuItem.getItemId())
                {
                    case R.id.action_exit:
                        Preferences pref = new Preferences(MainActivity.this);
                        // pref.put(Preferences.PREF_USERNAME, null);
                        pref.put(Preferences.PREF_PASSWORD, null);
                        FragmentNavigator.cleanBackStack();

                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                        break;

                    case R.id.action_not_sent:
                        switchViewMode(StudentFragment.ViewMode.NOT_SENT);
                        break;

                    case R.id.action_sent:
                        switchViewMode(StudentFragment.ViewMode.SENT);
                        break;
                }
                return true;
            }
        });

        StudentFragment.ViewMode viewMode = StudentFragment.ViewMode.NOT_SENT;
        if (vm != null)
            viewMode = StudentFragment.ViewMode.valueOf(vm);
        app.viewMode = viewMode;

        if ((user != null && !user.equals("")) && password != null && !password.equals(""))
        {
            new WSRunner(new WSCheckUser(user, password), this).execute();
        }
        else
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
	}

    private void switchViewMode(StudentFragment.ViewMode viewMode)
    {
        Fragment f = FragmentNavigator.getDefaultFragment();
        if (f instanceof StudentFragment && ((StudentFragment)f).getViewMode() == viewMode)
            return;

        StudentFragment sf = new StudentFragment();
        sf.setViewMode(viewMode);
        FragmentNavigator.add(sf, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Fragment f = FragmentNavigator.getDefaultFragment();
        if (item.getItemId() == android.R.id.home)
        {
            if (f instanceof StudentFragment)
                drawerLayout.openDrawer(GravityCompat.START);
            else
                FragmentNavigator.terminateFragment();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onWSResult(WSMethod[] methods)
    {
        WSCheckUser m = (WSCheckUser)methods[0];
        if (m.hasError())
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        UserData result = UserData.importFrom(m.getResult());
        app.user = result;
        Preferences pref = new Preferences(app.activity);
        pref.put(Preferences.PREF_USERNAME, result.username);
        pref.put(Preferences.PREF_PASSWORD, result.password);

        Fragment f = FragmentNavigator.getDefaultFragment();
        FragmentNavigator.add(f, false);
    }

}
