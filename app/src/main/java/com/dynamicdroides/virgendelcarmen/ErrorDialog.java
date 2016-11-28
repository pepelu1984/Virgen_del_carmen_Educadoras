package com.dynamicdroides.virgendelcarmen;

import android.app.Activity;

/**
 * Created by noel on 8/29/16.
 */
public class ErrorDialog
{

    public static void showConnectionError(Activity activity)
    {
        DialogBuilder.showSimpleDialog(activity, activity.getString(R.string.error_connection));
    }

    public static void showResultError(Activity activity)
    {
        DialogBuilder.showSimpleDialog(activity, activity.getString(R.string.error_ws_response));
    }

    public static void showLoginError(Activity activity)
    {
        DialogBuilder.showSimpleDialog(activity, activity.getString(R.string.error_login));
    }

}
