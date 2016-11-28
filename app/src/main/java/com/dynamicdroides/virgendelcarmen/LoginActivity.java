package com.dynamicdroides.virgendelcarmen;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dynamicdroides.virgendelcarmen.data.UserData;
import com.dynamicdroides.virgendelcarmen.service.WSMethod;
import com.dynamicdroides.virgendelcarmen.service.WSRunner;
import com.dynamicdroides.virgendelcarmen.service.impl.WSCheckUser;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by noel on 9/2/16.
 */
public class LoginActivity extends AppCompatActivity implements WSRunner.WSListener
{

    App app;

    EditText userEdit;
    EditText passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        app = (App)getApplication();
        app.activity = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary)));

        userEdit = (EditText)findViewById(R.id.usernameEditText);
        passwordEdit = (EditText)findViewById(R.id.passwordEditText);
        Button loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                login();
            }
        });
    }

    private void login()
    {
        String user = userEdit.getText().toString();
        String password = getMd5Data(passwordEdit.getText().toString());
        new WSRunner(new WSCheckUser(user, password), this).execute();
    }

    protected String getMd5Data(String text)
    {
        MessageDigest digest = null;
        try
        {
            digest = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        digest.update(text.getBytes(), 0, text.length());
        String md5 = new BigInteger(1, digest.digest()).toString(16);
        return md5;
    }

    @Override
    public void onWSResult(WSMethod[] methods)
    {
        WSCheckUser m = (WSCheckUser)methods[0];
        if (m.hasError())
        {
            if (m.getResultType() == WSRunner.WSRunnerResult.FAIL_ON_CONNECTION)
                ErrorDialog.showConnectionError(this);
            else
                ErrorDialog.showLoginError(this);
            return;
        }

        UserData result = UserData.importFrom(m.getResult());
        app.user = result;
        Preferences pref = new Preferences(app.activity);
        pref.put(Preferences.PREF_USERNAME, result.username);
        pref.put(Preferences.PREF_PASSWORD, result.password);
        pref.put(Preferences.PREF_FULLNAME, result.name + " " + result.lastName);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
