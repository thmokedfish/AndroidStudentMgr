package com.example.studentmgr;



import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.view.View;

public class SettingsActivity  extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

   private android.preference.EditTextPreference username;
    private EditTextPreference password;
    private CheckBoxPreference on_landscape_mode;
    private EditTextPreference textsize;

    final static String userkey="key_username";
    final static String passwordkey="key_password";
    final static String landscapekey="key_landscape_enable";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.my_settings);
        username=(EditTextPreference)findPreference(userkey);
        password=(EditTextPreference)findPreference(passwordkey);
        on_landscape_mode=(CheckBoxPreference)findPreference(landscapekey);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key)
        {
            case userkey:
                username.setSummary(sharedPreferences.getString(key,""));
                break;
            case passwordkey:
                password.setSummary(sharedPreferences.getString(key,""));
                break;
            default:
                break;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

        // Setup the initial values
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        //mListPreference.setSummary(sharedPreferences.getString(Consts.LIST_KEY, ""));
        username.setSummary(sharedPreferences.getString("username", ""));

        // Set up a listener whenever a key changes
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes
        getPreferences(MODE_PRIVATE).unregisterOnSharedPreferenceChangeListener(this);
    }
}
