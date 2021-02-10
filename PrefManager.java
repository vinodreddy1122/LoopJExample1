package lab1.adsalesapp.Utils;

/**
 * Created by Lenovo3 on 8/3/2019.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class PrefManager {

    private static SharedPreferences sharedPreferences;
    private static String MyPREFERENCES = "My_secure_Data";


    public static void setUserId(Context context, String key, String value) {
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getUserId(Context context, String key) {
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }


    public static void setUserName(Context context, String key, String value) {
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getUserName(Context context, String key) {
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static void setEmpCode(Context context, String key, String value) {
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getEmpCode(Context context, String key) {
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static void setPassword(Context context, String key, String value) {
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getPassword(Context context, String key) {
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static void setRoleId(Context context, String key, String value) {
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getRoleId(Context context, String key) {
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static void setUnitId(Context context, String key, String value) {
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getUnitId(Context context, String key) {
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static void setRolePercentage(Context context, String key, String value) {
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getRolePercentage(Context context, String key) {
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }



    public static boolean isInternetAvailable(Context context)
    {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
