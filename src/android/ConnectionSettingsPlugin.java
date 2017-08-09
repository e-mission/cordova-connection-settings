package edu.berkeley.eecs.emission.cordova.connectionsettings;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import edu.berkeley.eecs.emission.cordova.usercache.UserCache;
import edu.berkeley.eecs.emission.cordova.usercache.UserCacheFactory;

public class ConnectionSettingsPlugin extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getSettings")) {
            Context ctxt = cordova.getActivity();
            UserCache uc = UserCacheFactory.getUserCache(ctxt);
            JSONObject settings = uc.getLocalStorage(ConnectionSettings.CONNECTION_SETTINGS_KEY, false);
            callbackContext.success(settings);
            return true;
        } else if (action.equals("setSettings")) {
            Context ctxt = cordova.getActivity();
            JSONObject newSettings = data.getJSONObject(0);
            UserCache uc = UserCacheFactory.getUserCache(ctxt);
            uc.putLocalStorage(ConnectionSettings.CONNECTION_SETTINGS_KEY, newSettings);
            callbackContext.success();
            return true;
        } else {
            return false;
        }
    }
}

