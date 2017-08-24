


package catalogitsolutions.letmetutorapp.activity;

import android.content.Context;
import android.content.SharedPreferences;
//Purpose: Used for sessions
public class SessionManager {
    public void setPreferences(Context context, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences("students", Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getPreferences(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences("students", Context.MODE_PRIVATE);
        String position = prefs.getString(key, "");
        return position;
    }


    public void setPreferences1(Context context, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences("teacher", Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getPreferences1(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences("teacher", Context.MODE_PRIVATE);
        String position = prefs.getString(key, "");
        return position;
    }
}
