package edu.berkeley.eecs.emission.cordova.connectionsettings;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class ConnectionSettingsPlugin extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getSettings")) {
            Context ctxt = cordova.getActivity();
            JSONObject settings = ConnectionSettings.getSettings(ctxt);
            callbackContext.success(settings);
            return true;
        } else if (action.equals("setSettings")){
            Context ctxt = cordova.getActivity();
            JSONObject newSettings = data.getJSONObject(0);
            ConnectionSettings.setSettings(ctxt, newSettings);
            callbackContext.success();
            return true;
        } else if (action.equals("getDefaultSettings")) {
            Context ctxt = cordova.getActivity();
            JSONObject defaultSettings = ConnectionSettings.getDefaultConfig(ctxt);
            callbackContext.success(defaultSettings);
            return true;
        } else {
            return false;
        }
    }
}

