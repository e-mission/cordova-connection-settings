package edu.berkeley.eecs.emission.cordova.settings;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;

import edu.berkeley.eecs.emission.cordova.ConnectionSettings;

public class ConnectionSettingsPlugin extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getSettings")) {
            Context ctxt = cordova.getActivity();
            JSONObject retObject = JSONObject();
            retObject.put("connectURL", ConnectionSettings.getConnectURL(ctxt));
            retObject.put("isSkipAuth", ConnectionSettings.isSkipAuth(ctxt));
            retObject.put("googleWebAppClientID", ConnectionSettings.getGoogleWebAppClientID(ctxt));
            callbackContext.success(retObject);
            return true;
        } else {
            return false;
        }
    }
}

