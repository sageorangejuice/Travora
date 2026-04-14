package com.travora.app.model;

import android.content.Context;
import android.content.SharedPreferences;

public class UserManager {

    private static final String PREFS_NAME = "travora_prefs";
    private static final String KEY_USERNAME = "username";

    private static User currentUser;

    public static void setUser(User user) {
        currentUser = user;
    }

    public static User getUser() {
        return currentUser;
    }

    public static void saveToPrefs(Context context, String username) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(KEY_USERNAME, username)
                .apply();
        currentUser = new User(username);
    }

    public static void loadFromPrefs(Context context) {
        if (currentUser != null) return;
        String username = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getString(KEY_USERNAME, null);
        if (username != null) {
            currentUser = new User(username);
        }
    }

    public static void clearSession(Context context) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .remove(KEY_USERNAME)
                .apply();
        currentUser = null;
    }
}